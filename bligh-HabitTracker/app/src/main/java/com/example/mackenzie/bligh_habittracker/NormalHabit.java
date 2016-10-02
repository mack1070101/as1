package com.example.mackenzie.bligh_habittracker;

import java.util.ArrayList;

/**
 * Created by mackenzie on 28/09/16.
 */
public class NormalHabit extends Habit implements Habitable{
    public NormalHabit(String name, ArrayList days, ArrayList completions){
        super(name, days, completions);
    }

}
