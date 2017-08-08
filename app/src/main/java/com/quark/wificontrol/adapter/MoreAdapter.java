package com.quark.wificontrol.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.quark.api.auto.bean.ListCityInformation;
import com.quark.wificontrol.R;
import com.quark.wificontrol.api.ApiHttpClient;

import java.util.ArrayList;

/**
 * Created by pan on 2016/9/26 0026.
 * >#
 * >#
 */
public class MoreAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ListCityInformation> list;

    public MoreAdapter(Context context, ArrayList<ListCityInformation> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(R.layout.city_item, null);
        TextView titleT = (TextView) convertView.findViewById(R.id.title_tv);
        ImageView videoIv = (ImageView) convertView.findViewById(R.id.video_iv);
        TextView timeTv = (TextView) convertView.findViewById(R.id.date_tv);
        TextView removeTv = (TextView) convertView.findViewById(R.id.remove_tv);
        TextView addressTv = (TextView) convertView.findViewById(R.id.distance_tv);
        removeTv.setVisibility(View.GONE);

        titleT.setText(list.get(position).getTitle());
        ApiHttpClient.loadImage(list.get(position).getImages_01(), videoIv,R.drawable.video);
        timeTv.setText(list.get(position).getPost_date());
        addressTv.setText(list.get(position).getDistance());

        return convertView;

    }

}
