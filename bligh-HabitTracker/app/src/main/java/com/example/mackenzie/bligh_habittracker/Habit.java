package com.example.mackenzie.bligh_habittracker;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mackenzie on 27/09/16.
 */
public abstract class Habit implements Habitable {
    public String name;
    public Date dateEntered;
    //public ArrayList<String> days; //String containing days of the week the habit exits

    public Habit(String name) {
        //must impliment days it should occur. Maybe date type
        this.name = name;
        this.dateEntered = new Date();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        //Do I need to impliment error check for length?
        this.name = name;
    }

    public Date getDateEntered() {
        return dateEntered;
    }

    public void setDateEntered(Date dateEntered) {
        this.dateEntered = dateEntered;
    }

    @Override
    public String toString(){
        return name;
    }
}

