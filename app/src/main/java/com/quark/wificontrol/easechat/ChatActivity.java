package com.quark.wificontrol.easechat;

import android.os.Bundle;

import com.hyphenate.easeui.ui.EaseChatFragment;
import com.quark.wificontrol.R;

/**
 * chat activity，EaseChatFragment was used {@link }
 *
 */
public class ChatActivity extends BaseActivity {
    public static ChatActivity activityInstance;
    // 当前聊天的 ID
    private String mChatId;
    private EaseChatFragment chatFragment;
    public String toChatUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        activityInstance = this;
        // 这里直接使用EaseUI封装好的聊天界面
        chatFragment = new ChatFragment();
        toChatUsername = getIntent().getExtras().getString("userId");
        // 将参数传递给聊天界面
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.ec_layout_container, chatFragment).commit();

        initView();
    }

    /**
     * 初始化界面
     */
    private void initView() {

    }

    public String getToChatUsername(){
        return toChatUsername;
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
