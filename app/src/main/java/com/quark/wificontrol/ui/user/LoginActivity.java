package com.quark.wificontrol.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jfinal.kit.EncryptionKit;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.LoginRequest;
import com.quark.api.auto.bean.LoginResponse;
import com.quark.wificontrol.AppContext;
import com.quark.wificontrol.AppParam;
import com.quark.wificontrol.R;
import com.quark.wificontrol.api.ApiHttpClient;
import com.quark.wificontrol.api.ApiResponse;
import com.quark.wificontrol.api.remote.QuarkApi;
import com.quark.wificontrol.api.remote.RSAsecurity;
import com.quark.wificontrol.base.BaseActivity;
import com.quark.wificontrol.easechat.utils.PreferenceManager;
import com.quark.wificontrol.mainview.MainActivity;
import com.quark.wificontrol.util.TLog;
import com.quark.wificontrol.util.Utils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

/**
 * Created by pan on 2016/9/9 0009.
 * >#
 * >#登录
 */
public class LoginActivity extends BaseActivity {

    @InjectView(R.id.login_bt)
    Button loginBt;
    @InjectView(R.id.forgot_pwd)
    TextView forgotPwd;
    @InjectView(R.id.register_now)
    TextView registerNow;
    @InjectView(R.id.terms_tv)
    TextView termsTv;
    @InjectView(R.id.close_eye_ibt)
    ImageButton closeEyeIbt;
    @InjectView(R.id.pwd_et)
    EditText pwdEt;
    @InjectView(R.id.user_et)
    EditText userEt;

    String telephone;
    String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        new AppParam().setSharedPreferencesy(LoginActivity.this, "token", "");
        new AppParam().setSharedPreferencesy(LoginActivity.this, "isLogin", "0");
        userEt.setText(new AppParam().getTelephone(this));
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    boolean showpssword = false;

    @OnClick({R.id.cancel, R.id.login_bt, R.id.forgot_pwd, R.id.register_now, R.id.terms_tv, R.id.close_eye_ibt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                finish();
                break;
            case R.id.login_bt:
                if (check()) {
                    loginRequest();
                }
//                startActivityByClass(MainActivity.class);
                break;
            case R.id.forgot_pwd:
                startActivityByClass(ForgetPwdActivity.class);
                break;
            case R.id.register_now:
                startActivityByClass(RegisterActivity.class);
                break;
            case R.id.terms_tv:
                Bundle bundle = new Bundle();
                bundle.putString("url", "file:///android_asset/RegisterAgreement.htm");
                startActivityByClass(TermsActivity.class, bundle);
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
        }
    }

    private boolean check() {
        telephone = userEt.getText().toString();
        pwd = pwdEt.getText().toString();
        if (Utils.isEmpty(telephone)) {
            showToast("请输入手机号");
            return false;
        }
        if (Utils.isEmpty(pwd)) {
            showToast("请输入密码");
            return false;
        }
        return true;
    }

    public void loginRequest() {
        LoginRequest rq = new LoginRequest();
        rq.setTelephone(telephone);
        rq.setPassword(new RSAsecurity().DESAndRSAEncrypt(pwd));
        showWait(true);
        QuarkApi.HttpRequestNewList(rq, mHandlerget);
    }

    private final AsyncHttpResponseHandler mHandlerget = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, LoginActivity.this, LoginResponse.class);
            if (kd != null) {
                LoginResponse info = (LoginResponse) kd;
                if (info.getStatus() == 1) {
                    showToast(info.getMessage());
                    new AppParam().setSharedPreferencesy(LoginActivity.this, "token", info.getUser().getToken());
                    new AppParam().setSharedPreferencesy(LoginActivity.this, "isLogin", "1");
                    new AppParam().setSharedPreferencesy(LoginActivity.this, "telephone", info.getUser().getTelephone() + "");
                    TLog.error("======================== " + EncryptionKit.md5Encrypt(pwd));
                    new AppParam().setSharedPreferencesy(LoginActivity.this, "password", EncryptionKit.md5Encrypt(pwd));

                    PreferenceManager.getInstance().setCurrentUserNick(info.getUser().getNickname());
                    PreferenceManager.getInstance().setCurrentUserAvatar(ApiHttpClient.loadImage + info.getUser().getImage_01());

                    Intent intnet = new Intent(LoginActivity.this, MainActivity.class);
                    intnet.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intnet);
                    finish();
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
