package com.quark.wificontrol.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.quark.api.auto.bean.ListMovies;
import com.quark.wificontrol.R;

import java.util.ArrayList;

/**
 * Created by pan on 2016/9/26 0026.
 * >#
 * >#
 */
public class VideoFgAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ListMovies> list;
    Handler handler;

    public VideoFgAdapter(Context context, ArrayList<ListMovies> list, Handler handler) {
        this.context = context;
        this.list = list;
        this.handler = handler;
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

        convertView = LayoutInflater.from(context).inflate(R.layout.video_item, null);
        TextView titleTv = (TextView) convertView.findViewById(R.id.title_tv);
        ImageView videoIv = (ImageView) convertView.findViewById(R.id.video_iv);
        TextView timeTv = (TextView) convertView.findViewById(R.id.title_tv);
        TextView removeTv = (TextView) convertView.findViewById(R.id.remove_tv);

        Drawable nav_up = ContextCompat.getDrawable(context, R.drawable.download);
                	nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
        removeTv.setCompoundDrawables(nav_up, null, null, null);
        removeTv.setText("缓存本地");
        removeTv.setTextColor(ContextCompat.getColor(context,R.color.qianhuise));


        titleTv.setText(list.get(position).getTitle());



        return convertView;

    }

}
