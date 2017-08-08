package com.quark.wificontrol.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.DeleteMyPublicRequest;
import com.quark.api.auto.bean.DeleteMyPublicResponse;
import com.quark.api.auto.bean.ListCityInformation;
import com.quark.api.auto.bean.MyCityInfoListRequest;
import com.quark.api.auto.bean.MyCityInfoListResponse;
import com.quark.wificontrol.AppContext;
import com.quark.wificontrol.AppParam;
import com.quark.wificontrol.R;
import com.quark.wificontrol.adapter.CityAdapter;
import com.quark.wificontrol.api.ApiResponse;
import com.quark.wificontrol.api.remote.QuarkApi;
import com.quark.wificontrol.base.BaseFragment;
import com.quark.wificontrol.ui.find.GoodsDetailsActivity;
import com.quark.wificontrol.util.TLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cz.msebera.android.httpclient.Header;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

@SuppressLint("ResourceAsColor")
public class CityFragment extends BaseFragment {
    View oneViewt;
    @InjectView(R.id.xlstv)
    RecyclerView xlstv;

    ArrayList<ListCityInformation> datas;
    CityAdapter adapter;
    String city_information_id;

    int delete_id;
    @InjectView(R.id.nodata)
    TextView nodata;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        oneViewt = inflater.inflate(R.layout.fragment_city, container, false);
        ButterKnife.inject(this, oneViewt);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        xlstv.setLayoutManager(layoutManager);

        //设置动画
        xlstv.setItemAnimator(new SlideInLeftAnimator());
        xlstv.getItemAnimator().setRemoveDuration(1000);
        myCityInfoListRequest();
        return oneViewt;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 301:
                    city_information_id = msg.arg1 + "";
                    delete_id = msg.arg2;
                    deleteRequest();
                    break;
                default:
                    break;
            }
        }
    };


    int pn = 1;

    public void myCityInfoListRequest() {
        MyCityInfoListRequest rq = new MyCityInfoListRequest();
        rq.setPn(pn);
        rq.setPage_size(500);
        rq.setLatitude(new AppParam().getLat(getActivity()));
        rq.setLongitude(new AppParam().getLng(getActivity()));
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandlerget);
        TLog.error("这里是同城信息的数据+++++++++++++++++");
    }

    private final AsyncHttpResponseHandler mHandlerget = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, getActivity(), MyCityInfoListResponse.class);
            if (kd != null) {
                MyCityInfoListResponse info = (MyCityInfoListResponse) kd;
                if (info.getStatus() == 1) {
                    datas = new ArrayList();
                    List<ListCityInformation> tin = info.getMyCityInfoListResult().getMyCityInfoList().getList();
                    if (tin.size() > 0 && tin != null) {
                        datas.addAll(tin);
                        adapter = new CityAdapter(getActivity(), datas, handler);
                        xlstv.setAdapter(adapter);
                        adapter.setOnItemClickLitener(new CityAdapter.OnItemClickLitener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(getActivity(), GoodsDetailsActivity.class);
                                intent.putExtra("city_information_id", datas.get(position).getCity_information_id() + "");
                                startActivity(intent);

                            }

                            @Override
                            public void onItemLongClick(View view, int position) {

                            }
                        });
                    } else {
                        xlstv.setVisibility(View.GONE);
                        nodata.setVisibility(View.VISIBLE);
                    }
//                    dealData(info);
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

    /**
     * 删除发布接口
     *
     * @author pan
     * @time 2016/10/10 0010 下午 5:37
     */
    public void deleteRequest() {
        DeleteMyPublicRequest rq = new DeleteMyPublicRequest();
        rq.setType("1");//1-同城信息，2-其他
        rq.setInfo_id(city_information_id);
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandlerdel);
    }

    private final AsyncHttpResponseHandler mHandlerdel = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, getActivity(), DeleteMyPublicResponse.class);
            if (kd != null) {
                DeleteMyPublicResponse info = (DeleteMyPublicResponse) kd;
                if (info.getCode() == 200) {
                    adapter.remove(delete_id);
                    if (datas.size() == 0) {
                        xlstv.setVisibility(View.GONE);
                        nodata.setVisibility(View.VISIBLE);
                    }
                    showToast(info.getMessage());

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





