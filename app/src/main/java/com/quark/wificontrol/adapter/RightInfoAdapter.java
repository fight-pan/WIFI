package com.quark.wificontrol.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.quark.api.auto.bean.CityInformations;
import com.quark.wificontrol.R;
import com.quark.wificontrol.api.ApiHttpClient;

import java.util.List;

/**
 * Created by pan on 2016/9/26 0026.
 * >#
 * >#
 */
public class RightInfoAdapter extends BaseAdapter {

//    private LayoutInflater mInflater;
//    private List<CityInformations> mDatas;
//
//    public RightInfoAdapter(Context context, List<CityInformations> mDatas) {
//        mInflater = LayoutInflater.from(context);
//        this.mDatas = mDatas;
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder
//    {
//        public ViewHolder(View arg0)
//        {
//            super(arg0);
//        }
//
//        TextView titleTv;
//        ImageView imageIv;
//
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//
//        View view = mInflater.inflate(R.layout.right_info_item,
//                viewGroup, false);
//        ViewHolder viewHolder = new ViewHolder(view);
//        viewHolder.titleTv = (TextView) view.findViewById(R.id.title_tv);
//        viewHolder.imageIv = (ImageView) view.findViewById(R.id.image_iv);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(final RightInfoAdapter.ViewHolder holder,final int position) {
//        holder.titleTv.setText(mDatas.get(position).getTitle());
//        ApiHttpClient.loadImage(mDatas.get(position).getImages_01(), holder.imageIv, R.drawable.nearby);
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return mDatas.size();
//    }


    private Context context;
    private List<CityInformations> list;

    public RightInfoAdapter(Context context, List<CityInformations> list) {
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

        convertView = LayoutInflater.from(context).inflate(R.layout.right_info_item, null);
        TextView titleTv = (TextView) convertView.findViewById(R.id.title_tv);
        ImageView imageIv = (ImageView) convertView.findViewById(R.id.image_iv);

        titleTv.setText(list.get(position).getTitle());
        ApiHttpClient.loadImage(list.get(position).getImages_01(), imageIv, R.drawable.nearby);

        return convertView;
    }

}
