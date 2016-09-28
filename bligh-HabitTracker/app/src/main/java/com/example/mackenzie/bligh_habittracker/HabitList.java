package com.example.mackenzie.bligh_habittracker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mackenzie on 27/09/16.
 */
public class HabitList {
    List<Habit> habits = new ArrayList<>();

    public void addHabit(Habit habit) throws IllegalArgumentException {
        if (habits.contains(habit)) {
            throw new IllegalArgumentException("You cannot add duplicate habit");
        }
        habits.add(habit);
    }

    public Habit getHabit(int i) {
        return habits.get(i);
    }

    public void removeHabit(int i) {
        habits.remove(i);
    }

    public int getCountHabit(){
        return habits.size();
    }
}
