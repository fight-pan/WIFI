package com.quark.wificontrol.ui.personalCentel;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quark.api.auto.bean.UpdatePicRequest;
import com.quark.api.auto.bean.UpdatePicResponse;
import com.quark.wificontrol.AppContext;
import com.quark.wificontrol.R;
import com.quark.wificontrol.api.remote.QuarkApi;
import com.quark.wificontrol.base.BaseActivity;
import com.quark.wificontrol.ui.chooseImage.FileItem;
import com.quark.wificontrol.ui.widget.ImageUtils;
import com.quark.wificontrol.util.ImageUtilsyasuo;
import com.quark.wificontrol.util.TLog;
import com.quark.wificontrol.util.Utils;

import org.kymjs.kjframe.http.HttpCallBack;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by pan on 2016/9/24 0024.
 * >#
 * >#上传资料
 */
public class UploadDataActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {


    @InjectView(R.id.id_iv)
    ImageView idIv;
    @InjectView(R.id.business_iv)
    ImageView businessIv;
    @InjectView(R.id.health_iv)
    ImageView healthIv;
    @InjectView(R.id.upload_bt)
    Button uploadBt;
    @InjectView(R.id.business_tv)
    TextView businessTv;
    @InjectView(R.id.business_rly)
    RelativeLayout businessRly;
    @InjectView(R.id.health_tv)
    TextView healthTv;
    @InjectView(R.id.health_rly)
    RelativeLayout healthRly;

    String type;

    String idName;
    String businessName;
    String healthName;

    int imgType;//上传图片类型

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploaddata);
        ButterKnife.inject(this);
        setTopTitle("上传资料");
        setBackButton();


        type = (String) getValue4Intent("type");
        //判断开店者身份
        if (type.equals("1")) {

        } else if (type.equals("2")) {
            businessTv.setVisibility(View.GONE);
            businessRly.setVisibility(View.GONE);
            healthTv.setVisibility(View.GONE);
            healthRly.setVisibility(View.GONE);
        }

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.id_iv, R.id.business_iv, R.id.health_iv, R.id.upload_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_iv:
                imgType = 0;
                ImageUtils.showSheetPic(this, handlerphoto);
                break;
            case R.id.business_iv:
                imgType = 1;
                ImageUtils.showSheetPic(this, handlerphoto);
                break;
            case R.id.health_iv:
                imgType = 2;
                ImageUtils.showSheetPic(this, handlerphoto);

                break;
            case R.id.upload_bt:
                Bundle bundle = new Bundle();
                bundle.putString("type", type);
                if (type.equals("1")) {
                    bundle.putString("idN", idName);
                    bundle.putString("businessN", businessName);
                    bundle.putString("healthN", healthName);
                } else if (type.equals("2")) {
                    bundle.putString("idN", idName);
                }
                startActivityByClass(PerfectInformationActivity.class, bundle);
                break;
        }
    }


//    /*=========================拍照===========================*/
//    public static final int ACTION_TYPE_ALBUM = 0;
//    public static final int ACTION_TYPE_PHOTO = 1;
//    private boolean isChangeFace = false;
//
//    private final static int CROP = 200;
//
//    private final static String FILE_SAVEPATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/daka/";
//    private Uri origUri;
//    private File protraitFile;
//    private Bitmap protraitBitmap;
//    private String protraitPath;
//
//    /**
//     * 上传身份证照片
//     */
//    private void uploadNewPhoto() {
////        showWaitDialog("正在上传头像...");
//
//        // 获取头像缩略图
//        if (!StringUtils.isEmpty(protraitPath) && protraitFile.exists()) {
//            protraitBitmap = ImageUtils.loadImgThumbnail(protraitPath, 200, 200);
//        } else {
//            AppContext.showToast("图像不存在，上传失败");
//        }
//        if (protraitBitmap != null) {
//            try {
//
//                List<FileItem> ls = new LinkedList<FileItem>();
//                FileItem f = new FileItem("image_01", protraitFile);
//                ls.add(f);
//                UpdatePicRequest rq = new UpdatePicRequest();
//                showWait(true);
//                QuarkApi.HttpuploadFile(rq, ls, httpCallBack);
//            } catch (Exception e) {
//                AppContext.showToast("图像不存在，上传失败");
//            }
//        }
//    }
//
//    HttpCallBack httpCallBack = new HttpCallBack() {
//        @Override
//        public void onSuccess(String t) {
//            super.onSuccess(t);
//            showWait(false);
//            Log.e("error", "==" + t);
//            try {
//                UpdatePicResponse info = new UpdatePicResponse(t);
//                showToast(info.getMessage());
//                //
//                if (imgType == 0) {
//                    idIv.setImageBitmap(protraitBitmap);
//                    idName = info.getFileName();
//                    TLog.error("身份证"+idName);
//                } else if (imgType == 1) {
//                    businessIv.setImageBitmap(protraitBitmap);
//                    businessName = info.getFileName();
//                    TLog.error("营业照"+businessName);
//                } else if (imgType == 2) {
//                    healthIv.setImageBitmap(protraitBitmap);
//                    healthName = info.getFileName();
//                    TLog.error("卫生证"+healthName);
//                }
//
//            } catch (Exception e) {
//                Log.e("error", "數據解析出錯");
//            }
//            showWait(false);
//        }
//
//        public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
//            AppContext.showToast("更換失敗");
//
//            showWait(false);
//        }
//    };
//
//    /**
//     * 选择图片裁剪
//     */
//    private void startImagePick() {
//        Intent intent;
//        if (Build.VERSION.SDK_INT < 19) {
//            intent = new Intent();
//            intent.setAction(Intent.ACTION_GET_CONTENT);
//            intent.setType("image/*");
//            startActivityForResult(Intent.createChooser(intent, "選擇圖片"),
//                    ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP);
//        } else {
//            intent = new Intent(Intent.ACTION_PICK,
//                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//            intent.setType("image/*");
//            startActivityForResult(Intent.createChooser(intent, "選擇圖片"),
//                    ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP);
//        }
//    }
//
//    private void startTakePhoto() {
//        Intent intent;
//        // 判断是否挂载了SD卡
//        String savePath = "";
//        String storageState = Environment.getExternalStorageState();
//        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
//            savePath = Environment.getExternalStorageDirectory()
//                    .getAbsolutePath() + "/dakatemp/";
//            File savedir = new File(savePath);
//            if (!savedir.exists()) {
//                savedir.mkdirs();
//            }
//        }
//
//        // 没有挂载SD卡，无法保存文件
//        if (StringUtils.isEmpty(savePath)) {
//            AppContext.showToastShort("无法保存照片，请检查SD卡是否挂载");
//            return;
//        }
//
//        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
//                .format(new Date());
//        String fileName = "temp_" + timeStamp + ".jpg";// 照片命名
//        File out = new File(savePath, fileName);
//        Uri uri = Uri.fromFile(out);
//        origUri = uri;
//
//        String theLarge = savePath + fileName;
//
//        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//        startActivityForResult(intent,
//                ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA);
//    }
//
//    // 裁剪头像的绝对路径
//    private Uri getUploadTempFile(Uri uri) {
//        String storageState = Environment.getExternalStorageState();
//        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
//            File savedir = new File(FILE_SAVEPATH);
//            if (!savedir.exists()) {
//                savedir.mkdirs();
//            }
//        } else {
//            AppContext.showToast("無法保存上傳的頭像，請檢查SD卡是否掛載");
//            return null;
//        }
//        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
//        String thePath = ImageUtils.getAbsolutePathFromNoStandardUri(uri);
//
//        // 如果是标准Uri
//        if (StringUtils.isEmpty(thePath)) {
//            thePath = ImageUtils.getAbsoluteImagePath(this, uri);
//        }
//        String ext = FileUtils.getFileFormat(thePath);
//        ext = StringUtils.isEmpty(ext) ? "jpg" : ext;
//        // 照片命名
//        String cropFileName = "daka_" + timeStamp + "." + ext;
//        // 裁剪头像的绝对路径
//        protraitPath = FILE_SAVEPATH + cropFileName;
//        protraitFile = new File(protraitPath);
//
//        return Uri.fromFile(protraitFile);
//    }
//
//    /**
//     * 拍照后裁剪
//     *
//     * @param data 原始图片
//     */
//    private void startActionCrop(Uri data) {
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(data, "image/*");
//        intent.putExtra("output", this.getUploadTempFile(data));
//        intent.putExtra("crop", "true");
//        intent.putExtra("aspectX", 1);// 裁剪框比例
//        intent.putExtra("aspectY", 1);
//        intent.putExtra("outputX", CROP);// 输出图片大小
//        intent.putExtra("outputY", CROP);
//        intent.putExtra("scale", true);// 去黑边
//        intent.putExtra("scaleUpIfNeeded", true);// 去黑边
//        startActivityForResult(intent,
//                ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD);
//    }
//
//    @Override
//    public void onActivityResult(final int requestCode, final int resultCode,
//                                 final Intent imageReturnIntent) {
//        if (requestCode != 0) {
//            if (resultCode == 101) {
////                String name = imageReturnIntent.getStringExtra("name");
////                nameTv.setText(name);
//            }
//
//        }
//        //圖片
//        if (resultCode != Activity.RESULT_OK)
//            return;
//
//        switch (requestCode) {
//            case ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA:
//                startActionCrop(origUri);// 拍照后裁剪
//                break;
//            case ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP:
//                startActionCrop(imageReturnIntent.getData());// 选图后裁剪
//                break;
//            case ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD:
//                uploadNewPhoto();
//                break;
//        }
//
//
//    }
//
//    private static final int CAMERA_PERM = 1;
//
//    @AfterPermissionGranted(CAMERA_PERM)
//    private void startTakePhotoByPermissions() {
//        String[] perms = {Manifest.permission.CAMERA};
//        if (EasyPermissions.hasPermissions(this, perms)) {
//            try {
//                startTakePhoto();
//            } catch (Exception e) {
//                Toast.makeText(this, R.string.permissions_camera_error, Toast.LENGTH_LONG).show();
//            }
//        } else {
//            // Request one permission
//            EasyPermissions.requestPermissions(this,
//                    getResources().getString(R.string.str_request_camera_message),
//                    CAMERA_PERM, perms);
//        }
//    }
//
//    @Override
//    public void onPermissionsGranted(int requestCode, List<String> perms) {
//        try {
//            startTakePhoto();
//        } catch (Exception e) {
//            Toast.makeText(this, R.string.permissions_camera_error, Toast.LENGTH_LONG).show();
//        }
//    }
//
//    @Override
//    public void onPermissionsDenied(int requestCode, List<String> perms) {
//        Toast.makeText(this, R.string.permissions_camera_error, Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        // EasyPermissions handles the request result.
//        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
//    }
//
//    private Handler handlerphoto = new Handler() {
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 1:
//                    startTakePhotoByPermissions();
//                    break;
//                case 2:
//                    startImagePick();
//                    break;
//                default:
//                    break;
//            }
//            super.handleMessage(msg);
//        }
//
//        ;
//    };
///*=========================拍照end===========================*/

    /*=========================拍照===========================*/
    //=================通过判断权限启动===============
    private Handler handlerphoto = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    startTakePhotoByPermissions(ImageUtils.CAMERA_REQUEST_CODE);
                    break;
                case 2:
                    startTakePhotoByPermissions(ImageUtils.IMAGE_REQUEST_CODE);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }

        ;
    };

    private void startTakePhotoByPermissions(int type) {
        if (type == ImageUtils.CAMERA_REQUEST_CODE) {
            String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (EasyPermissions.hasPermissions(this, perms)) {
                try {
                    ImageUtils.startTakePhoto(this);
                } catch (Exception e) {
                    Toast.makeText(this, "相机无法完成初始化,请正确授权", Toast.LENGTH_LONG).show();
                }
            } else {
                EasyPermissions.requestPermissions(this, "请求获取相机权限", ImageUtils.CAMERA_REQUEST_CODE, perms);
            }
        } else {
            String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE};
            if (EasyPermissions.hasPermissions(this, perms)) {
                try {
                    ImageUtils.startImagePick(this);
                } catch (Exception e) {
                    Toast.makeText(this, "无法读取图片,请正确授权", Toast.LENGTH_LONG).show();
                }
            } else {
                EasyPermissions.requestPermissions(this, "请求读取图片的权限", ImageUtils.IMAGE_REQUEST_CODE, perms);
            }
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        try {
            if (requestCode == ImageUtils.CAMERA_REQUEST_CODE) {
                ImageUtils.startTakePhoto(this);
            } else {
                ImageUtils.startImagePick(this);
            }
        } catch (Exception e) {
            Toast.makeText(this, "授权不正确,操作无法进行", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Toast.makeText(this, "授权不正确,操作无法进行", Toast.LENGTH_LONG).show();
    }
//=========判断权限启动======================

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != 0) {
            switch (requestCode) {
                case ImageUtils.IMAGE_REQUEST_CODE:
                    if (imgType == 0) {
                        updateImage(data, idIv);
                    } else if (imgType == 1) {
                        updateImage(data, businessIv);
                    } else if (imgType == 2) {
                        updateImage(data, healthIv);
                    }
                    break;
                case ImageUtils.CAMERA_REQUEST_CODE:
                    if (Utils.hasSdcard()) {
                        if (imgType == 0) {
                            updateImage(idIv);
                        } else if (imgType == 1) {
                            updateImage(businessIv);
                        } else if (imgType == 2) {
                            updateImage(healthIv);
                        }
                    } else {
                        Toast mToast = Toast.makeText(this, "未找到存储卡，无法存储照片！", Toast.LENGTH_LONG);
                        mToast.setGravity(Gravity.CENTER, 0, 0);
                        mToast.show();
                    }
                    break;
            }
        }
    }

    /**
     * 上传图片 显示图片
     *
     * @author pan
     * @time 2016/12/6 0006 上午 9:44
     */
    public void updateImage(Intent data, ImageView view) {
        String imagePath1 = ImageUtils.getImageToViewyuantu(data.getData(), this, view);
        uploadNewPhoto(imagePath1);
    }

    public void updateImage(ImageView view) {
        String pa = ImageUtils.getImageToViewyuantu2(ImageUtils.cameraUri, this, view);
        uploadNewPhoto(pa);
    }

    /**
     * 上传新照片
     */
    //上傳圖片
    public void uploadNewPhoto(String imagePath1) {
        File file = null;
        try {
            file = ImageUtilsyasuo.compressAndGenImage(imagePath1, 200, false);
            List<FileItem> ls = new LinkedList<FileItem>();
            FileItem f = new FileItem("image_01", file);
            ls.add(f);
            UpdatePicRequest rq = new UpdatePicRequest();
            String ds = Math.random() + "";
//            rq.setPid(ds + "");   //消除缓存
            showWait(true);
            QuarkApi.HttpuploadFile(rq, ls, httpCallBack);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    HttpCallBack httpCallBack = new HttpCallBack() {
        @Override
        public void onSuccess(String t) {
            super.onSuccess(t);
            showWait(false);
            Log.e("error", "==" + t);
            try {
                UpdatePicResponse info = new UpdatePicResponse(t);
                showToast(info.getMessage());
                if (info.getStatus() == 1) {
                    if (imgType == 0) {
//                    idIv.setImageBitmap(ImageUtils.cameraUri);
                        idName = info.getFileName();
                        TLog.error("身份证" + idName);
                    } else if (imgType == 1) {
//                    businessIv.setImageBitmap(ImageUtils.cameraUri);
                        businessName = info.getFileName();
                        TLog.error("营业照" + businessName);
                    } else if (imgType == 2) {
//                    healthIv.setImageBitmap(ImageUtils.cameraUri);
                        healthName = info.getFileName();
                        TLog.error("卫生证" + healthName);
                    }
                }
            } catch (Exception e) {
                Log.e("error", "數據解析出錯");
            }
            showWait(false);
        }

        public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
            AppContext.showToast("Upload failed" + arg0);
            showWait(false);
        }
    };
    /*=================图片end==========================*/

}
