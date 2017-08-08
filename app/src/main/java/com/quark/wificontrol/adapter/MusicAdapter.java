package com.quark.wificontrol.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quark.api.auto.bean.Type;
import com.quark.wificontrol.R;

import java.util.ArrayList;

/**
 * Created by pan on 2016/9/26 0026.
 * >#
 * >#
 */
public class MusicAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Type> list;
    Handler handler;

    public MusicAdapter(Context context, ArrayList<Type> list, Handler handler) {
        this.context = context;
        this.list = list;
        this.handler = handler;
    }

    @Override
    public int getCount() {
        return 5;
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

        convertView = LayoutInflater.from(context).inflate(R.layout.music_item, null);
        TextView musicName = (TextView) convertView.findViewById(R.id.music_name);
        View headV = convertView.findViewById(R.id.head_v);

        if (position != 0 || position < list.size()){
            headV.setVisibility(View.GONE);
        }



        if (list != null && list.size() > 0) {
            musicName.setText(list.get(position).getName());
        }
        return convertView;

    }

}
