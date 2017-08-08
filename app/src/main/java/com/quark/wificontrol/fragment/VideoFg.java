package com.quark.wificontrol.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.ListMovies;
import com.quark.api.auto.bean.MoviesListRequest;
import com.quark.api.auto.bean.MoviesListResponse;
import com.quark.wificontrol.AppContext;
import com.quark.wificontrol.R;
import com.quark.wificontrol.adapter.VideoFgAdapter;
import com.quark.wificontrol.api.ApiResponse;
import com.quark.wificontrol.api.remote.QuarkApi;
import com.quark.wificontrol.base.BaseFragment;
import com.quark.wificontrol.ui.find.VideoActivity;
import com.quark.wificontrol.ui.widget.SlideTwoShowView;
import com.quark.wificontrol.util.TLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cz.msebera.android.httpclient.Header;
import me.maxwin.view.XListView;

@SuppressLint("ResourceAsColor")
public class VideoFg extends BaseFragment implements XListView.IXListViewListener {
    View oneViewt;
    @InjectView(R.id.xlstv)
    XListView xlstv;

    ArrayList<ListMovies> datas;
    VideoFgAdapter adapter;
    @InjectView(R.id.slideshowView)
    SlideTwoShowView slideshowView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        oneViewt = inflater.inflate(R.layout.fragment_videofg, container, false);
        ButterKnife.inject(this, oneViewt);
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        int sw = wm.getDefaultDisplay().getWidth();
        int pich = (int) ((sw * 275) / 750.0);
        TLog.error(pich + "pich lunbo");
        ViewGroup.LayoutParams params = slideshowView.getLayoutParams();
        params.height = pich;
        params.width = sw;
        slideshowView.setLayoutParams(params);

        myinitlist();
        movieslistRequest();
        return oneViewt;
    }

    public void myinitlist() {
        datas = new ArrayList();
        xlstv.setFooterDividersEnabled(false);
        // 设置xlistview可以加载、刷新
        xlstv.setPullLoadEnable(true);//购物车一次加载完 没有加载更多
        xlstv.setPullRefreshEnable(true);
        xlstv.setXListViewListener(this);
        xlstv.setOnItemClickListener(listListener);
        adapter = new VideoFgAdapter(getActivity(), datas, handler);
        xlstv.setAdapter(adapter);
        xlstv.setDivider(null);
        Message msg = handler.obtainMessage();
        msg.what = 1;
        msg.arg1 = datas.size();
        handler.sendMessage(msg);
    }

    AdapterView.OnItemClickListener listListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            position = position - 1;
            if (position < datas.size()) {

                Intent intent = new Intent(getActivity(), VideoActivity.class);
                intent.putExtra("url", datas.get(position).getMovies_url());
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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    int type = 1;
    int pn = 1;

    @Override
    public void onRefresh() {
        pn = 1;
        type = 1;
        movieslistRequest();

    }

    @Override
    public void onLoadMore() {
        type = 2;
        pn++;
        movieslistRequest();
    }

    public void movieslistRequest() {
        MoviesListRequest rq = new MoviesListRequest();
        rq.setPn(pn);
        rq.setPage_size(10);
        showWait(true);
        QuarkApi.HttpRequestNewList(rq, mHandlerget);
    }

    private final AsyncHttpResponseHandler mHandlerget = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, getActivity(), MoviesListResponse.class);
            if (kd != null) {
                MoviesListResponse info = (MoviesListResponse) kd;
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

    public void dealData(MoviesListResponse info) {
        if (type == 1) {
            datas.clear();
        }
        List<ListMovies> tin = info.getMoviesListResult().getMoviesList().getList();
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





