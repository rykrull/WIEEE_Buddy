package com.example.rkrul.wieeebuddy;

/**
 * Created by rkrul on 4/27/2016.
 */
public class User {
    private String fullName;
    private int uwId;
    private String email;

    public User(){

    }

    public User(String userName, String fullName, int uwId, String wiscmail){

        this.fullName = fullName;
        this.uwId = uwId;
        this.email = email;
    }

    public String getFullName(){
        return fullName;
    }
    public int getUwId(){
        return uwId;
    }
    public String getEmail(){
        return email;
    }
}
