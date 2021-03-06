package com.heaven.heavenhelp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.heaven.heavenhelp.model.UserInfo;

/**
 * Created by waylon on 15/11/20.
 */
public class SharePrefUtil {


    public static UserInfo getUserInfo(Context çontext){

        SharedPreferences userInfoPreferences =çontext.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        UserInfo userInfo = new UserInfo();
        userInfo.setToken(userInfoPreferences.getString("token", null));
        userInfo.setMobile(userInfoPreferences.getString("mobile",null));
        return userInfo;
    }


    public static void updateUserInfo(Context çontext,UserInfo userInfo){

        SharedPreferences userInfoPreferences =çontext.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userInfoPreferences.edit();
        editor.putString("token", userInfo.getToken());
        editor.putString("mobile", userInfo.getMobile());
        editor.commit();
    }


}
