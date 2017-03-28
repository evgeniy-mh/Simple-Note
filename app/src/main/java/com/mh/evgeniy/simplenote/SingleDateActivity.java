package com.mh.evgeniy.simplenote;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.Date;
/**
 * Created by evgeniy on 06.08.2016.
 */
public class SingleDateActivity extends SingleFragmentActivity{
    public static final String EXTRA_DATE_ID="com.mh.evgeniy.simplenote.date";


    @Override
    protected Fragment createFragment() {
        return new SingleDateFragment();
    }

    public static Intent newIntent(Context packageContext,Date date){
        Intent i=new Intent(packageContext,SingleDateActivity.class);
        i.putExtra(EXTRA_DATE_ID,date);
        return i;
    }
}