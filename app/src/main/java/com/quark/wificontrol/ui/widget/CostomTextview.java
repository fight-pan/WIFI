package com.quark.wificontrol.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by pan on 2016/9/30 0030.
 * >#
 * >#
 */
public class CostomTextview extends TextView {
    public CostomTextview(Context context) {
        super(context);
    }

    public CostomTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CostomTextview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public boolean isFocused() {
        return true;
    }
}
