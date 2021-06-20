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

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TimeTableFragment extends Fragment {
    View view;
    ArrayList<TimeTableDay> dayCourses;
    RecyclerView mainRecyclerView;
    TimeTableMainAdapter mainAdapter;
    Database db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_timetable, container, false);
        dayCourses = new ArrayList<>();

        //Set up db
        db = new SQLite(view.getContext());
        fetchDb();
        setMainRecycler();
        return view;
    }

    private void setMainRecycler(){
        mainRecyclerView = view.findViewById(R.id.timeTableMainRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        mainRecyclerView.setLayoutManager(layoutManager);
        mainAdapter = new TimeTableMainAdapter(view.getContext(), dayCourses);
        mainRecyclerView.setAdapter(mainAdapter);
    }

    private void fetchDb(){
        dayCourses.add(new TimeTableDay("Monday", db.GetTodaysClasses("Monday")));
        dayCourses.add(new TimeTableDay("Tuesday", db.GetTodaysClasses("Tuesday")));
        dayCourses.add(new TimeTableDay("Wednesday", db.GetTodaysClasses("Wednesday")));
        dayCourses.add(new TimeTableDay("Thursday", db.GetTodaysClasses("Thursday")));
        dayCourses.add(new TimeTableDay("Friday", db.GetTodaysClasses("Friday")));
    }
}
