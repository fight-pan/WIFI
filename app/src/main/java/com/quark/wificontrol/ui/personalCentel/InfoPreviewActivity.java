package com.quark.wificontrol.ui.personalCentel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.BaseInfoRequest;
import com.quark.api.auto.bean.InfoResponse;
import com.quark.wificontrol.AppContext;
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
 * Created by pan on 2016/9/24 0024.
 * >#
 * >#审核中 信息预览
 */
public class InfoPreviewActivity extends BaseActivity {


    @InjectView(R.id.name_et)
    EditText nameEt;
    @InjectView(R.id.phone_et)
    EditText phoneEt;
    @InjectView(R.id.company_name)
    EditText companyName;
    @InjectView(R.id.cpy_name_ly)
    LinearLayout cpyNameLy;
    @InjectView(R.id.company_address)
    EditText companyAddress;
    @InjectView(R.id.cpy_ads_ly)
    LinearLayout cpyAdsLy;
    @InjectView(R.id.family_address)
    EditText familyAddress;
    @InjectView(R.id.family_ly)
    LinearLayout familyLy;
    @InjectView(R.id.id_iv)
    ImageView idIv;
    @InjectView(R.id.business_tv)
    TextView businessTv;
    @InjectView(R.id.business_iv)
    ImageView businessIv;
    @InjectView(R.id.health_tv)
    TextView healthTv;
    @InjectView(R.id.health_iv)
    ImageView healthIv;
    @InjectView(R.id.submit_bt)
    Button submitBt;

    String type;
    @InjectView(R.id.one_v)
    View oneV;
    @InjectView(R.id.two_v)
    View twoV;
    @InjectView(R.id.three_v)
    View threeV;
    @InjectView(R.id.left)
    LinearLayout left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infoperview);
        ButterKnife.inject(this);
        setTopTitle("信息预览");
//        setBackButton();


        type = (String) getValue4Intent("type");

        if (type.equals("1")) {

            familyLy.setVisibility(View.GONE);
            threeV.setVisibility(View.GONE);
        } else if (type.equals("2")) {
            cpyNameLy.setVisibility(View.GONE);
            cpyAdsLy.setVisibility(View.GONE);
            familyLy.setVisibility(View.VISIBLE);
            businessTv.setVisibility(View.GONE);
            businessIv.setVisibility(View.GONE);
            healthIv.setVisibility(View.GONE);
            healthTv.setVisibility(View.GONE);
            oneV.setVisibility(View.GONE);
            twoV.setVisibility(View.GONE);
        }


        infoRequest();

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }


    public void infoRequest() {
        BaseInfoRequest rq = new BaseInfoRequest();
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandlerget);
    }

    private final AsyncHttpResponseHandler mHandlerget = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, InfoPreviewActivity.this, InfoResponse.class);
            if (kd != null) {
                InfoResponse info = (InfoResponse) kd;
                if (info.getStatus() == 1) {
                    nameEt.setText(info.getBaseInfoResult().getUserInfo().getUserAuditData().getReal_name());
                    phoneEt.setText(info.getBaseInfoResult().getUserInfo().getTelephone());
                    companyName.setText(info.getBaseInfoResult().getUserInfo().getUserAuditData().getCompany_name());
                    companyAddress.setText(info.getBaseInfoResult().getUserInfo().getUserAuditData().getProvince() +
                            info.getBaseInfoResult().getUserInfo().getUserAuditData().getCity() + info.getBaseInfoResult().getUserInfo().getUserAuditData().getArea()
                            + info.getBaseInfoResult().getUserInfo().getUserAuditData().getShort_area());
                    familyAddress.setText(info.getBaseInfoResult().getUserInfo().getUserAuditData().getProvince() +
                            info.getBaseInfoResult().getUserInfo().getUserAuditData().getCity() + info.getBaseInfoResult().getUserInfo().getUserAuditData().getArea()
                            + info.getBaseInfoResult().getUserInfo().getUserAuditData().getShort_area());
                    ApiHttpClient.loadImage(info.getBaseInfoResult().getUserInfo().getUserAuditData().getIdentity_card(), idIv);
                    ApiHttpClient.loadImage(info.getBaseInfoResult().getUserInfo().getUserAuditData().getBusiness_license(), businessIv);
                    ApiHttpClient.loadImage(info.getBaseInfoResult().getUserInfo().getUserAuditData().getHygiene_license(), healthIv);

                    if (info.getBaseInfoResult().getUserInfo().getUser_level() == 1) {
                        submitBt.setText("审核中");

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

    @OnClick(R.id.left)
    public void onClick() {
        Intent intent = new Intent(InfoPreviewActivity.this, MainActivity.class);
        intent.putExtra("person", "a");
        startActivity(intent);
        finish();
    }
}
