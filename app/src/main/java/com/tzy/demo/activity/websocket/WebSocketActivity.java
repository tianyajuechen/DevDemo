package com.tzy.demo.activity.websocket;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.tzy.demo.R;
import com.tzy.demo.application.MyApp;
import com.tzy.demo.bean.MsgData;
import com.tzy.demo.bean.RemoteAddressResp;
import com.tzy.demo.bean.SendMsg;
import com.tzy.demo.databinding.ActivityWebSocketBinding;
import com.tzy.demo.utils.AppUtil;
import com.tzy.demo.utils.DateUtils;
import okhttp3.*;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okio.ByteString;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WebSocketActivity extends AppCompatActivity {

    private ActivityWebSocketBinding mBinding;
    private List<String> mClientMsg, mServerMsg = new ArrayList<>();
    private MsgAdapter mClientAdapter, mServerAdapter;
    private WebSocket mWebSocket;
    private WebSocket mWebServerSocket;
    private MockWebServer mMockWebServer;
    private RemoteAddressResp mRemoteInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_web_socket);

        mClientAdapter = new MsgAdapter(null);
        mBinding.rvClinet.setAdapter(mClientAdapter);
        mBinding.rvClinet.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mServerAdapter = new MsgAdapter(null);
        mBinding.rvServer.setAdapter(mServerAdapter);
        mBinding.rvServer.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mBinding.etClientInput.setText(AppUtil.getIPAddress(this));
        initListener();
    }

    private void initListener() {
        mBinding.btConnect.setOnClickListener(v -> {
            connectServer(mBinding.etAddress.getText().toString());
        });

        mBinding.btDisconnect.setOnClickListener(v -> {
            if (mWebSocket != null)
                mWebSocket.close(-1, "客户端主动关闭");
        });

        mBinding.btServerConnect.setOnClickListener(v -> initWebServer());

        mBinding.btServerDisconnect.setOnClickListener(v -> {
            if (mMockWebServer != null) {
                try {
                    mMockWebServer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        mBinding.btClientSend.setOnClickListener(v -> {
            String msg = mBinding.etClientInput.getText().toString();
            if (TextUtils.isEmpty(msg)) return;
            if (mWebSocket != null) {
                if (mRemoteInfo == null) return;
                SendMsg sendMsg = new SendMsg();
                MsgData data = new MsgData();
                data.setFrom(mRemoteInfo.getResult().getVisitor_id());
                data.setContent(msg);
                sendMsg.setData(data);
                mWebSocket.send(new Gson().toJson(sendMsg, SendMsg.class));
            }
        });

        mBinding.btServerSend.setOnClickListener(v -> {
            String msg = mBinding.etServerInput.getText().toString();
            if (TextUtils.isEmpty(msg)) return;
            if (mWebServerSocket != null) {
                mWebServerSocket.send(msg);
            }
        });

        mBinding.btGetAddress.setOnClickListener(v -> {
            getRemoteAddress();
        });
    }

    private void connectServer(String url) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.pingInterval(60, TimeUnit.SECONDS);
        OkHttpClient client = builder.build();
        Request request = new Request.Builder().url(url).build();
        mWebSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
                addClientItem(response.toString());
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                addClientItem(text);
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                super.onMessage(webSocket, bytes);
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
                addClientItem("准备关闭连接... code=" + code + " reason=" + reason);
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
                addClientItem("连接关闭 code=" + code + " reason=" + reason);
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                super.onFailure(webSocket, t, response);
                addClientItem("连接失败 " + t.getMessage());
            }
        });
    }

    private void addClientItem(String msg) {
        runOnUiThread(() -> mClientAdapter.addData(DateUtils.getCurrDate() + "：" + msg));
    }

    private void initWebServer() {
        if (mMockWebServer == null) {
            mMockWebServer = new MockWebServer();
        }
        mMockWebServer.enqueue(new MockResponse().withWebSocketUpgrade(new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
                addServerItem(response.toString());
                mWebServerSocket = webSocket;
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                addServerItem(text);
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                super.onMessage(webSocket, bytes);
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
                addServerItem("准备关闭连接... code=" + code + " reason=" + reason);
                mWebServerSocket = null;
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
                addServerItem("连接关闭 code=" + code + " reason=" + reason);
                mWebServerSocket = null;
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                super.onFailure(webSocket, t, response);
                addServerItem("连接失败 " + t.getMessage());
                mWebServerSocket = null;
            }
        }));

        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    mMockWebServer.start(9999);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String url = "ws://" + mMockWebServer.getHostName() + ":" + mMockWebServer.getPort();
                runOnUiThread(() -> {
                    mBinding.etAddress.setText(url);
                    mBinding.etServerAddress.setText(url);
                });
            }
        }.start();
    }

    private void addServerItem(String msg) {
        runOnUiThread(() -> mServerAdapter.addData(DateUtils.getCurrDate() + "：" + msg));
    }

    private void getRemoteAddress() {
        retrofit2.Call<RemoteAddressResp> call = MyApp.getInstance().mApiService.getSocketAddress("LI64AWKpOgJCvNZ1TkmKkSR4V2Vy2PgpCn6QFcKNgcX1PXV2VW7TsAa6NE12GGiAoPUw07pBTswbaZKkcYFubxAvHJrZsGAToDZyhUfCyXVi68oIrmCt1Q==", "goodnight");
        call.enqueue(new retrofit2.Callback<RemoteAddressResp>() {
            @Override
            public void onResponse(retrofit2.Call<RemoteAddressResp> call, retrofit2.Response<RemoteAddressResp> response) {
                if (response.isSuccessful()) {
                    RemoteAddressResp remote = response.body();
                    mRemoteInfo = remote;
                    if (remote == null || remote.getCode() != 200) return;
                    mBinding.etAddress.setText(remote.getSocket());
                }
            }

            @Override
            public void onFailure(retrofit2.Call<RemoteAddressResp> call, Throwable t) {

            }
        });
    }
}