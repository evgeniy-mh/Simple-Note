package com.mh.evgeniy.simplenote;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by evgeniy on 30.07.2016.
 */
public class Note implements Serializable{


    private UUID mId;
    private String mTitle;
    private Date mDate;

    public Note(){

    }

    public Note(UUID id,String title, Date date){
        mId=id;
        mTitle=title;
        mDate=date;
    }


    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getNoteFilename(){
        return "NOTE_"+getId().toString();
    }

    public void setNoteText(File fileDir,String text){
        if(!text.isEmpty()) {

            File noteTextFile = new File(fileDir, getNoteFilename());

            try {
                FileWriter fw = new FileWriter(noteTextFile);
                fw.write(text);
                fw.flush();

            }catch (IOException ioe){
                //Toast.makeText(getActivity(),"Cant't save note text",Toast.LENGTH_LONG).show();
            }

        }

    }

    public String getNoteText(File fileDir){
        String text="";
        File noteTextFile = new File(fileDir, getNoteFilename());

        try{
            FileReader fr=new FileReader(noteTextFile);
            char[] buffer=new char[(int)noteTextFile.length()];
            fr.read(buffer);
            text=new String(buffer);

        }catch (IOException ioe){
            //Toast.makeText(getActivity(),"Cant't read note text file",Toast.LENGTH_LONG).show();
        }
        return text;
    }

}
