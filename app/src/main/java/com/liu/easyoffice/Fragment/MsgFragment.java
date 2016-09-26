package com.liu.easyoffice.Fragment;


import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.liu.easyoffice.R;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.message.ContactNotificationMessage;
import io.rong.push.RongPushClient;

/**
 * Created by hui on 2016/9/14.
 */
public class MsgFragment extends Fragment {
    private static MsgFragment instance=null;
    private Button btnChat;
    private Button startChat;

    public  static MsgFragment getInstance(){

        if (instance!=null){
            return instance;
        }else {
            return new MsgFragment();
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.msg_fragment,null);
        init(view);
        ConversationListFragment fragment= (ConversationListFragment) getChildFragmentManager().findFragmentById(R.id.conversationlist);
        Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")//设置群组会话聚合显示
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//设置讨论组会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//设置系统会话非聚合显示
                .build();

        fragment.setUri(uri);
        return view;
    }
    private void init(View view){
        startChat = ((Button) view.findViewById(R.id.startChat));
        startChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContactNotificationMessage contact = ContactNotificationMessage.obtain(ContactNotificationMessage.CONTACT_OPERATION_REQUEST,"1","2","hhh");
                contact.setExtra("I'm Bob");

                RongIMClient.getInstance().sendMessage(Conversation.ConversationType.SYSTEM, "2", contact, "hah", null, new RongIMClient.SendMessageCallback() {
                    @Override
                    public void onError(Integer integer, RongIMClient.ErrorCode errorCode) {
                        Log.e("add", "失败" + errorCode.getMessage());
                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        Log.e("add", "成功" + integer.toString());
                    }
                }, new RongIMClient.ResultCallback<Message>() {
                    @Override
                    public void onSuccess(Message message) {
                        Log.e("add","result-su"+message.toString());
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {
                        Log.e("add","result"+errorCode.toString());
                    }
                });


            }
        });
    }
}
