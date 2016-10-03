package com.example.mackenzie.bligh_habittracker;

import org.junit.Test;

import java.util.ArrayList;


import static org.junit.Assert.*;

/**
 * Created by mackenzie on 03/10/16.
 */
public class HabitTest{
    @Test
    public void createHabitTest() throws Exception{
        //Test for habit creation
        ArrayList<String> completions = new ArrayList<>();
        assertTrue(new NormalHabit("Eat", new ArrayList<String>(),completions) instanceof Habit);
    }

    @Test
    public void getHabitNameTest() throws Exception{
        ArrayList<String> completions = new ArrayList<>();
        Habit habit = new NormalHabit("Eat", new ArrayList<String>(),completions);
        assertTrue(habit.getName().equals("Eat"));
    }

    @Test
    public void habitDaysTest() throws Exception{
        ArrayList<String> days = new ArrayList<String>();
        days.add("Thurs");
        days.add("Fri");

        Habit habit = new NormalHabit("Eat",days, new ArrayList<String>());

        assertTrue(habit.getDays().contains("Thurs"));
        assertTrue(habit.getDays().contains("Fri"));
    }

    @Test
    public void habitAddCompletionsTest() throws Exception{
        ArrayList<String> completions = new ArrayList<>();
        Habit habit=  new NormalHabit("Eat",new ArrayList<String>(), completions);
        habit.addCompletion();
        assertTrue(habit.getCompletions().size() == 1);
    }

    @Test
    public void habitCompletionsSizeTest() throws Exception{
        ArrayList<String> completions = new ArrayList<>();
        Habit habit=  new NormalHabit("Eat",new ArrayList<String>(), completions);
        habit.addCompletion();
        assertTrue(habit.completionsSize() == 1);
    }

    @Test
    public void habitGetCompletionsTest() throws Exception{
        ArrayList<String> completions = new ArrayList<>();
        Habit habit=  new NormalHabit("Eat",new ArrayList<String>(), completions);
        habit.addCompletion();
        assertTrue(habit.getCompletions().equals(completions));
    }
}
