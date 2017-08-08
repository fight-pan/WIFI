package com.quark.wificontrol.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quark.api.auto.bean.CityInfoList;
import com.quark.wificontrol.R;
import com.quark.wificontrol.ui.find.GoodsDetailsActivity;
import com.quark.wificontrol.ui.find.MoreActivity;
import com.quark.wificontrol.ui.widget.NoneScrollGridView;

import java.util.ArrayList;

/**
 * Created by pan on 2016/9/26 0026.
 * >#
 * >#
 */
public class RightAdapter extends BaseAdapter{

//    private Context context;
//    private LayoutInflater mInflater;
//    private List<CityInfoList> mDatas;
//    private RightInfoAdapter adapter;
//
//    public RightAdapter(Context context, List<CityInfoList> mDatas) {
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
//        TextView nameTv;
//        GridView gridView;
//        TextView moreTv;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//
//        View view = mInflater.inflate(R.layout.right_item,
//                viewGroup, false);
//        ViewHolder viewHolder = new ViewHolder(view);
//        viewHolder.nameTv = (TextView) view.findViewById(R.id.name_tv);
//        viewHolder.moreTv = (TextView) view.findViewById(R.id.more_tv);
//        viewHolder.gridView = (GridView) view.findViewById(R.id.gridView);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(final RightAdapter.ViewHolder holder,final int position) {
//
//        holder.nameTv.setText(mDatas.get(position).getCity_classify_02_name());
//        holder.moreTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, MoreActivity.class);
//                intent.putExtra("city_classify_02_id",mDatas.get(position).getCity_classify_02_id()+"");
//                intent.putExtra("title",mDatas.get(position).getCity_classify_02_name());
//                context.startActivity(intent);
//            }
//        });
//
//        adapter = new RightInfoAdapter(context,mDatas.get(position).getCityInformations());
//        holder.gridView.setAdapter(adapter);
//        holder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
//                Intent intent = new Intent(context, GoodsDetailsActivity.class);
//                intent.putExtra("city_information_id",mDatas.get(position).getCityInformations().get(0).getCity_information_id()+"");
//                context.startActivity(intent);
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return mDatas.size();
//    }

    private Context context;
    private ArrayList<CityInfoList> list;

    private RightInfoAdapter adapter;

    public RightAdapter(Context context, ArrayList<CityInfoList> list) {
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

        convertView = LayoutInflater.from(context).inflate(R.layout.right_item, null);
        TextView nameTv = (TextView) convertView.findViewById(R.id.name_tv);
        NoneScrollGridView gridView = (NoneScrollGridView) convertView.findViewById(R.id.gridView);
        TextView moreTv = (TextView) convertView.findViewById(R.id.more_tv);


        nameTv.setText(list.get(position).getCity_classify_02_name());
        moreTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MoreActivity.class);
                intent.putExtra("city_classify_02_id",list.get(position).getCity_classify_02_id()+"");
                intent.putExtra("title",list.get(position).getCity_classify_02_name());
                context.startActivity(intent);

            }
        });

        adapter = new RightInfoAdapter(context,list.get(position).getCityInformations());
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent intent = new Intent(context, GoodsDetailsActivity.class);
                intent.putExtra("city_information_id",list.get(position).getCityInformations().get(i).getCity_information_id()+"");
                context.startActivity(intent);
            }
        });

        return convertView;

    }

}
