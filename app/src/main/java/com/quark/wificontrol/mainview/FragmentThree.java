//package com.quark.wificontrol.mainview;
//
//import android.annotation.SuppressLint;
//import android.graphics.drawable.BitmapDrawable;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.PopupWindow;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.quark.api.auto.bean.Type;
//import com.quark.wificontrol.R;
//import com.quark.wificontrol.adapter.MsgAdapter;
//import com.quark.wificontrol.base.BaseFragment;
//import com.quark.wificontrol.util.Utils;
//
//import java.util.ArrayList;
//
//import butterknife.ButterKnife;
//import butterknife.InjectView;
//import butterknife.OnClick;
//import me.maxwin.view.XListView;
//
//@SuppressLint("ResourceAsColor")
//public class FragmentThree extends BaseFragment implements XListView.IXListViewListener{
//    @InjectView(R.id.add_rly)
//    RelativeLayout addRly;
//    @InjectView(R.id.lv)
//    XListView lv;
//
//    View threeViewt;
//    MsgAdapter adapter;
//    ArrayList<Type> datas;
//    int pn = 1;
//    int type = 1;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        threeViewt = inflater.inflate(R.layout.fragment_three, container, false);
//        ButterKnife.inject(this, threeViewt);
//        myinitlist();
//
//        initAllData();
//        return threeViewt;
//    }
//
//    public void initAllData() {
//        Type type = new Type("系统消息助手提醒您,这里是深圳");
//        datas.add(type);
//    }
//
//    public void myinitlist() {
//        datas = new ArrayList<>();
//        lv.setFooterDividersEnabled(false);
//        // 设置xlistview可以加载、刷新
//        lv.setPullLoadEnable(true);//购物车一次加载完 没有加载更多
//        lv.setPullRefreshEnable(true);
//        lv.setXListViewListener(this);
////        xlstv.setOnItemClickListener(listListener);
//        adapter = new MsgAdapter(getActivity(), datas,handler);
//        lv.setAdapter(adapter);
//        lv.setDivider(null);
//        Message msg = handler.obtainMessage();
//        msg.what = 1;
//        msg.arg1 = datas.size();
//        handler.sendMessage(msg);
//    }
//
//    Handler handler = new Handler() {
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            int pdatasize = msg.arg1;
//            lv.isnomore(pdatasize < 10);
//            switch (msg.what) {
//                case 1:
//                    lv.stopRefresh();
//                    adapter.notifyDataSetChanged();
//                    break;
//                case 2:
//                    lv.stopLoadMore();
//                    adapter.notifyDataSetChanged();
//                    break;
//                default:
//                    break;
//            }
//        }
//    };
//
//
//    @Override
//    public void onRefresh() {
//        pn = 1;
//        type = 1;
//    }
//
//    @Override
//    public void onLoadMore() {
//        type = 2;
//        pn++;
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        ButterKnife.reset(this);
//    }
//
//    @OnClick(R.id.add_rly)
//    public void onClick() {
//
//
//    }
//
//
//    @OnClick({R.id.add_rly})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.add_rly:
//                initPopupwindow(view);
//                break;
//        }
//    }
//
//    public void initPopupwindow(View v) {
//        LayoutInflater inflater = LayoutInflater.from(getActivity());
//        final View popview = inflater.inflate(R.layout.popupwindow, null);
//        final PopupWindow popupWindow;
//        LinearLayout per = (LinearLayout) popview.findViewById(R.id.per);
//        per.getBackground().setAlpha(180);
//        popupWindow = new PopupWindow(popview, Utils.dip2px(getActivity(), 110), Utils.dip2px(getActivity(), 120));
//        popupWindow.setFocusable(true);
//        popupWindow.setOutsideTouchable(false);
//        popupWindow.setBackgroundDrawable(new BitmapDrawable());
//        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//            }
//        });
//
//        per.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popupWindow.dismiss();
//            }
//        });
//
//        //添加好友
//        TextView all = (TextView) popview.findViewById(R.id.all);
//        all.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popupWindow.dismiss();
//
//            }
//        });
//
//        //创建群组
//        TextView yichang = (TextView) popview.findViewById(R.id.yichang);
//        yichang.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popupWindow.dismiss();
//
//            }
//        });
//
//        //通讯录
//        TextView email = (TextView) popview.findViewById(R.id.email);
//        email.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popupWindow.dismiss();
//
//            }
//        });
//        popupWindow.showAsDropDown(v);
//    }
//}
//
//
//
//
//
