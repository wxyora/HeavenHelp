package com.heaven.heavenhelp.model;

/**
 * Created by Acer-002 on 2015/7/15.
 */
public class UserInfo {

    private String userName;

    private String mobile;

    private String userAge;

    private String token;


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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
