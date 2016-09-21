package com.liu.easyoffice.Application;

import android.app.Application;
import android.graphics.Typeface;

import org.xutils.BuildConfig;
import org.xutils.x;

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
        kaiTi=Typeface.createFromAsset(getAssets(),"fonts/kaiti.ttf");
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
    }
    public static MyApplication getInstance(){
        return instance;
    }
    public Typeface getTypeface(){
        return kaiTi;
    }
}
