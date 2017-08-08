package com.quark.wificontrol.ui.user;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.GetForgetCodeRequest;
import com.quark.api.auto.bean.GetForgetCodeResponse;
import com.quark.api.auto.bean.ResetPasswordRequest;
import com.quark.api.auto.bean.ResetPasswordResponse;
import com.quark.wificontrol.AppContext;
import com.quark.wificontrol.R;
import com.quark.wificontrol.api.ApiResponse;
import com.quark.wificontrol.api.remote.QuarkApi;
import com.quark.wificontrol.api.remote.RSAsecurity;
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
 * >#找回密码
 */
public class ForgetPwdActivity extends BaseActivity {

    String telephone;
    String code;
    String pwd;
    Handler handlercode;
    @InjectView(R.id.cancel_tv)
    TextView cancelTv;
    @InjectView(R.id.user_et)
    EditText userEt;
    @InjectView(R.id.user_iv)
    ImageView userIv;
    @InjectView(R.id.code_et)
    EditText codeEt;
    @InjectView(R.id.go_code)
    Button goCode;
    @InjectView(R.id.code_ly)
    LinearLayout codeLy;
    @InjectView(R.id.pwd_et)
    EditText pwdEt;
    @InjectView(R.id.pwd_iv)
    ImageView pwdIv;
    @InjectView(R.id.close_eye_ibt)
    ImageButton closeEyeIbt;
    @InjectView(R.id.edit_bt)
    Button eidtBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editpwd);
        ButterKnife.inject(this);

        handlercode = new Handler();

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    boolean showpssword = false;

    @OnClick({R.id.go_code, R.id.close_eye_ibt, R.id.cancel_tv, R.id.edit_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.go_code:
                telephone = userEt.getText().toString();
                if (Utils.isEmpty(userEt.getText().toString())) {
                    showToast("请输入手机号");
                } else {
                    getcodeRequest();
                }
                break;
            case R.id.close_eye_ibt:
                if (!showpssword) {
                    // 设置为明文显示
                    showpssword = true;
                    pwdEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    closeEyeIbt.setBackground(ContextCompat.getDrawable(this, R.drawable.open_eye));
                } else {
                    // 设置为秘闻显示
                    showpssword = false;
                    pwdEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    closeEyeIbt.setBackground(ContextCompat.getDrawable(this, R.drawable.close_eye));
                }
                // 切换后将EditText光标置于末尾
                CharSequence charSequence = pwdEt.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
                break;
            case R.id.cancel_tv:
                finish();
                break;
            case R.id.edit_bt:

                if (check()) {
                    editRequest();
                }
                break;
        }
    }

    private boolean check() {
        telephone = userEt.getText().toString();
        code = codeEt.getText().toString();
        pwd = pwdEt.getText().toString();
        if (Utils.isEmpty(telephone)) {
            showToast("请输入手机号");
            return false;
        }
        if (Utils.isEmpty(code)) {
            showToast("请输入验证码");
            return false;
        }
        if (Utils.isEmpty(pwd)) {
            showToast("请输入密码");
            return false;
        }
        return true;
    }

    /**
     * 获取验证码接口
     *
     * @author pan
     * @time 2016/10/8 0008 下午 3:06
     */
    public void getcodeRequest() {
        GetForgetCodeRequest rq = new GetForgetCodeRequest();
        rq.setTelephone(telephone);
        showWait(true);
        QuarkApi.HttpRequestNewList(rq, mHandlerget);
        UIHelper.countdown(goCode, handlercode, this, false);
    }

    private final AsyncHttpResponseHandler mHandlerget = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, ForgetPwdActivity.this, GetForgetCodeResponse.class);
            if (kd != null) {
                GetForgetCodeResponse info = (GetForgetCodeResponse) kd;
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
            UIHelper.countdown(goCode, handlercode, ForgetPwdActivity.this, false);
            showWait(false);
        }
    };
    /**
     * 修改密码接口
     *
     * @author pan
     * @time 2016/10/8 0008 下午 3:06
     */
    public void editRequest() {
        ResetPasswordRequest rq = new ResetPasswordRequest();
        rq.setLogin_username(telephone);
        rq.setTel_code(code);
        rq.setPassword(new RSAsecurity().DESAndRSAEncrypt(pwd));
        showWait(true);
        QuarkApi.HttpRequestNewList(rq, mHandler);
    }

    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, ForgetPwdActivity.this, ResetPasswordResponse.class);
            if (kd != null) {
                ResetPasswordResponse info = (ResetPasswordResponse) kd;
                if (info.getStatus() == 1) {
                    showToast(info.getMessage());
                    startActivityByClass(LoginActivity.class);
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
