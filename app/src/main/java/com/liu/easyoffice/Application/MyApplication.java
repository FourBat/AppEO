package com.liu.easyoffice.Application;

import android.app.Application;
import android.graphics.Typeface;

import org.xutils.BuildConfig;
import org.xutils.x;

import io.rong.imkit.RongIM;

/**
 * Created by hui on 2016/9/13.
 */
public class MyApplication extends Application{
    public static MyApplication instance;
    public Typeface kaiTi;
    @Override
    public void onCreate() {
        super.onCreate();
        instance= (MyApplication) getApplicationContext();
        kaiTi=Typeface.createFromAsset(getAssets(),"fonts/kaiti.ttf");//设置字体
        /**
         * 初始化Xutils
         */
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);

        /**
         * 初始化IMkit
         */
        RongIM.init(this);
    }
    public static MyApplication getInstance(){
        return instance;
    }
    public Typeface getTypeface(){
        return kaiTi;
    }

}
