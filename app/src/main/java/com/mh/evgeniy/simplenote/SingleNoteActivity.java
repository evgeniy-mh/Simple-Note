package com.mh.evgeniy.simplenote;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
/**
 * Created by evgeniy on 06.08.2016.
 */
public class SingleNoteActivity extends SingleFragmentActivity{

    public static String EXTRA_NOTE="com.mh.evgeniy.simplenote.extra_note";
    public static String EXTRA_NOTE_TEXT="com.mh.evgeniy.simplenote.extra_note_text";

    @Override
    protected Fragment createFragment() {
        return new SingleNoteFragment();
    }

    public static Intent newIntent(Context packageContext, Note note){
        Intent i =new Intent(packageContext,SingleNoteActivity.class);
        i.putExtra(EXTRA_NOTE,note);
        return i;
    }

}