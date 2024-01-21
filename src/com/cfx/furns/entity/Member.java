package com.cfx.furns.entity;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/1/20 13:11
 **/
public class Member {
    private int mId;
    private String mUserName;
    private String mPassword;
    private String mEmail;

    public Member() {
    }

    public Member(int id, String username, String password, String email) {
        mId = id;
        mUserName = username;
        mPassword = password;
        mEmail = email;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    @Override
    public String toString() {
        return "Member{" +
                "mId=" + mId +
                ", mUserName='" + mUserName + '\'' +
                ", mPassword='" + mPassword + '\'' +
                ", mEmail='" + mEmail + '\'' +
                '}';
    }
}
