//package com.quark.wificontrol.ui.personalCentel;
//
//import android.Manifest;
//import android.app.Activity;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Environment;
//import android.os.Message;
//import android.provider.MediaStore;
//import android.support.annotation.NonNull;
//import android.util.Log;
//import android.view.View;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.quark.api.auto.bean.ListCustomerBanner;
//import com.quark.api.auto.bean.UpdatePicRequest;
//import com.quark.api.auto.bean.UpdatePicResponse;
//import com.quark.wificontrol.AppContext;
//import com.quark.wificontrol.R;
//import com.quark.wificontrol.adapter.PicAdapter;
//import com.quark.wificontrol.api.remote.QuarkApi;
//import com.quark.wificontrol.base.BaseActivity;
//import com.quark.wificontrol.ui.chooseImage.FileItem;
//import com.quark.wificontrol.ui.chooseImage.ImageUtils;
//import com.quark.wificontrol.util.FileUtils;
//import com.quark.wificontrol.util.TLog;
//
//import org.kymjs.kjframe.http.HttpCallBack;
//
//import java.io.File;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.List;
//
//import butterknife.ButterKnife;
//import butterknife.InjectView;
//import butterknife.OnClick;
//import cz.msebera.android.httpclient.Header;
//import pub.devrel.easypermissions.AfterPermissionGranted;
//import pub.devrel.easypermissions.EasyPermissions;
//
////import com.jph.takephoto.model.TImage;
////import com.jph.takephoto.model.TResult;
////import com.quark.wificontrol.base.BaseTakePhotoActivity;
////import com.quark.wificontrol.base.TakePhotoCustomHelper;
//
///**
// * Created by pan on 2016/9/24 0024.
// * >#
// * >#上传照片
// */
//public class UploadPhotoActivityold extends BaseActivity {
//    @InjectView(R.id.upload_tv)
//    TextView uploadTv;
//
//    //    private CustomHelper customHelper;
//    int current;
//    String imgName;
//    String imgNames;
//    public static ArrayList<ListCustomerBanner> pics;
//    PicAdapter adapter;
//    @InjectView(R.id.GridView)
//    android.widget.GridView GridView;
//
//    private File protraitFile;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_uploadphone);
//        ButterKnife.inject(this);
//        setTopTitle("上传照片");
//        setBackButton();
//        msg = (ArrayList<TImage>) getValue4Intent("img");
//        initPic();
//        if (msg != null) {
//            pics.clear();
//            for (int i = 0; i < msg.size(); i++) {
//                ListCustomerBanner addpic = new ListCustomerBanner();
//                addpic.setBitmap(BitmapFactory.decodeFile(msg.get(i).getPath(), ImageUtils.getBitmapOption(3)));
//                addpic.setImageFilePath(msg.get(i).getPath());
//                addpic.setIsmodify(true);
//                addpic.setIsadd(false);
//                pics.add(addpic);
//            }
//            if (pics.size() < 7) {  //第七个为加号
//                ListCustomerBanner addpic = new ListCustomerBanner();
//                addpic.setIsadd(true);
//                pics.add(addpic);
//            }
//            adapter.notifyDataSetChanged();
//        }
//    }
//
//    public void initPic() {
//        pics = new ArrayList<>();
//        ListCustomerBanner addpic = new ListCustomerBanner();
//        addpic.setIsadd(true);
//        pics.add(addpic);
//        adapter = new PicAdapter(UploadPhotoActivity.this, handler, 0);
//        GridView.setAdapter(adapter);
//    }
//
//
//    @Override
//    public void takeFail(TResult result, String msg) {
//        super.takeFail(result, msg);
//    }
//
//    ArrayList<TImage> msg;
//
//    @Override
//    public void takeSuccess(TResult result) {
//        super.takeSuccess(result);
//        msg = result.getImages();
//        pics.clear();
//        for (int i = 0; i < msg.size(); i++) {
//            ListCustomerBanner addpic = new ListCustomerBanner();
//            addpic.setBitmap(BitmapFactory.decodeFile(msg.get(i).getPath(), ImageUtils.getBitmapOption(3)));
//            addpic.setImageFilePath(msg.get(i).getPath());
//            addpic.setIsmodify(true);
//            addpic.setIsadd(false);
//            pics.add(addpic);
//        }
//        if (pics.size() < 7) {  //第七个为加号
//            ListCustomerBanner addpic = new ListCustomerBanner();
//            addpic.setIsadd(true);
//            pics.add(addpic);
//        }
//        adapter.notifyDataSetChanged();
//
//    }
//
//    private Handler handler = new Handler() {
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 202:
//                    current = msg.arg1;
//                    TakePhotoCustomHelper pconfig = new TakePhotoCustomHelper();
//                    pconfig.setMaxPics(6);
//                    pconfig.setNeedCrop(true);
//                    ImageUtils.showSheetPic(UploadPhotoActivity.this, pconfig, getTakePhoto());
//                    break;
//                case 203:
//                    current = msg.arg1;
//                    pics.remove(current);
//                    adapter.notifyDataSetChanged();
//                    break;
//                default:
//                    break;
//            }
//            super.handleMessage(msg);
//        }
//    };
//
//    @OnClick({R.id.upload_tv})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.upload_tv:
//                if (msg == null) {
//                    showToast("请上传照片");
//                }else {
//                    //循环上传图片
//                    for (int i = 0; i < msg.size(); i++) {
//                        protraitFile = new File(msg.get(i).getPath());
//                        TLog.error("大小为：" + (protraitFile.length() / 1024));
//                        uploadNewPhoto();
//                    }
//                }
//                break;
//        }
//    }
//
//    /**
//     * 上传图片
//     *
//     * @author pan
//     * @time 2016/10/19 0019 下午 2:29
//     */
//    private void uploadNewPhoto() {
//
//        try {
//            List<FileItem> ls = new LinkedList<FileItem>();
//            FileItem f = new FileItem("image_01", protraitFile);
//            ls.add(f);
//            UpdatePicRequest rq = new UpdatePicRequest();
//            showWait(true);
//            QuarkApi.HttpuploadFile(rq, ls, httpCallBack);
//        } catch (Exception e) {
//            AppContext.showToast("图像不存在，上传失败");
//        }
//    }
//
//    List<String> imgList = new ArrayList<>();
//    StringBuilder sb = new StringBuilder();
//    HttpCallBack httpCallBack = new HttpCallBack() {
//        @Override
//        public void onSuccess(String t) {
//            super.onSuccess(t);
//            showWait(false);
//            Log.e("error", "==" + t);
//            try {
//                UpdatePicResponse info = new UpdatePicResponse(t);
//                showToast(info.getMessage());
//                imgName = info.getFileName();
//                imgList.add(imgName);
//                TLog.error("图片名+######" + imgName);
//                TLog.error(imgList.size() + "有多少个");
//
//                if (imgList.size() == msg.size()) {
//                    //拼接图片名
//                    for (int i = 0; i < imgList.size(); i++) {
//                        sb.append("#" + imgList.get(i));
//                    }
//                    imgNames = sb.substring(1, sb.length());
//                    TLog.error("最终拼接结果" + imgNames);
//                    Intent in = new Intent();
//                    in.putExtra("imgName", imgNames);
//                    in.putExtra("imgN", info.getFileName());
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("imgL", msg);
//                    in.putExtras(bundle);
//                    setResult(107, in);
//                    finish();
//                }
//            } catch (Exception e) {
//                Log.e("error", "數據解析出錯");
//            }
//            showWait(false);
//        }
//
//        public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
//            AppContext.showToast("上传图片失败");
//
//            showWait(false);
//        }
//    };
//
//    @Override
//    public void initView() {
//
//    }
//
//    @Override
//    public void initData() {
//
//    }
//
//
//
//
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
//     * 上传新照片
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
//                photoIv.setImageBitmap(protraitBitmap);
//                imgName = info.getFileName();
//
//            } catch (Exception e) {
//                Log.e("error", "數據解析出錯");
//            }
//            showWait(false);
//        }
//
//        public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
//            AppContext.showToast("更換頭像失敗");
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
//}
