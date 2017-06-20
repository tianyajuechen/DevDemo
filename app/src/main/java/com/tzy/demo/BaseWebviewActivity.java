package com.tzy.demo;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by YANG
 * 2016/10/21 13:27
 * webview基类
 */

public abstract class BaseWebviewActivity extends Activity {
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWebView = getWebView();
        if (null == mWebView)
            throw new NullPointerException("Webview can not be null !");
        mWebView.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        mWebView.setWebChromeClient(getWebChromeClient());
        mWebView.setWebViewClient(getWebViewClient());
        // 修改User-Agent
        mWebView.getSettings().setUserAgentString(mWebView.getSettings().getUserAgentString() + " Source/9niu_app_android");
        //MyLog.e("User-Agent", mWebView.getSettings().getUserAgentString());
    }

    @Override
    public void onPause() {
        mWebView.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        mWebView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        if (mWebView != null) {
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }

    private WebChromeClient getWebChromeClient() {
        return MyWebChromeClient() != null ? MyWebChromeClient() : new WebChromeClient();
    }

    private WebViewClient getWebViewClient() {
        return MyWebViewClient() !=null ? MyWebViewClient() : new WebViewClient();
    }

    protected abstract WebChromeClient MyWebChromeClient();

    protected abstract WebViewClient MyWebViewClient();

    protected abstract WebView getWebView();

    public void back() {
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
