package com.quark.wificontrol.ui.personalCentel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.PeopleClassifys;
import com.quark.api.auto.bean.PeopleClassifysRequest;
import com.quark.api.auto.bean.PeopleClassifysResponse;
import com.quark.wificontrol.AppContext;
import com.quark.wificontrol.R;
import com.quark.wificontrol.adapter.TypeAdapter;
import com.quark.wificontrol.api.ApiResponse;
import com.quark.wificontrol.api.remote.QuarkApi;
import com.quark.wificontrol.base.BaseActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

/**
 * Created by pan on 2016/9/24 0024.
 * >#
 * >#发布信息
 */
public class SelectTypeActivity extends BaseActivity {

    @InjectView(R.id.listView)
    ListView listView;

    ArrayList<PeopleClassifys> datas;
    TypeAdapter adapter;
    @InjectView(R.id.icon_1)
    LinearLayout icon1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecttype);
        ButterKnife.inject(this);
        setTopTitle("选择类型");
        setBackButton();

        datas = new ArrayList<>();

        peopleClassifysRequest();

        icon1.setVisibility(View.VISIBLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(SelectTypeActivity.this, TeachingActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("teachType", (Serializable) datas.get(position).getPeopleServicesClassify02s());
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else if (position == 1) {
                    Intent intent = new Intent(SelectTypeActivity.this, HouseActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("teachType", (Serializable) datas.get(position).getPeopleServicesClassify02s());
                    intent.putExtras(bundle);
                    startActivity(intent);
                } /*else if (position == 2) {
                    Intent intent = new Intent(SelectTypeActivity.this, CityInfoActivity.class);
                    startActivity(intent);
                }*/
            }
        });
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    public void peopleClassifysRequest() {
        PeopleClassifysRequest rq = new PeopleClassifysRequest();
        showWait(true);
        QuarkApi.HttpRequestNewList(rq, mHandlerget);
    }

    private final AsyncHttpResponseHandler mHandlerget = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, SelectTypeActivity.this, PeopleClassifysResponse.class);
            if (kd != null) {
                PeopleClassifysResponse info = (PeopleClassifysResponse) kd;
                if (info.getStatus() == 1) {

                    if (info.getPeopleClassifysResult().getPeopleClassifys() != null && info.getPeopleClassifysResult().getPeopleClassifys().size() > 0) {
                        List<PeopleClassifys> list = info.getPeopleClassifysResult().getPeopleClassifys();
                        datas.addAll(list);
                        adapter = new TypeAdapter(SelectTypeActivity.this, datas);
                        listView.setAdapter(adapter);
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

    @OnClick(R.id.icon_1)
    public void onClick() {
        startActivityByClass(CityInfoActivity.class);

    }
}
