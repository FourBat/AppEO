package com.liu.easyoffice.MyView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.liu.easyoffice.Application.MyApplication;

/**
 * Created by hui on 2016/9/19.
 * 设置字体
 */
public class MyFontView extends TextView {
    public MyFontView(Context context) {
        super(context);
//        setTypeface(MyApplication.getInstance().getTypeface());
    }

    public MyFontView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        setTypeface(MyApplication.getInstance().getTypeface());
    }

    public MyFontView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        setTypeface(MyApplication.getInstance().getTypeface());
    }
}
