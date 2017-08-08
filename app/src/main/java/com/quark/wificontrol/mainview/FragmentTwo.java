package com.quark.wificontrol.mainview;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quark.wificontrol.R;
import com.quark.wificontrol.base.BaseFragment;
import com.quark.wificontrol.fragment.CityFg;
import com.quark.wificontrol.fragment.GameFg;
import com.quark.wificontrol.fragment.MusicFg;
import com.quark.wificontrol.fragment.ServiceFg;
import com.quark.wificontrol.fragment.VideoFg;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class FragmentTwo extends BaseFragment {
    View twoLayout;
    @InjectView(R.id.one_tv)
    TextView oneTv;
    @InjectView(R.id.one_iv)
    ImageView oneIv;
    @InjectView(R.id.one_ly)
    LinearLayout oneLy;
    @InjectView(R.id.two_tv)
    TextView twoTv;
    @InjectView(R.id.two_iv)
    ImageView twoIv;
    @InjectView(R.id.two_ly)
    LinearLayout twoLy;
    @InjectView(R.id.three_tv)
    TextView threeTv;
    @InjectView(R.id.three_iv)
    ImageView threeIv;
    @InjectView(R.id.three_ly)
    LinearLayout threeLy;
    @InjectView(R.id.four_tv)
    TextView fourTv;
    @InjectView(R.id.four_iv)
    ImageView fourIv;
    @InjectView(R.id.four_ly)
    LinearLayout fourLy;
    @InjectView(R.id.five_tv)
    TextView fiveTv;
    @InjectView(R.id.five_iv)
    ImageView fiveIv;
    @InjectView(R.id.five_ly)
    LinearLayout fiveLy;

    private MusicFg musicFg;
    private VideoFg videoFg;
    private CityFg cityFg;
    private ServiceFg serviceFg;
    private GameFg gameFg;
    private FragmentManager fragmentManager;

    private int precurrent = 0;
    private int current = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        twoLayout = inflater.inflate(R.layout.fragment_two, container, false);
        ButterKnife.inject(this, twoLayout);
        fragmentManager = getActivity().getSupportFragmentManager();
        if (savedInstanceState != null) {
            musicFg = (MusicFg) fragmentManager.findFragmentByTag("musicFg");
            videoFg = (VideoFg) fragmentManager.findFragmentByTag("videoFg");
            cityFg = (CityFg) fragmentManager.findFragmentByTag("cityFg");
            serviceFg = (ServiceFg) fragmentManager.findFragmentByTag("serviceFg");
            gameFg = (GameFg) fragmentManager.findFragmentByTag("gameFg");
        }

        int id =getActivity().getIntent().getIntExtra("id", 0);
        if (id == 0) {
            current = 0;
        } else if (id == 1) {
            current = 1;
        } else if (id == 2) {
            current = 2;
        } else if (id == 3) {
            current = 3;
        } else if (id == 4) {
            current = 4;
        }
        setTabSelection(current);

        return twoLayout;
    }


    @OnClick({R.id.one_ly, R.id.two_ly, R.id.three_ly, R.id.four_ly, R.id.five_ly})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.one_ly:
                current = 0;
                setTabSelection(0);
                break;
            case R.id.two_ly:
                current = 1;
                setTabSelection(1);
                break;
            case R.id.three_ly:
                current = 2;
                setTabSelection(2);
                break;
            case R.id.four_ly:
                current = 3;
                setTabSelection(3);
                break;
            case R.id.five_ly:
                current = 4;
                setTabSelection(4);
                break;
        }
    }

    private void setTabSelection(int index) {
        clearState();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                oneTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.chengse));
                oneIv.setVisibility(View.VISIBLE);

                if (musicFg == null) {
                    musicFg = new MusicFg();
                    transaction.add(R.id.content_two, musicFg, "musicFg");
                } else {
                    transaction.show(musicFg);
                }
                break;
            case 1:
                twoTv.setTextColor(ContextCompat.getColor(getActivity(), R.color.chengse));
                twoIv.setVisibility(View.VISIBLE);

                if (videoFg == null) {
                    videoFg = new VideoFg();
                    transaction.add(R.id.content_two, videoFg, "videoFg");
                } else {
                    transaction.show(videoFg);
                }
                break;
            case 2:
                threeTv.setTextColor(ContextCompat.getColor(getActivity(), R.color.chengse));
                threeIv.setVisibility(View.VISIBLE);

                if (cityFg == null) {
                    cityFg = new CityFg();
                    transaction.add(R.id.content_two, cityFg, "cityFg");
                } else {
                    transaction.show(cityFg);
                }
                break;
            case 3:
                fourTv.setTextColor(ContextCompat.getColor(getActivity(), R.color.chengse));
                fourIv.setVisibility(View.VISIBLE);

                if (serviceFg == null) {
                    serviceFg = new ServiceFg();
                    transaction.add(R.id.content_two, serviceFg, "serviceFg");
                } else {
                    transaction.show(serviceFg);
                }
                break;
            case 4:
                fiveTv.setTextColor(ContextCompat.getColor(getActivity(), R.color.chengse));
                fiveIv.setVisibility(View.VISIBLE);

                if (gameFg == null) {
                    gameFg = new GameFg();
                    transaction.add(R.id.content_two, gameFg, "gameFg");
                } else {
                    transaction.show(gameFg);
                }
                break;
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (musicFg != null) {
            transaction.hide(musicFg);
        }
        if (videoFg != null) {
            transaction.hide(videoFg);
        }
        if (cityFg != null) {
            transaction.hide(cityFg);
        }
        if (serviceFg != null) {
            transaction.hide(serviceFg);
        }
        if (gameFg != null) {
            transaction.hide(gameFg);
        }
    }

    /**
     * @author pan
     * @time 2016/9/7 0007 下午 4:10
     */
    public void clearState() {
        oneTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.black));
        twoTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.black));
        threeTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.black));
        fourTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.black));
        fiveTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.black));
        oneIv.setVisibility(View.INVISIBLE);
        twoIv.setVisibility(View.INVISIBLE);
        threeIv.setVisibility(View.INVISIBLE);
        fourIv.setVisibility(View.INVISIBLE);
        fiveIv.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

}




