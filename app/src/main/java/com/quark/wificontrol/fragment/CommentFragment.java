package com.quark.wificontrol.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.CommentListRequest;
import com.quark.api.auto.bean.CommentListResponse;
import com.quark.api.auto.bean.ListComment;
import com.quark.wificontrol.AppContext;
import com.quark.wificontrol.R;
import com.quark.wificontrol.adapter.CommentAdapter;
import com.quark.wificontrol.api.ApiResponse;
import com.quark.wificontrol.api.remote.QuarkApi;
import com.quark.wificontrol.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cz.msebera.android.httpclient.Header;

@SuppressLint("ResourceAsColor")
public class CommentFragment extends BaseFragment {
    View oneViewt;
    ArrayList<ListComment> datas;
    CommentAdapter adapter;
    int pn = 1;
    int type = 1;
    String product_id;
    @InjectView(R.id.list)
    RecyclerView listView;

    String city_information_id;
    String tyPE;//1-同城，2-便民


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        oneViewt = inflater.inflate(R.layout.fragment_comment, container, false);
        ButterKnife.inject(this, oneViewt);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        listView.setLayoutManager(layoutManager);

        tyPE = getArguments().getString("type");
        city_information_id = getArguments().getString("city_information_id");
        commentListRequest();
        return oneViewt;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


    public void commentListRequest() {
        CommentListRequest rq = new CommentListRequest();
        rq.setCity_information_id(city_information_id);
        rq.setType(tyPE);
        rq.setPn(pn);
        rq.setPage_size(500);
        showWait(true);
        QuarkApi.HttpRequestNewList(rq, mHandlerget);
    }

    private final AsyncHttpResponseHandler mHandlerget = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, getActivity(), CommentListResponse.class);
            if (kd != null) {
                CommentListResponse info = (CommentListResponse) kd;
                if (info.getStatus() == 1) {
                        datas = new ArrayList();
                        List<ListComment> list = info.getCommentListResult().getCommentList().getList();
                        if (list != null && list.size() > 0) {
                            datas.addAll(list);
                            adapter = new CommentAdapter(getActivity(), datas);
                            listView.setAdapter(adapter);
                        } else {
                            ListComment empty = new ListComment();
                            datas.add(empty);
                            adapter = new CommentAdapter(getActivity(), datas);
                            listView.setAdapter(adapter);
                        }
                    } else {
                        showToast(info.getMessage());
                    }
                }
                showWait(false);
            }

            @Override
            public void onFailure ( int arg0, Header[] arg1,byte[] arg2, Throwable arg3){
                AppContext.showToast("网络出错" + arg0);
                showWait(false);
            }
        };
    }





