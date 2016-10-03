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
    private static final String FILENAME = "file.sav";

    public ArrayList<Habit> habitsList = new ArrayList<Habit>();
    public ArrayAdapter<Habit> adapter;
    public ArrayAdapter<String> stringArrayAdapter;
    private ListView oldHabitsCompletionsList;
    public List<String> completionDates = new ArrayList<String>();

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_habit_completions);

        oldHabitsCompletionsList = (ListView) findViewById(R.id.oldHabitsCompletionsList);
        //http://stackoverflow.com/questions/2468100/android-listview-click-howto

        oldHabitsCompletionsList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                //remove completion date from habit where habit name == compl
                String habitName = completionDates.get(position);
                int newlineIndex = habitName.indexOf("\n");
                int tabindex = habitName.indexOf("\t");
                String habitNameOnly = habitName.substring(0,newlineIndex);
                String dateRepresentation = habitName.substring(tabindex + 2);

                for(Habit habit: habitsList){
                    if(habit.getName().equals(habitNameOnly)){
                        habit.completions.remove(dateRepresentation);
                        completionDates.remove(position);
                        stringArrayAdapter.notifyDataSetChanged();
                        saveInFile();
                        // /adapter.notifyDataSetChanged();
                    }
                }


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
        for(Habit habitIterator: habitsList){
            for(Object o: habitIterator.getCompletions()){
                completionDates.add(habitIterator.getName() + "\non\t " + o);
            }
        }

        stringArrayAdapter = new ArrayAdapter<String>(this,R.layout.list_item, completionDates);
        oldHabitsCompletionsList.setAdapter(stringArrayAdapter);

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


