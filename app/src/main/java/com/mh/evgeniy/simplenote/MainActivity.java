package com.mh.evgeniy.simplenote;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Note> mNotes;
    private HashSet<Date> events;
    private NotesManager nm;
    private CalendarView cv;
    private RecyclerView mLastNotesRecyclerView;
    private NoteAdapter mNoteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        events = new HashSet<>();
        nm=NotesManager.get(MainActivity.this);

        cv = ((CalendarView)findViewById(R.id.calendar_view));

        mLastNotesRecyclerView=(RecyclerView)findViewById(R.id.last_notes_recycler_view);
        mLastNotesRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

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
        mNotes=nm.getNotes();

        if(events.size()!=0) events.clear();

        for(Note n:mNotes){
            events.add(n.getDate());
        }
        cv.updateCalendar(events);


        List<Note> reversedNotes=new ArrayList<Note>(mNotes);
        Collections.reverse(reversedNotes);
        if(mNoteAdapter==null){
            mNoteAdapter=new NoteAdapter(reversedNotes,MainActivity.this);
            mLastNotesRecyclerView.setAdapter(mNoteAdapter);
        }else {
            mNoteAdapter.setNotes(reversedNotes);
            mNoteAdapter.notifyDataSetChanged();
        }
        mLastNotesRecyclerView.scrollToPosition(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        nm.deleteAllNotes();
                        updateUI();
                        break;

                }
            }
        };

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about)
        {
            Intent i=new Intent(MainActivity.this,AboutActivity.class);
            startActivity(i);
            return true;
        }

        if(id == R.id.action_delete_all_notes){

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage(getString(R.string.delete_all_notes_question)).setPositiveButton(getString(R.string.delete_all_notes_question_y), dialogClickListener)
                    .setNegativeButton(getString(R.string.delete_all_notes_question_n),dialogClickListener).show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
