package com.example.rkrul.wieeebuddy;

/**
 * Created by rkrul on 5/1/2016.
 */
public class Project {
    private String name;
    private int year;
    private int interest;

    public Project(){}

    public Project(int year, int interest, String name){
        this.name = name;
        this.year = year;
        this.interest = interest;
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

    public void addInterest(){
        interest++;
    }

    public String toString(){
        return name+"\nBuild year: "+year+"\nInterest: "+interest+"\n";
    }

}
