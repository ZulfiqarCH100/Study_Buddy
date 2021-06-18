package com.example.studybuddy;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TodayAdapter extends RecyclerView.Adapter<TodayAdapter.ExampleViewHolder> {
    private ArrayList<Course> list;
    private TodayAdapter.onItemClickListener mListener;
    private DateFormat dateFormat;
    int cardColor = 0;

    //This interface would be implemented in main activity to get position of the item clicked.
    //The main activity is subscribed to the listeners via this interface.
    public interface onItemClickListener{
        public void subscribeChange(int position, boolean isChecked);
    }

    public void setOnItemClickListener(TodayAdapter.onItemClickListener listener){
        mListener = listener;
    }

    public TodayAdapter(ArrayList<Course> l) {
        this.list = l;
        dateFormat = new SimpleDateFormat("HH:mm"); //Fetch Day
    }

    //We cant use a non static class inside a static class so we pass the ExampleViewHolder a onclick listener object.
    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        public TextView courseName;
        public TextView teacher;
        public TextView section;
        public TextView venue;
        public TextView time;
        public TextView duration;

        public ExampleViewHolder(View itemView, TodayAdapter.onItemClickListener listener) {
            super(itemView);
            courseName = itemView.findViewById(R.id.todayCourse);
            teacher = itemView.findViewById(R.id.todayTeacher);
            section = itemView.findViewById(R.id.todaySection);
            venue = itemView.findViewById(R.id.todayRoom);
            time = itemView.findViewById(R.id.todayTime);
            duration = itemView.findViewById(R.id.todayDuration);
        }
    }

    @NonNull
    @Override
    public TodayAdapter.ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.today,parent,false);
        TodayAdapter.ExampleViewHolder evh = new TodayAdapter.ExampleViewHolder(v, mListener);
        return evh;
    }

    //Sets the values of one Example_object held by holder.
    @Override
    public void onBindViewHolder(@NonNull TodayAdapter.ExampleViewHolder holder, int position) {
        Course current = list.get(position);
        holder.courseName.setText(current.courseName);
        holder.teacher.setText(current.teacherName);
        holder.section.setText(current.section);
        holder.venue.setText(current.venue);
        holder.time.setText(current.getTime());
        holder.duration.setText(current.duration);

        RelativeLayout c = (RelativeLayout) holder.itemView.findViewById(R.id.todayCard);
        if (cardColor == 0) {
            Drawable myDrawable = holder.itemView.getResources().getDrawable(R.drawable.bg4);
            c.setBackground(myDrawable);
            cardColor++;
        }
        else if (cardColor == 1) {
            Drawable myDrawable = holder.itemView.getResources().getDrawable(R.drawable.bg1);
            c.setBackground(myDrawable);
            cardColor++;
        }
        else if (cardColor == 2) {
            Drawable myDrawable = holder.itemView.getResources().getDrawable(R.drawable.bg2);
            c.setBackground(myDrawable);
            cardColor ++;
        }
        else if (cardColor == 3) {
            Drawable myDrawable = holder.itemView.getResources().getDrawable(R.drawable.bg3);
            c.setBackground(myDrawable);
            cardColor = 0;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void changeList(ArrayList<Course> l){
        list = l;
    }
}
