package com.mh.evgeniy.simplenote.database;

/**
 * Created by evgeniy on 30.07.2016.
 */
public class NoteDbSchema {

    public static final class NotesTable{
        public static final String NAME="notes";

        public static final class Cols{
            public static final String UUID="uuid";
            public static final String DATE="note_date";
            public static final String TITLE="title";
        }



    }

}