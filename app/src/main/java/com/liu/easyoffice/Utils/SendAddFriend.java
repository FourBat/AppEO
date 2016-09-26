package com.liu.easyoffice.Utils;

import android.util.Log;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.MessageContent;
import io.rong.message.CommandNotificationMessage;
import io.rong.message.ContactNotificationMessage;
import io.rong.message.ImageMessage;
import io.rong.message.InformationNotificationMessage;
import io.rong.message.LocationMessage;
import io.rong.message.ProfileNotificationMessage;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;

/**
 * Created by hui on 2016/9/26.
 */

public class SendAddFriend {
    public static void sendMessage(final MessageContent msg) {
        RongIMClient.getInstance().sendMessage(Conversation.ConversationType.SYSTEM, "4", msg, null, null, new RongIMClient.SendMessageCallback() {
            @Override
            public void onError(Integer integer, RongIMClient.ErrorCode errorCode) {
                Log.d("sendMessage", "----发发发发发--发送消息失败----ErrorCode----" + errorCode.getValue());
            }

            @Override
            public void onSuccess(Integer integer) {
                if (msg instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) msg;
                    Log.d("sendMessage", "TextMessage---发发发发发--发送了一条【文字消息】-----" + textMessage.getContent());
                } else if (msg instanceof ImageMessage) {
                    ImageMessage imageMessage = (ImageMessage) msg;
                    Log.d("sendMessage", "ImageMessage--发发发发发--发送了一条【图片消息】--uri---" + imageMessage.getThumUri());
                } else if (msg instanceof VoiceMessage) {
                    VoiceMessage voiceMessage = (VoiceMessage) msg;
                    Log.e("sendMessage", "VoiceMessage--发发发发发--发送了一条【语音消息】---getExtra--" + voiceMessage.getExtra());
                    Log.d("sendMessage", "VoiceMessage--发发发发发--发送了一条【语音消息】--长度---" + voiceMessage.getDuration());
                } else if (msg instanceof LocationMessage) {
                    LocationMessage location = (LocationMessage) msg;
                    Log.d("sendMessage", "VoiceMessage--发发发发发--发送了一条【语音消息】---uri--" + location.getPoi());
                } else if (msg instanceof ContactNotificationMessage) {
                    ContactNotificationMessage mContactNotificationMessage = (ContactNotificationMessage) msg;
                    Log.d("sendMessage", "ContactNotificationMessage--发发发发发--发送了一条【联系人（好友）操作通知消息】---message--" + mContactNotificationMessage.getMessage());
                } else if (msg instanceof ProfileNotificationMessage) {
                    ProfileNotificationMessage mProfileNotificationMessage = (ProfileNotificationMessage) msg;
                    Log.d("sendMessage", "ProfileNotificationMessage--发发发发发--发送了一条【资料变更通知消息】---message--" + mProfileNotificationMessage.getData());
                } else if (msg instanceof CommandNotificationMessage) {
                    CommandNotificationMessage mCommandNotificationMessage = (CommandNotificationMessage) msg;
                    Log.d("sendMessage", "CommandNotificationMessage--发发发发发--发送了一条【命令通知消息】---message--" + mCommandNotificationMessage.getData());
                } else if (msg instanceof InformationNotificationMessage) {
                    InformationNotificationMessage mInformationNotificationMessage = (InformationNotificationMessage) msg;
                    Log.d("sendMessage", "InformationNotificationMessage--发发发发发--发送了一条【小灰条消息】---message--" + mInformationNotificationMessage.getMessage());

                }
            }
        });

    }
}
