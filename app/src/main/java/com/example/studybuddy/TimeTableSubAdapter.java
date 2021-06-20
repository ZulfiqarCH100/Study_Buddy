package com.example.studybuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TimeTableSubAdapter extends RecyclerView.Adapter<TimeTableSubAdapter.ExampleViewHolder> {
    private Context contex;
    ArrayList<Course> list;

    public TimeTableSubAdapter(Context contex, ArrayList<Course> l) {
        this.contex = contex;
        this.list = l;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.timetable_sub_item,parent,false);
        TimeTableSubAdapter.ExampleViewHolder evh = new TimeTableSubAdapter.ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        holder.courseName.setText(list.get(position).courseName);
        holder.courseTime.setText(list.get(position).getTime());
        holder.courseRoom.setText(list.get(position).venue);
        holder.duration.setText(list.get(position).duration);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        public TextView courseName;
        public TextView courseTime;
        public TextView courseRoom;
        public TextView duration;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            courseName = itemView.findViewById(R.id.timeTableCourse);
            courseTime = itemView.findViewById(R.id.timeTableTime);
            courseRoom = itemView.findViewById(R.id.timeTableRoom);
            duration = itemView.findViewById(R.id.timeTableDuration);

        }
    }
}
