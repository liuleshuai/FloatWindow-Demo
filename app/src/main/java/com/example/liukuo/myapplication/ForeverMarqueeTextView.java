package com.example.liukuo.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by LiuKuo at 2018/4/25
 */

public class ForeverMarqueeTextView extends TextView {
    public ForeverMarqueeTextView(Context context) {
        super(context);
    }

    public ForeverMarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ForeverMarqueeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
