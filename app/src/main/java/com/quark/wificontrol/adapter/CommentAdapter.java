package com.quark.wificontrol.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.quark.api.auto.bean.ListComment;
import com.quark.wificontrol.AppParam;
import com.quark.wificontrol.R;
import com.quark.wificontrol.api.ApiHttpClient;
import com.quark.wificontrol.ui.widget.CircularImage;
import com.quark.wificontrol.util.TLog;
import com.quark.wificontrol.util.Utils;

import java.util.ArrayList;


/**
 * Created by pan on 2016/9/7 0007.
 * >#
 * >#评论列表
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ListComment> list;

    public CommentAdapter(Context context, ArrayList<ListComment> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.comment_item, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        int sw = new AppParam().getScreenWidth(context);

        TLog.error("宽度"+sw);
//        ViewGroup.LayoutParams params2 = holder.layout.getLayoutParams();
//        params2.width = sw;
//        holder.layout.setLayoutParams(params2);

        if (!Utils.isEmpty(list.get(position).getContent())) {
            ApiHttpClient.loadImage(list.get(position).getUser_image_01(), holder.picIV, R.drawable.avatar_c);
            holder.commentTv.setText(list.get(position).getContent());
            holder.commentTime.setText(list.get(position).getPost_date());
            holder.commentName.setText(list.get(position).getUser_nickname());
            holder.ratingBar.setNumStars(list.get(position).getStar());
        } else {
            holder.layout.setVisibility(View.GONE);
            ViewGroup.LayoutParams params3 = holder.nodata.getLayoutParams();
            params3.width = sw;
            holder.nodata.setLayoutParams(params3);
            holder.nodata.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircularImage picIV;
        TextView commentName;
        TextView commentTime;
        TextView commentTv;
        RatingBar ratingBar;

        LinearLayout layout;
        TextView nodata;

        public ViewHolder(View itemView) {
            super(itemView);
            picIV = (CircularImage) itemView.findViewById(R.id.pic_iv);
            ;
            commentName = (TextView) itemView.findViewById(R.id.comment_name);
            ;
            commentTime = (TextView) itemView.findViewById(R.id.comment_time);
            ;
            commentTv = (TextView) itemView.findViewById(R.id.comment_tv);
            ;
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            ;
            layout = (LinearLayout) itemView.findViewById(R.id.layout);
            nodata = (TextView) itemView.findViewById(R.id.nodata);
        }
    }

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
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder holder;
//        if (convertView == null) {
//            holder = new ViewHolder();
//            convertView = LayoutInflater.from(context).inflate(R.layout.comment_item, null);
//            holder.picIV = (CircularImage) convertView.findViewById(R.id.pic_iv);
//            holder.commentName = (TextView) convertView.findViewById(R.id.comment_name);
//            holder.commentTime = (TextView) convertView.findViewById(R.id.comment_time);
//            holder.commentTv = (TextView) convertView.findViewById(R.id.comment_tv);
//            holder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);
//            convertView.setTag(holder);
//
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//
//        ApiHttpClient.loadImage(list.get(position).getUser_image_01(),holder.picIV,R.drawable.avatar_2);
//        holder.commentTv.setText(list.get(position).getContent());
//        holder.commentTime.setText(list.get(position).getPost_date());
//        holder.commentName.setText(list.get(position).getUser_nickname());
//        holder.ratingBar.setNumStars(list.get(position).getStar());
//
//        return convertView;
//
//    }
//
//    static class ViewHolder {
//        CircularImage picIV;
//        TextView commentName;
//        TextView commentTime;
//        TextView commentTv;
//        RatingBar ratingBar;
//
//    }
}
