package com.quark.wificontrol.ui.find;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.ListMusic;
import com.quark.api.auto.bean.MusicListRequest;
import com.quark.api.auto.bean.MusicListResponse;
import com.quark.wificontrol.AppContext;
import com.quark.wificontrol.R;
import com.quark.wificontrol.adapter.MusicPagerAdapter;
import com.quark.wificontrol.api.ApiHttpClient;
import com.quark.wificontrol.api.ApiResponse;
import com.quark.wificontrol.api.remote.QuarkApi;
import com.quark.wificontrol.base.BaseActivity;
import com.quark.wificontrol.mainview.MainActivity;
import com.quark.wificontrol.ui.widget.AutoListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

/**
 * Created by pan on 2016/9/30 0030.
 * >#
 * >#音乐播放界面
 */
public class MusicActivity extends BaseActivity implements AutoListView.OnLoadListener{

//    @InjectView(R.id.viewPager)
    ViewPager viewPager;
//    @InjectView(R.id.v_dot1)
    View vDot1;
//    @InjectView(R.id.v_dot2)
    View vDot2;
    @InjectView(R.id.lv)
    AutoListView lv;
    @InjectView(R.id.music_name)
    TextView musicName;
//    @InjectView(R.id.one_ly)
//    LinearLayout oneLy;
//    @InjectView(R.id.music_title)
    TextView musicTitle;
    @InjectView(R.id.play_iv)
    ImageView play_iv;
    @InjectView(R.id.loading)
    ProgressBar loading;

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
        setContentView(R.layout.activity_music);
        ButterKnife.inject(this);
        registerBoradcastReceiver();

        datas = MainActivity.datas;
        music_classify_id = (String) getValue4Intent("music_classify_id");
        music_title = (String) getValue4Intent("music_title");
        introduce = (String) getValue4Intent("introduce");

        initList();
        initpager();

        musicListRequest();
    }

    /**
     * 初始化pager
     *
     * @author pan
     * @time 2016/9/30 0030 下午 2:24
     */
    public void initpager() {
        View headview = LayoutInflater.from(this).inflate(R.layout.activity_music_head, null);
        vDot1 = (View)headview.findViewById(R.id.v_dot1);
        vDot2 = (View)headview.findViewById(R.id.v_dot2);
        viewPager = (ViewPager)headview.findViewById(R.id.viewPager);
        musicTitle = (TextView)headview.findViewById(R.id.music_title);

        viewList = new ArrayList<View>();
        View view1 = LayoutInflater.from(this).inflate(R.layout.vp_one, null);
        View view2 = LayoutInflater.from(this).inflate(R.layout.vp_two, null);
        TextView introduceTv = (TextView) view2.findViewById(R.id.introduce_tv);
        introduceTv.setText(introduce);
        viewList.add(view1);
        viewList.add(view2);

        MyPagerAdapter adapter = new MyPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(vpOnPageChangeListener);

        //获取焦点，让滚动条在顶部
//        oneLy.setFocusable(true);
//        oneLy.setFocusableInTouchMode(true);
//        oneLy.requestFocus();
        musicTitle.setText(music_title);
        lv.addHeaderView(headview);
    }

    ViewPager.OnPageChangeListener vpOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            if (position == 0) {
                vDot1.setBackground(ContextCompat.getDrawable(MusicActivity.this, R.drawable.white_yuan));
                vDot2.setBackground(ContextCompat.getDrawable(MusicActivity.this, R.drawable.tou_yuan));
            } else if (position == 1) {
                vDot1.setBackground(ContextCompat.getDrawable(MusicActivity.this, R.drawable.tou_yuan));
                vDot2.setBackground(ContextCompat.getDrawable(MusicActivity.this, R.drawable.white_yuan));
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void onLoad() {
        pn++;
        musicListRequest();
    }

//    @Override
//    public void onRefresh() {
//        showToast("222222222");
//    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewList.get(position));//删除页卡
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));//添加页卡
            return viewList.get(position);
        }
    }

    public void initList() {
        datas = new ArrayList<>();
        lv.setOnLoadListener(this);
        lv.setRefreshEnable(false);

        mAdapter = new MusicPagerAdapter(MusicActivity.this, datas, handler);
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
                case 10:
                    datas.get(currentSong).setIsplaying(false);
                    currentSong = msg.arg1;
                    datas.get(currentSong).setIsplaying(true);
                    mAdapter.notifyDataSetChanged();
                    String url = ApiHttpClient.loadmusic + datas.get(currentSong).getMusic_url();
                    toplay(url);
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
            Object kd = ApiResponse.get(arg2, MusicActivity.this, MusicListResponse.class);
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
                if (pn==1){
                    initMediaPlayer();
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

    //==========================================播放器===================================================
    private void initMediaPlayer() {
        if (currentSong == 0 && datas.size() > 0 ) {
            if (MainActivity.PLAYSTATE==1){
                String url = ApiHttpClient.loadmusic + datas.get(currentSong).getMusic_url();
                toplay(url);
                MainActivity.PLAYSTATE = 2;  //准备好播放
            }else {
                musicName.setText(MainActivity.songName);
            }
        }
    }

    public void toplay(String url) {
        loading.setVisibility(View.VISIBLE);
        MainActivity.toplay(url);

        musicName.setText(datas.get(currentSong).getTitle());
        MainActivity.songName = datas.get(currentSong).getTitle();
    }

    public void nextSong() {
        currentSong++;
        if (currentSong > datas.size() || currentSong == datas.size()) {
            currentSong--;
            showToast("已是最后一首了");
        } else {
            if (currentSong+4>datas.size()){
                onLoad();
            }
            String url = ApiHttpClient.loadmusic + datas.get(currentSong).getMusic_url();
            toplay(url);
            currentSong--;
            datas.get(currentSong).setIsplaying(false);
            currentSong++;
            datas.get(currentSong).setIsplaying(true);

            mAdapter.notifyDataSetChanged();
        }
    }

    public void prevSong() {
        currentSong--;
        if (currentSong < 0) {
            currentSong++;
            showToast("已是第一首了");
        } else {
            String url = ApiHttpClient.loadmusic + datas.get(currentSong).getMusic_url();
            toplay(url);
            currentSong++;
            datas.get(currentSong).setIsplaying(false);
            currentSong--;
            datas.get(currentSong).setIsplaying(true);
            mAdapter.notifyDataSetChanged();
        }
    }

    @OnClick({R.id.prev_iv, R.id.play_iv, R.id.next_iv,R.id.left_iv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_iv:
                if (datas.size()>0){
                    if (MainActivity.isplaying()){
                        MainActivity.pause();
                        play_iv.setBackground(ContextCompat.getDrawable(this, R.drawable.pause_selector));
                        datas.get(currentSong).setIsplaying(false);
                    }else{
                        MainActivity.play();
                        play_iv.setBackground(ContextCompat.getDrawable(this, R.drawable.play_selector));
                        datas.get(currentSong).setIsplaying(true);
                    }
                    mAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.prev_iv:
                prevSong();
                break;
            case R.id.next_iv:
                nextSong();
                break;
            case R.id.left_iv:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
//        mPlayer.stop();
        if (receiveBroadCast!=null){
            unregisterReceiver(receiveBroadCast);
        }
    }

    /**
     * 当按手机上的返回按键的时候，会自动调用系统的onKeyDown方法，
     * 而onKeyDown方法右会自动调用onDestroy()方法
     * 销毁该Activity,此时如果onDestroy()方法不重写，那么正在播放
     * 的音乐不会停止，所以这时候要重写onDestroy()方法，
     * 在该方法中，加入mediaplayer.stop()方法，表示按返回键的时候，
     * 会调用mediaPlayer对象的stop方法，
     * 从而停止音乐的播放
     */


    // 注册广播
    private ReceiveBroadCast receiveBroadCast;
    public void registerBoradcastReceiver() {
        receiveBroadCast = new ReceiveBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("musicBC"); // 只有持有相同的action的接受者才能接收此广播
        registerReceiver(receiveBroadCast, filter);
    }

    public class ReceiveBroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent data) {
            String position = data.getStringExtra("option");
            if (position.equals("next")) {
                nextSong();
            }else if(position.equals("complet")){
                loading.setVisibility(View.INVISIBLE);
            }
        }
    }
}
