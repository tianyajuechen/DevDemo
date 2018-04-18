package com.tzy.demo.activity.downloadprogress;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.tzy.demo.R;
import okhttp3.*;
import okio.*;

import java.io.*;

/**
 * Created by YANG
 * 2017/3/21 17:17
 * okhttp 下载
 */
public class OkhttpDownloadActivity extends Activity {

    @BindView(R.id.tv_pb)
    ProgressBar pb;
    @BindView(R.id.tv_percent)
    TextView tvPercent;
    @BindView(R.id.bt_download)
    Button btDownload;
    @BindView(R.id.activity_okhttp_download)
    LinearLayout activityOkhttpDownload;
    @BindView(R.id.tv_error_msg)
    TextView tvErrorMsg;
    @BindView(R.id.bt_install)
    Button btInstall;

    private static final String TAG = "download";
    private String mFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp_download);
        ButterKnife.bind(this);

        btDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                run();
            }
        });

        btInstall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //install();
            }
        });

        tvErrorMsg.setText(getExternalCacheDir().getParent()+"\n"+getExternalFilesDir("apk").getParent()+"\n"+getDiskCacheDir(this));
    }

    private void install() {
        File file = new File(getDiskCacheDir(OkhttpDownloadActivity.this), "aaa.apk");
        //file.renameTo(new File(getExternalCacheDir(), "aaa.apk"));

        if (file != null && file.length() > 0) {
            Intent install = new Intent(Intent.ACTION_VIEW);
            Uri downloadFileUri = Uri.fromFile(file);
            install.setDataAndType(downloadFileUri, "application/vnd.android.package-archive");
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(install);
        }
    }

    public void run() {
        Request request = new Request.Builder()
                .url("https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk")
                .build();

        final Progress.ProgressListener progressListener = new Progress.ProgressListener() {
            @Override
            public void update(final long bytesRead, final long contentLength, final boolean done) {
                Log.e(TAG, "bytesRead=" + bytesRead);
                Log.e(TAG, "contentLength=" + contentLength);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int percent = (int) ((100 * bytesRead) / contentLength);
                        pb.setProgress(percent);
                        tvPercent.setText(percent + " %");
                        if (done) {
                            pb.setProgress(100);
                            tvPercent.setText("100 %");
                            File file = new File(getDiskCacheDir(OkhttpDownloadActivity.this), "aaa.apk");
                            //file.renameTo(new File(getExternalCacheDir(), "aaa.apk"));

                            if (file != null && file.length() > 0) {
                                Intent install = new Intent(Intent.ACTION_VIEW);
                                Uri downloadFileUri = Uri.fromFile(file);
                                install.setDataAndType(downloadFileUri, "application/vnd.android.package-archive");
                                install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(install);
                            }
                        }
                    }
                });

            }
        };

        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response originalResponse = chain.proceed(chain.request());
                        return originalResponse.newBuilder()
                                .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                                .build();
                    }
                })
                .build();

        /*try (Response response = client.newCall(request).enqueue();) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            System.out.println(response.body().string());
        }*/

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                tvErrorMsg.setText(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    tvErrorMsg.setText("连接服务器异常");
                    return;
                }
                File file = saveFile(OkhttpDownloadActivity.this, response.body().byteStream(), "aaa.dl");
                if (!file.renameTo(new File(file.getParent(), "aaa.apk"))) {
                    Toast.makeText(OkhttpDownloadActivity.this, "文件下载失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 输入流保存到缓存目录
     * @param context
     * @param is 输入流
     * @param fileName 文件名
     * @return
     */
    private File saveFile(Context context, InputStream is, String fileName) {
        File file = null;
        try {
            file = new File(getDiskCacheDir(context), fileName);
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                fos.flush();
            }
            fos.close();
            bis.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 文件重命名 目录不变
     * @param file 文件
     * @param newName 新名称
     * @return
     */
    private boolean renameFile(File file, String newName) {
        if (file == null) return false;
        return file.renameTo(new File(file.getParent(), newName));
    }

    class ProgressResponseBody extends ResponseBody {

        private final ResponseBody responseBody;
        private final Progress.ProgressListener progressListener;
        private BufferedSource bufferedSource;

        public ProgressResponseBody(ResponseBody responseBody, Progress.ProgressListener progressListener) {
            this.responseBody = responseBody;
            this.progressListener = progressListener;
        }

        @Override
        public MediaType contentType() {
            return responseBody.contentType();
        }

        @Override
        public long contentLength() {
            return responseBody.contentLength();
        }

        @Override
        public BufferedSource source() {
            if (bufferedSource == null) {
                bufferedSource = Okio.buffer(source(responseBody.source()));
            }
            return bufferedSource;
        }

        private Source source(Source source) {
            return new ForwardingSource(source) {
                long totalBytesRead = 0L;

                @Override
                public long read(Buffer sink, long byteCount) throws IOException {
                    long bytesRead = super.read(sink, byteCount);
                    // read() returns the number of bytes read, or -1 if this source is exhausted.
                    totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                    progressListener.update(totalBytesRead, responseBody.contentLength(), bytesRead == -1);
                    return bytesRead;
                }
            };
        }
    }

    interface ProgressListener {
        void update(long bytesRead, long contentLength, boolean done);
    }

    /**
     * 获取app缓存目录
     * @param context
     * @return
     */
    public String getDiskCacheDir(Context context) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }
}
