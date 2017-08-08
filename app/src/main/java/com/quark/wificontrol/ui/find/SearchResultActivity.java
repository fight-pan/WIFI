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

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cz.msebera.android.httpclient.Header;
import me.maxwin.view.XListView;

/**
 * Created by pan on 2016/9/8 0008.
 * >#
 * >#搜索结果
 */
public class SearchResultActivity extends BaseActivity implements XListView.IXListViewListener {

    ArrayList<ListCityInformation> datas;
    MoreAdapter adapter;
    @InjectView(R.id.xlstv)
    XListView xlstv;

    String search;
    String city = "深圳市";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchresult);
        ButterKnife.inject(this);
        setTopTitle("搜索结果");
        setBackButton();
        search = (String) getValue4Intent("search");
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
        adapter = new MoreAdapter(this, datas);
        xlstv.setAdapter(adapter);
        xlstv.setDivider(null);
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
                Intent intent  = new Intent(SearchResultActivity.this,GoodsDetailsActivity.class);
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


    int type = 1;
    int pn = 1;

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


    public void cityInfoMoreListRequest() {
        CityInfoMoreListRequest rq = new CityInfoMoreListRequest();
        rq.setCity(new AppParam().getCity(this));
        rq.setKw(search);
        rq.setCity_classify_02_id("0");
        rq.setLatitude(new AppParam().getLat(this));
        rq.setLongitude(new AppParam().getLng(this));
        rq.setPn(pn);
        rq.setPage_size(10);
        showWait(true);
        QuarkApi.HttpRequestNewList(rq, mHandlerget);
    }

    private final AsyncHttpResponseHandler mHandlerget = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, SearchResultActivity.this, CityInfoMoreListResponse.class);
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
}
