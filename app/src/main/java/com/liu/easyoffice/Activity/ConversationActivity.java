package com.liu.easyoffice.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.liu.easyoffice.R;
import com.liu.easyoffice.Utils.Utils;
import com.liu.easyoffice.pojo.User;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;

/**
 * 会话界面
 * Created by hui on 2016/9/22.
 */

public class ConversationActivity extends FragmentActivity {
    private TextView chatTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation);
        init();
    }

    /**
     * 控件初始化
     */
    private void init(){
        chatTitle = ((TextView) findViewById(R.id.chat_title));
        initMsg();
    }

    /**
     * 控件信息初始化
     */
    private void initMsg(){
        //设置页面title
        Uri uri=getIntent().getData();
        final String targetId=uri.getQueryParameter("targetId").toString();
        final String title=uri.getQueryParameter("title").toString();
        chatTitle.setText(title);
        RongIM.setConversationBehaviorListener(new RongIM.ConversationBehaviorListener() {
            @Override
            public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
                Intent intent=new Intent(getApplicationContext(),ContactMsgActivity.class);
                intent.putExtra("userId",userInfo.getUserId());
                startActivity(intent);

                Toast.makeText(context,userInfo.getName(),Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
                return false;
            }

            @Override
            public boolean onMessageClick(Context context, View view, Message message) {
                return false;
            }

            @Override
            public boolean onMessageLinkClick(Context context, String s) {
                return false;
            }

            @Override
            public boolean onMessageLongClick(Context context, View view, Message message) {
                return false;
            }
        });
    }
}
