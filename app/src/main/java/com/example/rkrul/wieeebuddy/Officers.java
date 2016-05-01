package com.example.rkrul.wieeebuddy;

import java.io.Serializable;

/**
 * Created by JohnB on 5/1/2016.
 */
public class Officers implements Serializable {

    private String Name;
    private String Email;
    private String Position;


    public Officers(){
    }

    public Officers(String Name, String Email, String Position){
        this.Name  = Name;
        this.Email = Email;
        this.Position = Position;
    }

    public String getName(){return Name;}
    public String getEmail(){return Email;}
    public String getPosition(){return Position;}

    public String toString(){
        return "Name: " + Name + "\n" + "Position " + Position + "\n" + "Email: " + Email;
    }
}
