package com.quark.wificontrol.easechat;

import android.os.Bundle;

import com.quark.wificontrol.R;

/**
 * chat activity，EaseChatFragment was used {@link }
 * 聊天
 */
public class ContactViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        ContactListFragment chatFragment = new ContactListFragment();

        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.ec_layout_container, chatFragment).commit();

    }



    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}
