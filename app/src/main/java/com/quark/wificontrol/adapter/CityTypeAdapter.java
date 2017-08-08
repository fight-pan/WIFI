package com.quark.wificontrol.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quark.api.auto.bean.CityClassifys;
import com.quark.wificontrol.R;

import java.util.ArrayList;

/**
 * Created by pan on 2016/9/24 0024.
 * >#
 * >#同城信息分类列表
 */
public class CityTypeAdapter extends BaseAdapter {


    private Context context;
    private ArrayList<CityClassifys> list;

    public CityTypeAdapter(Context context, ArrayList<CityClassifys> list) {
        this.context = context;
        this.list = list;
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

        convertView = LayoutInflater.from(context).inflate(R.layout.type_item, null);
        TextView typeName = (TextView) convertView.findViewById(R.id.type_name);

        if (list != null && list.size() >= 0){
            typeName.setText(list.get(position).getCity_classify_01_name());
        }

        return convertView;

    }

}
