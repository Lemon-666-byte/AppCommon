package com.ui.main.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.base.BaseActivity;
import com.common.R;
import com.constant.Config;
import com.utils.LogUtils;

import butterknife.BindView;


/**
 * WebView
 */
public class BaseWebViewActivity extends BaseActivity {

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.webViewProgressBar)
    ProgressBar webViewProgressBar;

    private String url;


    @Override
    public void setData(Bundle bundle) {
        if (bundle != null) {
            url = bundle.getString(Config.Extras.url, "");
        }
        settingWebView();
        webView.loadUrl(url);
    }

    @Override
    public void addListeners() {

    }

    @SuppressLint("SetJavaScriptEnabled")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void settingWebView() {
        // 设置webview
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setDatabaseEnabled(true);
        // 启用本地存储
        settings.setGeolocationEnabled(true);
        settings.setDomStorageEnabled(true);
        // 支持缩放
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        // 渲染优先级
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);

        settings.setDisplayZoomControls(false);
        // 自适应屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        // 触摸焦点起作用，如果不设置，则在点击网页文本输入框时，不能弹出软键盘及不响应其他的一些事件
        webView.requestFocus();
        webView.clearCache(true);
        webView.setWebViewClient(new MyWebViewClient());
        // 加载网络页面
        MyWebChromeClient myWebChromeClient = new MyWebChromeClient();
        webView.setWebChromeClient(myWebChromeClient);
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("tel:")) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                // 去调用那些可以处理拨号行为的Activity
                startActivity(intent);
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

    }

    private class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            LogUtils.e("onReceivedTitle--->" + title);
            mCommonTitle.setTitle(title);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                if (webViewProgressBar != null) {
                    webViewProgressBar.setVisibility(View.GONE);
                }
            } else {
                if (webViewProgressBar != null) {
                    webViewProgressBar.setVisibility(View.VISIBLE);
                    webViewProgressBar.setProgress(newProgress);
                }
            }
        }

    }

    @Override
    public void onBackPressedSupport() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressedSupport();
        }

    }


    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_web;
    }

    @Override
    protected void initInjector() {

    }
    

    @Override
    protected void onDestroy() {
        // 解决 播放音乐或者视频时 退出后音乐视频没有停止的原因
        if (webView != null) {
            webView.stopLoading();
            webView.loadUrl("");
            webView.reload();
            webView.getSettings().setBuiltInZoomControls(true);
            webView.setVisibility(View.GONE);
            if (webView != null) {
                webView.destroy();
                webView = null;
            }
        }
        super.onDestroy();
    }
}