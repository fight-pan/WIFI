package com.quark.wificontrol.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.quark.wificontrol.R;


/**
 * Created by XIAO-Y on 2016/8/25.
 * >#自定义检索view
 */
public class QuickIndexBar extends View {

    private String[] indexArr = { "#","$","A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z" };
    private Paint paint;
    private int width;
    private float cellHeight;//格子的高度
    public QuickIndexBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public QuickIndexBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public QuickIndexBar(Context context) {
        super(context);
        init();
    }

    private void init(){
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);//设置抗锯齿
//        paint.setColor(Color.WHITE);
        paint.setColor(ContextCompat.getColor(getContext(), R.color.color_lv));
        paint.setTextSize(18);
        paint.setFakeBoldText(true);
        paint.setTextAlign(Paint.Align.CENTER);//设置文字原点的位置是底边的中心

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = getMeasuredWidth();
        cellHeight = getMeasuredHeight()*1f/indexArr.length;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //遍历字母的数组,等分的绘制26个字母的位置
        for (int i = 0; i < indexArr.length; i++) {
            float x = width/2;
            float y = cellHeight/2 + getTextHeight(indexArr[i])/2 + i*cellHeight;
            //如果正在绘制的字母和刚刚触摸的字母一样，那么就变颜色
//            paint.setColor(i==lastIndex?Color.BLUE:Color.RED);
            paint.setColor(i==lastIndex?Color.BLUE: ContextCompat.getColor(getContext(), R.color.color_lv));
            paint.setTextSize(18);
            canvas.drawText(indexArr[i],x,y, paint);
        }
    }
    private int lastIndex = -1;//上个字母的索引
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                int index = (int) (y/cellHeight);
                if(index!=lastIndex){
                    //说明字母改变了
                    //增强代码的健壮性
                    if(index>=0 && index<indexArr.length){
                        if(listener!=null){
                            listener.onLetterChange(indexArr[index]);
                        }
                    }
                }
                //更新lastIndex
                lastIndex = index;
                break;
            case MotionEvent.ACTION_UP:
                lastIndex = -1;//重置所记录的索引
                break;
        }
        invalidate();//刷新，引起onDraw回调
        return true;
    }

    /**
     * 获取一段文字的高度
     * @param text
     * @return
     */
    private int getTextHeight(String text){
        Rect bounds = new Rect();
        paint.getTextBounds(text,0,text.length(), bounds);//方法执行完毕，bounds就有值了
        return bounds.height();
    }

    private OnTouchLetterChangeListener listener;
    public void setOnTouchLetterChangeListener(OnTouchLetterChangeListener listener){
        this.listener = listener;
    }

    /**
     * 触摸字母改变的监听器
     * @author Administrator
     *
     */
    public interface OnTouchLetterChangeListener{
        void onLetterChange(String letter);
    }
}
