package com.mh.evgeniy.simplenote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by evgeniy on 06.08.2016.
 */
public class SingleDateFragment extends Fragment{

    public static final int NEW_NOTE_REQUEST_CODE=0;
    public static final int SINGLE_NOTE_REQUEST_CODE=1;

    private Date mDate;
    private TextView mDayTextView;
    private TextView mYearMonthTextView;
    private RecyclerView mNotesRecyclerView;
    private NoteAdapter mNoteAdapter;
    private List<Note> mTodayNotes;
    private Button mAddNoteButton;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mDate=(Date)getActivity().getIntent().getSerializableExtra(SingleDateActivity.EXTRA_DATE_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_single_date,container,false);

        SimpleDateFormat formatter=new SimpleDateFormat("d");
        mDayTextView =(TextView)view.findViewById(R.id.dayTextView);
        mDayTextView.setText(formatter.format(mDate));
        formatter=new SimpleDateFormat("MMMM yyyy");
        mYearMonthTextView=(TextView)view.findViewById(R.id.monthYearTextView);
        mYearMonthTextView.setText(formatter.format(mDate));

        mNotesRecyclerView=(RecyclerView)view.findViewById(R.id.notes_recycler_view);
        mNotesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        mAddNoteButton=(Button)view.findViewById(R.id.add_note_button);
        mAddNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//запускать другой интент

                Intent i=NewNoteActivity.newIntent(getActivity(),null,mDate); //только создание нового ноута
                startActivityForResult(i,NEW_NOTE_REQUEST_CODE);
            }
        });

        return view;
    }


    private class NoteHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Note mNote;
        private TextView mNoteTitle;

        public NoteHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mNoteTitle=(TextView)itemView.findViewById(R.id.list_item_note_title);
        }

        public void bindNote(Note note){
            mNote=note;
            mNoteTitle.setText(mNote.getTitle());
        }

        @Override
        public void onClick(View v) { //считывать текст из файла тут
            /*Intent i = SingleNoteActivity.newIntent(getActivity(), mNote);
            startActivityForResult(i,SINGLE_NOTE_REQUEST_CODE);*/
            Intent i=NotePagerActivity.newIntent(getActivity(),mNote.getId(),mNote.getDate());
            startActivity(i);

        }
    }

    private class NoteAdapter extends RecyclerView.Adapter<NoteHolder>{

        private List<Note> mNotes;

        public NoteAdapter(List<Note> notes){
            mNotes=notes;
        }

        public void setNotes(List<Note> notes){
            mNotes=notes;
        }

        @Override
        public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater=LayoutInflater.from(getActivity());
            View view=layoutInflater.inflate(R.layout.list_item_note,parent,false);

            return new NoteHolder(view);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode== Activity.RESULT_OK) {

            if (requestCode == NEW_NOTE_REQUEST_CODE) {//добавить новый ноут

                String noteTitle=data.getStringExtra(NewNoteFragment.EXTRA_NOTE_TITLE);
                String noteText=data.getStringExtra(NewNoteFragment.EXTRA_NOTE_TEXT);

                if(!noteTitle.isEmpty() && !noteTitle.equals(" ")) {
                    Note note = new Note(UUID.randomUUID(), noteTitle, mDate);
                    NotesManager.get(getActivity()).addNote(note);

                    File externalFilesDir = getContext().getFilesDir();
                    note.setNoteText(externalFilesDir,noteText);
                    updateUI();
                }
                else{
                    Toast.makeText(getActivity(),"Please enter note title", Toast.LENGTH_SHORT).show();
                }



            }

            /*if(requestCode==SINGLE_NOTE_REQUEST_CODE){
                boolean isNoteDeleted=data.getBooleanExtra(SingleNoteFragment.EXTRA_NOTE_IS_CHANGED,false);
                if(isNoteDeleted) updateUI();
            }*/
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    private void updateUI(){
        mTodayNotes=NotesManager.get(getActivity()).getNotes(mDate);
        if(mNoteAdapter==null){
            mNoteAdapter=new NoteAdapter(mTodayNotes);
            mNotesRecyclerView.setAdapter(mNoteAdapter);
        }else
        {
            mNoteAdapter.setNotes(mTodayNotes);
            mNoteAdapter.notifyDataSetChanged();
        }
    }
}