package com.example.rkrul.wieeebuddy;

/**
 * Created by rkrul on 5/1/2016.
 */
public class Project {
    private int year;
    private int interest;

    public Project(){}

    public Project(int year, int interest){
        this.year = year;
        this.interest = interest;
    }

    public int getYear(){
        return year;
    }

    public int getInterest(){
        return interest;
    }

    public void addInterest(){
        interest++;
    }

}
