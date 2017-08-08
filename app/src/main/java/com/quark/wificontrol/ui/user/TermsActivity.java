package com.quark.wificontrol.ui.user;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.quark.wificontrol.R;
import com.quark.wificontrol.base.BaseActivity;
import com.quark.wificontrol.util.Utils;

/**
 * Created by pan on 2016/9/9 0009.
 * >#
 * >#使用条款
 */
public class TermsActivity extends BaseActivity {

    WebView webView;
    WebSettings webSettings;
    String type = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        setTopTitle("使用条款");
        String from = (String)getValue4Intent("from");
        if (!Utils.isEmpty(from)&&from.equals("aboutme")){
            setTopTitle("关于我们");
        }
        setBackButton();
        webView = (WebView) findViewById(R.id.web);
        webSettings = webView.getSettings();
        String url = (String)getValue4Intent("url");
        showHtml(url);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }


    public void showHtml(String url) {
//        String content="";
        webView.getSettings().setDefaultTextEncodingName("utf-8");// 避免中文乱码

        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        webSettings = webView.getSettings();

        webSettings.setJavaScriptEnabled(true);

        webSettings.setNeedInitialFocus(false);

        webSettings.setSupportZoom(true);

        webSettings.setLoadWithOverviewMode(true);//适应屏幕

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        webSettings.setLoadsImagesAutomatically(true);//自动加载图片
        webView.loadUrl(url);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (!webView.getSettings().getLoadsImagesAutomatically()) {
                    webView.getSettings().setLoadsImagesAutomatically(true);
                }
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                // 加载网页失败时处理  如：
                view.loadDataWithBaseURL(null,
                        "<div align=\"center\"><br><span style=\"color:#242424;display:block;padding-top:50px\">数据加载失败</span></div>",
                        "text/html",
                        "utf-8",
                        null);
            }
        });
    }
}
