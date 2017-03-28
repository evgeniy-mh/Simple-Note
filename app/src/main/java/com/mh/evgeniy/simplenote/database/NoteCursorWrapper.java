package com.mh.evgeniy.simplenote.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.mh.evgeniy.simplenote.Note;

import java.util.Date;
import java.util.UUID;

import static com.mh.evgeniy.simplenote.database.NoteDbSchema.*;

/**
 * Created by evgeniy on 31.07.2016.
 */
public class NoteCursorWrapper extends CursorWrapper{

    public NoteCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Note getNote(){
        String uuidString=getString(getColumnIndex(NotesTable.Cols.UUID));
        String title=getString(getColumnIndex(NotesTable.Cols.TITLE));
        long date=getLong(getColumnIndex(NotesTable.Cols.DATE));

        Note note=new Note(UUID.fromString(uuidString),title,new Date(date));
        return note;
    }
}