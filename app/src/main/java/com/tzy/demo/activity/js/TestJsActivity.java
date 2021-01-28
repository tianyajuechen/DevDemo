package com.tzy.demo.activity.js;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.*;
import android.widget.Button;
import android.widget.Toast;
import com.google.gson.Gson;
import com.tzy.demo.R;

import java.util.HashMap;
import java.util.Map;

public class TestJsActivity extends Activity {
    private WebView webview;
    private Button bt;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_js);

        webview = findViewById(R.id.webview);
        bt = findViewById(R.id.bt);

        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        webview.addJavascriptInterface(new JsInterface(), "jsObj");
        webview.setWebChromeClient(new MyWebChromeClient());
        webview.loadUrl("file:///android_asset/hello.html");


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webview.loadUrl("javascript: showFromHtml()");
            }
        });
    }

    private class JsInterface {
        @JavascriptInterface
        public String HtmlcallJava() {
            Map<String, String> map = new HashMap<>();
            map.put("name", "张三");
            map.put("age", "18");
            return new Gson().toJson(map);
        }

        @JavascriptInterface
        public String HtmlcallJava2(final String param) {
            return "Html call Java : " + param;
        }

        @JavascriptInterface
        public void JavacallHtml() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    webview.loadUrl("javascript: showFromHtml()");
                }
            });
        }

        @JavascriptInterface
        public void JavacallHtml2() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    webview.loadUrl("javascript: showFromHtml2('IT-homer blog')");
                }
            });
        }

    }

    public void JavacallHtml() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                webview.loadUrl("javascript: showFromHtml()");
                Toast.makeText(TestJsActivity.this, "clickBtn", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }
}
