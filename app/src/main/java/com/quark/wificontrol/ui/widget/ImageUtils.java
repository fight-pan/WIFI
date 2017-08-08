package com.quark.wificontrol.ui.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quark.wificontrol.AppContext;
import com.quark.wificontrol.R;
import com.quark.wificontrol.ui.chooseImage.ImageUtilSOS;
import com.quark.wificontrol.util.FileUtils;
import com.quark.wificontrol.util.StringUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageUtils {

    public static final int IMAGE_REQUEST_CODE = 0;     //相册取图
    public static final int CAMERA_REQUEST_CODE = 1;    //相机拍照
    public static final int RESULT_REQUEST_CODE = 2;    //裁剪后的图片
    public static String absolutePath;
    public final static String FILE_SAVEPATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/gulugulu/Portrait/";

    public static Uri cropUri;
    public static Uri cameraUri;
    public static File protraitFile;
    public static Bitmap protraitBitmap;
    public static String protraitPath;

    public static Dialog showSheetPic(final Context context, final Handler handler) {
        final Dialog dlg = new Dialog(context, R.style.ActionSheet);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.actionsheet, null);
        final int cFullFillWidth = 10000;
        layout.setMinimumWidth(cFullFillWidth);
        TextView mContent = (TextView) layout.findViewById(R.id.content);
        TextView mCancel = (TextView) layout.findViewById(R.id.cancel);
        TextView mTitle = (TextView) layout.findViewById(R.id.title);

        mContent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);
                dlg.dismiss();
            }
        });

        mTitle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.what = 2;
                handler.sendMessage(msg);
                dlg.dismiss();
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dlg.dismiss();
            }
        });
        Window w = dlg.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.x = 0;
        final int cMakeBottom = -1000;
        lp.y = cMakeBottom;
        lp.gravity = Gravity.BOTTOM;
        dlg.onWindowAttributesChanged(lp);
        dlg.setCanceledOnTouchOutside(false);
        dlg.setContentView(layout);
        dlg.show();

        return dlg;
    }

    /**
     * fragment
     *
     * @author leon
     * @time 2016/6/30 0030 下午 3:08
     */
    public static void startImagePick(Fragment comtent) {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            comtent.startActivityForResult(Intent.createChooser(intent, "选择图片"), IMAGE_REQUEST_CODE);
        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            comtent.startActivityForResult(Intent.createChooser(intent, "选择图片"), IMAGE_REQUEST_CODE);
        }
    }

    /**
     * Activity
     *
     * @author leon
     * @time 2016/6/30 0030 下午 3:08
     */
    public static void startImagePick(Activity comtent) {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            comtent.startActivityForResult(Intent.createChooser(intent, "选择图片"), IMAGE_REQUEST_CODE);
        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            comtent.startActivityForResult(Intent.createChooser(intent, "选择图片"), IMAGE_REQUEST_CODE);
        }
    }

    /**
     * activity
     *
     * @author leon
     * @time 2016/6/30 0030 下午 3:09
     */
    public static void startTakePhoto(Activity content) {
        // 判断是否挂载了SD卡
        String savePath = "";
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/skilltemp/";
            File savedir = new File(savePath);
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
        }
        // 没有挂载SD卡，无法保存文件
        if (TextUtils.isEmpty(savePath)) {
            AppContext.showToastShort("Unable to save photos, please check whether the SD card mount");
            return;
        }
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String fileName = timeStamp + ".jpg";// 照片命名
        File out = new File(savePath, fileName);
        cameraUri = Uri.fromFile(out);
        absolutePath = savePath + fileName; // 该照片的绝对路径

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
        content.startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    /**
     * @author leon
     * @time 2016/6/30 0030 下午 3:09
     */
    public static void startTakePhoto(Fragment content) {
        // 判断是否挂载了SD卡
        String savePath = "";
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/skilltemp/";
            File savedir = new File(savePath);
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
        }
        // 没有挂载SD卡，无法保存文件
        if (TextUtils.isEmpty(savePath)) {
            AppContext.showToastShort("Unable to save photos, please check whether the SD card mount");
            return;
        }
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String fileName = timeStamp + ".jpg";// 照片命名
        File out = new File(savePath, fileName);
        cameraUri = Uri.fromFile(out);
        absolutePath = savePath + fileName; // 该照片的绝对路径

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
        content.startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    public static String getImageToView(Intent data, ImageView pic) {
        // 获取头像 裁剪是保存了路径
        if (!StringUtils.isEmpty(protraitPath) && protraitFile.exists()) {
            protraitBitmap = ImageUtilSOS.loadImgThumbnail(protraitPath);
        } else {
            AppContext.showToast("Image does not exist, upload failed");
        }
        if (protraitBitmap != null) {
            if (pic != null) {
                pic.setImageBitmap(protraitBitmap);
            }
        }
        return protraitPath;
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public static void startPhotoZoom(Uri uri, int x, int y, Activity activity) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", x);
        intent.putExtra("aspectY", y);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", x);
        intent.putExtra("outputY", y);
        intent.putExtra("scale", true);// 去黑边
        intent.putExtra("scaleUpIfNeeded", true);// 去黑边
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("output", getUploadTempFile(uri, activity));

        activity.startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    /**
     * 在fragment中使用
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public static void startPhotoZoom(Uri uri, int x, int y, Activity activity, Fragment fragment) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", x);
        intent.putExtra("aspectY", y);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", x);
        intent.putExtra("outputY", y);
        intent.putExtra("scale", true);// 去黑边
        intent.putExtra("scaleUpIfNeeded", true);// 去黑边
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("output", getUploadTempFile(uri, activity));
        fragment.startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    // 裁剪头像的绝对路径
    public static Uri getUploadTempFile(Uri uri, Activity activity) {
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            File savedir = new File(FILE_SAVEPATH);
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
        } else {
            AppContext.showToast("Cannot save the uploaded picture, please check whether the SD card mount");
            return null;
        }
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String thePath = ImageUtilSOS.getAbsolutePathFromNoStandardUri(uri);

        // 如果是标准Uri
        if (StringUtils.isEmpty(thePath)) {
            thePath = ImageUtilSOS.getAbsoluteImagePath(activity, uri);
        }

        String ext = FileUtils.getFileFormat(thePath);
        ext = StringUtils.isEmpty(ext) ? "jpg" : ext;
        // 照片命名
        String cropFileName = "tmp_" + timeStamp + "." + ext;
        // 裁剪头像的绝对路径
        protraitPath = FILE_SAVEPATH + cropFileName;
        protraitFile = new File(protraitPath);
        cropUri = Uri.fromFile(protraitFile);

        return cropUri;
    }

    public static String getImageToViewyuantu(Uri uri, Activity activity, ImageView pic) {
        String selectedImagePath = "";
        if (uri != null) {
            selectedImagePath = ImageUtilSOS.getImagePath(uri, activity);
        }

        if (!StringUtils.isEmpty(selectedImagePath)) {
            Bitmap bitm = ImageUtilSOS.loadImgThumbnail(selectedImagePath);
            if (pic != null) {
                if (pic != null) {
                    pic.setImageBitmap(bitm);
                }
            }
        } else {
            AppContext.showToast("Image does not exist, upload failed");
        }

        return selectedImagePath;
    }

    public static String getImageToViewyuantu2(Uri uri, Activity activity, ImageView pic) {
        Bitmap bitmap = ImageUtilSOS.loadImgThumbnail(absolutePath, 100, 100);
        if (pic != null) {
            pic.setImageBitmap(bitmap);
        }

        return absolutePath;
    }

    public static BitmapFactory.Options getBitmapOption(int inSampleSize) {
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = inSampleSize;
        return options;
    }

}
