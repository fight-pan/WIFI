package com.quark.wificontrol.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quark.api.auto.bean.Friend;
import com.quark.wificontrol.R;

import java.util.ArrayList;

/**
 * Created by XIAO-Y on 2016/8/25.
 * >#
 */
public class FriendAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Friend> list;

    public FriendAdapter(Context context, ArrayList<Friend> list) {
        super();
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
        if(convertView==null){
            convertView = View.inflate(context, R.layout.adapter_friend, null);
        }
        FriendHolder holder = FriendHolder.getHolder(convertView);

        //bind data
        Friend friend = list.get(position);
        String letter = friend.getPinyin().charAt(0)+"";
        if(position>0){
            String lastLetter = list.get(position-1).getPinyin().charAt(0)+"";
            if(letter.equals(lastLetter)){
                //需要隐藏当前的首字母
                holder.tv_letter.setVisibility(View.GONE);
            }else {
                holder.tv_letter.setVisibility(View.VISIBLE);
                holder.tv_letter.setText(letter);
            }
        }else {
            holder.tv_letter.setVisibility(View.VISIBLE);
            holder.tv_letter.setText(letter);
        }
        holder.tv_name.setText(friend.getName());
        return convertView;
    }

    static class FriendHolder{
        TextView tv_letter,tv_name;
        public FriendHolder(View convertView){
            tv_letter = (TextView) convertView.findViewById(R.id.tv_letter);
            tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        }
        public static FriendHolder getHolder(View convertView){
            FriendHolder friendHolder = (FriendHolder) convertView.getTag();
            if(friendHolder==null){
                friendHolder = new FriendHolder(convertView);
                convertView.setTag(friendHolder);
            }
            return friendHolder;
        }
    }

}

