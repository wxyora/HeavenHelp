package com.heaven.heavenhelp.model;

/**
 * Created by Acer-002 on 2015/7/15.
 */
public class UserInfo {

    private String userName;

    private String userAge;


    public UserInfo() {

    }

    public UserInfo(String userName, String userAge) {
        this.userName = userName;
        this.userAge = userAge;
    }

    public String getUserAge() {
        return userAge;
    }

    public String getUserName() {
        return userName;
    }
}
