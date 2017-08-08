package com.quark.wificontrol.ui.find;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ScrollView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.ListMusic;
import com.quark.api.auto.bean.MusicListRequest;
import com.quark.api.auto.bean.MusicListResponse;
import com.quark.wificontrol.AppContext;
import com.quark.wificontrol.R;
import com.quark.wificontrol.adapter.MusicPagerAdapter;
import com.quark.wificontrol.api.ApiResponse;
import com.quark.wificontrol.api.remote.QuarkApi;
import com.quark.wificontrol.base.BaseActivity;
import com.quark.wificontrol.ui.widget.AutoListForScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cz.msebera.android.httpclient.Header;

/**
 * Created by pan on 2016/9/30 0030.
 * >#
 * >#音乐播放界面
 */
public class TestActivity extends BaseActivity implements AutoListForScrollView.OnLoadListener, AutoListForScrollView.OnRefreshListener {


    @InjectView(R.id.lv)
    AutoListForScrollView lv;
    @InjectView(R.id.scrollView)
    ScrollView scrollView;

    MusicPagerAdapter mAdapter;
    String music_classify_id;//
    int pn = 1;//Infer
    String music_title;//音乐标题
    ArrayList<View> viewList;
    String introduce;


    ArrayList<ListMusic> datas;
    int currentSong = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        ButterKnife.inject(this);
        music_classify_id = (String) getValue4Intent("music_classify_id");
        music_title = (String) getValue4Intent("music_title");
        introduce = (String) getValue4Intent("introduce");
        scrollView.requestDisallowInterceptTouchEvent(false);

        initList();
        musicListRequest();
    }

    @Override
    public void onLoad() {
        pn++;
        musicListRequest();
    }

    public void initList() {
        datas = new ArrayList<>();
//        lv.setOnLoadListener(this);
//        lv.setLoadEnable(true);
        lv.setOnRefreshListener(this);
        lv.setOnLoadListener(this);

        mAdapter = new MusicPagerAdapter(TestActivity.this, datas, handler);
        lv.setAdapter(mAdapter);
        lv.setDivider(null);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int size = msg.arg1;
            switch (msg.what) {
                case 1:
                    lv.onLoadComplete();
                    lv.setResultSize(size);
                    mAdapter.notifyDataSetChanged();
                    break;

                default:
                    break;
            }
        }
    };

    public void musicListRequest() {
        MusicListRequest rq = new MusicListRequest();
        rq.setMusic_classify_id(music_classify_id);
        rq.setPn(pn);
        rq.setPage_size(10);
        if (pn==1){
            showWait(true);
        }
        QuarkApi.HttpRequestNewList(rq, mHandlerget);
    }

    private final AsyncHttpResponseHandler mHandlerget = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, TestActivity.this, MusicListResponse.class);
            if (kd != null) {
                MusicListResponse info = (MusicListResponse) kd;
                if (info.getStatus() == 1) {
                    List<ListMusic> tdata = info.getMusicListResult().getMusicList().getList();
                    Message msg = new Message();
                    msg.what = 1;
                    msg.arg1 = 0;
                    if (tdata != null && tdata.size() > 0) {
                        datas.addAll(tdata);
                        msg.arg1 = tdata.size();
                    }
                    handler.sendMessage(msg);
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


    @Override
    public void onRefresh() {

    }
}
