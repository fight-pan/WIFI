package com.quark.wificontrol.ui.personalCentel;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.UpdateOtherRequest;
import com.quark.api.auto.bean.UpdateOtherResponse;
import com.quark.wificontrol.AppContext;
import com.quark.wificontrol.R;
import com.quark.wificontrol.api.ApiResponse;
import com.quark.wificontrol.api.remote.QuarkApi;
import com.quark.wificontrol.base.BaseActivity;
import com.quark.wificontrol.easechat.utils.PreferenceManager;
import com.quark.wificontrol.util.Utils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

/**
 * Created by pan on 2016/8/23 0023.
 * >#
 * >#修改昵称
 */
public class EditNameActivity extends BaseActivity {

    @InjectView(R.id.sign)
    TextView sign;
    @InjectView(R.id.right)
    LinearLayout right;

    @InjectView(R.id.name_et)
    EditText nameEt;

    String name;
    @InjectView(R.id.ok_bt)
    Button okBt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editname);
        ButterKnife.inject(this);
        setTopTitle("修改姓名");
        setBackButton();

        name = (String) getValue4Intent("name");
        nameEt.setText(name);
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
    }

    @OnClick(R.id.ok_bt)
    public void onClick() {

        if (Utils.isEmpty(nameEt.getText().toString())) {
            showToast("姓名不能为空");
        } else {
            editNameRequest();
        }
    }

    public void editNameRequest() {
        UpdateOtherRequest gr = new UpdateOtherRequest();
        gr.setNickname(nameEt.getText().toString());
        showWait(true);
        QuarkApi.HttpRequest(gr, mHandlerget);
    }

    private final AsyncHttpResponseHandler mHandlerget = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, EditNameActivity.this, UpdateOtherResponse.class);
            if (kd != null) {
                UpdateOtherResponse info = (UpdateOtherResponse) kd;
                if (info.getStatus() == 1) {
                    //更新环信中本人昵称
                    PreferenceManager.getInstance().setCurrentUserNick(nameEt.getText().toString());

                    Intent in = new Intent();
                    in.putExtra("name", nameEt.getText().toString());
                    setResult(101, in);
                    showToast(info.getMessage());
                    Intent senin = new Intent("four");
                    senin.putExtra("position","1");
                    sendBroadcast(senin);
                    finish();

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
