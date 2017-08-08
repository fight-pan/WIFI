package com.quark.wificontrol.ui.personalCentel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.AdRequest;
import com.quark.api.auto.bean.AdResponse;
import com.quark.api.auto.bean.CityClassify02s;
import com.quark.wificontrol.AppContext;
import com.quark.wificontrol.R;
import com.quark.wificontrol.adapter.CityTypeClassAdapter;
import com.quark.wificontrol.api.ApiResponse;
import com.quark.wificontrol.api.remote.QuarkApi;
import com.quark.wificontrol.base.BaseActivity;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cz.msebera.android.httpclient.Header;

/**
 * Created by pan on 2016/9/24 0024.
 * >#
 * >#美食
 */
public class CateActivity extends BaseActivity {

    @InjectView(R.id.listView)
    ListView listView;

    ArrayList<CityClassify02s> CityClassify;
    CityTypeClassAdapter adapter;
    @InjectView(R.id.icon_1)
    LinearLayout icon1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecttype);
        ButterKnife.inject(this);

        setBackButton();
        icon1.setVisibility(View.GONE);
//        datas = new ArrayList<>();

        CityClassify = (ArrayList<CityClassify02s>) getValue4Intent("CityClassify");
        String head = (String) getValue4Intent("head");

        setTopTitle(head);
        adapter = new CityTypeClassAdapter(this, CityClassify);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(CateActivity.this, IssueCityInfoActivity.class);
                intent.putExtra("city_classify_02_id",
                        CityClassify.get(position).getCity_classify_02_id() + "");
                intent.putExtra("head",CityClassify.get(position).getCity_classify_02_name());
                startActivity(intent);
            }
        });
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    public void adRequest() {
        AdRequest rq = new AdRequest();
        showWait(true);
        QuarkApi.HttpRequestNewList(rq, mHandlerget);
    }

    private final AsyncHttpResponseHandler mHandlerget = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, CateActivity.this, AdResponse.class);
            if (kd != null) {
                AdResponse info = (AdResponse) kd;
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
            showWait(false);
        }
    };
}
