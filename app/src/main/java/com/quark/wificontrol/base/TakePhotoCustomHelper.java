//package com.quark.wificontrol.base;
//
//import android.net.Uri;
//import android.os.Environment;
//
//import com.jph.takephoto.app.TakePhoto;
//import com.jph.takephoto.compress.CompressConfig;
//import com.jph.takephoto.model.CropOptions;
//
//import java.io.File;
//
///**
// * - 支持通过相机拍照获取图片
// * - 支持从相册选择图片
// * - 支持从文件选择图片
// * - 支持多图选择
// * - 支持批量图片裁切
// * - 支持批量图片压缩
// * - 支持对图片进行压缩
// * - 支持对图片进行裁剪
// * - 支持对裁剪及压缩参数自定义
// * - 提供自带裁剪工具(可选)
// * - 支持智能选取及裁剪异常处理
// * - 支持因拍照Activity被回收后的自动恢复
// * Author: crazycodeboy
// * Date: 2016/9/21 0007 20:10
// * Version:3.0.0
// * 技术博文：http://www.cboy.me
// * GitHub:https://github.com/crazycodeboy
// * Eamil:crazycodeboy@gmail.com
// */
//public class TakePhotoCustomHelper {
//    boolean needCrop = true; //是否需要裁剪
//    int cropTool = 2; //使用的裁剪工具 1第三方裁剪工具 2tokePhoto自带的裁剪工具  默认使用tokePhoto只带的 用手机的有的手机有问题
//    int rgCropSize = 1;//尺寸/比例 1尺寸 2比例
//    int height = 800; //尺寸
//    int width = 800;  //尺寸
//    boolean needCompress = false; //是否需要压缩
//    boolean needShowProgressBar = true; //是否显示压缩进度条
//    int maxSize = 300*1024; //大小不超过？b
//    int maxPixel = 1200; //长或宽不超过？ px
//    int maxPics = 1;  //最多选择多少张
//    int pickFrom = 1;//从哪选择：1相册 2文件
//
//    public void getPhoto(int type, TakePhoto takePhoto) {
//        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
//        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
//        Uri imageUri = Uri.fromFile(file);
//
//        configCompress(takePhoto);
//        if (type == 1) {
//            if (maxPics > 1) {
//                if (needCrop) {
//                    takePhoto.onPickMultipleWithCrop(maxPics, getCropOptions());
//                } else {
//                    takePhoto.onPickMultiple(maxPics);
//                }
//                return;
//            }
//            if (pickFrom == 2) {
//                if (needCrop) {
//                    takePhoto.onPickFromDocumentsWithCrop(imageUri, getCropOptions());
//                } else {
//                    takePhoto.onPickFromDocuments();
//                }
//                return;
//            } else {
//                if (needCrop) {
//                    takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions());
//                } else {
//                    takePhoto.onPickFromGallery();
//                }
//            }
//        } else { //拍照
//            if (needCrop) {
//                takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
//            } else {
//                takePhoto.onPickFromCapture(imageUri);
//            }
//        }
//    }
//
//    private void configCompress(TakePhoto takePhoto) {
//        if (!needCompress) {
//            takePhoto.onEnableCompress(null, false);
//            return;
//        }
//        if (!needShowProgressBar) {
//            return;
//        }
//        CompressConfig config = new CompressConfig.Builder().setMaxSize(maxSize).setMaxPixel(maxPixel).create();
//        takePhoto.onEnableCompress(config, needShowProgressBar);
//    }
//
//    private CropOptions getCropOptions() {
//        if (!needCrop) return null;
//        boolean withWonCrop;
//        if (cropTool == 2) {
//            withWonCrop = true;
//        } else {
//            withWonCrop = false;
//        }
//        CropOptions.Builder builder = new CropOptions.Builder();
//
//        if (rgCropSize == 2) {
//            builder.setAspectX(width).setAspectY(height);
//        } else {
//            builder.setOutputX(width).setOutputY(height);
//        }
//        builder.setWithOwnCrop(withWonCrop);
//        return builder.create();
//    }
//
//    public void setNeedCrop(boolean needCrop) {
//        this.needCrop = needCrop;
//    }
//
//    public void setCropTool(int cropTool) {
//        this.cropTool = cropTool;
//    }
//
//    public void setRgCropSize(int rgCropSize) {
//        this.rgCropSize = rgCropSize;
//    }
//
//    public void setHeight(int height) {
//        this.height = height;
//    }
//
//    public void setWidth(int width) {
//        this.width = width;
//    }
//
//    public void setNeedCompress(boolean needCompress) {
//        this.needCompress = needCompress;
//    }
//
//    public void setNeedShowProgressBar(boolean needShowProgressBar) {
//        this.needShowProgressBar = needShowProgressBar;
//    }
//
//    public void setMaxSize(int maxSize) {
//        this.maxSize = maxSize;
//    }
//
//    public void setMaxPixel(int maxPixel) {
//        this.maxPixel = maxPixel;
//    }
//
//    public void setMaxPics(int maxPics) {
//        this.maxPics = maxPics;
//    }
//
//    public void setPickFrom(int pickFrom) {
//        this.pickFrom = pickFrom;
//    }
//}
