package com.quark.wificontrol.ui.find;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.ListPeopelServices;
import com.quark.api.auto.bean.PeopelServicesListRequest;
import com.quark.api.auto.bean.PeopelServicesListResponse;
import com.quark.wificontrol.AppContext;
import com.quark.wificontrol.AppParam;
import com.quark.wificontrol.R;
import com.quark.wificontrol.adapter.PeopelServicesAdapter;
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
 * Created by pan on 2016/10/8 0008.
 * >#
 * >#便民服务列表
 */
public class PeopelServicesListActivity extends BaseActivity implements XListView.IXListViewListener{


    String people_services_classify_02_id;

    @InjectView(R.id.xlstv)
    XListView xlstv;

    PeopelServicesAdapter adapter;
    ArrayList<ListPeopelServices> datas;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        ButterKnife.inject(this);

        setBackButton();

        people_services_classify_02_id = (String) getValue4Intent("people_services_classify_02_id");
        title = (String) getValue4Intent("title");
        setTopTitle(title);
        myinitlist();
        peopelServicesListRequest();
    }

    public void myinitlist() {
        datas = new ArrayList();
        xlstv.setFooterDividersEnabled(false);
        // 设置xlistview可以加载、刷新
        xlstv.setPullLoadEnable(true);//购物车一次加载完 没有加载更多
        xlstv.setPullRefreshEnable(true);
        xlstv.setXListViewListener(this);
        xlstv.setOnItemClickListener(listListener);
        adapter = new PeopelServicesAdapter(PeopelServicesListActivity.this, datas);
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
               Intent intent  = new Intent(PeopelServicesListActivity.this,PeopelDetailsActivity.class);
               intent.putExtra("people_services_id",datas.get(position).getPeople_services_id()+"");
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

    public void peopelServicesListRequest() {
        PeopelServicesListRequest rq = new PeopelServicesListRequest();
        rq.setPeople_services_classify_02_id(people_services_classify_02_id);
        rq.setLongitude(new AppParam().getLng(this));
        rq.setLatitude(new AppParam().getLat(this));
        rq.setPn(pn);
        rq.setPage_size(10);
        showWait(true);
        QuarkApi.HttpRequestNewList(rq, mHandlerget);
    }

    private final AsyncHttpResponseHandler mHandlerget = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, PeopelServicesListActivity.this, PeopelServicesListResponse.class);
            if (kd != null) {
                PeopelServicesListResponse info = (PeopelServicesListResponse) kd;
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

    public void dealData(PeopelServicesListResponse info) {
        if (type == 1) {
            datas.clear();
        }
        List<ListPeopelServices> tin = info.getPeopelServicesListResult().getPeopelServicesList().getList();
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
        peopelServicesListRequest();
    }

    @Override
    public void onLoadMore() {
        type = 2;
        pn++;
        peopelServicesListRequest();
    }
}
