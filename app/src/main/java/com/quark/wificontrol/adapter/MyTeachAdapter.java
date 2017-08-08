package com.quark.wificontrol.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.quark.api.auto.bean.ListPeopelServices;
import com.quark.wificontrol.R;
import com.quark.wificontrol.api.ApiHttpClient;

import java.util.ArrayList;

/**
 * Created by pan on 2016/9/26 0026.
 * >#
 * >#
 */
public class MyTeachAdapter extends RecyclerView.Adapter<MyTeachAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ListPeopelServices> list;
    Handler handler;

    public MyTeachAdapter(Context context, ArrayList<ListPeopelServices> list, Handler handler) {
        this.context = context;
        this.list = list;
        this.handler = handler;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.city_item, null));
    }

    //点击接口
    public interface OnItemClickLitener {
        /**
         * 点击事件处理
         *
         * @author pan
         * @time 2016/10/31 0031 上午 11:18
         */
        void onItemClick(View view, int position);

        /**
         * 长按点击事件处理
         *
         * @author pan
         * @time 2016/10/31 0031 上午 11:18
         */
        void onItemLongClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.removeTv.setText("删除");
        holder.removeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.what = 302;
                msg.arg1 = list.get(position).getPeople_services_id();
                msg.arg2 = holder.getLayoutPosition();
                handler.sendMessage(msg);

            }
        });

        holder.titleT.setText(list.get(position).getTitle());
        ApiHttpClient.loadImage(list.get(position).getImages_01(), holder.videoIv, R.drawable.nearby);
        holder.timeTv.setText(list.get(position).getPost_date());
        holder.distanTv.setText(list.get(position).getDistance());
//        holder.rly.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, PeopelDetailsActivity.class);
//                intent.putExtra("people_services_id", list.get(position).getPeople_services_id() + "");
//                context.startActivity(intent);
//            }
//        });

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleT;
        ImageView videoIv;
        TextView timeTv;
        TextView distanTv;
        TextView removeTv;
        RelativeLayout rly;

        public ViewHolder(View itemView) {
            super(itemView);
            titleT = (TextView) itemView.findViewById(R.id.title_tv);
            videoIv = (ImageView) itemView.findViewById(R.id.video_iv);
            timeTv = (TextView) itemView.findViewById(R.id.date_tv);
            distanTv = (TextView) itemView.findViewById(R.id.distance_tv);
            removeTv = (TextView) itemView.findViewById(R.id.remove_tv);
            rly = (RelativeLayout) itemView.findViewById(R.id.rly);

        }
    }

    public void remove(int i) {
        list.remove(i);
        notifyItemRemoved(i);
        notifyItemRangeChanged(i,list.size()-i);
    }

//    private Context context;
//    private ArrayList<ListPeopelServices> list;
//    Handler handler;
//
//    public MyTeachAdapter(Context context, ArrayList<ListPeopelServices> list, Handler handler) {
//        this.context = context;
//        this.list = list;
//        this.handler = handler;
//    }
//
//    @Override
//    public int getCount() {
//        return list.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//
//        convertView = LayoutInflater.from(context).inflate(R.layout.city_item, null);
//        TextView titleTv = (TextView) convertView.findViewById(R.id.title_tv);
//        ImageView videoIv = (ImageView) convertView.findViewById(R.id.video_iv);
//        TextView timeTv = (TextView) convertView.findViewById(R.id.date_tv);
//        TextView distanceTv = (TextView) convertView.findViewById(R.id.distance_tv);
//        TextView removeTv = (TextView) convertView.findViewById(R.id.remove_tv);
//
//        removeTv.setText("删除");
//        titleTv.setText(list.get(position).getTitle());
//        ApiHttpClient.loadImage(list.get(position).getImages_01(), videoIv);
//        timeTv.setText(list.get(position).getPost_date());
//        distanceTv.setText(list.get(position).getDistance());
//
//        removeTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Message msg = new Message();
//                msg.what = 302;
//                msg.arg1 = list.get(position).getPeople_services_id();
//                msg.arg2 = position;
//                handler.sendMessage(msg);
//            }
//        });
//
//        return convertView;
//
//    }

}
