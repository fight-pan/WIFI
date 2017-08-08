package com.quark.wificontrol.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.quark.api.auto.bean.Type;
import com.quark.wificontrol.R;

import java.util.List;

/**
 * Created by pan on 2016/9/29 0029.
 * >#
 * >#
 */
public class MsgAdapter extends BaseSwipeAdapter {
    private Context context;
    private List<Type> list;
    Handler handler;

    public MsgAdapter(Context context, List<Type> list, Handler handler) {
        this.context = context;
        this.list = list;
        this.handler = handler;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipeLayout;
    }

    @Override
    public View generateView(final int position, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.msg_swipelayout, null);
        return v;
    }

    @Override
    public void fillValues(final int position, View convertView) {

        TextView titleTv = (TextView) convertView.findViewById(R.id.title_tv);
        TextView msgTime = (TextView) convertView.findViewById(R.id.msg_time);
        TextView msgTv = (TextView) convertView.findViewById(R.id.msg_tv);

        titleTv.setText(list.get(position).getName());


    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
