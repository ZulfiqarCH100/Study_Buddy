package com.example.studybuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TimeTableMainAdapter extends RecyclerView.Adapter<TimeTableMainAdapter.ExampleViewHolder> {
    private Context contex;
    ArrayList<TimeTableDay> list;

    public TimeTableMainAdapter(Context contex, ArrayList<TimeTableDay> l) {
        this.contex = contex;
        this.list = l;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.timetable_main_item,parent,false);
        TimeTableMainAdapter.ExampleViewHolder evh = new TimeTableMainAdapter.ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        holder.day.setText(list.get(position).day);
        setSubRecycler(holder.subRecycler, list.get(position).courses);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        public TextView day;
        public RecyclerView subRecycler;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.timeTableDay);
            subRecycler = itemView.findViewById(R.id.timeTableSubRecycler);
        }
    }

    private void setSubRecycler(RecyclerView recyclerView, ArrayList<Course> l){
        TimeTableSubAdapter subAdapter = new TimeTableSubAdapter(contex, l);
        recyclerView.setLayoutManager(new LinearLayoutManager(contex, RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(subAdapter);
    }
}
