package com.quark.wificontrol.ui.find;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.CityInfoDetailRequest;
import com.quark.api.auto.bean.CityInfoDetailResponse;
import com.quark.api.auto.bean.ImageViewList;
import com.quark.wificontrol.AppContext;
import com.quark.wificontrol.AppParam;
import com.quark.wificontrol.R;
import com.quark.wificontrol.api.ApiHttpClient;
import com.quark.wificontrol.api.ApiResponse;
import com.quark.wificontrol.api.remote.QuarkApi;
import com.quark.wificontrol.base.BaseFragementActivity;
import com.quark.wificontrol.fragment.CommentFragment;
import com.quark.wificontrol.fragment.ContentFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by pan on 2016/9/8 0008.
 * >#
 * >#同城    详细信息
 */
public class GoodsDetailsActivity extends BaseFragementActivity implements EasyPermissions.PermissionCallbacks {

    @InjectView(R.id.one_tv)
    TextView oneTv;
    @InjectView(R.id.two_tv)
    TextView twoTv;
    @InjectView(R.id.pager)
    ViewPager pager;

    String product_id;
    int current = 0;
    @InjectView(R.id.collapsing)
    CollapsingToolbarLayout collapsing;
    @InjectView(R.id.comment_ly)
    LinearLayout commentLy;
    @InjectView(R.id.title_tv)
    TextView titleTv;
    @InjectView(R.id.date_tv)
    TextView dateTv;
    @InjectView(R.id.distance_tv)
    TextView distanceTv;
    @InjectView(R.id.ratingBar)
    RatingBar ratingBar;
    @InjectView(R.id.company_name)
    TextView companyName;
    @InjectView(R.id.image_iv)
    ImageView imageIv;
    @InjectView(R.id.company_address)
    TextView companyAddress;
    @InjectView(R.id.telephone_tv)
    TextView telephoneTv;

    private List<Fragment> fragmentList;

    String city_information_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_goodsdetails);
        ButterKnife.inject(this);
//        product_id = (String) getValue4Intent("product_id");


        city_information_id = (String) getValue4Intent("city_information_id");

        cityInfoDetailRequest();
    }


    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.view_tv, R.id.comment_ly, R.id.one_rly, R.id.two_rly, R.id.phone_ly, R.id.left_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_tv:
                List<ImageViewList> ivList = new ArrayList();
                String[] strImg = info.getCityInfoDetailResult().getCityInfoDetail().getImages_01().split("#");
                for (int i = 0;i<strImg.length; i++){
                    String img = strImg[i];
                    ImageViewList iv  = new ImageViewList(img);
                    ivList.add(iv);
                }
                Bundle bd = new Bundle();
                bd.putSerializable("imgs", (Serializable) ivList);
                Intent intent = new Intent(this, ShowImageActivity.class);
                intent.putExtras(bd);

                startActivity(intent);
                break;
            case R.id.comment_ly:
                Bundle bundle = new Bundle();
                bundle.putString("type", info.getCityInfoDetailResult().getCityInfoDetail().getUserAuditData().getType() + "");
                bundle.putString("id", info.getCityInfoDetailResult().getCityInfoDetail().getCity_information_id() + "");
                startActivityByClass(EvaluationActivity.class, bundle);
                break;
            case R.id.one_rly:
                current = 0;
                pager.setCurrentItem(0);
                break;
            case R.id.two_rly:
                current = 1;
                pager.setCurrentItem(1);
                break;
            case R.id.phone_ly:
                startByPermissions();
                break;
            case R.id.left_iv:
                finish();
                break;
        }
    }

    public void initpage() {
        ContentFragment contentFragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("city_information_id", city_information_id);
        contentFragment.setArguments(bundle);

        CommentFragment commentFragment = new CommentFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("city_information_id", city_information_id);
        bundle2.putString("type", info.getCityInfoDetailResult().getCityInfoDetail().getUserAuditData().getType() + "");
        commentFragment.setArguments(bundle2);

        fragmentList = new ArrayList();
        fragmentList.add(contentFragment);
        fragmentList.add(commentFragment);

        MyPagerAdapter myFragmentAdapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(myFragmentAdapter);
        pager.addOnPageChangeListener(tabOnPageChangeListener);
        pager.setCurrentItem(0);
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
        twoTv.setTextColor(ContextCompat.getColor(this, R.color.black));
        if (current == 0) {
            oneTv.setTextColor(ContextCompat.getColor(this, R.color.chengse));
        } else if (current == 1) {
            twoTv.setTextColor(ContextCompat.getColor(this, R.color.chengse));
        }
    }

    public void cityInfoDetailRequest() {
        CityInfoDetailRequest rq = new CityInfoDetailRequest();
        rq.setCity_information_id(city_information_id);
        rq.setLongitude(new AppParam().getLng(this));
        rq.setLatitude(new AppParam().getLat(this));
        showWait(true);
        QuarkApi.HttpRequestNewList(rq, mHandlerget);
    }

    CityInfoDetailResponse info;
    private final AsyncHttpResponseHandler mHandlerget = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, GoodsDetailsActivity.this, CityInfoDetailResponse.class);
            if (kd != null) {
                info = (CityInfoDetailResponse) kd;
                if (info.getStatus() == 1) {
                    initpage();
                    titleTv.setText(info.getCityInfoDetailResult().getCityInfoDetail().getTitle());
                    dateTv.setText("发布时间: " + info.getCityInfoDetailResult().getCityInfoDetail().getPost_date());
                    distanceTv.setText("距离: " + info.getCityInfoDetailResult().getCityInfoDetail().getDistance());
                    telephoneTv.setText(info.getCityInfoDetailResult().getCityInfoDetail().getUserAuditData().getReal_name() + ": " +
                            info.getCityInfoDetailResult().getCityInfoDetail().getUserAuditData().getTelephone());
                    ratingBar.setRating(info.getCityInfoDetailResult().getCityInfoDetail().getAverage_star());

                    String[] imgStr = info.getCityInfoDetailResult().getCityInfoDetail().getImages_01().split("#");
                    ApiHttpClient.loadImage(imgStr[0], imageIv, R.drawable.banner);
                    companyName.setText(info.getCityInfoDetailResult().getCityInfoDetail().getUserAuditData().getCompany_name());
                    companyAddress.setText(info.getCityInfoDetailResult().getCityInfoDetail().getProvince() +
                            info.getCityInfoDetailResult().getCityInfoDetail().getCity() + info.getCityInfoDetailResult().getCityInfoDetail().getArea() +
                            info.getCityInfoDetailResult().getCityInfoDetail().getShort_area());

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

    private void startByPermissions() {
        String[] perms = {Manifest.permission.CALL_PHONE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            call();
        } else {
            EasyPermissions.requestPermissions(this, "请求获取拨打电话权限", 10001, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        call();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Toast.makeText(this, "授权不正确,操作无法进行", Toast.LENGTH_LONG).show();
    }

    public void call() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + info.getCityInfoDetailResult().getCityInfoDetail().getUserAuditData().getTelephone());
        intent.setData(data);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);
    }
}
