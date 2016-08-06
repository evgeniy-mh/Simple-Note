package com.mh.evgeniy.simplenote.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.mh.evgeniy.simplenote.database.NoteDbSchema.*;

/**
 * Created by evgeniy on 30.07.2016.
 */
public class NoteBaseHelper extends SQLiteOpenHelper{
    private static final String LOG_TAG="NoteBaseHelper";
    private static final int VERSION=1;
    private static final String DATABASE_NAME="noteBase.db";


    public NoteBaseHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d(LOG_TAG,"onCreate(SQLiteDatabase db)");

        /*db.execSQL("create table " + NotesTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                NotesTable.Cols.UUID + " TEXT, " +
                NotesTable.Cols.TITLE + " TEXT, " +
                NotesTable.Cols.DATE +"LONG );"
        );*/

        db.execSQL("create table " + NotesTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                NotesTable.Cols.UUID + ", " +
                NotesTable.Cols.TITLE + ", " +
                NotesTable.Cols.DATE +" INTEGER);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(LOG_TAG,"onUpgrade(...)");
    }
}