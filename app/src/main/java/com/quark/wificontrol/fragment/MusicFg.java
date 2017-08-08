package com.quark.wificontrol.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.MusicClassifysResponse;
import com.quark.api.auto.bean.MusicsClassifys;
import com.quark.api.auto.bean.MusicsClassifysRequest;
import com.quark.wificontrol.AppContext;
import com.quark.wificontrol.R;
import com.quark.wificontrol.adapter.MusicCardAdapter;
import com.quark.wificontrol.api.ApiResponse;
import com.quark.wificontrol.api.remote.QuarkApi;
import com.quark.wificontrol.base.BaseFragment;
import com.quark.wificontrol.ui.widget.SlideOneShowView;
import com.quark.wificontrol.util.ScaleTransformer;
import com.quark.wificontrol.util.TLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cz.msebera.android.httpclient.Header;

@SuppressLint("ResourceAsColor")
public class MusicFg extends BaseFragment {
    View oneViewt;
    @InjectView(R.id.viewpager)
    ViewPager viewPager;
    MusicCardAdapter adapter;

    ArrayList<MusicsClassifys> datas;
    @InjectView(R.id.slideshowView)
    SlideOneShowView slideshowView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        oneViewt = inflater.inflate(R.layout.fragment_musicfg, container, false);
        ButterKnife.inject(this, oneViewt);
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        int sw = wm.getDefaultDisplay().getWidth();
        int pich = (int) ((sw * 275) / 750.0);
        TLog.error(pich + "pich lunbo");
        ViewGroup.LayoutParams params = slideshowView.getLayoutParams();
        params.height = pich;
        params.width = sw;
        slideshowView.setLayoutParams(params);

        musicRequest();
        return oneViewt;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public void musicRequest() {
        MusicsClassifysRequest rq = new MusicsClassifysRequest();
        showWait(true);
        QuarkApi.HttpRequestNewList(rq, mHandlerget);
    }

    private final AsyncHttpResponseHandler mHandlerget = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, getActivity(), MusicClassifysResponse.class);
            if (kd != null) {
                MusicClassifysResponse info = (MusicClassifysResponse) kd;
                if (info.getStatus() == 1) {
                    if (info.getMusicsClassifys() != null && info.getMusicsClassifys().size() > 0) {
                        List<MusicsClassifys> list = info.getMusicsClassifys();
                        datas = new ArrayList<>();
                        datas.addAll(list);
                        adapter = new MusicCardAdapter(getActivity(), datas);
                        viewPager.setAdapter(adapter);
                        viewPager.setPageMargin((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                                48, getResources().getDisplayMetrics()));
                        viewPager.setPageTransformer(false, new ScaleTransformer(getActivity()));
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
}





