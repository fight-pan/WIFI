package com.quark.wificontrol.mainview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.HomeBannerRequest;
import com.quark.api.auto.bean.HomeBannerResponse;
import com.quark.api.auto.bean.ListhomeBanner;
import com.quark.wificontrol.AppContext;
import com.quark.wificontrol.R;
import com.quark.wificontrol.api.ApiHttpClient;
import com.quark.wificontrol.api.remote.QuarkApi;
import com.quark.wificontrol.base.BaseFragment;
import com.quark.wificontrol.ui.find.AdDetailActivity;
import com.quark.wificontrol.ui.widget.SlideHomeShowView;
import com.quark.wificontrol.util.TLog;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;


@SuppressLint("ResourceAsColor")
public class FragmentHome extends BaseFragment {
    View oneViewt;
    @InjectView(R.id.slideshowView)
    SlideHomeShowView slideshowView;
    @InjectView(R.id.music_tv)
    TextView musicTv;
    @InjectView(R.id.video_tv)
    TextView videoTv;
    @InjectView(R.id.city_tv)
    TextView cityTv;
    @InjectView(R.id.service_tv)
    TextView serviceTv;
    @InjectView(R.id.more_tv)
    TextView moreTv;
    @InjectView(R.id.one_iv)
    ImageView oneIv;
    @InjectView(R.id.two_iv)
    ImageView twoIv;
    @InjectView(R.id.img_ly)
    LinearLayout imgLy;


    private FragmentHome homeFragment;
    private FragmentOne oneFragment;
    private FragmentTwo twoFragment;
    //    private FragmentThree threeFragment;
    private FragmentFour fourFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        oneViewt = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.inject(this, oneViewt);
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        int sw = wm.getDefaultDisplay().getWidth();
        int pich = (int) ((sw * 462) / 750.0);
        TLog.error(pich + "pich lunbo");
        ViewGroup.LayoutParams params = slideshowView.getLayoutParams();
        params.height = pich;
        params.width = sw;
        slideshowView.setLayoutParams(params);

        int pich2 = (int) ((sw * 231) / 750.0);
        ViewGroup.LayoutParams params2 = imgLy.getLayoutParams();
        params2.height = pich2;
        params2.width = sw;
        imgLy.setLayoutParams(params2);

        getLeftData();
        return oneViewt;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


    @OnClick({R.id.music_tv, R.id.video_tv, R.id.city_tv, R.id.service_tv, R.id.more_tv, R.id.one_iv, R.id.two_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.music_tv:
                Intent in1 = new Intent(getActivity(), MainActivity.class);
                in1.putExtra("home", "a");
                in1.putExtra("id", 0);
                startActivity(in1);
                getActivity().finish();
                break;
            case R.id.video_tv:
                Intent in2 = new Intent(getActivity(), MainActivity.class);
                in2.putExtra("home", "a");
                in2.putExtra("id", 1);
                startActivity(in2);
                getActivity().finish();
                break;
            case R.id.city_tv:
                Intent in3 = new Intent(getActivity(), MainActivity.class);
                in3.putExtra("home", "a");
                in3.putExtra("id", 2);
                startActivity(in3);
                getActivity().finish();
                break;
            case R.id.service_tv:
                Intent in4 = new Intent(getActivity(), MainActivity.class);
                in4.putExtra("home", "a");
                in4.putExtra("id", 3);
                startActivity(in4);
                getActivity().finish();
                break;
            case R.id.more_tv:
                Intent in5 = new Intent(getActivity(), MainActivity.class);
                in5.putExtra("home", "a");
                in5.putExtra("id", 4);
                startActivity(in5);
                getActivity().finish();
                break;
            case R.id.one_iv:

                break;
            case R.id.two_iv:
                break;
        }
    }


    public void getLeftData() {
        HomeBannerRequest rq = new HomeBannerRequest();
        rq.setPosition_index("1");//1-首页页面，2-发现页面
//        rq.setPosition_faxian("2");//40-首页，41-发现-音乐轮播，42-发现-影城轮播，43-发现-同城轮播，44-发现-便民服务轮播，45-发现-游戏轮播
        QuarkApi.HttpRequestNewList(rq, mHandlerleft);
    }

    private final AsyncHttpResponseHandler mHandlerleft = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            String ds = null;
            try {
                ds = new String(arg2, "UTF-8");
                final HomeBannerResponse homeinfo = new HomeBannerResponse(ds);
                final List<ListhomeBanner> banners = homeinfo.getHomeBannerResult().getHomeBanner();
                if (homeinfo.getStatus() == 1) {

                    for (int i = 0; i < banners.size(); i++) {
                        if (banners.get(i).getPosition() == 2) {
                            ApiHttpClient.loadImage(banners.get(i).getImage_01(), oneIv, R.drawable.banner_1);
                            final String id = banners.get(i).getIndex_banner_id() + "";
                            oneIv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getActivity(), AdDetailActivity.class);
                                    intent.putExtra("index_banner_id", id);
                                    startActivity(intent);
                                }
                            });
                        }
                        if (banners.get(i).getPosition() == 3) {
                            ApiHttpClient.loadImage(banners.get(i).getImage_01(), twoIv, R.drawable.banner_1);
                            final String rightId = banners.get(i).getIndex_banner_id() + "";
                            twoIv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getActivity(), AdDetailActivity.class);
                                    intent.putExtra("index_banner_id", rightId);
                                    startActivity(intent);
                                }
                            });
                        }


                    }


                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("error", "解析出错");
            }
            Log.e("error", ds);
        }

        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
            AppContext.showToast("网络出错" + arg0);
            Log.e("error", "出错");
        }

        @Override
        public void onFinish() {
            super.onFinish();

        }
    };

//    public void getrightData() {
//        HomeBannerRequest rq = new HomeBannerRequest();
//        rq.setPosition_index("1");//1-首页页面，2-发现页面
////        rq.setPosition_faxian("3");//40-首页，41-发现-音乐轮播，42-发现-影城轮播，43-发现-同城轮播，44-发现-便民服务轮播，45-发现-游戏轮播
//        QuarkApi.HttpRequestNewList(rq, mHandler);
//    }
//
//    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {
//        @Override
//        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//            String ds = null;
//            try {
//                ds = new String(arg2, "UTF-8");
//                final HomeBannerResponse info = new HomeBannerResponse(ds);
//                List<ListhomeBanner> banners = info.getHomeBannerResult().getHomeBanner();
//                if (banners != null && !Utils.isEmpty(banners.get(0).getImage_01())) {
//                    ApiHttpClient.loadImage(banners.get(0).getImage_01(), twoIv, R.drawable.banner_1);
//                    twoIv.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent = new Intent(getActivity(), AdDetailActivity.class);
//                            intent.putExtra("index_banner_id", info.getHomeBannerResult().getHomeBanner().get(0).getIndex_banner_id() + "");
//                            startActivity(intent);
//                        }
//                    });
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                Log.e("error", "解析出错");
//            }
//            Log.e("error", ds);
//        }
//
//        @Override
//        public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
//            AppContext.showToast("网络出错" + arg0);
//            Log.e("error", "出错");
//        }
//
//        @Override
//        public void onFinish() {
//            super.onFinish();
//
//        }
//    };


}
