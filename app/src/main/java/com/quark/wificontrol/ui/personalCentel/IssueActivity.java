package com.quark.wificontrol.ui.personalCentel;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.UploadPeopleServerRequest;
import com.quark.api.auto.bean.UploadPeopleServerResponse;
import com.quark.wificontrol.AppContext;
import com.quark.wificontrol.AppParam;
import com.quark.wificontrol.R;
import com.quark.wificontrol.api.ApiHttpClient;
import com.quark.wificontrol.api.ApiResponse;
import com.quark.wificontrol.api.remote.QuarkApi;
import com.quark.wificontrol.base.BaseActivity;
import com.quark.wificontrol.ui.widget.WheelChooseDialog;
import com.quark.wificontrol.util.TLog;
import com.quark.wificontrol.util.Utils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

//import com.jph.takephoto.model.TImage;

/**
 * Created by pan on 2016/9/24 0024.
 * >#
 * >#中学一对一 发布
 */
public class IssueActivity extends BaseActivity{

    @InjectView(R.id.photo_iv)
    ImageView photoIv;

    String people_services_classify_02_id;
    String images_01;//封面#号链接
    String title;//标题
    String province;//省
    String city;//市
    String area;//区
    String short_area;//街道小区简称
    String latitude;//维度
    String longitude;//经度
    String content;//内容
    String headTitle; //头部标题

    @InjectView(R.id.address_et)
    EditText addressEt;
    @InjectView(R.id.short_area_et)
    EditText shortAreaEt;
    @InjectView(R.id.content_et)
    EditText contentEt;
    @InjectView(R.id.title_et)
    EditText titleEt;

    String tag;
    @InjectView(R.id.title_tv)
    TextView titleTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);
        ButterKnife.inject(this);
        setBackButton();

        people_services_classify_02_id = (String) getValue4Intent("people_services_classify_02_id");
        headTitle = (String) getValue4Intent("head");
        setTopTitle(headTitle);

        tag = (String) getValue4Intent("tag");
        if (Utils.isEmpty(tag)) {
            titleTv.setText("小区");
            titleEt.setHint("小区名称");
        }
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.photo_iv, R.id.ok_bt, R.id.address_et})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.photo_iv:

                Intent intent = new Intent(this, UploadPhotoActivity.class);
                Bundle bundle = new Bundle();
//                bundle.putSerializable("img", imgL);
                intent.putExtras(bundle);
                startActivityForResult(intent, 107);
                break;
            case R.id.address_et:
                WheelChooseDialog ds1 = new WheelChooseDialog();
                ds1.showSheetPic(this, handler);

                break;
            case R.id.ok_bt:

                if (check()) {
                    uploadPeopleServerRequest();
                }

                break;
        }
    }

    private boolean check() {
        title = titleEt.getText().toString();
        short_area = shortAreaEt.getText().toString();
        content = contentEt.getText().toString();

        if (Utils.isEmpty(title)) {
            showToast("请输入标题");
            return false;
        }
        if (Utils.isEmpty(title)) {
            showToast("请输入详细地址");
            return false;
        }
        if (Utils.isEmpty(title)) {
            showToast("请输入内容");
            return false;
        }

        return true;
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
                    addressEt.setText(province + " " + city + " " + area);
                    TLog.error("sheng" + province + "shi" + city + "qu" + area);
                    break;

                default:
                    break;
            }
        }
    };


    public void uploadPeopleServerRequest() {
        UploadPeopleServerRequest rq = new UploadPeopleServerRequest();
//        String people_services_classify_02_id;
//        String images_01;//封面#号链接
//        String title;//标题
//        String province;//省
//        String city;//市
//        String area;//区
//        String short_area;//街道小区简称
//        String latitude;//维度
//        String longitude;//经度
//        String content;//内容
        rq.setPeople_services_classify_02_id(people_services_classify_02_id);
        rq.setImages_01(images_01);
        rq.setTitle(title);
        rq.setProvince(province);
        rq.setCity(city);
        rq.setArea(area);
        rq.setLongitude(new AppParam().getLng(this));
        rq.setLatitude(new AppParam().getLat(this));
        rq.setShort_area(short_area);
        rq.setContent(content);
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandlerget);
    }

    private final AsyncHttpResponseHandler mHandlerget = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, IssueActivity.this, UploadPeopleServerResponse.class);
            if (kd != null) {
                UploadPeopleServerResponse info = (UploadPeopleServerResponse) kd;
                if (info.getStatus() == 1) {
                    showToast(info.getMessage());
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

//    ArrayList<TImage> imgL;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode != 0) {
            if (resultCode == 107) {
                images_01 = data.getStringExtra("imgName");
//                imgL = (ArrayList<TImage>) data.getSerializableExtra("imgL");
                TLog.error("这里这里" + images_01 + "!!!!!!!!!!");
                String imgN = data.getStringExtra("imgN");
                ApiHttpClient.loadImage(imgN, photoIv);
            }
        }
    }

}