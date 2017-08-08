package com.quark.wificontrol.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quark.api.auto.bean.ListMusic;
import com.quark.wificontrol.R;

import java.util.ArrayList;

/**
 * Created by pan on 2016/9/26 0026.
 * >#
 * >#
 */
public class MusicPagerAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ListMusic> list;
    Handler handler;

    public MusicPagerAdapter(Context context, ArrayList<ListMusic> list, Handler handler) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.music_item_pager, null);
        TextView musicName = (TextView) convertView.findViewById(R.id.music_name);
        LinearLayout play_ly = (LinearLayout)convertView.findViewById(R.id.play_ly);
        ImageView itplay = (ImageView)convertView.findViewById(R.id.itplay);

        musicName.setText(list.get(position).getTitle());
        play_ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.what = 10;
                msg.arg1 = position;
                handler.sendMessage(msg);
            }
        });
        if (list.get(position).isplaying){
            itplay.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.pause));
        }

        return convertView;

    }

}
