package com.quark.wificontrol.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quark.api.auto.bean.CityClassifyBeans;
import com.quark.wificontrol.R;

import java.util.ArrayList;

/**
 * Created by pan on 2016/9/26 0026.
 * >#
 * >#
 */
public class LeftAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<CityClassifyBeans> list;
    public int currentposition = 0;

    public LeftAdapter(Context context, ArrayList<CityClassifyBeans> list) {
        this.context = context;
        this.list = list;
    }

    public void setCurrentPosition(int currentposition) {
        this.currentposition = currentposition;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(R.layout.left_item, null);
        TextView leftName = (TextView) convertView.findViewById(R.id.left_name);

        leftName.setText(list.get(position).getCity_classify_01_name());
        if (position == currentposition) {
            leftName.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
            leftName.setTextColor(ContextCompat.getColor(context, R.color.chengse));
        } else {
            leftName.setBackgroundColor(ContextCompat.getColor(context, R.color.beijing));
        }

        return convertView;

    }

}
