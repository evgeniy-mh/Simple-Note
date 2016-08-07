package com.mh.evgeniy.simplenote;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by evgeniy on 06.08.2016.
 */
public class NotePagerActivity extends AppCompatActivity {

    //private static String EXTRA_NOTE="com.mh.evgeniy.simplenote.extra_note";
    //private static String EXTRA_DATE="com.mh.evgeniy.simplenote.extra_date";
    private static String EXTRA_NOTE_ID="com.mh.evgeniy.simplenote.extra_note_id";
    private static String EXTRA_NOTES_DATE="com.mh.evgeniy.simplenote.extra_notes_date";

    private ViewPager mViewPager;
    private List<Note> mNotes;

    public static Intent newIntent(Context packageContext, UUID noteId, Date notesDate){
        Intent i=new Intent(packageContext,NotePagerActivity.class);
        //i.putExtra(EXTRA_DATE,date);
        i.putExtra(EXTRA_NOTE_ID,noteId);
        i.putExtra(EXTRA_NOTES_DATE,notesDate);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_pager);

        //Note note=(Note)getIntent().getSerializableExtra(EXTRA_NOTE);
        Date date=(Date)getIntent().getSerializableExtra(EXTRA_NOTES_DATE);
        UUID noteId=(UUID)getIntent().getSerializableExtra(EXTRA_NOTE_ID);

        mViewPager=(ViewPager)findViewById(R.id.activity_note_pager_view_pager);
        mNotes=NotesManager.get(this).getNotes(date); //!


        FragmentManager fragmentManager=getSupportFragmentManager();

        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Note note=mNotes.get(position);
                return SingleNoteFragment.newInstance(note);
            }

            @Override
            public int getCount() {
                return mNotes.size();
            }
        });

        for(int i=0;i<mNotes.size();i++){
            if(mNotes.get(i).getId().equals(noteId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }


}