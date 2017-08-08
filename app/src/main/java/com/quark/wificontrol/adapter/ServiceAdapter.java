package com.quark.wificontrol.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quark.api.auto.bean.PeopleServicesClassify02;
import com.quark.api.auto.bean.PeopleServicesClassifyBeans;
import com.quark.wificontrol.R;
import com.quark.wificontrol.ui.widget.DividerGridItemDecoration;

import java.util.ArrayList;

/**
 * Created by pan on 2016/9/26 0026.
 * >#
 * >#
 */
public class ServiceAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<PeopleServicesClassifyBeans> list;
    Handler handler;

    GridServiceAdapter adapter;

    public ServiceAdapter(Context context, ArrayList<PeopleServicesClassifyBeans> list, Handler handler) {
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

        convertView = LayoutInflater.from(context).inflate(R.layout.service_item, null);
        TextView nameTv = (TextView) convertView.findViewById(R.id.name_tv);

        nameTv.setText(list.get(position).getPeople_services_classify_01_name());

        RecyclerView gridview = (RecyclerView) convertView.findViewById(R.id.gridView);

        //gridview效果
        GridLayoutManager mgr = new GridLayoutManager(context, 3);
        gridview.setLayoutManager(mgr);

        adapter = new GridServiceAdapter(context, (ArrayList<PeopleServicesClassify02>) list.get(position).getPeopleServicesClassify02());
        gridview.setAdapter(adapter);
        //添加分割线
        gridview.addItemDecoration(new DividerGridItemDecoration(context, 3));

//        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
//
//                if (list.get(position).getPeopleServicesClassify02().get(i).getType() == 1) {
//                    Intent intent = new Intent(context, PeopelServicesListActivity.class);
//                    intent.putExtra("people_services_classify_02_id", list.get(position).getPeopleServicesClassify02().get(i).getPeople_services_classify_02_id() + "");
//                    intent.putExtra("title", list.get(position).getPeopleServicesClassify02().get(i).getPeople_services_classify_02_name());
//                    context.startActivity(intent);
//                } else if (list.get(position).getPeopleServicesClassify02().get(i).getType() == 2){
//                    Intent intent = new Intent(context, PeopleServicesDetailActivity.class);
//                    intent.putExtra("people_services_id", list.get(position).getPeopleServicesClassify02().get(i).getPeople_services_classify_02_id() + "");
//                    context.startActivity(intent);
//                }
//            }
//        });

        return convertView;

    }

}
