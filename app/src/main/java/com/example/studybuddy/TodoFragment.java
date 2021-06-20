package com.example.studybuddy;

import android.content.Intent;
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
    private Button toDoButton;
    Database db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_todo, container, false);
        db = new SQLite(view.getContext());
        fetchData();
        buildRecyclerView();
        toDoButton = view.findViewById(R.id.todoAddMore);
        toDoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), AddToDoActivity.class);
                //We start the activity for result so that we can update this fragment from the main when it returns.
                getActivity().startActivityForResult(intent, 1);
            }
        });
        return view;
    }


    private void fetchData(){
        todos = new ArrayList<>();
        todos = db.getAllToDo();
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
                db.removeToDo(t.getID());
                mAdapter.notifyItemRemoved(position);
                todos.remove(position);
            }
        });
    }

}
