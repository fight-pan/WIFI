package com.quark.wificontrol.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.CityInfoList;
import com.quark.api.auto.bean.CityInfoListRequest;
import com.quark.api.auto.bean.CityInfoListResponse;
import com.quark.wificontrol.AppContext;
import com.quark.wificontrol.R;
import com.quark.wificontrol.adapter.RightAdapter;
import com.quark.wificontrol.api.ApiResponse;
import com.quark.wificontrol.api.remote.QuarkApi;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cz.msebera.android.httpclient.Header;
import me.maxwin.view.XListView;

/**
 * category页面的右侧片段显示
 *
 * @author pan
 * @time 2016/8/22 0022 下午 5:31
 */
public class CategoryFragment extends Fragment implements XListView.IXListViewListener {
    View twoLayout;
    @InjectView(R.id.nodata)
    TextView nodata;
    @InjectView(R.id.xlstv)
    XListView xlstv;

    ArrayList<CityInfoList> datas;

    RightAdapter adapter;

    String city;
    String city_classify_01_id;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        twoLayout = inflater.inflate(R.layout.category_fragment, container, false);
        ButterKnife.inject(this, twoLayout);
        city_classify_01_id = getArguments().getString("city_classify_01_id");
        city = getArguments().getString("city");
        myinitlist();
        getData();
        return twoLayout;
    }

    public void myinitlist() {
        datas = new ArrayList();
        xlstv.setFooterDividersEnabled(false);
        // 设置xlistview可以加载、刷新
        xlstv.setPullLoadEnable(false);//购物车一次加载完 没有加载更多
        xlstv.setPullRefreshEnable(true);
        xlstv.setXListViewListener(this);
//        xlstv.setOnItemClickListener(listListener);
        adapter = new RightAdapter(getActivity(), datas);
        xlstv.setDivider(null);
        xlstv.setAdapter(adapter);
        Message msg = handler.obtainMessage();
        msg.what = 1;
        msg.arg1 = datas.size();
        handler.sendMessage(msg);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public void getData() {
        CityInfoListRequest rq = new CityInfoListRequest();
        rq.setCity(city);
        rq.setCity_classify_01_id(city_classify_01_id);
        QuarkApi.HttpRequestNewList(rq, mHandler);
    }

    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, getActivity(), CityInfoListResponse.class);
            if (kd != null) {
                CityInfoListResponse info = (CityInfoListResponse) kd;

                dealdatas(info);
            }
//            showWait(false);
        }

        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
            AppContext.showToast("网络出错" + arg0);
//            showWait(false);
        }
    };

    public void dealdatas(CityInfoListResponse info) {
        if (type == 1) {
            datas.clear();
        }
        List<CityInfoList> tproducts = info.getCityInfoListResult().getCityInfoList();
        Message msg = handler.obtainMessage();
        msg.what = type;
        if (tproducts != null) {
            datas.addAll(tproducts);
            msg.arg1 = tproducts.size();
        } else {
            msg.arg1 = 0;
        }
        handler.sendMessage(msg);
    }

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


    int pn = 1;
    int type = 1;
    @Override
    public void onRefresh() {
        pn = 1;
        type = 1;
        getData();
    }

    @Override
    public void onLoadMore() {
        type = 2;
        pn++;
        getData();
    }
}
