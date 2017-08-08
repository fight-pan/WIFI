package com.quark.wificontrol.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.CityClassify02s;
import com.quark.api.auto.bean.CityClassifyBeans;
import com.quark.api.auto.bean.CityClassifyListRequest;
import com.quark.api.auto.bean.CityClassifyListResponse;
import com.quark.wificontrol.AppContext;
import com.quark.wificontrol.R;
import com.quark.wificontrol.adapter.LeftAdapter;
import com.quark.wificontrol.api.ApiResponse;
import com.quark.wificontrol.api.remote.QuarkApi;
import com.quark.wificontrol.base.BaseFragment;
import com.quark.wificontrol.ui.find.PositioningActivity;
import com.quark.wificontrol.ui.find.SearchActivity;
import com.quark.wificontrol.ui.widget.SlideThreeShowView;
import com.quark.wificontrol.util.TLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

@SuppressLint("ResourceAsColor")
public class CityFg extends BaseFragment {
    View oneViewt;
    @InjectView(R.id.left_lv)
    ListView leftLv;

    Map<String, CategoryFragment> frg = new HashMap<>();

    ArrayList<CityClassifyBeans> datas;
    ArrayList<CityClassify02s> rdatas;

    LeftAdapter leftAdapter;
    @InjectView(R.id.city_ty)
    TextView cityTy;
    @InjectView(R.id.slideshowView)
    SlideThreeShowView slideshowView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        oneViewt = inflater.inflate(R.layout.fragment_cityfg, container, false);
        ButterKnife.inject(this, oneViewt);

        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        int sw = wm.getDefaultDisplay().getWidth();
        int pich = (int) ((sw * 275) / 750.0);
        TLog.error(pich + "pich lunbo");
        ViewGroup.LayoutParams params = slideshowView.getLayoutParams();
        params.height = pich;
        params.width = sw;
        slideshowView.setLayoutParams(params);


        datas = new ArrayList<>();
        rdatas = new ArrayList<>();

        cityClassListRequest();
        return oneViewt;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


    @OnClick({R.id.city_ty, R.id.search_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.city_ty:
                Intent inte = new Intent(getActivity(), PositioningActivity.class);
                startActivityForResult(inte, 110);
                break;
            case R.id.search_tv:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode != 0) {
            if (resultCode == 110) {
                String cityStr = data.getStringExtra("city");
                cityTy.setText(cityStr);

            }
        }
    }


    public void cityClassListRequest() {
        CityClassifyListRequest rq = new CityClassifyListRequest();
        rq.setCity(cityTy.getText().toString());
        showWait(true);
        QuarkApi.HttpRequestNewList(rq, mHandlerget);
    }

    private final AsyncHttpResponseHandler mHandlerget = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, getActivity(), CityClassifyListResponse.class);
            if (kd != null) {
                CityClassifyListResponse info = (CityClassifyListResponse) kd;
                if (info.getStatus() == 1) {

                    if (info.getCityClassifyListResult().getCityClassifyBeans() != null && info.getCityClassifyListResult().getCityClassifyBeans().size() > 0) {
                        List<CityClassifyBeans> list = info.getCityClassifyListResult().getCityClassifyBeans();
                        datas.addAll(list);
                        leftAdapter = new LeftAdapter(getActivity(), datas);
                        leftLv.setAdapter(leftAdapter);
                        leftLv.setOnItemClickListener(cclick);
                        showone();
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

    public void showone() {
        leftAdapter.setCurrentPosition(0);
        leftAdapter.notifyDataSetChanged();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        String city_classify_01_id = datas.get(0).getCity_classify_01_id() + "";
        hideFragments(fragmentTransaction);
        if (frg.containsKey(city_classify_01_id)) {
            fragmentTransaction.show(frg.get(city_classify_01_id));
        } else {
            CategoryFragment myFragment = new CategoryFragment();
            fragmentTransaction.add(R.id.fragment_container, myFragment, city_classify_01_id);
            Bundle bundle = new Bundle();
            bundle.putString("city_classify_01_id", city_classify_01_id);
            bundle.putString("city", cityTy.getText().toString());
            myFragment.setArguments(bundle);
            frg.put(city_classify_01_id, myFragment);
        }
        fragmentTransaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        for (Map.Entry<String, CategoryFragment> entry : frg.entrySet()) {
            if (entry.getValue() != null) {
                transaction.hide(entry.getValue());
            }
        }
    }

    AdapterView.OnItemClickListener cclick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            leftAdapter.setCurrentPosition(position);
            leftAdapter.notifyDataSetChanged();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            String city_classify_01_id = datas.get(position).getCity_classify_01_id() + "";
            hideFragments(fragmentTransaction);
            if (frg.containsKey(city_classify_01_id)) {
                fragmentTransaction.show(frg.get(city_classify_01_id));
            } else {
                CategoryFragment myFragment = new CategoryFragment();
                fragmentTransaction.add(R.id.fragment_container, myFragment, city_classify_01_id);
                Bundle bundle = new Bundle();
                bundle.putString("city_classify_01_id", city_classify_01_id);
                bundle.putString("city", cityTy.getText().toString());
                myFragment.setArguments(bundle);
                frg.put(city_classify_01_id, myFragment);
            }
            fragmentTransaction.commit();
        }
    };
}





