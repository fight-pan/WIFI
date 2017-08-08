package com.quark.wificontrol.mainview;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.quark.wificontrol.AppContext;
import com.quark.wificontrol.AppParam;
import com.quark.wificontrol.R;
import com.quark.wificontrol.base.BaseFragment;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import butterknife.ButterKnife;
import butterknife.InjectView;

@SuppressLint("ResourceAsColor")
public class FragmentOne extends BaseFragment {
    View oneViewt;
    @InjectView(R.id.webView)
    WebView webView;
    @InjectView(R.id.nowifi)
    LinearLayout nowifi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        oneViewt = inflater.inflate(R.layout.fragment_one, container, false);
        ButterKnife.inject(this, oneViewt);
        registerPayReceiver();
        toNetword();
        return oneViewt;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    Handler mHandler = new Handler();

    //随机数
    int a = (int) (Math.random() * 100000);
    String mac = new AppParam().getPhone_sn(AppContext.instance);
    String networdUrl = "http://m.szmpy.com/index0.htm?f=wifiyun&m=" + a + "&appmac=" + mac;

    @SuppressLint("JavascriptInterface")
    public void toNetword() {
//        TLog.error("上网地址：" + networdUrl);
        showWait(true);
        nowifi.setVisibility(View.GONE);
        webView.setVisibility(View.VISIBLE);
        WebSettings webSettings = webView.getSettings();

        if (Build.VERSION.SDK_INT >= 19) {
            webView.getSettings().setLoadsImagesAutomatically(true);
        } else {
            webView.getSettings().setLoadsImagesAutomatically(false);
        }
        webView.getSettings().setUserAgentString("android");

        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//允许js弹出窗口

        webView.requestFocus();// 触摸焦点起作用
        webView.setHorizontalScrollBarEnabled(false);// 水平不显示
        webView.setVerticalScrollBarEnabled(false); // 垂直不显示

        webView.addJavascriptInterface(
                new Object() {

                    @JavascriptInterface
                    public void toPay(final String appId, final String partnerId, final String prepayId, final String packageValue, final String nonceStr, final String timeStamp, final String sign) {

                        mHandler.post(new Runnable() {
                            public void run() {
//			    				  System.out.println(id+"======================================================================================="+money);
                                Log.e("ew", appId + "=====================================================================================" + partnerId);
                                Toast.makeText(getActivity(), "appId=" + appId + "   partnerId=" + partnerId + "   prepayId=" + prepayId + "   packageValue=" + packageValue + "   nonceStr=" + nonceStr + "   timeStamp=" + timeStamp + "   sign=" + sign, Toast.LENGTH_LONG).show();
                                weixingPay(appId, partnerId, prepayId, packageValue, nonceStr, timeStamp, sign);
                            }
                        });
                    }
                }, "android"); //android是对象名

        webView.loadUrl(networdUrl);
//        webView.loadUrl("file:///android_asset/paytext.html");
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                showWait(false);
                if (!webView.getSettings().getLoadsImagesAutomatically()) {
                    webView.getSettings().setLoadsImagesAutomatically(true);
                }

                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                showWait(false);
                super.onReceivedError(view, errorCode, description, failingUrl);
                // 加载网页失败时处理  如：
//                view.loadDataWithBaseURL(null,
//                        "<div align=\"center\"><br><span style=\"color:#242424;display:block;padding-top:50px\">数据加载失败</span></div>",
//                        "text/html",
//                        "utf-8",
//                        null);
                nowifi.setVisibility(View.VISIBLE);
                webView.setVisibility(View.GONE);
                nowifi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toNetword();
                    }
                });
            }
        });
    }

    //======================================微信支付====================================
    public static String appid = "wx60641adb4960a751";
    public void weixingPay(String appId, String partnerId, String prepayId, String packageValue, String nonceStr, String timeStamp, String sign) {
        final IWXAPI msgApi = WXAPIFactory.createWXAPI(getActivity(), null);
        msgApi.registerApp("wx60641adb4960a751");
        PayReq req = new PayReq();
        req.appId = "wx60641adb4960a751";
        req.partnerId = partnerId;
        req.prepayId = prepayId;
        req.packageValue = packageValue;
        req.nonceStr = nonceStr;
        req.timeStamp = timeStamp;
        req.sign = sign;
        if (msgApi.isWXAppInstalled()) {
            msgApi.sendReq(req);
        } else {
            Toast.makeText(getActivity(), "尚未安装微信或微信版本过低", Toast.LENGTH_LONG).show();
        }
    }


    PayReceiver payreceiver;

    public void registerPayReceiver() {
        payreceiver = new PayReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("payReceiver");
        getActivity().registerReceiver(payreceiver, filter);
    }

    public class PayReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String option = intent.getStringExtra("option");
            if ("success".equals(option)) {
                webView.loadUrl("javascript:paySuccess('success')");
            } else if ("cancel".equals(option)) {
                showToast("用户取消");
                webView.loadUrl("javascript:paySuccess('false')");
            } else {
                String result = intent.getStringExtra("result");
                showToast(""+result);
                webView.loadUrl("javascript:paySuccess('false')");
            }
        }
    }

    @Override
    public void onDestroy() {
        try {
            if (payreceiver != null) {
                getActivity().unregisterReceiver(payreceiver);
            }
        } catch (Exception e) {
        }
        super.onDestroy();
    }

//    @OnClick(R.id.testup)
//    public void test(View b) {
//        webView.loadUrl("javascript:paySuccess('success')");
//    }
}
