package com.mh.evgeniy.simplenote;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.Date;

/**
 * Created by evgeniy on 06.08.2016.
 */
public class NewNoteActivity extends SingleFragmentActivity{

    public static final String EXTRA_NEW_NOTE_FRAGMENT_DATE ="com.mh.evgeniy.simplenote.new_date_note_date_title"; //дата для отображения в заголовке фрагмента
    public static final String EXTRA_NEW_NOTE_FRAGMENT_EXISTING_NOTE ="com.mh.evgeniy.simplenote.note_to_edit"; //если не null, то это для редактирование ноута

    @Override
    protected Fragment createFragment() {

        return new NewNoteFragment();
    }

    public static Intent newIntent(Context packageContext,Note note,Date date){
        Intent i =new Intent(packageContext,NewNoteActivity.class);
        i.putExtra(EXTRA_NEW_NOTE_FRAGMENT_DATE,date);

        i.putExtra(EXTRA_NEW_NOTE_FRAGMENT_EXISTING_NOTE,note);

        return i;
    }
}