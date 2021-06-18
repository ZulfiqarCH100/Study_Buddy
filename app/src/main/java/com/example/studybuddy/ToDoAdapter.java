package com.example.studybuddy;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ExampleViewHolder> {
    private ArrayList<ToDo> list;
    private onItemClickListener mListener;
    private DateFormat dateFormat;

    //This interface would be implemented in main activity to get position of the item clicked.
    //The main activity is subscribed to the listeners via this interface.
    public interface onItemClickListener{
        public void subscribeChange(int position, boolean isChecked);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        mListener = listener;
    }

    public ToDoAdapter(ArrayList<ToDo> l) {
        this.list = l;
        dateFormat = new SimpleDateFormat("HH:mm"); //Fetch Day
    }

    //We cant use a non static class inside a static class so we pass the ExampleViewHolder a onclick listener object.
    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        public TextView text;
        public TextView dueDate;
        public CheckBox mCheckBox;
        public ExampleViewHolder(View itemView, onItemClickListener listener) {
            super(itemView);
            text = itemView.findViewById(R.id.todoTask);
            dueDate = itemView.findViewById(R.id.todoTaskTime);
            mCheckBox = itemView.findViewById(R.id.todoCheckDone);

            //OnClick Listener for the checkBox.
            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.subscribeChange(position, isChecked); //Notify the program checkbox has changed.
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo,parent,false);
        ExampleViewHolder evh = new ExampleViewHolder(v, mListener);
        return evh;
    }

    //Sets the values of one Example_object held by holder.
    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        ToDo current = list.get(position);
        holder.text.setText(current.getText());
        holder.dueDate.setText(current.getDueDay());
        holder.mCheckBox.setChecked(current.getDone());
        RelativeLayout c = (RelativeLayout) holder.itemView.findViewById(R.id.todoCard);
        c.setBackgroundColor(Color.parseColor(current.getColor())); //We can set any background color for the card.
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void changeList(ArrayList<ToDo> l){
        list = l;
    }
}
