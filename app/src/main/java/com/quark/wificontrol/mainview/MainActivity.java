package com.quark.wificontrol.mainview;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.util.EMLog;
import com.quark.api.auto.bean.ListMusic;
import com.quark.wificontrol.AppContext;
import com.quark.wificontrol.AppParam;
import com.quark.wificontrol.R;
import com.quark.wificontrol.easechat.ChatActivity;
import com.quark.wificontrol.easechat.Constant;
import com.quark.wificontrol.easechat.ConversationListFragment;
import com.quark.wificontrol.easechat.DemoHelper;
import com.quark.wificontrol.easechat.GroupsActivity;
import com.quark.wificontrol.easechat.runtimepermissions.PermissionsManager;
import com.quark.wificontrol.easechat.runtimepermissions.PermissionsResultAction;
import com.quark.wificontrol.ui.user.LoginActivity;
import com.quark.wificontrol.util.TLog;
import com.quark.wificontrol.util.Utils;
import com.quark.wificontrol.util.ValidateHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pub.devrel.easypermissions.EasyPermissions;

@SuppressLint("ResourceAsColor")
public class MainActivity extends FragmentActivity implements View.OnClickListener, MediaPlayer.OnErrorListener, MediaPlayer.OnBufferingUpdateListener,
        MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, EasyPermissions.PermissionCallbacks {
    private static final String TAG = "JPush";
    @InjectView(R.id.unread_msg_number)
    TextView unreadLabel;

    private FragmentHome homeFragment;
    private FragmentOne oneFragment;
    private FragmentTwo twoFragment;
    private ConversationListFragment threeFragment;

    private FragmentFour fourFragment;

    private View homeLayout;
    private View oneLayout;
    private View twoLayout;
    private View threeLayout;
    private View fourLayout;

    private ImageView homeImage;
    private ImageView oneImage;
    private ImageView twoImage;
    private ImageView threeImage;
    private ImageView fourImage;

    private TextView homeText;
    private TextView oneText;
    private TextView twoText;
    private TextView threeText;
    private TextView fourText;
    private TextView fiveText;

    private int current = 0;
    private int precurrent = 0;

    private FragmentManager fragmentManager;
    public static MainActivity instance;

    int msnumbers = 0;
    public static boolean isForeground = false;
    public static boolean out = false;
    private LocationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        registerBoradcastReceiver();
        initMediaPlayer();

        startbyPermissions();
        out = false;
        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null) {

            homeFragment = (FragmentHome) fragmentManager.findFragmentByTag("homeFragment");
            oneFragment = (FragmentOne) fragmentManager.findFragmentByTag("oneFragment");
            twoFragment = (FragmentTwo) fragmentManager.findFragmentByTag("twoFragment");
            threeFragment = (ConversationListFragment) fragmentManager.findFragmentByTag("threeFragment");
            fourFragment = (FragmentFour) fragmentManager.findFragmentByTag("fourFragment");
        }


        WindowManager windowManager = getWindowManager();
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        int sw = dm.widthPixels;
        int h = dm.heightPixels;
        new AppParam().setSharedPreferencesy(this, "screenWidth", sw);
        new AppParam().setSharedPreferencesy(this, "screenHeight", h);

        initViews();


        String home = getIntent().getStringExtra("home");
        String person = getIntent().getStringExtra("person");

        if (!Utils.isEmpty(home)) {
            if (home.equals("a")) {
                current = 1;
            }
        }

        if (!Utils.isEmpty(person)) {
            if (person.equals("a")) {
                current = 3;
            }
        }


        setTabSelection(current);
        if (new AppParam().isLogin(this)) {
            signIn();
        }
//        UpdateChecker.checkForDialog(MainActivity.this, ApiHttpClient.updateUrl+"?versionCode="+Utils.getVersionCode(this));
    }

    private void initViews() {
        homeLayout = findViewById(R.id.home_layout);
        oneLayout = findViewById(R.id.one_layout);
        twoLayout = findViewById(R.id.two_layout);
        threeLayout = findViewById(R.id.three_layout);
        fourLayout = findViewById(R.id.four_layout);

        homeImage = (ImageView) findViewById(R.id.home_image);
        oneImage = (ImageView) findViewById(R.id.one_image);
        twoImage = (ImageView) findViewById(R.id.two_image);
        threeImage = (ImageView) findViewById(R.id.three_image);
        fourImage = (ImageView) findViewById(R.id.four_image);

        homeText = (TextView) findViewById(R.id.home_text);
        oneText = (TextView) findViewById(R.id.one_text);
        twoText = (TextView) findViewById(R.id.two_text);
        threeText = (TextView) findViewById(R.id.three_text);
        fourText = (TextView) findViewById(R.id.four_text);

        homeLayout.setOnClickListener(this);
        oneLayout.setOnClickListener(this);
        twoLayout.setOnClickListener(this);
        threeLayout.setOnClickListener(this);
        fourLayout.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        precurrent = current;
        int id = v.getId();
        if (id == R.id.home_layout) {
//            if (new AppParam().isLogin(this)) {
            current = 0;
            setTabSelection(0);
//            }else{
//                Intent intent = new Intent(this, LoginActivity.class);
//                startActivity(intent);
//            }
        } else if (id == R.id.two_layout) {
//            if (new AppParam().isLogin(this)) {
            current = 1;
            setTabSelection(1);
//            }else{
//                Intent intent = new Intent(this, LoginActivity.class);
//                startActivity(intent);
//            }
        } else if (id == R.id.three_layout) {
            if (new AppParam().isLogin(this)) {
                current = 2;
                setTabSelection(2);
            } else {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }

        } else if (id == R.id.four_layout) {
            current = 3;
            setTabSelection(3);
        } else if (id == R.id.one_layout) {

//            if (new AppParam().isLogin(this)) {
            current = 4;
            setTabSelection(4);
//            }else{
//                Intent intent = new Intent(this, LoginActivity.class);
//                startActivity(intent);
//            }
        }
    }


    private void setTabSelection(int index) {
        clearSelection();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                homeImage.setImageResource(R.drawable.home_1);
                homeText.setTextColor(getResources().getColor(R.color.chengse));
                if (homeFragment == null) {
                    homeFragment = new FragmentHome();
                    transaction.add(R.id.content, homeFragment, "homeFragment");
                } else {
                    transaction.show(homeFragment);
                }

                break;
            case 1:

                twoImage.setImageResource(R.drawable.discover_2);
                twoText.setTextColor(getResources().getColor(R.color.chengse));
//                if (new AppParam().isLogin(this)) {
                if (twoFragment == null) {
                    twoFragment = new FragmentTwo();
                    transaction.add(R.id.content, twoFragment, "twoFragment");
                } else {
                    transaction.show(twoFragment);
                }
//                } else {
//                    twoFragment = new FragmentTwo();
//                    transaction.add(R.id.content, twoFragment, "twoFragment");
//                }
                break;

            case 2:
                threeImage.setImageResource(R.drawable.message_2);
                threeText.setTextColor(getResources().getColor(R.color.chengse));
                if (threeFragment == null) {
                    threeFragment = new ConversationListFragment();
                    transaction.add(R.id.content, threeFragment, "threeFragment");
                } else {
                    transaction.show(threeFragment);
                }
                break;
            case 3:
                fourImage.setImageResource(R.drawable.me_2);
                fourText.setTextColor(getResources().getColor(R.color.chengse));
//                if (new AppParam().isLogin(this)) {
                if (fourFragment == null) {
                    fourFragment = new FragmentFour();
                    transaction.add(R.id.content, fourFragment, "fourFragment");
                } else {
                    transaction.show(fourFragment);
                }
//                } else {
//                    Intent intent = new Intent(this, LoginActivity.class);
//                    startActivity(intent);
//                }
                break;
            case 4:
                oneImage.setImageResource(R.drawable.wifi_1);
                oneText.setTextColor(getResources().getColor(R.color.chengse));
//                if (oneFragment == null) {
                oneFragment = new FragmentOne();
                transaction.add(R.id.content, oneFragment, "oneFragment");
//                } else {
//                    transaction.show(oneFragment);
//                }

                break;
        }
        transaction.commit();
    }

    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection() {
        homeImage.setImageResource(R.drawable.home_2);
        homeText.setTextColor(Color.parseColor("#82858b"));

        oneImage.setImageResource(R.drawable.wifi_2);
        oneText.setTextColor(Color.parseColor("#82858b"));

        twoImage.setImageResource(R.drawable.discover_1);
        twoText.setTextColor(Color.parseColor("#82858b"));

        threeImage.setImageResource(R.drawable.message_1);
        threeText.setTextColor(Color.parseColor("#82858b"));

        fourImage.setImageResource(R.drawable.me_1);
        fourText.setTextColor(Color.parseColor("#82858b"));

//        fiveImage.setImageResource(R.drawable.menu_five);
//        fiveText.setTextColor(Color.parseColor("#82858b"));
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {

        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (oneFragment != null) {
            transaction.remove(oneFragment);
        }
        if (twoFragment != null) {
//            if (new AppParam().isLogin(this)) {
            transaction.hide(twoFragment);
//            } else {
//                transaction.remove(twoFragment);
//            }
        }
        if (threeFragment != null) {
            transaction.hide(threeFragment);
        }
        if (fourFragment != null) {
//            if (new AppParam().isLogin(this)) {
            transaction.hide(fourFragment);
//            } else {
//                transaction.remove(fourFragment);
//            }
        }
    }

    boolean canload = true;


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
            exitApp();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exitApp() {
        final AlertDialog dlg = new AlertDialog.Builder(this).create();
        dlg.setTitle("退出提示");
        dlg.setMessage("是否确定退出？");
        dlg.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPlayer.stop(); //关闭播放器
                mPlayer.release();
//                mPlayer = null;
                finish();
            }
        });
        dlg.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dlg.cancel();
            }
        });
        dlg.show();
    }

    //关闭播放页也能播放 播放器控制在mainActivity中 界面在musicActivity中控制
    //==========================================简单实现音乐播放======================================
    public static ArrayList<ListMusic> datas;
    public static MediaPlayer mPlayer = null;
    //    int currentSong = 0;
    public static int PLAYSTATE = 1; //播放状态： 1初始状态 (2准备好播放 3正在播放 4暂停)
    public static String songName = "";

    public void initMediaPlayer() {
        mPlayer = new MediaPlayer();
        datas = new ArrayList<>();
        PLAYSTATE = 1;

        mPlayer.setOnErrorListener(this);
        mPlayer.setOnBufferingUpdateListener(this);
        mPlayer.setOnCompletionListener(this);
        mPlayer.setOnPreparedListener(this);
    }

    public static void toplay(String url) {
        mPlayer.reset();
        try {
            mPlayer.setDataSource(url); //调用setDataSource方法，传入音频文件的http位置，此时处于Initialized状态
        } catch (Exception e) {
            e.printStackTrace();
        }
        mPlayer.prepareAsync();   //调用prepareAsync方法，试MediaPlayer对象进入Preparing状态，让内部播放引擎继续未完成的准备工作。
    }

    public static void play() {
        mPlayer.start();
    }

    public static void pause() {
        mPlayer.pause();
    }

    public static boolean isplaying() {
        return mPlayer.isPlaying();
    }

    boolean isFirstIn = true;

    /**
     * 当prepareAsync方法完成时，将调用onPrepared（）方法，表明音频准备播放。
     */
    @Override
    public void onPrepared(MediaPlayer mp) {
        TLog.error("=============================onPrepared");

        Intent intent = new Intent("musicBC");
        intent.putExtra("option", "complet");
        sendBroadcast(intent);

        if (!isFirstIn)
            mPlayer.start();
        isFirstIn = false;
    }

    /**
     * 当MediaPlayer完成播放音频文件时，将调用onCompletion方法。
     * 此时设置“播放”按钮可点击，“暂停”按钮不能点击
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        TLog.error("播放完了-----");
        //发通知给界面
        if (ValidateHelper.isNetworkAvailable(this)) {
            Intent intent = new Intent("musicBC");
            intent.putExtra("option", "next");
            sendBroadcast(intent);
        } else {
            Toast.makeText(AppContext.instance, "当前网络不可用", Toast.LENGTH_SHORT).show();
        }
    }

    //当MediaPlayer正在缓冲的时候，将调用该Activity的onBufferingUpdate方法。
    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        // TODO Auto-generated method stub
//        bufferView.setText("" + percent + "%");
        TLog.error("=============================" + percent);

    }

    /**
     * 如果MediaPlayer出现错误，将调用onError方法。
     */
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        // TODO Auto-generated method stub
        return false;
    }

//    @Override
//    protected void onDestroy() {
//        // TODO Auto-generated method stub
//        super.onDestroy();
////        mPlayer.stop();
//        if (receiveBroadCast != null) {
//            unregisterReceiver(receiveBroadCast);
//        }
//    }

    /**
     * 当按手机上的返回按键的时候，会自动调用系统的onKeyDown方法，
     * 而onKeyDown方法右会自动调用onDestroy()方法
     * 销毁该Activity,此时如果onDestroy()方法不重写，那么正在播放
     * 的音乐不会停止，所以这时候要重写onDestroy()方法，
     * 在该方法中，加入mediaplayer.stop()方法，表示按返回键的时候，
     * 会调用mediaPlayer对象的stop方法，
     * 从而停止音乐的播放
     */

    //==========================================简单实现音乐播放 end==================================
    // 注册广播
    private ReceiveBroadCast receiveBroadCast;

    public void registerBoradcastReceiver() {
        receiveBroadCast = new ReceiveBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("mainBC"); // 只有持有相同的action的接受者才能接收此广播
        registerReceiver(receiveBroadCast, filter);
    }

    public class ReceiveBroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent data) {
            String position = data.getStringExtra("position");
            if (position.equals("1")) {
                setTabSelection(1);
            } else if (position.equals("3")) {
                setTabSelection(3);
            }
        }
    }

    /**
     * 获取手机信息
     *
     * @author leon
     * @time 2016/9/19 0019 上午 9:07
     */
    public void getphoninfo() {
        final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, androidId;
        tmDevice = "" + tm.getDeviceId();
        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        new AppParam().setSharedPreferencesy(this, "phone_sn", tmDevice + androidId);
    }

    private void startbyPermissions() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            try {
                getphoninfo();
            } catch (Exception e) {
                Toast.makeText(this, "缺少运行权限,请正确授权", Toast.LENGTH_LONG).show();
            }
        } else {
            EasyPermissions.requestPermissions(this, "申请运行所需的权限，如果拒绝将影响您的正常使用", 10001, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        getphoninfo();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Toast.makeText(this, "缺少运行权限,请正确授权", Toast.LENGTH_LONG).show();
    }

    //=========================================环信=================================================

    /**
     * 环信登录方法
     */
    private void signIn() {
//        mDialog = new ProgressDialog(this);
//        mDialog.setMessage("正在登陆，请稍后...");
//        mDialog.show();

        String username = new AppParam().getTelephone(this);
        String password = new AppParam().getpassword(this);
        TLog.error("username:" + username + "        password:" + password);
//        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
//            Toast.makeText(ECLoginActivity.this, "用户名和密码不能为空", Toast.LENGTH_LONG).show();
//            return;
//        }

        EMClient.getInstance().login(username, password, new EMCallBack() {
            /**
             * 登陆成功的回调
             */
            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        mDialog.dismiss();

                        // 加载所有会话到内存
                        EMClient.getInstance().chatManager().loadAllConversations();
                        // 加载所有群组到内存，如果使用了群组的话
                        // EMClient.getInstance().groupManager().loadAllGroups();
//                        Toast.makeText(MainActivity.this, "环信登录成功", Toast.LENGTH_LONG).show();
                        initHuanxin();
                        // 登录成功跳转界面
//                        Intent intent = new Intent(ECLoginActivity.this, ECMainActivity.class);
//                        startActivity(intent);
//                        finish();
                    }
                });
            }

            /**
             * 登陆错误的回调
             * @param i
             * @param s
             */
            @Override
            public void onError(final int i, final String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        mDialog.dismiss();
                        Log.d("lzan13", "登录失败 Error code:" + i + ", message:" + s);
                        /**
                         * 关于错误码可以参考官方api详细说明
                         * http://www.easemob.com/apidoc/android/chat3.0/classcom_1_1hyphenate_1_1_e_m_error.html
                         */
                        switch (i) {
                            // 网络异常 2
                            case EMError.NETWORK_ERROR:
                                Toast.makeText(MainActivity.this, "网络错误 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            // 无效的用户名 101
                            case EMError.INVALID_USER_NAME:
                                Toast.makeText(MainActivity.this, "无效的用户名 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            // 无效的密码 102
                            case EMError.INVALID_PASSWORD:
                                Toast.makeText(MainActivity.this, "无效的密码 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            // 用户认证失败，用户名或密码错误 202
                            case EMError.USER_AUTHENTICATION_FAILED:
                                Toast.makeText(MainActivity.this, "用户认证失败，用户名或密码错误 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            // 用户不存在 204
                            case EMError.USER_NOT_FOUND:
                                Toast.makeText(MainActivity.this, "用户不存在 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            // 无法访问到服务器 300
                            case EMError.SERVER_NOT_REACHABLE:
                                Toast.makeText(MainActivity.this, "无法访问到服务器 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            // 等待服务器响应超时 301
                            case EMError.SERVER_TIMEOUT:
                                Toast.makeText(MainActivity.this, "等待服务器响应超时 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            // 服务器繁忙 302
                            case EMError.SERVER_BUSY:
                                Toast.makeText(MainActivity.this, "服务器繁忙 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            // 未知 Server 异常 303 一般断网会出现这个错误
                            case EMError.SERVER_UNKNOWN_ERROR:
                                Toast.makeText(MainActivity.this, "未知的服务器异常 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            default:
                                Toast.makeText(MainActivity.this, "ml_sign_in_failed code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                });
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    /**
     * update unread message count
     */
//    public void updateUnreadLabel() {
//        int count = getUnreadMsgCountTotal();
//        if (count > 0) {
//            unreadLabel.setText(String.valueOf(count));
//            unreadLabel.setVisibility(View.VISIBLE);
//        } else {
//            unreadLabel.setVisibility(View.INVISIBLE);
//        }
//    }

    /**
     * get unread message count
     *
     * @return
     */
//    public int getUnreadMsgCountTotal() {
//        int unreadMsgCountTotal = 0;
//        int chatroomUnreadMsgCount = 0;
//        unreadMsgCountTotal = EMClient.getInstance().chatManager().getUnreadMsgsCount();
//        for (EMConversation conversation : EMClient.getInstance().chatManager().getAllConversations().values()) {
//            if (conversation.getType() == EMConversation.EMConversationType.ChatRoom)
//                chatroomUnreadMsgCount = chatroomUnreadMsgCount + conversation.getUnreadMsgCount();
//        }
//        return unreadMsgCountTotal - chatroomUnreadMsgCount;
//    }


//    protected static final String TAG = "MainActivity";
    // textview for unread message count
//    private TextView unreadLabel;
    // textview for unread event message
//    private TextView unreadAddressLable;

//    private Button[] mTabs;
//    private ContactListFragment contactListFragment;
//    private Fragment[] fragments;
//    private int index;
//    private int currentTabIndex;
    // user logged into another device
    public boolean isConflict = false;
    // user account was removed
    private boolean isCurrentAccountRemoved = false;


    /**
     * check if current user account was remove
     */
    public boolean getCurrentAccountRemoved() {
        return isCurrentAccountRemoved;
    }


    protected void initHuanxin() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String packageName = getPackageName();
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                Intent intent = new Intent();
                intent.setAction(android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + packageName));
                startActivity(intent);
            }
        }

        // runtime permission for android 6.0, just require all permissions here for simple
        requestPermissions();
        showExceptionDialogFromIntent(getIntent());

//        inviteMessgeDao = new InviteMessgeDao(this);
//        UserDao userDao = new UserDao(this);

        //register broadcast receiver to receive the change of group from DemoHelper
        registerBroadcastReceiver();

        EMClient.getInstance().contactManager().setContactListener(new MyContactListener());
        //debug purpose only
        registerInternalDebugReceiver();
    }

    @TargetApi(23)
    private void requestPermissions() {
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
//				Toast.makeText(MainActivity.this, "All permissions have been granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(String permission) {
                //Toast.makeText(MainActivity.this, "Permission " + permission + " has been denied", Toast.LENGTH_SHORT).show();
            }
        });
    }

    EMMessageListener messageListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            // notify new message
            for (EMMessage message : messages) {
                DemoHelper.getInstance().getNotifier().onNewMsg(message);
            }
            refreshUIWithMessage();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //red packet code : 处理红包回执透传消息
            for (EMMessage message : messages) {
                EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
                final String action = cmdMsgBody.action();//获取自定义action
//                if (action.equals(RPConstant.REFRESH_GROUP_RED_PACKET_ACTION)) {
//                    RedPacketUtil.receiveRedPacketAckMessage(message);
//                }
            }
            //end of red packet code
            refreshUIWithMessage();
        }

        @Override
        public void onMessageReadAckReceived(List<EMMessage> messages) {
        }

        @Override
        public void onMessageDeliveryAckReceived(List<EMMessage> message) {
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
        }
    };

    private void refreshUIWithMessage() {
        runOnUiThread(new Runnable() {
            public void run() {
                // refresh unread count
                updateUnreadLabel();
//                if (currentTabIndex == 0) {
                // refresh conversation list
                if (threeFragment != null) {
                    threeFragment.refresh();
                }
//                }
            }
        });
    }


    private void registerBroadcastReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.ACTION_CONTACT_CHANAGED);
        intentFilter.addAction(Constant.ACTION_GROUP_CHANAGED);
//        intentFilter.addAction(RPConstant.REFRESH_GROUP_RED_PACKET_ACTION);
        broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                updateUnreadLabel();
//                updateUnreadAddressLable();
//                if (currentTabIndex == 0) {
                // refresh conversation list
                if (threeFragment != null) {
                    threeFragment.refresh();
                }
//                } else if (currentTabIndex == 1) {
//                    if(contactListFragment != null) {
//                        contactListFragment.refresh();
//                    }
//                }
                String action = intent.getAction();
                if (action.equals(Constant.ACTION_GROUP_CHANAGED)) {
                    if (EaseCommonUtils.getTopActivity(MainActivity.this).equals(GroupsActivity.class.getName())) {
                        GroupsActivity.instance.onResume();
                    }
                }
                //red packet code : 处理红包回执透传消息
//                if (action.equals(RPConstant.REFRESH_GROUP_RED_PACKET_ACTION)){
//                    if (conversationListFragment != null){
//                        conversationListFragment.refresh();
//                    }
//                }
                //end of red packet code
            }
        };
        broadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    public class MyContactListener implements EMContactListener {
        @Override
        public void onContactAdded(String username) {
        }

        @Override
        public void onContactDeleted(final String username) {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (ChatActivity.activityInstance != null && ChatActivity.activityInstance.toChatUsername != null &&
                            username.equals(ChatActivity.activityInstance.toChatUsername)) {
                        String st10 = getResources().getString(R.string.have_you_removed);
                        Toast.makeText(MainActivity.this, ChatActivity.activityInstance.getToChatUsername() + st10, Toast.LENGTH_LONG)
                                .show();
                        ChatActivity.activityInstance.finish();
                    }
                }
            });
        }

        @Override
        public void onContactInvited(String username, String reason) {
        }

        @Override
        public void onContactAgreed(String username) {
        }

        @Override
        public void onContactRefused(String username) {
        }
    }

    private void unregisterBroadcastReceiver() {
        broadcastManager.unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (exceptionBuilder != null) {
            exceptionBuilder.create().dismiss();
            exceptionBuilder = null;
            isExceptionDialogShow = false;
        }


        try {
            unregisterReceiver(internalDebugReceiver);
            if (broadcastReceiver != null) {
                unregisterBroadcastReceiver();
            }

            if (receiveBroadCast != null) {
                unregisterReceiver(receiveBroadCast);
            }
        } catch (Exception e) {
        }

    }

    /**
     * update unread message count
     */
    public void updateUnreadLabel() {
        int count = getUnreadMsgCountTotal();
        if (count > 0) {
            unreadLabel.setText(String.valueOf(count));
            unreadLabel.setVisibility(View.VISIBLE);
        } else {
            unreadLabel.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * update the total unread count
     */
//    public void updateUnreadAddressLable() {
//        runOnUiThread(new Runnable() {
//            public void run() {
//                int count = getUnreadAddressCountTotal();
//                if (count > 0) {
//                    unreadAddressLable.setVisibility(View.VISIBLE);
//                } else {
//                    unreadAddressLable.setVisibility(View.INVISIBLE);
//                }
//            }
//        });
//
//    }

    /**
     * get unread event notification count, including application, accepted, etc
     *
     * @return
     */
//    public int getUnreadAddressCountTotal() {
//        int unreadAddressCountTotal = 0;
//        unreadAddressCountTotal = inviteMessgeDao.getUnreadMessagesCount();
//        return unreadAddressCountTotal;
//    }

    /**
     * get unread message count
     *
     * @return
     */
    public int getUnreadMsgCountTotal() {
        int unreadMsgCountTotal = 0;
        int chatroomUnreadMsgCount = 0;
        unreadMsgCountTotal = EMClient.getInstance().chatManager().getUnreadMsgsCount();
        for (EMConversation conversation : EMClient.getInstance().chatManager().getAllConversations().values()) {
            if (conversation.getType() == EMConversation.EMConversationType.ChatRoom)
                chatroomUnreadMsgCount = chatroomUnreadMsgCount + conversation.getUnreadMsgCount();
        }
        return unreadMsgCountTotal - chatroomUnreadMsgCount;
    }

//    private InviteMessgeDao inviteMessgeDao;

    @Override
    protected void onResume() {
        super.onResume();

        if (!isConflict && !isCurrentAccountRemoved) {
            updateUnreadLabel();
//            updateUnreadAddressLable();
        }

        // unregister this event listener when this activity enters the
        // background
        DemoHelper sdkHelper = DemoHelper.getInstance();
        sdkHelper.pushActivity(this);

        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }

    @Override
    protected void onStop() {
        EMClient.getInstance().chatManager().removeMessageListener(messageListener);
        DemoHelper sdkHelper = DemoHelper.getInstance();
        sdkHelper.popActivity(this);

        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("isConflict", isConflict);
        outState.putBoolean(Constant.ACCOUNT_REMOVED, isCurrentAccountRemoved);
        super.onSaveInstanceState(outState);
    }


    private android.app.AlertDialog.Builder exceptionBuilder;
    private boolean isExceptionDialogShow = false;
    private BroadcastReceiver internalDebugReceiver;
    //    private ConversationListFragment conversationListFragment;
    private BroadcastReceiver broadcastReceiver;
    private LocalBroadcastManager broadcastManager;

    private int getExceptionMessageId(String exceptionType) {
        if (exceptionType.equals(Constant.ACCOUNT_CONFLICT)) {
            return R.string.connect_conflict;
        } else if (exceptionType.equals(Constant.ACCOUNT_REMOVED)) {
            return R.string.em_user_remove;
        } else if (exceptionType.equals(Constant.ACCOUNT_FORBIDDEN)) {
            return R.string.user_forbidden;
        }
        return R.string.Network_error;
    }

    /**
     * show the dialog when user met some exception: such as login on another device, user removed or user forbidden
     */
    private void showExceptionDialog(String exceptionType) {
        isExceptionDialogShow = true;
        DemoHelper.getInstance().logout(false, null);
        String st = getResources().getString(R.string.Logoff_notification);
        if (!MainActivity.this.isFinishing()) {
            // clear up global variables
            try {
                if (exceptionBuilder == null)
                    exceptionBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
                exceptionBuilder.setTitle(st);
                exceptionBuilder.setMessage(getExceptionMessageId(exceptionType));
                exceptionBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        exceptionBuilder = null;
                        isExceptionDialogShow = false;
                        finish();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
                exceptionBuilder.setCancelable(false);
                exceptionBuilder.create().show();
                isConflict = true;
            } catch (Exception e) {
                EMLog.e(TAG, "---------color conflictBuilder error" + e.getMessage());
            }
        }
    }

    private void showExceptionDialogFromIntent(Intent intent) {
        EMLog.e(TAG, "showExceptionDialogFromIntent");
        if (!isExceptionDialogShow && intent.getBooleanExtra(Constant.ACCOUNT_CONFLICT, false)) {
            showExceptionDialog(Constant.ACCOUNT_CONFLICT);
        } else if (!isExceptionDialogShow && intent.getBooleanExtra(Constant.ACCOUNT_REMOVED, false)) {
            showExceptionDialog(Constant.ACCOUNT_REMOVED);
        } else if (!isExceptionDialogShow && intent.getBooleanExtra(Constant.ACCOUNT_FORBIDDEN, false)) {
            showExceptionDialog(Constant.ACCOUNT_FORBIDDEN);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        showExceptionDialogFromIntent(intent);
    }

    /**
     * debug purpose only, you can ignore this
     */
    private void registerInternalDebugReceiver() {
        internalDebugReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                DemoHelper.getInstance().logout(false, new EMCallBack() {

                    @Override
                    public void onSuccess() {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                finish();
                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            }
                        });
                    }

                    @Override
                    public void onProgress(int progress, String status) {
                    }

                    @Override
                    public void onError(int code, String message) {
                    }
                });
            }
        };
        IntentFilter filter = new IntentFilter(getPackageName() + ".em_internal_debug");
        registerReceiver(internalDebugReceiver, filter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }
    //=========================================环信end==============================================


}