package com.example.studybuddy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TodayFragment extends Fragment {
    private View view; //To use FindViewById we need to inflate this view, done in onCreateView.
    private ArrayList<Course> today;
    private RecyclerView mRecyclerview;
    private TodayAdapter mAdapter; //Only sends as many objects to the RecyclerView as it can view in one screen.
    private RecyclerView.LayoutManager mLayoutManager;
    Database db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_today, container, false);

        //Make db connection.
        db = new SQLite(view.getContext());
        fetchData();
        buildRecyclerView();
        return view;
    }

    private void fetchData(){
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        today = db.GetTodaysClasses(new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime()));
    }

    public void buildRecyclerView() {
        mRecyclerview = view.findViewById(R.id.todayRecyclerView);
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mAdapter = new TodayAdapter(today);
        mRecyclerview.setLayoutManager(mLayoutManager);
        mRecyclerview.setAdapter(mAdapter);
    }

}
