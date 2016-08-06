package com.mh.evgeniy.simplenote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Note> mNotes;
    private HashSet<Date> events;
    private NotesManager nm;
    private CalendarView cv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        events = new HashSet<>();
        nm=NotesManager.get(MainActivity.this);

        cv = ((CalendarView)findViewById(R.id.calendar_view));
        updateUI();

        // assign event handler
        cv.setEventHandler(new CalendarView.EventHandler()
        {
            @Override
            public void onDayLongPress(Date date)
            {
                // show returned day
                DateFormat df = SimpleDateFormat.getDateInstance();
                Toast.makeText(MainActivity.this, df.format(date), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDayPress(Date date) {
                // show returned day
                DateFormat df = SimpleDateFormat.getDateInstance();
                Toast.makeText(MainActivity.this, df.format(date),  Toast.LENGTH_SHORT).show();

                Intent i=SingleDateActivity.newIntent(MainActivity.this,date);
                startActivity(i);
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();

        updateUI();
    }

    private void updateUI(){
        Log.d("sdsadasdas","updateUI()");
        mNotes=nm.getNotes();

        if(events.size()!=0) events.clear();

        for(Note n:mNotes){
            events.add(n.getDate());
        }
        cv.updateCalendar(events);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}