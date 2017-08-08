package com.quark.wificontrol.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quark.api.auto.bean.Type;
import com.quark.wificontrol.R;
import com.quark.wificontrol.adapter.VideoAdapter;
import com.quark.wificontrol.base.BaseFragment;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.maxwin.view.XListView;

@SuppressLint("ResourceAsColor")
public class VideoFragment extends BaseFragment implements XListView.IXListViewListener {
    View twoViewt;
    @InjectView(R.id.xlstv)
    XListView xlstv;
    ArrayList<Type> datas;
    VideoAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        twoViewt = inflater.inflate(R.layout.fragment_video, container, false);
        ButterKnife.inject(this, twoViewt);

        myinitlist();
        return twoViewt;
    }

    public void myinitlist() {
        datas = new ArrayList();
        xlstv.setFooterDividersEnabled(false);
        // 设置xlistview可以加载、刷新
        xlstv.setPullLoadEnable(true);//购物车一次加载完 没有加载更多
        xlstv.setPullRefreshEnable(true);
        xlstv.setXListViewListener(this);
//        xlstv.setOnItemClickListener(listListener);
        adapter = new VideoAdapter(getActivity(), datas, handler);
        xlstv.setAdapter(adapter);
        xlstv.setDivider(null);
        Message msg = handler.obtainMessage();
        msg.what = 1;
        msg.arg1 = datas.size();
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
    }

    @Override
    public void onLoadMore() {
        type = 2;
        pn++;
    }

}





