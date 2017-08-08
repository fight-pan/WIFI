package com.quark.wificontrol.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.quark.wificontrol.R;
import com.quark.wificontrol.api.ApiHttpClient;
import com.quark.wificontrol.base.BaseFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;
/**
 * 便民信息详情 后台
 * @author pan
 * @time 2016/10/19 0019 下午 5:29
 */

@SuppressLint("ResourceAsColor")
public class PeopleContentFragment extends BaseFragment {
    View oneViewt;
    //    WebView webView;
    WebSettings webSettings;
    String ds;

    String people_services_id;
    @InjectView(R.id.web)
    WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        oneViewt = inflater.inflate(R.layout.fragment_content, container, false);
        ButterKnife.inject(this, oneViewt);

//        webView = (WebView) getActivity().findViewById(R.id.web);
        webSettings = webView.getSettings();


        people_services_id = getArguments().getString("people_services_id");

        String url = ApiHttpClient.HOSTURL + "/app/PeopleServicess/peopleServicesDetailH5?people_services_id="+people_services_id;
        showWebInfo(url);
//        productInfoDetailH5Request();
        return oneViewt;
    }



    public void showWebInfo(String url){
        webView.getSettings().setJavaScriptEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);//自动加载图片
        webView.loadUrl(url);
        WebSettings webSettings = webView.getSettings();
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}





