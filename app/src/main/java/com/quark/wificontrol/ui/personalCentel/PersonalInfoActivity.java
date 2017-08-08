package com.quark.wificontrol.ui.personalCentel;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.BaseInfoRequest;
import com.quark.api.auto.bean.InfoResponse;
import com.quark.api.auto.bean.UpdateAvatarRequest;
import com.quark.api.auto.bean.UpdateAvatarResponse;
import com.quark.wificontrol.AppContext;
import com.quark.wificontrol.AppParam;
import com.quark.wificontrol.R;
import com.quark.wificontrol.api.ApiHttpClient;
import com.quark.wificontrol.api.ApiResponse;
import com.quark.wificontrol.api.remote.QuarkApi;
import com.quark.wificontrol.base.BaseActivity;
import com.quark.wificontrol.easechat.DemoHelper;
import com.quark.wificontrol.mainview.MainActivity;
import com.quark.wificontrol.ui.chooseImage.FileItem;
import com.quark.wificontrol.ui.chooseImage.ImageUtils;
import com.quark.wificontrol.ui.user.EditPwdActivity;
import com.quark.wificontrol.ui.widget.CircularImage;
import com.quark.wificontrol.util.FileUtils;
import com.quark.wificontrol.util.StringUtils;

import org.kymjs.kjframe.http.HttpCallBack;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by pan on 2016/9/24 0024.
 * >#个人资料
 * >#
 */
public class PersonalInfoActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    @InjectView(R.id.out_bt)
    Button outBt;
    @InjectView(R.id.pic_iv)
    CircularImage picIv;
    @InjectView(R.id.name_tv)
    TextView nameTv;
    @InjectView(R.id.phone_tv)
    TextView phoneTv;
    @InjectView(R.id.ren_tv)
    TextView renTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalinfo);
        ButterKnife.inject(this);
        setTopTitle("个人资料");
        setBackButton();

        infoRequest();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.ren_tv, R.id.icon_1, R.id.icon_2, R.id.icon_4, R.id.out_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ren_tv:
                startActivityByClass(OpenShopActivity.class);
                break;
            case R.id.icon_1:
                ImageUtils.showSheetPic(this, handlerphoto);
                break;
            case R.id.icon_2:
                Intent in = new Intent(this, EditNameActivity.class);
                in.putExtra("name", nameTv.getText().toString());
                startActivityForResult(in, 101);
                break;
            case R.id.icon_4:
                startActivityByClass(EditPwdActivity.class);
                break;
            case R.id.out_bt:
                exitApp();
                break;
        }
    }

    private void exitApp() {
        final AlertDialog dlg = new AlertDialog.Builder(PersonalInfoActivity.this).create();
        dlg.setTitle("退出提示");
        dlg.setMessage("是否确定退出登录？");
        dlg.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                signOut();
                new AppParam().setSharedPreferencesy(PersonalInfoActivity.this, "token", "");
                new AppParam().setSharedPreferencesy(PersonalInfoActivity.this, "isLogin", "");

                Intent intnet = new Intent(PersonalInfoActivity.this, MainActivity.class);
                intnet.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intnet);
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

    /**
     * 退出环信登录
     */
    private void signOut() {
        // 调用sdk的退出登录方法，第一个参数表示是否解绑推送的token，没有使用推送或者被踢都要传false
        DemoHelper.getInstance().logout(false, new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.i("lzan13", "logout success");
                // 调用退出成功，结束app
//                finish();
            }

            @Override
            public void onError(int i, String s) {
                Log.i("lzan13", "logout error " + i + " - " + s);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    public void infoRequest() {
        BaseInfoRequest rq = new BaseInfoRequest();
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandlerget);
    }

    private final AsyncHttpResponseHandler mHandlerget = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, PersonalInfoActivity.this, InfoResponse.class);
            if (kd != null) {
                InfoResponse info = (InfoResponse) kd;
                if (info.getStatus() == 1) {

                    ApiHttpClient.loadImage(info.getBaseInfoResult().getUserInfo().getImage_01(), picIv, R.drawable.avatar_me);
                    nameTv.setText(info.getBaseInfoResult().getUserInfo().getNickname());
                    phoneTv.setText(info.getBaseInfoResult().getUserInfo().getTelephone());
                    int type = info.getBaseInfoResult().getUserInfo().getUser_level();
                    if (type == 0 || type == 3) {
                        renTv.setVisibility(View.VISIBLE);
                    }

                } else {
                    showToast(info.getMessage());
                }
            }
            showWait(false);
        }

        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
            AppContext.showToast("网络出错" + arg0);
            showWait(false);
        }
    };


    /*=========================拍照===========================*/
    public static final int ACTION_TYPE_ALBUM = 0;
    public static final int ACTION_TYPE_PHOTO = 1;
    private boolean isChangeFace = false;

    private final static int CROP = 200;

    private final static String FILE_SAVEPATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/daka/";
    private Uri origUri;
    private File protraitFile;
    private Bitmap protraitBitmap;
    private String protraitPath;

    /**
     * 上传新照片
     */
    private void uploadNewPhoto() {
//        showWaitDialog("正在上传头像...");

        // 获取头像缩略图
        if (!StringUtils.isEmpty(protraitPath) && protraitFile.exists()) {
            protraitBitmap = ImageUtils.loadImgThumbnail(protraitPath, 200, 200);
        } else {
            AppContext.showToast("图像不存在，上传失败");
        }
        if (protraitBitmap != null) {
            try {

                List<FileItem> ls = new LinkedList<FileItem>();
                FileItem f = new FileItem("image_01", protraitFile);
                ls.add(f);
                UpdateAvatarRequest rq = new UpdateAvatarRequest();
                showWait(true);

                QuarkApi.HttpuploadFile(rq, ls, httpCallBack);
            } catch (Exception e) {
                AppContext.showToast("图像不存在，上传失败");
            }
        }
    }

    HttpCallBack httpCallBack = new HttpCallBack() {
        @Override
        public void onSuccess(String t) {
            super.onSuccess(t);
            showWait(false);
            Log.e("error", "==" + t);
            try {
                UpdateAvatarResponse info = new UpdateAvatarResponse(t);
                showToast(info.getMessage());
                picIv.setImageBitmap(protraitBitmap);
                Intent senin = new Intent("four");
                senin.putExtra("position", "1");
                sendBroadcast(senin);
            } catch (Exception e) {
                Log.e("error", "數據解析出錯");
            }
            showWait(false);
        }

        public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
            AppContext.showToast("更換頭像失敗");

            showWait(false);
        }
    };

    /**
     * 选择图片裁剪
     */
    private void startImagePick() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "選擇圖片"),
                    ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP);
        } else {
            intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "選擇圖片"),
                    ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP);
        }
    }

    private void startTakePhoto() {
        Intent intent;
        // 判断是否挂载了SD卡
        String savePath = "";
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            savePath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/dakatemp/";
            File savedir = new File(savePath);
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
        }

        // 没有挂载SD卡，无法保存文件
        if (StringUtils.isEmpty(savePath)) {
            AppContext.showToastShort("无法保存照片，请检查SD卡是否挂载");
            return;
        }

        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date());
        String fileName = "temp_" + timeStamp + ".jpg";// 照片命名
        File out = new File(savePath, fileName);
        Uri uri = Uri.fromFile(out);
        origUri = uri;

        String theLarge = savePath + fileName;

        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent,
                ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA);
    }

    // 裁剪头像的绝对路径
    private Uri getUploadTempFile(Uri uri) {
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            File savedir = new File(FILE_SAVEPATH);
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
        } else {
            AppContext.showToast("無法保存上傳的頭像，請檢查SD卡是否掛載");
            return null;
        }
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String thePath = ImageUtils.getAbsolutePathFromNoStandardUri(uri);

        // 如果是标准Uri
        if (StringUtils.isEmpty(thePath)) {
            thePath = ImageUtils.getAbsoluteImagePath(this, uri);
        }
        String ext = FileUtils.getFileFormat(thePath);
        ext = StringUtils.isEmpty(ext) ? "jpg" : ext;
        // 照片命名
        String cropFileName = "daka_" + timeStamp + "." + ext;
        // 裁剪头像的绝对路径
        protraitPath = FILE_SAVEPATH + cropFileName;
        protraitFile = new File(protraitPath);

        return Uri.fromFile(protraitFile);
    }

    /**
     * 拍照后裁剪
     *
     * @param data 原始图片
     */
    private void startActionCrop(Uri data) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(data, "image/*");
        intent.putExtra("output", this.getUploadTempFile(data));
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);// 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", CROP);// 输出图片大小
        intent.putExtra("outputY", CROP);
        intent.putExtra("scale", true);// 去黑边
        intent.putExtra("scaleUpIfNeeded", true);// 去黑边
        startActivityForResult(intent,
                ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD);
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode,
                                 final Intent imageReturnIntent) {
        if (requestCode != 0) {
            if (resultCode == 101) {
                String name = imageReturnIntent.getStringExtra("name");
                nameTv.setText(name);
            }

        }
        //圖片
        if (resultCode != Activity.RESULT_OK)
            return;

        switch (requestCode) {
            case ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA:
                startActionCrop(origUri);// 拍照后裁剪
                break;
            case ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP:
                startActionCrop(imageReturnIntent.getData());// 选图后裁剪
                break;
            case ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD:
                uploadNewPhoto();
                break;
        }


    }

    private static final int CAMERA_PERM = 1;

    @AfterPermissionGranted(CAMERA_PERM)
    private void startTakePhotoByPermissions() {
        String[] perms = {Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {
            try {
                startTakePhoto();
            } catch (Exception e) {
                Toast.makeText(this, R.string.permissions_camera_error, Toast.LENGTH_LONG).show();
            }
        } else {
            // Request one permission
            EasyPermissions.requestPermissions(this,
                    getResources().getString(R.string.str_request_camera_message),
                    CAMERA_PERM, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        try {
            startTakePhoto();
        } catch (Exception e) {
            Toast.makeText(this, R.string.permissions_camera_error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Toast.makeText(this, R.string.permissions_camera_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private Handler handlerphoto = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    startTakePhotoByPermissions();
                    break;
                case 2:
                    startImagePick();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }

        ;
    };
/*=========================拍照end===========================*/
}
