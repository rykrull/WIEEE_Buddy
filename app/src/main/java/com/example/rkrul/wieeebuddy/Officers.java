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
        return Html.fromHtml("<font color='#981717'>Name:</font> ") + name + "\n" + "Position: " + position + "\n" + "Email: " + email+"\n";
        //Html.fromHtml("RGB colors are <font color='#FF0000'>RED</font>, <font color='#00FF00'>GREEN</font> and <font color='#0000FF'>BLUE</font>")
    }
}
