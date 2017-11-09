package com.example.tuanpham.pgcasestudy.stories;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tuanpham.pgcasestudy.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class StoriesActivityFragment extends Fragment {

    public StoriesActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }
}
