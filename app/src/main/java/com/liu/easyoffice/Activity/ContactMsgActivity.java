package com.liu.easyoffice.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.liu.easyoffice.R;
import com.liu.easyoffice.Utils.Utils;
import com.liu.easyoffice.pojo.User;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by hui on 2016/9/22.
 */

public class ContactMsgActivity extends Activity {
    private ImageView contactImg;
    private TextView contactName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_msg);
        init();
    }

    /**
     * 初始化控件
     */
    private void init(){
        contactImg = ((ImageView) findViewById(R.id.contact_iv_title));
        contactName = ((TextView) findViewById(R.id.contact_tv_name));
        initMsg();
    }
    /**
     * 初始化控件信息
     */
    private void initMsg(){
        String userId=getIntent().getStringExtra("userId");
        Log.e("con","user=="+userId);
        RequestParams params=new RequestParams(Utils.CONTACTS_MSG_URL);
        params.addParameter("userId",userId);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("con",result);
                Gson gson=new Gson();
                User user=gson.fromJson(result, User.class);
                String userName=user.getUserName();
                String imgUrl=user.getImgUrl();
                contactName.setText(userName);
                x.image().bind(contactImg,imgUrl);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("con",ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }
}
