package com.example.mackenzie.bligh_habittracker;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.reflect.TypeToken;

public class MainActivity extends AppCompatActivity {
    //Declare variables used by main activity
    private static final String FILENAME = "file.sav";
    private EditText bodyText; // Where input from GUI is stored
    private ListView oldhabitsList;

    public ArrayList<Habit> habitsList = new ArrayList<Habit>(); //Stores all created habits when not in file

    private ArrayAdapter<Habit> adapter;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Declaring references to buttons in the GUI
        bodyText = (EditText) findViewById(R.id.body);
        Button saveButton = (Button) findViewById(R.id.save);
        final Button deleteHabitsButton = (Button) findViewById(R.id.delete_button);
        oldhabitsList = (ListView) findViewById(R.id.oldHabitsList);
        final CheckBox monday = (CheckBox) findViewById(R.id.monday);
        final CheckBox tuesday = (CheckBox) findViewById(R.id.tuesday);
        final CheckBox wednesday = (CheckBox) findViewById(R.id.wednesday);
        final CheckBox thursday = (CheckBox) findViewById(R.id.thursday);
        final CheckBox friday = (CheckBox) findViewById(R.id.friday);
        final CheckBox saturday = (CheckBox) findViewById(R.id.saturday);
        final CheckBox sunday = (CheckBox) findViewById(R.id.sunday);
        final Button viewCompletionsButton = (Button) findViewById(R.id.view_button);

        //Called when saveButton is clicked
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                String text = bodyText.getText().toString();
                ArrayList<String> selectedDays = new ArrayList<String>(); //holds string containing days of week that habit should occur on
                ArrayList<String> completions = new ArrayList<String>(); //holds dates the habit is completed on as strings
                int checked = 0; //Used to ensure that user picks atleast 1 day the habit occurs
                if(text.equals("") || text.equals(" ") || text.equals("\n") || text.equals("\t")){
                    //Do not accept bad inputs in the text field
                    return;
                }
                //http://stackoverflow.com/questions/18336151/how-to-check-if-android-checkbox-is-checked-within-its-onclick-method-declared
                //Adding what days the habit should be completed on
                if (monday.isChecked()){
                    selectedDays.add("Mon");
                    checked++;
                }
                if (tuesday.isChecked()){
                    selectedDays.add("Tues");
                    checked++;
                }
                if (wednesday.isChecked()){
                    selectedDays.add("Wed");
                    checked++;
                }
                if (thursday.isChecked()){
                    selectedDays.add("Thurs");
                    checked++;
                }
                if (friday.isChecked()){
                    selectedDays.add("Fri");
                    checked++;
                }
                if (saturday.isChecked()){
                    selectedDays.add("Sat");
                    checked++;
                }
                if (sunday.isChecked()){
                    selectedDays.add("Sun");
                    checked++;
                }
                if( checked == 0){
                    return;
                }
                //Create a new habit, add it to the habitsList and save it to file
                Habit newHabit = new NormalHabit(text, selectedDays, completions);
                habitsList.add(newHabit);
                adapter.notifyDataSetChanged();
                saveInFile();
            }
        });

        //Called when deleteHabitsButton is clicked; user wants to delete habits
       deleteHabitsButton.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
               setResult(RESULT_OK);
               deleteHabits();
           }
        });

        //Called when viewCompleteionsButton is clicked; user wants to view and delete habits
        viewCompletionsButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                editCompletions();
            }
        });

        //http://stackoverflow.com/questions/2468100/android-listview-click-howto
        //When an item in the GUI list is clicked, add a completion for today
        oldhabitsList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Habit habit = habitsList.get(position);
                habit.addCompletion();
                saveInFile();
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        // On start load habits from file if they exist, and setup adapter
        super.onStart();
        loadFromFile();

        adapter = new ArrayAdapter<Habit>(this,R.layout.list_item, habitsList);
        oldhabitsList.setAdapter(adapter);
    }

    private void loadFromFile() {
        //Load habits from file
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            // Code from http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            Type listType = new TypeToken<ArrayList<NormalHabit>>(){}.getType();

            habitsList = gson.fromJson(in,listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            habitsList = new ArrayList<Habit>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }

    private void saveInFile() {
        //Save habits in file
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    0);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(habitsList, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }

    public void editCompletions(){
        //called by viewCompletionsButton, switches to activity dealing with habit completions
        Intent intent = new Intent(MainActivity.this, viewHabitCompletionsActivity.class);
        startActivity(intent);
    }

    public void deleteHabits(){
        //Called by deleteHabitsButton, switches to activity dealing with habit deletions
        Intent intent = new Intent(MainActivity.this, DeleteHabitsActivity.class);
        startActivity(intent);
    }
}