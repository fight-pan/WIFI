package com.quark.wificontrol.ui.personalCentel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.quark.wificontrol.R;
import com.quark.wificontrol.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by pan on 2016/9/24 0024.
 * >#
 * >#一键开店
 */
public class OpenShopActivity extends BaseActivity {


    @InjectView(R.id.company_bt)
    Button companyBt;
    @InjectView(R.id.personal_bt)
    Button personalBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_openshop);
        ButterKnife.inject(this);
        setTopTitle("选择角色");
        setBackButton();

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.company_bt, R.id.personal_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.company_bt:
                Intent in = new Intent(this,UploadDataActivity.class);
                in.putExtra("type","1");
                startActivity(in);
                break;
            case R.id.personal_bt:
                Intent in2 = new Intent(this,UploadDataActivity.class);
                in2.putExtra("type","2");
                startActivity(in2);
                break;
        }
    }
}
