package com.mh.evgeniy.simplenote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by evgeniy on 06.08.2016.
 */
public class NewNoteFragment extends Fragment{

    public static final String EXTRA_NOTE_TITLE="com.mh.evgeniy.simplenote.noteTitle";
    public static final String EXTRA_NOTE_TEXT="com.mh.evgeniy.simplenote.noteText";

    private TextView mFragmentNoteTitle;
    private TextView mDayTextView;
    private EditText mNoteTitle;
    private EditText mNoteText;
    private Button mSaveButton;
    private Date mDate;

    private Note mNote; //может быть null


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mDate=(Date)getActivity().getIntent().getSerializableExtra(NewNoteActivity.EXTRA_NEW_NOTE_FRAGMENT_DATE);
        mNote=(Note)getActivity().getIntent().getSerializableExtra(NewNoteActivity.EXTRA_NEW_NOTE_FRAGMENT_EXISTING_NOTE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_new_note,container,false);

        mFragmentNoteTitle=(TextView)view.findViewById(R.id.fragment_note_title);

        SimpleDateFormat format=new SimpleDateFormat("d MMMM");
        String newNoteTitle=format.format(mDate);

        mFragmentNoteTitle.setText(getString(R.string.new_note_fragment_title)+" "+System.getProperty("line.separator")
                +newNoteTitle);

        mDayTextView=(TextView)view.findViewById(R.id.dayTextView);
        format=new SimpleDateFormat("d");
        mDayTextView.setText(format.format(mDate));
        mNoteTitle=(EditText)view.findViewById(R.id.note_title_text_edit);
        mNoteText=(EditText)view.findViewById(R.id.note_text_text_edit);

        if(mNote!=null){
            mNoteTitle.setText(mNote.getTitle());

            File externalFilesDir = getContext().getFilesDir();
            mNoteText.setText(mNote.getNoteText(externalFilesDir));
        }

        mSaveButton=(Button)view.findViewById(R.id.save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });


        return view;
    }

    private void saveNote(){
        String noteTitle=mNoteTitle.getText().toString();
        String noteText=mNoteText.getText().toString();

        Intent i=new Intent();
        i.putExtra(EXTRA_NOTE_TITLE,noteTitle);
        i.putExtra(EXTRA_NOTE_TEXT,noteText);
        getActivity().setResult(Activity.RESULT_OK,i);
        getActivity().finish();
    }

}