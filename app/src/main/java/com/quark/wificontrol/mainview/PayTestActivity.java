package com.quark.wificontrol.mainview;

import android.os.Bundle;

import com.quark.wificontrol.R;
import com.quark.wificontrol.base.BaseActivity;

import butterknife.ButterKnife;

/**
 * Created by pan on 2016/9/9 0009.
 * >#
 * >#关于我们
 */
public class PayTestActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_one);
        ButterKnife.inject(this);
        setTopTitle("支付测试");
        setBackButton();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }


}
