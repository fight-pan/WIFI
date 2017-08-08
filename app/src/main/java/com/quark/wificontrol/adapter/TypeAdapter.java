package com.quark.wificontrol.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quark.api.auto.bean.PeopleClassifys;
import com.quark.wificontrol.R;

import java.util.ArrayList;

/**
 * Created by pan on 2016/9/24 0024.
 * >#
 * >#
 */
public class TypeAdapter extends BaseAdapter {


    private Context context;
    private ArrayList<PeopleClassifys> list;

    public TypeAdapter(Context context, ArrayList<PeopleClassifys> list) {
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
            typeName.setText(list.get(position).getPeople_services_classify_01_name());
        }

        return convertView;

    }

}
