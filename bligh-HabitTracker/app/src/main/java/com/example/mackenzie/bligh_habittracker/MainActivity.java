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
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "file.sav";
    private EditText bodyText;
    private ListView oldhabitsList;

    private ArrayList<Habit> habitsList = new ArrayList<Habit>();

    private ArrayAdapter<Habit> adapter;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bodyText = (EditText) findViewById(R.id.body);
        Button saveButton = (Button) findViewById(R.id.save);
        Button deleteHabitsButton = (Button) findViewById(R.id.delete_button);
        oldhabitsList = (ListView) findViewById(R.id.oldHabitsList);
        final CheckBox monday = (CheckBox) findViewById(R.id.monday);
        final CheckBox tuesday = (CheckBox) findViewById(R.id.tuesday);
        final CheckBox wednesday = (CheckBox) findViewById(R.id.wednesday);
        final CheckBox thursday = (CheckBox) findViewById(R.id.thursday);
        final CheckBox friday = (CheckBox) findViewById(R.id.friday);
        final CheckBox saturday = (CheckBox) findViewById(R.id.saturday);
        final CheckBox sunday = (CheckBox) findViewById(R.id.sunday);


        saveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                String text = bodyText.getText().toString();
                ArrayList<String> selectedDays = new ArrayList<String>();

                //http://stackoverflow.com/questions/18336151/how-to-check-if-android-checkbox-is-checked-within-its-onclick-method-declared
                if (monday.isChecked()){
                    selectedDays.add("Mon");
                }
                if (tuesday.isChecked()){
                    selectedDays.add("Tues");
                }
                if (wednesday.isChecked()){
                    selectedDays.add("Wed");
                }
                if (thursday.isChecked()){
                    selectedDays.add("Thurs");
                }
                if (friday.isChecked()){
                    selectedDays.add("Fri");
                }
                if (saturday.isChecked()){
                    selectedDays.add("Sat");
                }
                if (sunday.isChecked()){
                    selectedDays.add("Sun");
                }

                Habit newHabit = new NormalHabit(text, selectedDays);

                habitsList.add(newHabit);
                adapter.notifyDataSetChanged();

                saveInFile();
            }
        });

       deleteHabitsButton.setOnClickListener(new View.OnClickListener() {

           public void onClick(View v) {
               setResult(RESULT_OK);
               String text = bodyText.getText().toString();

               habitsList.clear();

               adapter.notifyDataSetChanged();

              saveInFile();
           }
        });

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();

        adapter = new ArrayAdapter<Habit>(this,R.layout.list_item, habitsList);
        oldhabitsList.setAdapter(adapter);
    }

    private void loadFromFile() {
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
}