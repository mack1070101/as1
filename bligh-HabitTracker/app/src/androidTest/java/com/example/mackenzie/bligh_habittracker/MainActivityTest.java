package com.example.mackenzie.bligh_habittracker;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by mackenzie on 03/10/16.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2 {

    public MainActivityTest(){
        super(com.example.mackenzie.bligh_habittracker.Habit.class);
    }

    public void testStart() throws  Exception{
        Activity activity = getActivity();
    }


}
