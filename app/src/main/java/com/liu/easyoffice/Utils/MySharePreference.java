package com.liu.easyoffice.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.liu.easyoffice.pojo.Company;
import com.liu.easyoffice.pojo.Group;
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
    public static Company getCurrentCompany(Context context){
        SharedPreferences preferences=context.getSharedPreferences("company",Context.MODE_PRIVATE);
        Long tcId=preferences.getLong("tcId",0);
        String tcName=preferences.getString("tcName",null);
        Company company=new Company();
        company.setTcId(tcId);
        company.setTcName(tcName);
        return company;
    }
    public static Group getCurrentCroup(Context context){
        SharedPreferences preferences=context.getSharedPreferences("company",Context.MODE_PRIVATE);
        Long tgId=preferences.getLong("tgId",0);
        Group group=new Group();
        group.setTgId(tgId);
        return group;
    }
}
