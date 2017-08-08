package com.quark.wificontrol.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.AdRequest;
import com.quark.api.auto.bean.AdResponse;
import com.quark.wificontrol.AppContext;
import com.quark.wificontrol.AppParam;
import com.quark.wificontrol.R;
import com.quark.wificontrol.api.ApiHttpClient;
import com.quark.wificontrol.api.ApiResponse;
import com.quark.wificontrol.api.remote.QuarkApi;
import com.quark.wificontrol.base.BaseActivity;
import com.quark.wificontrol.mainview.MainActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

/**
 * Created by pan on 2016/9/9 0009.
 * >#
 * >#第一次进入app
 */
public class PortalActivity extends BaseActivity {

    @InjectView(R.id.login_bt)
    Button loginBt;
    @InjectView(R.id.trial_tv)
    TextView trialTv;
    @InjectView(R.id.ad_iv)
    ImageView adIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portal);
        ButterKnife.inject(this);

        adRequest();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.login_bt, R.id.trial_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_bt:
                startActivityByClass(LoginActivity.class);
                break;
            case R.id.trial_tv:
                startActivityByClass(TouristsActivity.class);
                break;
        }
    }


    /**
     * 广告接口
     *
     * @author pan
     * @time 2016/10/8 0008 下午 2:54
     */
    public void adRequest() {
        AdRequest rq = new AdRequest();
        showWait(true);
        QuarkApi.HttpRequestNewList(rq, mHandlerget);
    }

    AdResponse info;
    private final AsyncHttpResponseHandler mHandlerget = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, PortalActivity.this, AdResponse.class);
            if (kd != null) {
                info = (AdResponse) kd;
                if (info.getStatus() == 1) {
                    ApiHttpClient.loadImage(info.getAd().getImage_01(), adIv);

                    if (new AppParam().isLogin(PortalActivity.this)) {
                        Intent intent = new Intent(PortalActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(PortalActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                } else {
                    showToast(info.getMessage());
                }
            }
            showWait(false);
        }

        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
            AppContext.showToast("网络出错" + arg0);
            showWait(false);
        }
    };
}
