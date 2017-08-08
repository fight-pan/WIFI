package com.quark.wificontrol.ui.find;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.quark.api.auto.bean.ImageViewList;
import com.quark.wificontrol.R;
import com.quark.wificontrol.api.ApiHttpClient;
import com.quark.wificontrol.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class ShowImageActivity extends BaseActivity {
    WindowManager wm;
    private ViewPager viewPager;
    private List<ImageView> imageViewsList;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    String[] imageUrls;
    int dsslid;

    List<ImageViewList> imgs;
    int currentItem;

    @InjectView(R.id.current)
    TextView current;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.show_bigpic);
        ButterKnife.inject(this);
        initImageLoader(this);


        imgs = (List<ImageViewList>) getValue4Intent("imgs");
//        currentItem = (int) getValue4Intent("current");

        imageUrls = new String[imgs.size()];
        for (int i = 0; i < imgs.size(); i++) {
            imageUrls[i] = ApiHttpClient.loadImage + imgs.get(i).getIv();
        }

        imageViewsList = new ArrayList<ImageView>();
        // 热点个数与图片特殊相等
        for (int i = 0; i < imageUrls.length; i++) {
            ImageView view = new ImageView(this);
            view.setTag(imageUrls[i]);
            if (i == 0) //给一个默认图

//        		view.setBackgroundResource(R.drawable.default_big);
                //这里修改为黑色背景图
                view.setBackgroundResource(R.drawable.black_bg);
//            view.setScaleType(ScaleType.CENTER_CROP);
            imageViewsList.add(view);
        }

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setFocusable(true);

        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
        viewPager.setCurrentItem(currentItem);


    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    /**
     * 填充ViewPager的页面适配器
     */
    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public void destroyItem(View container, int position, Object object) {
            // TODO Auto-generated method stub
            //((ViewPag.er)container).removeView((View)object);
            ((ViewPager) container).removeView(imageViewsList.get(position));
        }

        @Override
        public Object instantiateItem(View container, final int position) {
            final ImageView imageView = imageViewsList.get(position);
            imageLoader.displayImage(imageView.getTag() + "", imageView, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    //刚开始加载图片显示的图片
                    imageView.setImageDrawable(ContextCompat.getDrawable(ShowImageActivity.this, R.drawable.banner));
                    //修改为黑色背景
//                    imageView.setImageDrawable(ContextCompat.getDrawable(ShowImageActivity.this, R.drawable.black_bg));
                }

                @Override
                public void onLoadingFailed(String imageUri, View view,
                                            FailReason failReason) {
                    // TODO Auto-generated method stub
                    imageView.setImageDrawable(ContextCompat.getDrawable(ShowImageActivity.this, R.drawable.banner));
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    // TODO Auto-generated method stub
                    imageView.setImageBitmap(loadedImage);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    // TODO Auto-generated method stub

                }
            });

            ((ViewPager) container).addView(imageViewsList.get(position));

//			 per.setOnClickListener(new OnClickListener() {
//					
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						Toast.makeText(context, "=="+pos, 0).show();
//					}
//				});


            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    showToast("dinia");
                    finish();
                }
            });
            return imageViewsList.get(position);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return imageViewsList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
            // TODO Auto-generated method stub

        }

        @Override
        public Parcelable saveState() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void finishUpdate(View arg0) {
            // TODO Auto-generated method stub

        }
    }

    /**
     * ViewPager的监听器
     * 当ViewPager中页面的状态发生改变时调用
     */
    private class MyPageChangeListener implements OnPageChangeListener {

        boolean isAutoPlay = false;

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub
            switch (arg0) {
                case 1:// 手势滑动，空闲中
                    isAutoPlay = false;
                    break;
                case 2:// 界面切换中
                    isAutoPlay = true;
                    break;
                case 0:// 滑动结束，即切换完毕或者加载完毕
                    // 当前为最后一张，此时从右向左滑，则切换到第一张
                    if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1 && !isAutoPlay) {
//                    viewPager.setCurrentItem(0);
                    }
                    // 当前为第一张，此时从左向右滑，则切换到最后一张
                    else if (viewPager.getCurrentItem() == 0 && !isAutoPlay) {
//                    viewPager.setCurrentItem(viewPager.getAdapter().getCount() - 1);
                    }
                    break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub
//        	pictitle.setText(newBanner.get(arg0).getTag());
            current.setText((arg0 + 1) + "/" + imgs.size());
        }

        @Override
        public void onPageSelected(final int pos) {
            // TODO Auto-generated method stub
            currentItem = pos;

        }
    }

    /**
     * ImageLoader 图片组件初始化
     *
     * @param context
     */
    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you
        // may tune some of them,
        // or you can create default configuration by
        // ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory().discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).writeDebugLogs() // Remove
                // for
                // release
                // app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }
}




