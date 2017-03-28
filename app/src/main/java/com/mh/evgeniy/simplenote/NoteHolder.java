package com.mh.evgeniy.simplenote;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by evgeniy on 06.08.2016.
 */
public class NoteHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private Note mNote;
    private TextView mNoteTitle;
    private Activity mActivity;

    public NoteHolder(View itemView, Activity activity) {
        super(itemView);
        itemView.setOnClickListener(this);
        mNoteTitle=(TextView)itemView.findViewById(R.id.list_item_note_title);

        mActivity=activity;
    }

    public void bindNote(Note note){
        mNote=note;
        mNoteTitle.setText(mNote.getTitle());
    }


    @Override
    public void onClick(View v) { //считывать текст из файла тут
            /*Intent i = SingleNoteActivity.newIntent(getActivity(), mNote);
            startActivityForResult(i,SINGLE_NOTE_REQUEST_CODE);*/
        if(mActivity!=null) {
            Intent i = NotePagerActivity.newIntent(mActivity, mNote.getId(), mNote.getDate());
            mActivity.startActivity(i);
        }

    }
}