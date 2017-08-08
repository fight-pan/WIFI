package com.quark.wificontrol.ui.personalCentel;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quark.wificontrol.R;
import com.quark.wificontrol.base.BaseFragementActivity;
import com.quark.wificontrol.fragment.MusicFragment;
import com.quark.wificontrol.fragment.VideoFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by pan on 2016/9/24 0024.
 * >#
 * >#我的缓存
 */
public class MyCacheActivity extends BaseFragementActivity {


    @InjectView(R.id.music_ly)
    LinearLayout musicLy;
    @InjectView(R.id.video_ly)
    LinearLayout videoLy;
    @InjectView(R.id.one_tv)
    TextView oneTv;
    @InjectView(R.id.one_iv)
    ImageView oneIv;
    @InjectView(R.id.two_tv)
    TextView twoTv;
    @InjectView(R.id.two_iv)
    ImageView twoIv;


    private MusicFragment musicFragment;
    private VideoFragment videoFragment;
    private FragmentManager fragmentManager;

    private int precurrent = 0;
    private int current = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null) {
            musicFragment = (MusicFragment) fragmentManager.findFragmentByTag("musicFragment");
            videoFragment = (VideoFragment) fragmentManager.findFragmentByTag("videoFragment");
        }
        setContentView(R.layout.activity_mycache);
        ButterKnife.inject(this);
        setTopTitle("我的缓存");
        setBackButton();

        setTabSelection(current);


    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.music_ly, R.id.video_ly})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.music_ly:
                current = 0;
                setTabSelection(0);
                break;
            case R.id.video_ly:
                current = 1;
                setTabSelection(1);
                break;
        }
    }

    private void setTabSelection(int index) {
        clearState();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                oneTv.setTextColor(ContextCompat.getColor(this, R.color.chengse));
                oneIv.setVisibility(View.VISIBLE);
                if (musicFragment == null) {
                    musicFragment = new MusicFragment();
                    transaction.add(R.id.content_two, musicFragment, "musicFragment");
                } else {
                    transaction.show(musicFragment);
                }
                break;
            case 1:
                twoTv.setTextColor(ContextCompat.getColor(this, R.color.chengse));
                twoIv.setVisibility(View.VISIBLE);

                if (videoFragment == null) {
                    videoFragment = new VideoFragment();
                    transaction.add(R.id.content_two, videoFragment, "videoFragment");
                } else {
                    transaction.show(videoFragment);
                }
                break;
        }
        transaction.commit();
    }

    public void clearState() {
        oneTv.setTextColor(ContextCompat.getColor(this, R.color.black));
        oneIv.setVisibility(View.INVISIBLE);
        twoTv.setTextColor(ContextCompat.getColor(this, R.color.black));
        twoIv.setVisibility(View.INVISIBLE);
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (musicFragment != null) {
            transaction.hide(musicFragment);
        }
        if (videoFragment != null) {

            transaction.hide(videoFragment);
        }
    }


}
