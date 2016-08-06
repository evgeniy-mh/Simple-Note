package com.mh.evgeniy.simplenote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mh.evgeniy.simplenote.database.NoteBaseHelper;
import com.mh.evgeniy.simplenote.database.NoteCursorWrapper;
import com.mh.evgeniy.simplenote.database.NoteDbSchema;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.mh.evgeniy.simplenote.database.NoteDbSchema.*;


/**
 * Created by evgeniy on 06.08.2016.
 */
public class NotesManager {

    private static NotesManager mNotesManager;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private File mNotesFilesDir;

    private NotesManager(Context context){
        mContext=context.getApplicationContext();
        mDatabase=new NoteBaseHelper(mContext).getWritableDatabase();
        mNotesFilesDir = mContext.getFilesDir();
    }

    public static NotesManager get(Context context){
        if(mNotesManager==null) mNotesManager=new NotesManager(context);

        return mNotesManager;
    }

    public List<Note> getNotes(){
        List<Note> notes=new ArrayList<>();
        NoteCursorWrapper cursorWrapper=queryNotes(null,null);

        try{
            cursorWrapper.moveToFirst();
            while(!cursorWrapper.isAfterLast()){
                notes.add(cursorWrapper.getNote());
                cursorWrapper.moveToNext();
            }
        }finally {
            cursorWrapper.close();
        }
        return notes;
    }

    public List<Note> getNotes(Date date){ //найти все заметки для определенного дня
        List<Note> notes=new ArrayList<>();
        List<Note> allNotes=getNotes();

        for(Note n : allNotes){

            if(n.getDate().getDate()==date.getDate() &&
                    n.getDate().getMonth()==date.getMonth() &&
                    n.getDate().getYear()==date.getYear()){

                notes.add(n);

                /*Log.d("asaas","added to list note "+n.getTitle());
                Log.d("asaas","added to list note "+n.getDate().getDay());
                Log.d("asaas","added to list note "+n.getDate().getDate());*/
            }

        }

        return notes;
    }

    public void addNote(Note note){
        ContentValues values=getContentValues(note);
        mDatabase.insert(NotesTable.NAME,null,values);
    }

    public void deleteNote(Note note){
        mDatabase.delete(NotesTable.NAME,NotesTable.Cols.UUID+" = ?",
                new String[] {note.getId().toString()});

        File noteTextFile=new File(mNotesFilesDir,note.getNoteFilename());
        boolean deleted=noteTextFile.delete();

        //Log.d("sdasda",deleted? "file "+note.getTitle()+" deleted" : "cant delete file of "+note.getTitle());

    }

    public void updateNote(Note note){
        String uuidString=note.getId().toString();
        ContentValues values=getContentValues(note);

        mDatabase.update(NotesTable.NAME,values,NotesTable.Cols.UUID+" = ?",
                new String[] {uuidString});
    }

    private NoteCursorWrapper queryNotes(String whereClause,String[] whereArgs){
        Cursor cursor=mDatabase.query(
                NotesTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );
        return new NoteCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Note note){
        ContentValues values=new ContentValues();
        values.put(NotesTable.Cols.UUID,note.getId().toString());
        values.put(NotesTable.Cols.TITLE,note.getTitle());
        values.put(NotesTable.Cols.DATE,note.getDate().getTime());

        /*Log.d("DBBB","UUID="+note.getId().toString());
        Log.d("DBBB","TITLE="+note.getTitle());
        Log.d("DBBB","DATE="+note.getDate().getTime());*/

        return values;
    }
}
