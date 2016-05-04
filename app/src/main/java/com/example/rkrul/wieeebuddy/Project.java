package com.example.rkrul.wieeebuddy;

import android.widget.ArrayAdapter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by rkrul on 5/1/2016.
 */
public class Project implements Serializable {
    private String name;
    private int year;
    private int interest;
    private ArrayList<String> userinterest;

    public Project(){}

    public Project(int year, int interest, String name, ArrayList<String> userinterest){
        this.name = name;
        this.year = year;
        this.interest = interest;
        this.userinterest = userinterest;
    }

    public int getYear(){
        return year;
    }

    public int getInterest(){
        return interest;
    }

    public String getName(){
        return name;
    }

    public ArrayList<String> getUserinterest(){
        return userinterest;
    }

    public void addInterest(User user){
        interest++;
        userinterest.add(user.getFullName());
    }

    public void loseInterest(User user){
        interest--;
        userinterest.remove(user.getFullName());
    }


    public String toString(){
        return name+"\nBuild year: "+year+"\nInterest: "+interest+"\n";
    }

}
