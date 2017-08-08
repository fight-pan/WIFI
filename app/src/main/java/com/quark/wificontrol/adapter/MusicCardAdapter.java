package com.quark.wificontrol.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.quark.api.auto.bean.MusicsClassifys;
import com.quark.wificontrol.R;
import com.quark.wificontrol.api.ApiHttpClient;
import com.quark.wificontrol.ui.find.MusicActivity;

import java.util.List;

/**
 * Created by
 */
public class MusicCardAdapter extends PagerAdapter {
    private List<MusicsClassifys> list;
    private Context context;
    private LayoutInflater inflater;

    public MusicCardAdapter(Context context, List<MusicsClassifys> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = inflater.inflate(R.layout.vp_item, container, false);
        ImageView musicIv = (ImageView) view.findViewById(R.id.music_iv);
        TextView musicName = (TextView) view.findViewById(R.id.music_title);

        container.addView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MusicActivity.class);
                intent.putExtra("music_classify_id",list.get(position).getMusic_classify_id()+"");
                intent.putExtra("music_title",list.get(position).getClassify_name());
                intent.putExtra("introduce",list.get(position).getIntroduce());
                context.startActivity(intent);
            }
        });

        if (list != null && list.size() > 0) {
            ApiHttpClient.loadImage(list.get(position).getImages_01(), musicIv,R.drawable.music);
            musicName.setText(list.get(position).getClassify_name());
        }
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
