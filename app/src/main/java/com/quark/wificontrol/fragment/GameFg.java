package com.quark.wificontrol.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.quark.wificontrol.R;
import com.quark.wificontrol.base.BaseFragment;
import com.quark.wificontrol.ui.widget.SlideFiveShowView;
import com.quark.wificontrol.util.TLog;

import butterknife.ButterKnife;
import butterknife.InjectView;

@SuppressLint("ResourceAsColor")
public class GameFg extends BaseFragment {
    View oneViewt;
    @InjectView(R.id.slideshowView)
    SlideFiveShowView slideshowView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        oneViewt = inflater.inflate(R.layout.fragment_gamefg, container, false);
        ButterKnife.inject(this, oneViewt);

        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        int sw = wm.getDefaultDisplay().getWidth();
        int pich = (int) ((sw * 275) / 750.0);
        TLog.error(pich + "pich lunbo");
        ViewGroup.LayoutParams params = slideshowView.getLayoutParams();
        params.height = pich;
        params.width = sw;
        slideshowView.setLayoutParams(params);


        return oneViewt;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}





