package com.quark.wificontrol.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quark.api.auto.bean.PeopleServicesClassify02;
import com.quark.wificontrol.R;
import com.quark.wificontrol.ui.find.PeopelServicesListActivity;
import com.quark.wificontrol.ui.find.PeopleServicesDetailActivity;

import java.util.ArrayList;

/**
 * Created by pan on 2016/9/26 0026.
 * >#
 * >#
 */
public class GridServiceAdapter extends RecyclerView.Adapter<GridServiceAdapter.ViewHolder> {

    private Context context;
    private ArrayList<PeopleServicesClassify02> list;

    public GridServiceAdapter(Context context, ArrayList<PeopleServicesClassify02> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public GridServiceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.service_grid_item, null));
    }

    @Override
    public void onBindViewHolder(GridServiceAdapter.ViewHolder holder,final int position) {

        holder.nameTv.setText(list.get(position).getPeople_services_classify_02_name());
        holder.sicRly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).getType() == 1) {//1-app发布，2-后台发布
                    Intent intent = new Intent(context, PeopelServicesListActivity.class);
                    intent.putExtra("people_services_classify_02_id", list.get(position).getPeople_services_classify_02_id() + "");
                    intent.putExtra("title", list.get(position).getPeople_services_classify_02_name());
                    context.startActivity(intent);
                } else if (list.get(position).getType() == 2){
//                    Intent intent = new Intent(context, PeopleServicesDetailActivity.class);
//                    intent.putExtra("people_services_id", list.get(position).getPeople_services_classify_02_id() + "");
//                    context.startActivity(intent);

                    Intent intent = new Intent(context, PeopleServicesDetailActivity.class);
                    intent.putExtra("url", list.get(position).getWeb_url() + "");
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout sicRly;
        TextView nameTv;

        public ViewHolder(View itemView) {
            super(itemView);
            sicRly = (LinearLayout) itemView.findViewById(R.id.sic_rly);
            nameTv = (TextView) itemView.findViewById(R.id.name_tv);

        }
    }


//    private Context context;
//    private ArrayList<PeopleServicesClassify02> list;
//
//    public GridServiceAdapter(Context context, ArrayList<PeopleServicesClassify02> list) {
//        this.context = context;
//        this.list = list;
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
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        convertView = LayoutInflater.from(context).inflate(R.layout.service_grid_item, null);
//        TextView nameTv = (TextView) convertView.findViewById(R.id.name_tv);
//        nameTv.setText(list.get(position).getPeople_services_classify_02_name());
//
//        return convertView;
//
//    }

}
