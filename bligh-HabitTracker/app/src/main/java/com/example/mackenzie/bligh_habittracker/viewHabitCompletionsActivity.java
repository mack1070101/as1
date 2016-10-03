package com.example.mackenzie.bligh_habittracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
import java.util.List;

public class viewHabitCompletionsActivity extends AppCompatActivity {
    //Declare variables for use in viewHabitCompletionsActivity
    private static final String FILENAME = "file.sav";
    public ArrayList<Habit> habitsList = new ArrayList<Habit>(); //Stores list of existing habits
    public ArrayAdapter<Habit> adapter; //Adapter for habitsList
    private ListView oldHabitsCompletionsList;
    public List<String> completionDates = new ArrayList<String>();
    public ArrayAdapter<String> stringArrayAdapter;//adapter for completionDates

    /* Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_habit_completions);

        oldHabitsCompletionsList = (ListView) findViewById(R.id.oldHabitsCompletionsList);
        //Called when a list item is clicked
        oldHabitsCompletionsList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                //When list item is clicked, aquire information to to complete habit
                String habitName = completionDates.get(position);
                int newlineIndex = habitName.indexOf("\n");
                int tabIndex = habitName.indexOf("\t");
                String habitNameOnly = habitName.substring(0,newlineIndex); //Name of habit as a string
                String dateRepresentation = habitName.substring(tabIndex + 2); //Date habit was completed as a string

                for(Habit habit: habitsList){
                    /*For every habit in the habit list, find where the name of the habit matches it's completion
                      then remove the date of completion that the user touched*/
                    if(habit.getName().equals(habitNameOnly)){
                        habit.completions.remove(dateRepresentation);
                        completionDates.remove(position);
                        stringArrayAdapter.notifyDataSetChanged();
                        saveInFile();
                    }
                }
                //Perform end of function operations; save changes and update the visible info
                saveInFile();
                stringArrayAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();
        /*When the activity starts, generate a list of completion dates and their corresponding
         habits*/
        for(Habit habitIterator: habitsList){
            for(Object o: habitIterator.getCompletions()){
                completionDates.add(habitIterator.getName() + "\non\t " + o);
            }
        }
        //Display list of completions
        stringArrayAdapter = new ArrayAdapter<String>(this,R.layout.list_item, completionDates);
        oldHabitsCompletionsList.setAdapter(stringArrayAdapter);

    }

    private void loadFromFile() {
        // On start load habits from file if they exist, and setup adapter
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
        //Load habits from file
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


