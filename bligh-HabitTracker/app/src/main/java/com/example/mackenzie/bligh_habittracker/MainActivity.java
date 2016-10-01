package com.example.mackenzie.bligh_habittracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String FILENAME = "file.sav";
    private EditText bodyText;
    private ListView oldHabitList;

    private ArrayList<Habit> habitList = new ArrayList<Habit>();

    private ArrayAdapter<Habit> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bodyText = (EditText) findViewById(R.id.body);
        Button saveHabit = (Button) findViewById(R.id.saveHabit);
        final Button habitStats = (Button) findViewById(R.id.view_button);
        Button habitRemoval = (Button) findViewById(R.id.delete_button);
        /*CheckBox sundayBox = (CheckBox) findViewById(R.id.sunday);
        CheckBox mondayBox = (CheckBox) findViewById(R.id.monday);
        CheckBox tuesdayBox = (CheckBox) findViewById(R.id.tuesday);
        CheckBox wednesdayBox = (CheckBox) findViewById(R.id.wednesday);
        CheckBox thursdayBox = (CheckBox) findViewById(R.id.thursday);
        CheckBox fridayBox = (CheckBox) findViewById(R.id.friday);
        CheckBox saturdayBox = (CheckBox) findViewById(R.id.saturday);*/
        oldHabitList = (ListView) findViewById(R.id.oldHabitList);

        saveHabit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                String text = bodyText.getText().toString();

                Habit newHabit = new NormalHabit(text);

                System.out.print(text);

                habitList.add(newHabit);

                adapter.notifyDataSetChanged();

                saveInFile();


            }
        });

        /*Must impliment view and removal buttons. As well as days
        habitStats.setOnClickListener(new View.OnClickListener()){
            when clicked, switch to habit stats activity
        }*/
        habitRemoval.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                setResult(RESULT_OK);
                habitList.clear();
                adapter.notifyDataSetChanged();

                saveInFile();
            }
        });
    }

    protected void onStart() {
        super.onStart();
        loadFromFile();
        adapter = new ArrayAdapter<Habit>(this, R.layout.list_item, habitList);
        oldHabitList.setAdapter(adapter);
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            Type listType = new TypeToken<ArrayList<NormalHabit>>() {}.getType();

            habitList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            habitList = new ArrayList<Habit>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }

    }

    private void saveInFile(){
        try{
            FileOutputStream fos = openFileOutput(FILENAME,0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(habitList, out);
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