package com.liu.easyoffice.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.liu.easyoffice.pojo.User;

/**
 * Created by hui on 2016/9/22.
 */

public class MySharePreference {
    public static User getCurrentUser(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        String userId = preferences.getString("userId", "");
        String userName=preferences.getString("userName","");
        String imgUrl=preferences.getString("imgUrl","");
        User user=new User();
        user.setUserId(userId);
        user.setUserName(userName);
        user.setImgUrl(imgUrl);
        return user;
    }
}
