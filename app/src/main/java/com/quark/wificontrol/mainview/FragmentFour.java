package com.quark.wificontrol.mainview;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.BaseInfoRequest;
import com.quark.api.auto.bean.InfoResponse;
import com.quark.wificontrol.AppContext;
import com.quark.wificontrol.AppParam;
import com.quark.wificontrol.R;
import com.quark.wificontrol.api.ApiHttpClient;
import com.quark.wificontrol.api.ApiResponse;
import com.quark.wificontrol.api.remote.QuarkApi;
import com.quark.wificontrol.base.BaseFragment;
import com.quark.wificontrol.easechat.utils.PreferenceManager;
import com.quark.wificontrol.ui.personalCentel.InfoPreviewActivity;
import com.quark.wificontrol.ui.personalCentel.MyCacheActivity;
import com.quark.wificontrol.ui.personalCentel.MyShopActivity;
import com.quark.wificontrol.ui.personalCentel.OpenShopActivity;
import com.quark.wificontrol.ui.personalCentel.PersonalInfoActivity;
import com.quark.wificontrol.ui.personalCentel.SelectTypeActivity;
import com.quark.wificontrol.ui.user.AboutmeActivity;
import com.quark.wificontrol.ui.user.LoginActivity;
import com.quark.wificontrol.ui.widget.CircularImage;
import com.quark.wificontrol.util.TLog;
import com.quark.wificontrol.util.Utils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;


@SuppressLint("ResourceAsColor")
public class FragmentFour extends BaseFragment {
    View fourViewt;
    @InjectView(R.id.icon_1)
    LinearLayout icon1;
    @InjectView(R.id.icon_2)
    LinearLayout icon2;
    @InjectView(R.id.icon_3)
    LinearLayout icon3;
    @InjectView(R.id.shop_iv)
    ImageView shopIv;
    @InjectView(R.id.name_tv)
    TextView nameTv;
    @InjectView(R.id.pic_iv)
    CircularImage picIv;
    @InjectView(R.id.icon_0_v)
    View icon0V;
    @InjectView(R.id.icon_1_v)
    View icon1V;
    @InjectView(R.id.icon_2_v)
    View icon2V;
    @InjectView(R.id.icon_0)
    LinearLayout icon0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fourViewt = inflater.inflate(R.layout.fragment_four, container, false);
        ButterKnife.inject(this, fourViewt);

        if (new AppParam().isLogin(getActivity())) {
            infoRequest();
        } else {
            showToast("请先登录");
        }
        //发送广播
        registerBoradcastReceiver();
        return fourViewt;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.pic_iv, R.id.icon_0, R.id.icon_1, R.id.icon_2, R.id.icon_3})
    public void onClick(View view) {
        if (new AppParam().isLogin(getActivity())) {
            switch (view.getId()) {
                case R.id.pic_iv:
                    Intent intent = new Intent(getActivity(), PersonalInfoActivity.class);
                    startActivity(intent);
                    break;
                case R.id.icon_0:
                    if (!Utils.isEmpty(new AppParam().getType(getActivity()))) {
                        Intent in = new Intent(getActivity(), InfoPreviewActivity.class);
                        in.putExtra("type", new AppParam().getType(getActivity()));
                        startActivity(in);
                    }else {
                        Intent in0 = new Intent(getActivity(), OpenShopActivity.class);
                        startActivity(in0);
                    }
                    break;
                case R.id.icon_1:
                    Intent in1 = new Intent(getActivity(), SelectTypeActivity.class);
                    startActivity(in1);
                    break;
                case R.id.icon_2:
                    Intent in2 = new Intent(getActivity(), MyShopActivity.class);
                    startActivity(in2);
                    break;
                case R.id.icon_3:
                    Intent in3 = new Intent(getActivity(), MyCacheActivity.class);
                    startActivity(in3);
                    break;
            }
        } else {
            startActivity(new Intent().setClass(getActivity(), LoginActivity.class));
        }
    }

    @OnClick(R.id.icon_about)
    public void ab(View b) {
        Intent in4 = new Intent(getActivity(), AboutmeActivity.class);
        startActivity(in4);
    }

    public void infoRequest() {
        BaseInfoRequest rq = new BaseInfoRequest();
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandlerget);
    }

    private final AsyncHttpResponseHandler mHandlerget = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, getActivity(), InfoResponse.class);
            if (kd != null) {
                InfoResponse info = (InfoResponse) kd;
                if (info.getStatus() == 1) {
                    //更新环信头像
                    PreferenceManager.getInstance().setCurrentUserAvatar(ApiHttpClient.loadImage + info.getBaseInfoResult().getUserInfo().getImage_01());

                    ApiHttpClient.loadImage(info.getBaseInfoResult().getUserInfo().getImage_01(), picIv, R.drawable.avatar_me);
                    nameTv.setText(info.getBaseInfoResult().getUserInfo().getNickname());

                    int type = info.getBaseInfoResult().getUserInfo().getUser_level();
                    //商家或者创业者标示  根据用户的身份显示
                    if (type == 0 || type == 1 || type == 3) {
                        shopIv.setVisibility(View.GONE);
                        icon1.setVisibility(View.GONE);
                        icon1V.setVisibility(View.GONE);
                        icon2.setVisibility(View.GONE);
                        icon2V.setVisibility(View.GONE);
                    } else if (type == 20 || type == 21) {
                        {
                            shopIv.setVisibility(View.VISIBLE);
                            icon0V.setVisibility(View.GONE);
                            icon0.setVisibility(View.GONE);
                        }
                    }

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

    // 注册广播
    private ReceiveBroadCast receiveBroadCast;

    public void registerBoradcastReceiver() {
        receiveBroadCast = new ReceiveBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("four"); // 只有持有相同的action的接受者才能接收此广播
        getActivity().registerReceiver(receiveBroadCast, filter);
    }

    public class ReceiveBroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent data) {
            String position = data.getStringExtra("position");
            if (position.equals("1")) {
                //头像
                infoRequest();
            }
        }
    }

    @Override
    public void onDestroy() {
        try {
            getActivity().unregisterReceiver(receiveBroadCast); // 注销广播接收器
        } catch (Exception e) {
            TLog.error("fragment 销毁出错");
        }
        super.onDestroy();
    }
}





