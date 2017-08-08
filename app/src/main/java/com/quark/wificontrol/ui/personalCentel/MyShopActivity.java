package com.quark.wificontrol.ui.personalCentel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.BaseInfoRequest;
import com.quark.api.auto.bean.InfoResponse;
import com.quark.wificontrol.AppContext;
import com.quark.wificontrol.R;
import com.quark.wificontrol.api.ApiHttpClient;
import com.quark.wificontrol.api.ApiResponse;
import com.quark.wificontrol.api.remote.QuarkApi;
import com.quark.wificontrol.base.BaseFragementActivity;
import com.quark.wificontrol.fragment.CityFragment;
import com.quark.wificontrol.fragment.ShopFragment;
import com.quark.wificontrol.fragment.TeachingFragment;
import com.quark.wificontrol.ui.widget.CircularImage;
import com.quark.wificontrol.util.TLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

/**
 * Created by pan on 2016/9/24 0024.
 * >#
 * >#我的店铺
 */
public class MyShopActivity extends BaseFragementActivity {


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
    @InjectView(R.id.pager)
    ViewPager pager;
    @InjectView(R.id.pic_iv)
    CircularImage picIv;
    @InjectView(R.id.name_tv)
    TextView nameTv;
    private List<Fragment> fragmentList;
    int current = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        fragmentManager = getSupportFragmentManager();
//        if (savedInstanceState != null) {
//            cityFragment = (CityFragment) fragmentManager.findFragmentByTag("cityFragment");
//            teachingFragment = (TeachingFragment) fragmentManager.findFragmentByTag("teachingFragment");
//            shopFragment = (ShopFragment) fragmentManager.findFragmentByTag("shopFragment");
//        }
        setContentView(R.layout.activity_myshop);
        ButterKnife.inject(this);
//        setTopTitle("我的店铺");
//        setBackButton();
        infoRequest();

    }

    @OnClick({R.id.one_ly, R.id.two_ly, R.id.three_ly, R.id.left_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.one_ly:
                current = 0;
                pager.setCurrentItem(0);
                break;
            case R.id.two_ly:
                current = 1;
                pager.setCurrentItem(1);
                break;
            case R.id.three_ly:
                current = 2;
                pager.setCurrentItem(2);
                break;
            case R.id.left_iv:
                finish();
                break;
        }
    }

    public void initpage() {
        CityFragment cityFragment = new CityFragment();
        Bundle bundle = new Bundle();
//        bundle.putString("people_services_classify_01_id", info.getBaseInfoResult().getPeopleServicesClassify01s().get(0).ge);
        cityFragment.setArguments(bundle);

        TeachingFragment teachingFragment = new TeachingFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("people_services_classify_01_id", info.getBaseInfoResult().getPeopleServicesClassify01s().get(0).getPeople_services_classify_01_id() + "");
        teachingFragment.setArguments(bundle2);

        ShopFragment shopFragment = new ShopFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putString("people_services_classify_01_id", info.getBaseInfoResult().getPeopleServicesClassify01s().get(1).getPeople_services_classify_01_id() + "");
        shopFragment.setArguments(bundle3);

        fragmentList = new ArrayList();
        fragmentList.add(cityFragment);
        fragmentList.add(teachingFragment);
        fragmentList.add(shopFragment);

        MyPagerAdapter myFragmentAdapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(myFragmentAdapter);
        pager.addOnPageChangeListener(tabOnPageChangeListener);
        pager.setCurrentItem(0);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    /**
     * ViewPager的适配器
     *
     * @author zj
     *         2012-5-24 下午2:26:57
     */
    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            return fragmentList.get(arg0);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
        /**
         * 注销super
         * 设置fragemnt不重新加载
         * @author pan
         * @time 2016/10/28 0028 上午 10:15
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            TLog.error("销毁"+position);
        }
    }

    ViewPager.OnPageChangeListener tabOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            cleantab(position);

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public void cleantab(int current) {

        oneTv.setTextColor(ContextCompat.getColor(this, R.color.black));
        oneIv.setVisibility(View.INVISIBLE);
        twoTv.setTextColor(ContextCompat.getColor(this, R.color.black));
        twoIv.setVisibility(View.INVISIBLE);
        threeTv.setTextColor(ContextCompat.getColor(this, R.color.black));
        threeIv.setVisibility(View.INVISIBLE);
        if (current == 0) {
            oneTv.setTextColor(ContextCompat.getColor(this, R.color.chengse));
            oneIv.setVisibility(View.VISIBLE);
        } else if (current == 1) {
            twoTv.setTextColor(ContextCompat.getColor(this, R.color.chengse));
            twoIv.setVisibility(View.VISIBLE);
        } else if (current == 2) {
            threeTv.setTextColor(ContextCompat.getColor(this, R.color.chengse));
            threeIv.setVisibility(View.VISIBLE);
        }
    }

    public void infoRequest() {
        BaseInfoRequest rq = new BaseInfoRequest();
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandlerget);
    }

    InfoResponse info;
    private final AsyncHttpResponseHandler mHandlerget = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, MyShopActivity.this, InfoResponse.class);
            if (kd != null) {
                info = (InfoResponse) kd;
                if (info.getStatus() == 1) {
                    initpage();

                    ApiHttpClient.loadImage(info.getBaseInfoResult().getUserInfo().getImage_01(), picIv, R.drawable.avatar_me);
                    nameTv.setText(info.getBaseInfoResult().getUserInfo().getNickname());
                    twoTv.setText(info.getBaseInfoResult().getPeopleServicesClassify01s().get(0).getPeople_services_classify_01_name());
                    threeTv.setText(info.getBaseInfoResult().getPeopleServicesClassify01s().get(1).getPeople_services_classify_01_name());
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


//    private void setTabSelection(int index) {
//        clearState();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        hideFragments(transaction);
//        switch (index) {
//            case 0:
//                oneTv.setTextColor(ContextCompat.getColor(this, R.color.chengse));
//                oneIv.setVisibility(View.VISIBLE);
//                if (cityFragment == null) {
//                    cityFragment = new CityFragment();
//                    transaction.add(R.id.content_two, cityFragment, "cityFragment");
//                } else {
//                    transaction.show(cityFragment);
//                }
//                break;
//            case 1:
//                twoTv.setTextColor(ContextCompat.getColor(this, R.color.chengse));
//                twoIv.setVisibility(View.VISIBLE);
//
//                if (teachingFragment == null) {
//                    teachingFragment = new TeachingFragment();
//                    transaction.add(R.id.content_two, teachingFragment, "teachingFragment");
//                } else {
//                    transaction.show(teachingFragment);
//                }
//                break;
//
//            case 2:
//                threeTv.setTextColor(ContextCompat.getColor(this, R.color.chengse));
//                threeIv.setVisibility(View.VISIBLE);
//
//                if (shopFragment == null) {
//                    shopFragment = new ShopFragment();
//                    transaction.add(R.id.content_two, shopFragment, "shopFragment");
//                } else {
//                    transaction.show(shopFragment);
//                }
//                break;
//        }
//        transaction.commit();
//    }

//    public void clearState() {
//        oneTv.setTextColor(ContextCompat.getColor(this, R.color.black));
//        oneIv.setVisibility(View.INVISIBLE);
//        twoTv.setTextColor(ContextCompat.getColor(this, R.color.black));
//        twoIv.setVisibility(View.INVISIBLE);
//        threeTv.setTextColor(ContextCompat.getColor(this, R.color.black));
//        threeIv.setVisibility(View.INVISIBLE);
//
//    }

//    private void hideFragments(FragmentTransaction transaction) {
//        if (cityFragment != null) {
//            transaction.hide(cityFragment);
//        }
//        if (teachingFragment != null) {
//
//            transaction.hide(teachingFragment);
//        }
//
//        if (shopFragment != null) {
//
//            transaction.hide(shopFragment);
//        }
//    }


}
