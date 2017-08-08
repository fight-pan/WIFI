package com.quark.wificontrol.ui.personalCentel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.CityClassifys;
import com.quark.api.auto.bean.CityClassifysRequest;
import com.quark.api.auto.bean.CityClassifysResponse;
import com.quark.wificontrol.AppContext;
import com.quark.wificontrol.R;
import com.quark.wificontrol.adapter.CityTypeAdapter;
import com.quark.wificontrol.api.ApiResponse;
import com.quark.wificontrol.api.remote.QuarkApi;
import com.quark.wificontrol.base.BaseActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cz.msebera.android.httpclient.Header;

/**
 * Created by pan on 2016/9/24 0024.
 * >#
 * >#同城信息
 */
public class CityInfoActivity extends BaseActivity {

    @InjectView(R.id.listView)
    ListView listView;

    ArrayList<CityClassifys> datas;
    CityTypeAdapter adapter;
    @InjectView(R.id.icon_1)
    LinearLayout icon1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecttype);
        ButterKnife.inject(this);
        setTopTitle("同城信息");
        setBackButton();

        icon1.setVisibility(View.GONE);
        datas = new ArrayList<>();
        cityRequest();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    public void cityRequest() {
        CityClassifysRequest rq = new CityClassifysRequest();
        showWait(true);
        QuarkApi.HttpRequestNewList(rq, mHandlerget);
    }

    private final AsyncHttpResponseHandler mHandlerget = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, CityInfoActivity.this, CityClassifysResponse.class);
            if (kd != null) {
                CityClassifysResponse info = (CityClassifysResponse) kd;
                if (info.getStatus() == 1) {

                    if (info.getCityClassifysResult().getCityClassifys() != null && info.getCityClassifysResult().getCityClassifys().size() > 0) {
                        List<CityClassifys> list = info.getCityClassifysResult().getCityClassifys();
                        datas.addAll(list);
                        adapter = new CityTypeAdapter(CityInfoActivity.this, datas);
                        listView.setAdapter(adapter);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                Intent intent = new Intent(CityInfoActivity.this,CateActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("CityClassify", (Serializable) datas.get(position).getCityClassify02s());
                                bundle.putString("head",datas.get(position).getCity_classify_01_name());
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
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
}
