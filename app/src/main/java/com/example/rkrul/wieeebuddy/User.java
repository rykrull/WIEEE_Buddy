package com.example.rkrul.wieeebuddy;

/**
 * Created by rkrul on 4/27/2016.
 */
public class User {
    private String userName;
    private String fullName;
    private int uwId;
    private String wiscmail;


    public User(String userName, String fullName, int uwId, String wiscmail){
        this.userName = userName;
        this.fullName = fullName;
        this.uwId = uwId;
        this.wiscmail = wiscmail;
    }
    public String getUserName(){
        return userName;
    }

    public String getFullName(){
        return fullName;
    }

    public int getUwId(){
        return uwId;
    }
    public String getWiscmail(){
        return wiscmail;
    }
}
