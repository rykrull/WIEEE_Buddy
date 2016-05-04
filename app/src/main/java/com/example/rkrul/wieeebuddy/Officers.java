package com.example.rkrul.wieeebuddy;

import android.text.Html;

import java.io.Serializable;

/**
 * Created by JohnB on 5/1/2016.
 */
public class Officers implements Serializable {

    private String name;
    private String email;
    private String position;


    public Officers(){
    }

    public Officers(String name, String email, String position){
        this.name  = name;
        this.email = email;
        this.position = position;
    }

    public String getName(){return name;}
    public String getEmail(){return email;}
    public String getPosition(){return position;}

    public String toString(){
        return "Name: " + name + "\nPosition: " + position + "\nEmail: " + email+"\n";
    }
}
