package com.example.studybuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class AddToDoActivity extends AppCompatActivity {
    Calendar calendar;
    int d_hour = -1, d_minute = -1;
    DatePickerDialog datePickerDialog;
    TextView date, time;
    Button saveButton;
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
                                if (hourOfDay == 0) {
                                    hourOfDay += 12;
                                    //  format = "AM";
                                } else if (hourOfDay == 12) {
                                    //format = "PM";
                                } else if (hourOfDay > 12) {
                                    hourOfDay -= 12;
                                    //format = "PM";
                                } else {
                                    //format = "AM";
                                }
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
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(AddToDoActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                date.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        saveButton = (Button)findViewById(R.id.saveButton2);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add validation code + Storing to DB code here
            }
        });
    }



}