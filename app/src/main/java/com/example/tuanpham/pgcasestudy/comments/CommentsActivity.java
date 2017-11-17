package com.example.tuanpham.pgcasestudy.comments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.tuanpham.pgcasestudy.R;
import com.example.tuanpham.pgcasestudy.stories.StoriesFragment;

import java.util.ArrayList;

/**
 * Created by tuanpham on 11/14/17.
 */

public class CommentsActivity extends AppCompatActivity {

    public static final java.lang.String EXTRA_COMMENT_LIST = "COMMENT_LIST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);
        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);

        if (null == savedInstanceState) {
            ArrayList<Integer> commentIdList = getIntent().getExtras().getIntegerArrayList(EXTRA_COMMENT_LIST);
            initFragment(CommentsFragment.newInstance(commentIdList));
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void initFragment(Fragment notesFragment) {
        // Add the NotesFragment to the layout
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.contentFrame, notesFragment);
        transaction.commit();
    }
}
