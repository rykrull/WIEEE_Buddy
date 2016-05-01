package com.example.rkrul.wieeebuddy;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by rkrul on 4/27/2016.
 */
@SuppressWarnings("serial")
public class User implements Serializable {

    private String uwId;
    private String email;
    private String userId;
    private ArrayList<String> eventsAttended;
    private int points;
    private String fullName;

    public User(){

    }

    public User(String userId, String email, String fullName, String uwId, ArrayList<String> eventsAttended, int points){
        this.fullName = fullName;
        this.uwId = uwId;
        this.email = email;
        this.eventsAttended = eventsAttended;
        this.points = points;
        this.userId = userId;
    }

    public String getUserId(){
        return userId;
    }
    public String getFullName(){return fullName;}
    public String getUwId(){
        return uwId;
    }
    public String getEmail(){
        return email;
    }
    public int getPoints(){return points;}
    public ArrayList<String> getEventsAttended(){ return eventsAttended;}
}
