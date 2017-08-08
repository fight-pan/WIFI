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
import com.quark.wificontrol.adapter.MusicAdapter;
import com.quark.wificontrol.base.BaseFragment;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.maxwin.view.XListView;

@SuppressLint("ResourceAsColor")
public class MusicFragment extends BaseFragment implements XListView.IXListViewListener{
    View oneViewt;


    ArrayList<Type> datas;
    MusicAdapter adapter;
    @InjectView(R.id.xlstv)
    me.maxwin.view.XListView xlstv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        oneViewt = inflater.inflate(R.layout.fragment_video, container, false);
        ButterKnife.inject(this, oneViewt);


        myinitlist();
        return oneViewt;
    }

    public void myinitlist() {
        datas = new ArrayList();
        xlstv.setFooterDividersEnabled(false);
        // 设置xlistview可以加载、刷新
        xlstv.setPullLoadEnable(true);//购物车一次加载完 没有加载更多
        xlstv.setPullRefreshEnable(true);
        xlstv.setXListViewListener(this);
//        xlstv.setOnItemClickListener(listListener);
        adapter = new MusicAdapter(getActivity(), datas, handler);
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
}





