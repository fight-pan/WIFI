package com.quark.wificontrol.ui.personalCentel;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.GetSetCodeRequest;
import com.quark.api.auto.bean.GetSetCodeResponse;
import com.quark.api.auto.bean.UploadAuditDataRequest;
import com.quark.api.auto.bean.UploadAuditDataResponse;
import com.quark.wificontrol.AppContext;
import com.quark.wificontrol.AppParam;
import com.quark.wificontrol.R;
import com.quark.wificontrol.api.ApiResponse;
import com.quark.wificontrol.api.remote.QuarkApi;
import com.quark.wificontrol.base.BaseActivity;
import com.quark.wificontrol.ui.widget.WheelChooseDialog;
import com.quark.wificontrol.util.TLog;
import com.quark.wificontrol.util.UIHelper;
import com.quark.wificontrol.util.Utils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

/**
 * Created by pan on 2016/9/24 0024.
 * >#
 * >#完善信息
 */
public class PerfectInformationActivity extends BaseActivity {


    @InjectView(R.id.name_et)
    EditText nameEt;
    @InjectView(R.id.phone_et)
    EditText phoneEt;
    @InjectView(R.id.get_code)
    Button getCode;
    @InjectView(R.id.code_et)
    EditText codeEt;
    @InjectView(R.id.company_name)
    EditText companyName;
    @InjectView(R.id.company_address)
    EditText companyAddress;
    @InjectView(R.id.submit_bt)
    Button submitBt;
    Handler handlercode;

    @InjectView(R.id.family_et)
    EditText familyEt;
    @InjectView(R.id.cpy_name_ly)
    LinearLayout cpyNameLy;
    @InjectView(R.id.cpy_ads_ly)
    LinearLayout cpyAdsLy;
    @InjectView(R.id.family_ly)
    LinearLayout familyLy;
    @InjectView(R.id.short_area_et)
    EditText shortAreaEt;


    String type;//1-商家,2-创业者
    String province;//省
    String city;//市
    String area;//区
    String short_area;//街道小区简称
    @InjectView(R.id.one_v)
    View oneV;

    String idName;//身份证正面
    String businessName;//营业执照
    String healthName;//卫生许可证

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfectinformation);
        ButterKnife.inject(this);
        setTopTitle("完善信息");
        setBackButton();

        handlercode = new Handler();
        type = (String) getValue4Intent("type");
        phoneEt.setText(new AppParam().getTelephone(this));

        if (type.equals("1")) {
            cpyNameLy.setVisibility(View.VISIBLE);
            cpyAdsLy.setVisibility(View.VISIBLE);
            familyLy.setVisibility(View.GONE);
            idName = (String) getValue4Intent("idN");
            businessName = (String) getValue4Intent("businessN");
            healthName = (String) getValue4Intent("healthN");
        } else if (type.equals("2")) {
            oneV.setVisibility(View.GONE);
            cpyNameLy.setVisibility(View.GONE);
            cpyAdsLy.setVisibility(View.GONE);
            familyLy.setVisibility(View.VISIBLE);
            idName = (String) getValue4Intent("idN");
        }
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.get_code, R.id.company_address, R.id.family_et, R.id.submit_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get_code:
                getcodeRequest();

                break;
            case R.id.company_address:
                WheelChooseDialog ds1 = new WheelChooseDialog();
                ds1.showSheetPic(this, handler);
                break;
            case R.id.family_et:
                WheelChooseDialog ds = new WheelChooseDialog();
                ds.showSheetPic(this, handler);
                break;

            case R.id.submit_bt:

                if (check()) {
                    uploadAuditDataRequest();
                }

                break;
        }
    }

    Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 201:
                    String address = msg.obj + "";
                    String addStr[] = address.split(",");
                    province = addStr[0];
                    city = addStr[1];
                    area = addStr[2];

                    if (type.equals("1")) {
                        companyAddress.setText(province + " " + city + " " + area);
                    } else if (type.equals("2")) {
                        familyEt.setText(province + " " + city + " " + area);
                    }
                    TLog.error("sheng" + province + "shi" + city + "qu" + area);
                    break;

                default:
                    break;
            }
        }
    };

    private boolean check() {

        if (Utils.isEmpty(nameEt.getText().toString())) {
            showToast("请输入真实姓名");
            return false;
        }
        if (Utils.isEmpty(phoneEt.getText().toString())) {
            showToast("请输入手机号码");
            return false;
        }
        if (Utils.isEmpty(codeEt.getText().toString())) {
            showToast("请输入验证码");
            return false;
        }

        if (Utils.isEmpty(shortAreaEt.getText().toString())) {
            showToast("请输入详细地址");
            return false;
        }

        if (type.equals("1")) {
            if (Utils.isEmpty(companyName.getText().toString())) {
                showToast("请输入公司姓名");
                return false;
            }
            if (Utils.isEmpty(companyAddress.getText().toString())) {
                showToast("请输入公司地址");
                return false;
            }
        } else if (type.equals("2")) {
            if (Utils.isEmpty(familyEt.getText().toString())) {
                showToast("请输入家庭地址");
                return false;
            }

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
        GetSetCodeRequest rq = new GetSetCodeRequest();
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandlerget);
        UIHelper.countdown(getCode, handlercode, this, false);
    }

    private final AsyncHttpResponseHandler mHandlerget = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, PerfectInformationActivity.this, GetSetCodeResponse.class);
            if (kd != null) {
                GetSetCodeResponse info = (GetSetCodeResponse) kd;
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
            UIHelper.countdown(getCode, handlercode, PerfectInformationActivity.this, false);
            showWait(false);
        }
    };


    public void uploadAuditDataRequest() {
        UploadAuditDataRequest rq = new UploadAuditDataRequest();
        rq.setType(type);
        rq.setCity(city);
        rq.setTel_code(codeEt.getText().toString());
        rq.setTelephone(phoneEt.getText().toString());
        rq.setCompany_name(companyName.getText().toString());
        rq.setProvince(province);
        rq.setArea(area);
        rq.setReal_name(nameEt.getText().toString());
        rq.setIdentity_card(idName);
        rq.setHygiene_license(healthName);
        rq.setBusiness_license(businessName);
        rq.setShort_area(shortAreaEt.getText().toString());
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandlerupload);
    }

    private final AsyncHttpResponseHandler mHandlerupload = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, PerfectInformationActivity.this, UploadAuditDataResponse.class);
            if (kd != null) {
                UploadAuditDataResponse info = (UploadAuditDataResponse) kd;
                if (info.getStatus() == 1) {
                    Bundle bundle = new Bundle();
                    bundle.putString("type", type);
                    new AppParam().setSharedPreferencesy(PerfectInformationActivity.this, "type", type);
                    startActivityByClass(InfoPreviewActivity.class, bundle);
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
