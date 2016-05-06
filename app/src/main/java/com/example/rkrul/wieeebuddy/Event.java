package com.example.rkrul.wieeebuddy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by rkrul on 4/27/2016.
 */
public class Event implements Serializable{
    private String name;
    private String startTime;
    private String endTime;
    private String date;
    private String description;
    private String location;
    private ArrayList<String> attendees;
    private double latitude;
    private double longitude;

    public Event(){

    }

    public Event(String name, String startTime, String endTime, String date, String description, String location, ArrayList<String> attendees, double latitude, double longitude){
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.description = description;
        this.location = location;
        this.attendees = attendees;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName(){
        return name;
    }

    public String getStartTime(){
        return startTime;
    }

    public String getEndTime(){
        return endTime;
    }

    public String getDate(){
        return date;
    }

    public String getDescription(){
        return description;
    }

    public String getLocation() {
        return location;
    }

    public ArrayList<String> getAttendees(){
        return attendees;
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public int numAttendees(){
        return attendees.size() - 1;
    }

    public void addAttendees(String userName) {
        attendees.add(userName);
    }

    public void removeAttendees(String userName) {
        attendees.remove(userName);
    }


    public String toString(){
        return name + "\n" + date + "  " + startTime + " - " + endTime + "\n" + location+"\n";
    }
}
