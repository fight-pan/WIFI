package com.quark.wificontrol.ui.personalCentel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.quark.api.auto.bean.PeopleServicesClassify02s;
import com.quark.wificontrol.R;
import com.quark.wificontrol.adapter.TeachAdapter;
import com.quark.wificontrol.base.BaseActivity;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by pan on 2016/9/24 0024.
 * >#
 * >#房屋出售
 */
public class HouseActivity extends BaseActivity {

    @InjectView(R.id.listView)
    ListView listView;

    ArrayList<PeopleServicesClassify02s> teachtype;
    TeachAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecttype);
        ButterKnife.inject(this);
        setTopTitle("房屋租赁");
        setBackButton();

        teachtype = new ArrayList<>();
        teachtype = new ArrayList<>();
        teachtype = (ArrayList<PeopleServicesClassify02s>) getValue4Intent("teachType");
        adapter = new TeachAdapter(this, teachtype);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(HouseActivity.this, IssueActivity.class);
                intent.putExtra("people_services_classify_02_id",
                        teachtype.get(position).getPeople_services_classify_02_id() + "");
                intent.putExtra("head", teachtype.get(position).getPeople_services_classify_02_name());
                startActivity(intent);

            }
        });
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }
}
