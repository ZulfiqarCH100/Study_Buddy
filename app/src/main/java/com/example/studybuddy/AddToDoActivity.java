package com.example.studybuddy;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class AddToDoActivity extends AppCompatActivity {
    Calendar calendar;
    int d_hour = -1, d_minute = -1, mYear, mMonth, mDay;
    DatePickerDialog datePickerDialog;
    TextView date, time;
    Button saveButton;
    EditText taskName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do);
        time = (TextView) findViewById(R.id.dueTime);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int CalendarHour, CalendarMinute;
                calendar = Calendar.getInstance();
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);
                TimePickerDialog timepickerdialog = new TimePickerDialog(AddToDoActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                d_hour = hourOfDay;
                                d_minute = minute;
                                time.setText(d_hour + ":" + d_minute);
                            }
                        }, CalendarHour, CalendarMinute, true);
                timepickerdialog.show();
            }
        });

        date = findViewById(R.id.dueDate);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR); // current year
                mMonth = c.get(Calendar.MONTH); // current month
                mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(AddToDoActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                date.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);
                                mYear = year;
                                mDay = dayOfMonth;
                                mMonth = monthOfYear + 1;

                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.show();
            }
        });
        saveButton = (Button)findViewById(R.id.saveButton2);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Toast t;
                if(validateData() == true){
                    t  = Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT);
                    SQLite db = new SQLite(getApplicationContext());
                    db.SaveToDo(taskName.getText().toString(), d_hour, d_minute, mDay, mMonth, mYear);
                    Intent i = new Intent();
                    setResult(RESULT_OK, i);
                    finish();
                }
                else{
                    t = Toast.makeText(getApplicationContext(), "Incomplete Data", Toast.LENGTH_SHORT);
                }
                t.show();
            }
        });
    }

    private boolean validateData(){
        boolean flag = true;
        taskName = (EditText) findViewById(R.id.toDoTask);
        time = (TextView) findViewById(R.id.dueTime);
        date = (TextView) findViewById(R.id.dueDate);
        if(isEmpty(taskName)){
            flag = false;
            taskName.setError("Required");
        }
        if(isEmpty(time)){
            flag = false;
            time.setError("Required");
        } else {
            time.setError(null);
        }
        if(isEmpty(date)){
            flag = false;
            date.setError("Required");
        }else{
            date.setError(null);
        }

        return flag;
    }

    private boolean isEmpty(EditText e ){
        CharSequence str = e.getText().toString();
        return TextUtils.isEmpty(str);
    }

    private boolean isEmpty(TextView e ){
        CharSequence str = e.getText().toString();
        return TextUtils.isEmpty(str);
    }
}