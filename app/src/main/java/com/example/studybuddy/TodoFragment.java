package com.example.studybuddy;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.PrimitiveIterator;

public class TodoFragment extends Fragment {
    private View view; //To use FindViewById we need to inflate this view, done in onCreateView.
    private ArrayList<ToDo> todos;
    private RecyclerView mRecyclerview;
    private ToDoAdapter mAdapter; //Only sends as many objects to the RecyclerView as it can view in one screen.
    private RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_todo, container, false);
        todos = new ArrayList<>();
        makeTodo();
        buildRecyclerView();
        return view;
    }
    
    public void makeTodo(){
        todos.add(new ToDo("Hello", "Kal", "#E57373"));
        todos.add(new ToDo("Hello2", "Kal2", "#EA80FC"));
        todos.add(new ToDo("Hello3", "Kal3", "#43A047"));
        todos.add(new ToDo("Hello4", "Kal4", "#FFFF00"));

    }

    public void buildRecyclerView() {
        mRecyclerview = view.findViewById(R.id.todoRecyclerView);
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mAdapter = new ToDoAdapter(todos);
        mRecyclerview.setLayoutManager(mLayoutManager);
        mRecyclerview.setAdapter(mAdapter);

        //Subscribing to the click listeners by implementing the interface.
        mAdapter.setOnItemClickListener(new ToDoAdapter.onItemClickListener() {
            @Override
            public void subscribeChange(int position, boolean isChecked) {
                ToDo t = todos.get(position);
                if (t.getDone() != isChecked) { //This if is needed to filter unnecessary calls to the function when we scroll.
                    t.setDone(isChecked);
                    //Save the change in db
                    /*
                    if(isChecked)
                        db.saveCity(t.name);
                    else
                        db.deleteCity(t.name);

                     */
                    mAdapter.notifyItemChanged(position);
                }
            }
        });
    }

}
