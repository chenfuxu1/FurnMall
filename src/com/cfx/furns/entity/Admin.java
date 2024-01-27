package com.cfx.furns.entity;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/1/20 13:11
 *
 * 管理员实体类
 **/
public class Admin {
    private Integer mId; // id
    private String mUserName; // 用户名
    private String mPassword; // 密码
    private String mEmail; // 邮箱

    public Admin() {
    }

    public Admin(Integer id, String username, String password, String email) {
        mId = id;
        mUserName = username;
        mPassword = password;
        mEmail = email;
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
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
        return "Admin{" +
                "mId=" + mId +
                ", mUserName='" + mUserName + '\'' +
                ", mPassword='" + mPassword + '\'' +
                ", mEmail='" + mEmail + '\'' +
                '}';
    }
}
