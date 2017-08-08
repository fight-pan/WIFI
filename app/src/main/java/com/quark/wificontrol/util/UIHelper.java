package com.quark.wificontrol.util;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.quark.wificontrol.AppConfig;
import com.quark.wificontrol.AppContext;


/**
 * 界面帮助类
 *
 * @author FireAnt（http://my.oschina.net/LittleDY）
 * @version 创建时间：2014年10月10日 下午3:33:36
 *
 */
public class UIHelper {

    /** 全局web样式 */
    // 链接样式文件，代码块高亮的处理
    public final static String linkCss = "<script type=\"text/javascript\" src=\"file:///android_asset/shCore.js\"></script>"
            + "<script type=\"text/javascript\" src=\"file:///android_asset/brush.js\"></script>"
            + "<script type=\"text/javascript\" src=\"file:///android_asset/client.js\"></script>"
            + "<script type=\"text/javascript\" src=\"file:///android_asset/detail_page.js\"></script>"
            + "<script type=\"text/javascript\">SyntaxHighlighter.all();</script>"
            + "<script type=\"text/javascript\">function showImagePreview(var url){window.location.url= url;}</script>"
            + "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/shThemeDefault.css\">"
            + "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/shCore.css\">"
            + "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/css/common.css\">";
    public final static String WEB_STYLE = linkCss;

    public static final String WEB_LOAD_IMAGES = "<script type=\"text/javascript\"> var allImgUrls = getAllImgSrc(document.body.innerHTML);</script>";

    private static final String SHOWIMAGE = "ima-api:action=showImage&data=";

    static int recLen = AppConfig.MSMSECOND;
    public static void countdown(final TextView getcode, final Handler handler, final Context context,boolean isreget){
        getcode.setClickable(false);
        if (isreget){
            recLen = 1;
        }else{
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    recLen--;
                    if (recLen > 0) {
                        getcode.setText("等待"+recLen+"s");
                        handler.postDelayed(this, 1000);
                    } else {
                        recLen = AppConfig.MSMSECOND;
                        getcode.setClickable(true);
                        getcode.setText("获取验证码");
                    }
                }
            }, 1000);
        }


//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                recLen--;
//                if (recLen > 0) {
//                    getcode.setText("等待"+recLen+"s");
//                    handler.postDelayed(this, 1000);
//                } else {
//                    recLen = AppConfig.MSMSECOND;
//                    getcode.setClickable(true);
//                    getcode.setTextColor(context.getResources().getColor(R.color.huise));
//                    getcode.setText("獲取驗證碼");
//                }
//            }
//        };
    }

    /**
     * 获取试用码倒计时
     * @author pan
     * @time 2016/10/9 0009 下午 2:49
     */
    public static void countdddd(final TextView getcode, final Handler handler, final Context context,boolean isreget) {
        getcode.setClickable(false);
        if (isreget) {
            recLen = 1;
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    recLen--;
                    if (recLen > 0) {
                        getcode.setText("等待" + recLen + "s");
                        handler.postDelayed(this, 1000);
                    } else {
                        recLen = AppConfig.MSMSECOND;
                        getcode.setClickable(true);
                        getcode.setText("如果没有试用码,或者忘记试用码,点击重新获取");
                    }
                }
            }, 1000);
        }
    }

    /**
     * 清除app缓存
     *
     * @param activity
     */
    public static void clearAppCache(Activity activity) {
        final Handler handlerbase = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    AppContext.showToastShort("缓存清除成功");
                } else {
                    AppContext.showToastShort("缓存清除失败");
                }
            }
        };
        new Thread() {
            @Override
            public void run() {
                Message msg = new Message();
                try {
                    AppContext.getInstance().clearAppCache();
                    msg.what = 1;
                } catch (Exception e) {
                    e.printStackTrace();
                    msg.what = -1;
                }
                handlerbase.sendMessage(msg);
            }
        }.start();
    }

}
