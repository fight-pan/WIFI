package com.quark.wificontrol.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quark.api.auto.bean.PeopleServicesClassify02s;
import com.quark.wificontrol.R;

import java.util.ArrayList;

/**
 * Created by pan on 2016/9/24 0024.
 * >#
 * >#
 */
public class TeachAdapter extends BaseAdapter {


    private Context context;
    private ArrayList<PeopleServicesClassify02s> list;

    public TeachAdapter(Context context, ArrayList<PeopleServicesClassify02s> list) {
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
            typeName.setText(list.get(position).getPeople_services_classify_02_name());
        }

        return convertView;

    }

}
