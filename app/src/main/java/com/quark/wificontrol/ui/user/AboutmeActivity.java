package com.quark.wificontrol.ui.user;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.quark.wificontrol.R;
import com.quark.wificontrol.base.BaseActivity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by pan on 2016/9/9 0009.
 * >#
 * >#关于我们
 */
public class AboutmeActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks{

    @InjectView(R.id.phone_1)
    TextView phone1;
    @InjectView(R.id.phone_2)
    TextView phone2;
    @InjectView(R.id.phone_3)
    TextView phone3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutme);
        ButterKnife.inject(this);
        setTopTitle("关于我们");
        setBackButton();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    String phoneStr;
    @OnClick({R.id.phone_1, R.id.phone_2, R.id.phone_3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.phone_1:
                phoneStr = "075523206697";
                startbyPermissions();
                break;
            case R.id.phone_2:
                phoneStr = "13823614677";
                startbyPermissions();
                break;
            case R.id.phone_3:
                phoneStr = "4000702082";
                startbyPermissions();
                break;
        }
    }

    private void startbyPermissions() {
        String[] perms = {Manifest.permission.CALL_PHONE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            try {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + phoneStr);
                intent.setData(data);
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(this, "缺少运行权限,请正确授权", Toast.LENGTH_LONG).show();
            }
        } else {
            EasyPermissions.requestPermissions(this, "申请运行所需的权限，如果拒绝将影响您的正常使用", 10001, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Toast.makeText(this, "缺少运行权限,请正确授权", Toast.LENGTH_LONG).show();
    }
}
