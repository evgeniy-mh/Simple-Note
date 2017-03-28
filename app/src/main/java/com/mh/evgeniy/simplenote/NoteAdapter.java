package com.mh.evgeniy.simplenote;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by evgeniy on 06.08.2016.
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteHolder>{

    private List<Note> mNotes;
    private Activity mActivity;

    public NoteAdapter(List<Note> notes, Activity activity){
        mNotes=notes;
        mActivity=activity;
    }

    public void setNotes(List<Note> notes){
        mNotes=notes;
    }

    @Override
    public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(mActivity);
        View view=layoutInflater.inflate(R.layout.list_item_note,parent,false);

        return new NoteHolder(view,mActivity);
    }

    @Override
    public void onBindViewHolder(NoteHolder holder, int position) {
        Note note=mNotes.get(position);
        holder.bindNote(note);
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }
}