package com.quark.wificontrol.ui.personalCentel;

import android.Manifest;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.quark.api.auto.bean.ListCustomerBanner;
import com.quark.api.auto.bean.UpdatePicRequest;
import com.quark.api.auto.bean.UpdatePicResponse;
import com.quark.wificontrol.AppContext;
import com.quark.wificontrol.R;
import com.quark.wificontrol.adapter.PicAdapter;
import com.quark.wificontrol.api.remote.QuarkApi;
import com.quark.wificontrol.base.BaseActivity;
import com.quark.wificontrol.ui.chooseImage.FileItem;
import com.quark.wificontrol.ui.chooseImage.ImageMultiUtils;
import com.quark.wificontrol.ui.chooseImage.ImageUtils;
import com.quark.wificontrol.util.TLog;

import org.kymjs.kjframe.http.HttpCallBack;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import me.nereo.multi_image_selector.MultiImageSelector;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by pan on 2016/9/24 0024.
 * >#
 * >#上传照片
 */
public class UploadPhotoActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    @InjectView(R.id.upload_tv)
    TextView uploadTv;

    //    private CustomHelper customHelper;
    int current;
    String imgName;
    String imgNames;
    public static ArrayList<ListCustomerBanner> pics;
    PicAdapter adapter;
    @InjectView(R.id.GridView)
    android.widget.GridView GridView;

//    private File protraitFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadphone);
        ButterKnife.inject(this);
        setTopTitle("上传照片");
        setBackButton();
        initPic();
    }

    public void initPic() {
        pics = new ArrayList<>();
        ListCustomerBanner addpic = new ListCustomerBanner();
        addpic.setIsadd(true);
        pics.add(addpic);
        adapter = new PicAdapter(UploadPhotoActivity.this, handler, 0);
        GridView.setAdapter(adapter);
    }

    @OnClick({R.id.upload_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.upload_tv:
                if (pics == null||pics.size()==0) {
                    showToast("请上传照片");
                }else {
                    //循环上传图片
//                    dealImage1(0);
                    beginupload();
                }
                break;
        }
    }

    public void beginupload(){
        for (int i = 0; i < pics.size()-1; i++) {
//            protraitFile = new File(pics.get(i).getPath());
//            TLog.error("大小为：" + (pics.get(i).getFile1().length() / 1024));
            uploadNewPhoto(pics.get(i).getImageFilePath(),i);
        }
    }

    /**
     * 上传图片
     *
     * @author pan
     * @time 2016/10/19 0019 下午 2:29
     */
    private void uploadNewPhoto(String pathpic,int position) {
        try {
            List<FileItem> ls = new LinkedList<FileItem>();
            File file  = new File(pathpic);
            FileItem f = new FileItem("image_01", file);
            ls.add(f);

            UpdatePicRequest rq = new UpdatePicRequest();
            showWait(true);
            QuarkApi.HttpuploadFile(rq, ls, httpCallBack);
        } catch (Exception e) {
            AppContext.showToast("图像不存在，上传失败");
        }
    }

    List<String> imgList = new ArrayList<>();
    StringBuilder sb = new StringBuilder();
    HttpCallBack httpCallBack = new HttpCallBack() {
        @Override
        public void onSuccess(String t) {
            super.onSuccess(t);
            showWait(false);
            Log.e("error", "==" + t);
            try {
                UpdatePicResponse info = new UpdatePicResponse(t);
                showToast(info.getMessage());
                imgName = info.getFileName();
                imgList.add(imgName);
                TLog.error("图片名+######" + imgName);
                TLog.error(imgList.size() + "有多少个");

                if (imgList.size() == (pics.size()-1)) {
                    //拼接图片名
                    for (int i = 0; i < imgList.size(); i++) {
                        sb.append("#" + imgList.get(i));
                    }
                    imgNames = sb.substring(1, sb.length());
                    TLog.error("最终拼接结果" + imgNames);
                    Intent in = new Intent();
                    in.putExtra("imgName", imgNames);
                    in.putExtra("imgN", info.getFileName());
                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("imgL", pics);
                    in.putExtras(bundle);
                    setResult(107, in);
                    finish();
                }
            } catch (Exception e) {
                Log.e("error", "數據解析出錯");
            }
            showWait(false);
        }

        public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
            AppContext.showToast("上传图片失败");

            showWait(false);
        }
    };

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 202:
                    current = msg.arg1;
    //                    ImageUtils.showSheetPic(UploadPhotoActivity.this, handlerphoto);
                    startTakePhotoByPermissions();
                    break;
                case 203:
                    current = msg.arg1;
                    pics.remove(current);
                    adapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        };
    };

    private void startTakePhotoByPermissions() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            try {
                pickImage();
            } catch (Exception e) {
                Toast.makeText(this, "相机无法完成初始化,请正确授权", Toast.LENGTH_LONG).show();
            }
        } else {
            EasyPermissions.requestPermissions(this, "请求获取相机权限", ImageMultiUtils.CAMERA_REQUEST_CODE, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        try {
            pickImage();
        } catch (Exception e) {
            Toast.makeText(this, "授权不正确,操作无法进行", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Toast.makeText(this, "授权不正确,操作无法进行", Toast.LENGTH_LONG).show();
    }
    //=========判断权限启动======================
/*===================使用multiimageselector选择图片===========================*/
    private static final int REQUEST_IMAGE = 2;
    int maxNum = 6;
    public void pickImage(){
        MultiImageSelector selector = MultiImageSelector.create(UploadPhotoActivity.this);
        selector.showCamera(true);
        selector.count(maxNum);
        selector.multi();
        selector.origin(mSelectPath);
        selector.start(UploadPhotoActivity.this, REQUEST_IMAGE);
    }

    /*===================使用multiimageselector选择图片end===========================*/
    private ArrayList<String> mSelectPath;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != 0) {
            if (resultCode == RESULT_OK) {
                pics.clear();
                mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
//                Log.e("error", "图片张数：" + mSelectPath.size() + "");
                for (int i=0;i<mSelectPath.size();i++) {
                    Log.e("error", mSelectPath.get(i));
                    if (!mSelectPath.get(i).equals("edit")){
                        ListCustomerBanner addpic = new ListCustomerBanner();
                        addpic.setBitmap(BitmapFactory.decodeFile(mSelectPath.get(i), ImageUtils.getBitmapOption(3)));
                        addpic.setImageFilePath(mSelectPath.get(i));
                        addpic.setIsmodify(true);
                        addpic.setIsadd(false);
                        pics.add(addpic);
                    }
                }
                if (pics.size()<7){  //第七个为加号
                    ListCustomerBanner addpic = new ListCustomerBanner();
                    addpic.setIsadd(true);
                    pics.add(addpic);
                }
                adapter.notifyDataSetChanged();
            }
        }
    }


}
