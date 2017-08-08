package com.quark.wificontrol.ui.find;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.CityInfoMoreListRequest;
import com.quark.api.auto.bean.CityInfoMoreListResponse;
import com.quark.api.auto.bean.ListCityInformation;
import com.quark.wificontrol.AppContext;
import com.quark.wificontrol.AppParam;
import com.quark.wificontrol.R;
import com.quark.wificontrol.adapter.MoreAdapter;
import com.quark.wificontrol.api.ApiResponse;
import com.quark.wificontrol.api.remote.QuarkApi;
import com.quark.wificontrol.base.BaseActivity;
import com.quark.wificontrol.util.TLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cz.msebera.android.httpclient.Header;
import me.maxwin.view.XListView;

/**
 * Created by pan on 2016/10/8 0008.
 * >#
 * >#更多
 */
public class MoreActivity extends BaseActivity implements XListView.IXListViewListener{


    String city_classify_02_id;
    String kw;//Infer
    String city = "深圳市";//市
    String latitude;//维度
    String longitude;//经度

    @InjectView(R.id.xlstv)
    XListView xlstv;

    MoreAdapter adapter;
    ArrayList<ListCityInformation> datas;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        ButterKnife.inject(this);

        setBackButton();

        city_classify_02_id = (String) getValue4Intent("city_classify_02_id");
        title = (String) getValue4Intent("title");
        setTopTitle(title);
        myinitlist();
        cityInfoMoreListRequest();
    }

    public void myinitlist() {
        datas = new ArrayList();
        xlstv.setFooterDividersEnabled(false);
        // 设置xlistview可以加载、刷新
        xlstv.setPullLoadEnable(true);//购物车一次加载完 没有加载更多
        xlstv.setPullRefreshEnable(true);
        xlstv.setXListViewListener(this);
        xlstv.setOnItemClickListener(listListener);
        adapter = new MoreAdapter(MoreActivity.this, datas);
        xlstv.setDivider(null);
        xlstv.setAdapter(adapter);
        Message msg = handler.obtainMessage();
        msg.what = 1;
        msg.arg1 = datas.size();
        handler.sendMessage(msg);
    }

    AdapterView.OnItemClickListener listListener  = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            position = position-1;
            if (position< datas.size()){
                Intent intent  = new Intent(MoreActivity.this,GoodsDetailsActivity.class);
                intent.putExtra("city_information_id",datas.get(position).getCity_information_id()+"");
                startActivity(intent);
            }
        }
    };

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    xlstv.setDataSize(msg.arg1);
                    xlstv.stopRefresh();
                    adapter.notifyDataSetChanged();
                    break;
                case 2:
                    xlstv.setDataSize(msg.arg1);
                    xlstv.stopLoadMore();
                    adapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    public void cityInfoMoreListRequest() {
        CityInfoMoreListRequest rq = new CityInfoMoreListRequest();
        rq.setCity_classify_02_id(city_classify_02_id);
        rq.setCity(new AppParam().getCity(this));
        rq.setLongitude(new AppParam().getLng(this));
        rq.setLatitude(new AppParam().getLat(this));
        rq.setKw(kw);
        rq.setPn(pn);
        rq.setPage_size(10);
        showWait(true);
        QuarkApi.HttpRequestNewList(rq, mHandlerget);

        TLog.error(new AppParam().getCity(this)+"+++++++搜索城市");
    }

    private final AsyncHttpResponseHandler mHandlerget = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, MoreActivity.this, CityInfoMoreListResponse.class);
            if (kd != null) {
                CityInfoMoreListResponse info = (CityInfoMoreListResponse) kd;
                if (info.getStatus() == 1) {

                    dealData(info);
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

    public void dealData(CityInfoMoreListResponse info) {
        if (type == 1) {
            datas.clear();
        }
        List<ListCityInformation> tin = info.getCityInfoMoreListResult().getCityInfoMoreList().getList();
        Message msg = handler.obtainMessage();
        msg.what = type;
        if (tin != null && tin.size() > 0) {
            datas.addAll(tin);
            msg.arg1 = tin.size();
        } else {
            msg.arg1 = 0;
        }
        handler.sendMessage(msg);
    }

    int pn = 1;
    int type = 1;
    @Override
    public void onRefresh() {
        pn = 1;
        type = 1;
        cityInfoMoreListRequest();
    }

    @Override
    public void onLoadMore() {
        type = 2;
        pn++;
        cityInfoMoreListRequest();
    }
}
