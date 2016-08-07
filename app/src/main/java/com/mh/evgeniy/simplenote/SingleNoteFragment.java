package com.mh.evgeniy.simplenote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by evgeniy on 06.08.2016.
 */
public class SingleNoteFragment extends Fragment{

    public static final int EDIT_NOTE_REQUEST_CODE=0;

    public static final String EXTRA_NOTE_IS_CHANGED ="com.mh.evgeniy.simplenote.is_note_deleted";
    private static final String ARG_NOTE="note";

    private Note mNote;
    private String mNoteText;
    private TextView mYearMonthTextView;
    private TextView mNoteTitleTextView;
    private TextView mNoteTextTextView;
    private Date mDate;
    //private Button mDeleteNoteButton;
    //private Button mEditNoteButton;
    private File externalFilesDir;


    public static SingleNoteFragment newInstance(Note note){
        Bundle args=new Bundle();
        args.putSerializable(ARG_NOTE,note);

        SingleNoteFragment fragment=new SingleNoteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        //mNote=(Note)getActivity().getIntent().getSerializableExtra(SingleNoteActivity.EXTRA_NOTE);
        mNote=(Note)getArguments().getSerializable(ARG_NOTE);
        mDate=mNote.getDate();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_single_note,container,false);

        mYearMonthTextView=(TextView)view.findViewById(R.id.fragment_note_title);
        mNoteTitleTextView=(TextView)view.findViewById(R.id.note_title_text_view);
        mNoteTextTextView=(TextView)view.findViewById(R.id.note_text_text_view);
        /*mDeleteNoteButton=(Button)view.findViewById(R.id.delete_note_button);
        mEditNoteButton=(Button)view.findViewById(R.id.edit_note_button);

        mDeleteNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNote();
                getActivity().finish();
            }
        });
        mEditNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editNote();
            }
        });*/
        externalFilesDir = getContext().getFilesDir();

        updateUI();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_single_note_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.menu_item_edit_note:
                editNote();
                return true;
            case R.id.menu_item_delete_note:
                deleteNote();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI(){
        SimpleDateFormat formatter=new SimpleDateFormat("d MMMM yyyy");
        mYearMonthTextView.setText(getString(R.string.note_fragment_title)+" "+formatter.format(mDate));
        mNoteTitleTextView.setText(mNote.getTitle());

        mNoteText=mNote.getNoteText(externalFilesDir);
        mNoteTextTextView.setText(mNoteText);
    }

    private void editNote(){

        Intent i=NewNoteActivity.newIntent(getActivity(),mNote,mDate); //редактирование ноута
        startActivityForResult(i,EDIT_NOTE_REQUEST_CODE);

    }

    private void deleteNote(){
        NotesManager.get(getActivity()).deleteNote(mNote);
        setResultActivityChanged(true);
        getActivity().finish();
    }

    private void setResultActivityChanged(boolean isChanged){
        Intent i=new Intent();
        i.putExtra(EXTRA_NOTE_IS_CHANGED,isChanged);
        getActivity().setResult(Activity.RESULT_OK,i);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode== Activity.RESULT_OK) {
            if (requestCode == EDIT_NOTE_REQUEST_CODE) {//редактировать ноут
                String noteNewTitle = data.getStringExtra(NewNoteFragment.EXTRA_NOTE_TITLE);
                String noteNewText = data.getStringExtra(NewNoteFragment.EXTRA_NOTE_TEXT);

                if (!noteNewTitle.isEmpty() && !noteNewTitle.equals(" ")) {
                    if (!noteNewTitle.equals(mNote.getTitle())) {
                        mNote.setTitle(noteNewTitle);
                        NotesManager.get(getActivity()).updateNote(mNote);
                        updateUI();
                    }
                    if (!noteNewText.equals(mNoteText)) {
                        mNote.setNoteText(externalFilesDir, noteNewText);
                        updateUI();
                    }

                    setResultActivityChanged(true);
                }else{
                    Toast.makeText(getActivity(),"Please enter note title",Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

}
