package com.developer.jacob.statmaster;

public class User {

    private String mName;
    private String mUsername;
    private String mPassword;
    private String mEmail;
    private String mPrimaryPosition;
    private String mSecondaryPosition;

    public User()
    {
        mName = "";
        mUsername = "";
        mPassword = "";
        mEmail = "";
        mPrimaryPosition = "";
        mSecondaryPosition = "";
    }

    public User(String name, String username, String password, String email) {
        mName = name;
        mUsername = username;
        mPassword = password;
        mEmail = email;
        mPrimaryPosition = "";
        mSecondaryPosition = "";
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
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

    public String getPrimaryPosition() {
        return mPrimaryPosition;
    }

    public void setPrimaryPosition(String primaryPosition) {
        mPrimaryPosition = primaryPosition;
    }

    public String getSecondaryPosition() {
        return mSecondaryPosition;
    }

    public void setSecondaryPosition(String secondaryPosition) {
        mSecondaryPosition = secondaryPosition;
    }
}
