package com.quark.wificontrol.ui.user;

import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.GetTryCodeRequest;
import com.quark.api.auto.bean.GetTryCodeResponse;
import com.quark.api.auto.bean.TryUseRequest;
import com.quark.api.auto.bean.TryUseResponse;
import com.quark.wificontrol.AppContext;
import com.quark.wificontrol.R;
import com.quark.wificontrol.api.ApiResponse;
import com.quark.wificontrol.api.remote.QuarkApi;
import com.quark.wificontrol.base.BaseActivity;
import com.quark.wificontrol.util.UIHelper;
import com.quark.wificontrol.util.Utils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

/**
 * Created by pan on 2016/9/9 0009.
 * >#
 * >游客试用
 */
public class TouristsActivity extends BaseActivity {

    @InjectView(R.id.user_et)
    EditText userEt;
    @InjectView(R.id.code_et)
    EditText codeEt;
    @InjectView(R.id.go_bt)
    Button goBt;
    @InjectView(R.id.get_code)
    TextView getCode;
    Handler handlercode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourists);
        ButterKnife.inject(this);
        handlercode = new Handler();

        getCode.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        getCode.getPaint().setAntiAlias(true);//抗锯齿
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.get_code, R.id.go_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get_code:
                if (Utils.isEmpty(userEt.getText().toString())) {
                    showToast("请输入手机号码");
                } else {
                    getCodeRequest();
                }

                break;
            case R.id.go_bt:
                if (check()) {

                    tryRequest();

                }
                break;
        }
    }

    private boolean check() {
        if (Utils.isEmpty(userEt.getText().toString())) {
            showToast("请输入手机号码");
            return false;
        }
        if (Utils.isEmpty(codeEt.getText().toString())) {
            showToast("请输入试用码");
            return false;
        }
        return true;

    }

    /**
     * 获取试用码
     *
     * @author pan
     * @time 2016/10/9 0009 下午 2:51
     */
    public void getCodeRequest() {
        GetTryCodeRequest rq = new GetTryCodeRequest();
        rq.setTelephone(userEt.getText().toString());
        showWait(true);
        QuarkApi.HttpRequestNewList(rq, mHandlerget);
        UIHelper.countdddd(getCode, handlercode, this, false);
    }

    private final AsyncHttpResponseHandler mHandlerget = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, TouristsActivity.this, GetTryCodeResponse.class);
            if (kd != null) {
                GetTryCodeResponse info = (GetTryCodeResponse) kd;
                if (info.getStatus() == 1) {

                } else {
                    showToast(info.getMessage());
                }
            }
            showWait(false);
        }

        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
            AppContext.showToast("网络出错" + arg0);
            UIHelper.countdddd(getCode, handlercode, TouristsActivity.this, false);
            showWait(false);
        }
    };

    public void tryRequest() {
        TryUseRequest rq = new TryUseRequest();
        rq.setTelephone(userEt.getText().toString());
        rq.setTel_code(codeEt.getText().toString());
        showWait(true);
        QuarkApi.HttpRequestNewList(rq, mHandleruser);
    }

    private final AsyncHttpResponseHandler mHandleruser = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, TouristsActivity.this, TryUseResponse.class);
            if (kd != null) {
                TryUseResponse info = (TryUseResponse) kd;
                if (info.getStatus() == 1) {
                    startActivityByClass(TrialActivity.class);

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
