package com.example.mackenzie.bligh_habittracker;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mackenzie on 27/09/16.
 */
public abstract class Habit implements Habitable {
    public String name;
    public Date dateEntered;
    public ArrayList days; //String containing days of the week the habit exits
    public ArrayList completions;

    public Habit(String name, ArrayList days, ArrayList completions) {
        //must impliment days it should occur. Maybe date type
        this.name = name;
        this.dateEntered = new Date();
        this.days = days;
        this.completions = completions;
    }
    public void addCompletion(){
        completions.add(new Date().toString());

    }

    public void removeCompletion(Date dayCompleted){
        completions.remove(dayCompleted);
    }

    public ArrayList getDays() {
        return days;
    }

    public void setDays(ArrayList days) {
        this.days = days;
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

        return this.name + " on " + this.days.toString() + completions.toString();
    }
}

