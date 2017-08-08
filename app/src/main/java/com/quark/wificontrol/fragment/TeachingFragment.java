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
import android.widget.AdapterView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.DeleteMyPublicRequest;
import com.quark.api.auto.bean.DeleteMyPublicResponse;
import com.quark.api.auto.bean.ListPeopelServices;
import com.quark.api.auto.bean.MyPeopelServicesListRequest;
import com.quark.api.auto.bean.MyPeopelServicesListResponse;
import com.quark.wificontrol.AppContext;
import com.quark.wificontrol.AppParam;
import com.quark.wificontrol.R;
import com.quark.wificontrol.adapter.MyTeachAdapter;
import com.quark.wificontrol.api.ApiResponse;
import com.quark.wificontrol.api.remote.QuarkApi;
import com.quark.wificontrol.base.BaseFragment;
import com.quark.wificontrol.ui.find.PeopelDetailsActivity;
import com.quark.wificontrol.util.TLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cz.msebera.android.httpclient.Header;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import me.maxwin.view.XListView;

@SuppressLint("ResourceAsColor")
public class TeachingFragment extends BaseFragment implements XListView.IXListViewListener {
    View oneViewt;
    @InjectView(R.id.xlstv)
    RecyclerView xlstv;
    ArrayList<ListPeopelServices> datas;
    MyTeachAdapter adapter;

    String people_services_classify_01_id;
    int people_services_id;
    int delete_id;
    @InjectView(R.id.nodata)
    TextView nodata;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        oneViewt = inflater.inflate(R.layout.fragment_teaching, container, false);
        ButterKnife.inject(this, oneViewt);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        xlstv.setLayoutManager(layoutManager);

        people_services_classify_01_id = getArguments().getString("people_services_classify_01_id");
        // 设置item动画
        xlstv.setItemAnimator(new SlideInLeftAnimator());
        xlstv.getItemAnimator().setRemoveDuration(1000);
//        myinitlist();
        myPeopelServicesListRequest();
        return oneViewt;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public void myinitlist() {
        datas = new ArrayList();
//        xlstv.setFooterDividersEnabled(false);
        // 设置xlistview可以加载、刷新
//        xlstv.setPullLoadEnable(true);//购物车一次加载完 没有加载更多
//        xlstv.setPullRefreshEnable(true);
//        xlstv.setXListViewListener(this);
//        xlstv.setOnItemClickListener(listListener);
        adapter = new MyTeachAdapter(getActivity(), datas, handler);
        xlstv.setAdapter(adapter);
//        xlstv.setDivider(null);
        Message msg = handler.obtainMessage();
        msg.what = 1;
        msg.arg1 = datas.size();
        handler.sendMessage(msg);
    }

    AdapterView.OnItemClickListener listListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            position = position - 1;
            if (position < datas.size()) {
                Intent intent = new Intent(getActivity(), PeopelDetailsActivity.class);
                intent.putExtra("people_services_id", datas.get(position).getPeople_services_id() + "");
                startActivity(intent);
            }
        }
    };

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
//                    xlstv.setDataSize(msg.arg1);
//                    xlstv.stopRefresh();
                    adapter.notifyDataSetChanged();
                    break;
                case 2:
//                    xlstv.setDataSize(msg.arg1);
//                    xlstv.stopLoadMore();
                    adapter.notifyDataSetChanged();
                    break;
                case 302:
                    people_services_id = msg.arg1;
                    delete_id = msg.arg2;
                    deleteRequest();
                    break;

                default:
                    break;
            }
        }
    };

    int type = 1;
    int pn = 1;

    @Override
    public void onRefresh() {
        pn = 1;
        type = 1;
        myPeopelServicesListRequest();
    }

    @Override
    public void onLoadMore() {
        type = 2;
        pn++;
        myPeopelServicesListRequest();
    }

    public void myPeopelServicesListRequest() {
        MyPeopelServicesListRequest rq = new MyPeopelServicesListRequest();
        rq.setPeople_services_classify_01_id(people_services_classify_01_id);
        rq.setLatitude(new AppParam().getLat(getActivity()));
        rq.setLongitude(new AppParam().getLng(getActivity()));
        rq.setPn(pn);
        rq.setPage_size(500);
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandlerget);
        TLog.error("这里是私人教学的数据");
    }

    private final AsyncHttpResponseHandler mHandlerget = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, getActivity(), MyPeopelServicesListResponse.class);
            if (kd != null) {
                MyPeopelServicesListResponse info = (MyPeopelServicesListResponse) kd;
                if (info.getStatus() == 1) {

                    datas = new ArrayList();
                    List<ListPeopelServices> tin = info.getMyPeopelServicesListResult().getMyPeopelServicesList().getList();
                    if (tin.size() > 0 && tin != null) {
                        datas.addAll(tin);
                        adapter = new MyTeachAdapter(getActivity(), datas, handler);
                        xlstv.setAdapter(adapter);
                        adapter.setOnItemClickLitener(new MyTeachAdapter.OnItemClickLitener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(getActivity(), PeopelDetailsActivity.class);
                                intent.putExtra("people_services_id", datas.get(position).getPeople_services_id() + "");
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

    public void dealData(MyPeopelServicesListResponse info) {
        if (type == 1) {
            datas.clear();
        }
        List<ListPeopelServices> tin = info.getMyPeopelServicesListResult().getMyPeopelServicesList().getList();
        Message msg = handler.obtainMessage();
        msg.what = type;
        if (tin != null && tin.size() > 0) {
            datas.addAll(tin);
            msg.arg1 = tin.size();
        } else {
            msg.arg1 = 0;
        }
        handler.sendMessage(msg);
    }

    /**
     * 删除发布接口
     *
     * @author pan
     * @time 2016/10/10 0010 下午 5:37
     */
    public void deleteRequest() {
        DeleteMyPublicRequest rq = new DeleteMyPublicRequest();
        rq.setType("2");//1-同城信息，2-其他
        rq.setInfo_id(people_services_id + "");
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

//                    datas.remove(delete_id);
////                    adapter.notifyItemRemoved(delete_id);
//                    adapter.notifyDataSetChanged();
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





