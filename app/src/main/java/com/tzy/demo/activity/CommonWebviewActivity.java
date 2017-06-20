package com.tzy.demo.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.*;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.tzy.demo.BaseWebviewActivity;
import com.tzy.demo.R;
import com.tzy.demo.finals.Constants;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by YANG
 * 2016/6/28 15:17
 */
public class CommonWebviewActivity extends BaseWebviewActivity {

    @Bind(R.id.pb)
    ProgressBar pb;
    @Bind(R.id.webview)
    WebView webview;
    @Bind(R.id.bt)
    Button bt;

    private String mUrl;
    private static final String TAG = "CommonWebviewActivity";
    private ValueCallback mUploadMessage;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private final static int FILECHOOSER_RESULTCODE = 1;
    private final static int REQUEST_CODE_FREE = 2;
    public final static int RESULT_CODE_SUCCESS = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.common_webview_layout);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);

        mUrl = getIntent().getStringExtra(Constants.URL_PATH);
        if (TextUtils.isEmpty(mUrl)) {
            //MyLog.e(TAG, "you must put url to this class");
            finish();
            return;
        }

        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setLoadWithOverviewMode(true);

        webview.getSettings().setAppCacheEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setDatabaseEnabled(true);
        webview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webview.loadUrl(mUrl);
        Log.e("webview_url", mUrl);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String insert = "document.getElementsByName('searchword')[0].value = \"网络公司\";";
                final String click = "document.getElementById('btn_query').click();";

                webview.evaluateJavascript(insert, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                        Toast.makeText(CommonWebviewActivity.this, s, 1000).show();
                    }
                });

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        webview.evaluateJavascript(click, new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String s) {
                                Toast.makeText(CommonWebviewActivity.this, s, 1000).show();
                            }
                        });
                    }
                }, 1000);
            }
        });
    }

    private class MyWebViewClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            pb.setProgress(newProgress);
            super.onProgressChanged(view, newProgress);
        }

        // For Android 3.0+
        public void openFileChooser(ValueCallback uploadMsg) {

            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            startActivityForResult(Intent.createChooser(i, "File Chooser"),
                    FILECHOOSER_RESULTCODE);

        }

        // For Android 3.0+
        public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("*/*");
            startActivityForResult(
                    Intent.createChooser(i, "File Browser"),
                    FILECHOOSER_RESULTCODE);
        }

        //For Android 4.1
        public void openFileChooser(ValueCallback uploadMsg, String acceptType, String capture) {
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            startActivityForResult(Intent.createChooser(i, "File Chooser"),
                    FILECHOOSER_RESULTCODE);

        }

        // For Android 5.0+
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            mUploadCallbackAboveL = filePathCallback;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("*/*");
            startActivityForResult(
                    Intent.createChooser(i, "File Browser"),
                    FILECHOOSER_RESULTCODE);
            return true;
        }
    }

    private class RedirectClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            pb.setVisibility(View.VISIBLE);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            pb.setVisibility(View.GONE);
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }
    }

    @Override
    protected WebChromeClient MyWebChromeClient() {
        return new MyWebViewClient();
    }

    @Override
    protected WebViewClient MyWebViewClient() {
        return new RedirectClient();
    }

    @Override
    protected WebView getWebView() {
        return webview;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage && null == mUploadCallbackAboveL) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (mUploadCallbackAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
        if (requestCode != FILECHOOSER_RESULTCODE
                || mUploadCallbackAboveL == null) {
            return;
        }

        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {

            } else {
                String dataString = data.getDataString();
                ClipData clipData = data.getClipData();

                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }

                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        mUploadCallbackAboveL.onReceiveValue(results);
        mUploadCallbackAboveL = null;
        return;
    }

    //flipscreen not loading again
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public String stringByEvaluatingJavaScriptFromString(String script) {
        try {
            //由webview取到webviewcore
            Field field_webviewcore = WebView.class.getDeclaredField("mWebViewCore");
            field_webviewcore.setAccessible(true);
            Object obj_webviewcore = field_webviewcore.get(this);
            //由webviewcore取到BrowserFrame
            Field field_BrowserFrame = obj_webviewcore.getClass().getDeclaredField("mBrowserFrame");
            field_BrowserFrame.setAccessible(true);
            Object obj_frame = field_BrowserFrame.get(obj_webviewcore);
            //获取BrowserFrame对象的stringByEvaluatingJavaScriptFromString方法
            Method method_stringByEvaluatingJavaScriptFromString = obj_frame.getClass().getMethod("stringByEvaluatingJavaScriptFromString", String.class);
            //执行stringByEvaluatingJavaScriptFromString方法
            Object obj_value = method_stringByEvaluatingJavaScriptFromString.invoke(obj_frame, script);
            //返回执行结果
            return String.valueOf(obj_value);
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}