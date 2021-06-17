package com.example.studybuddy;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
public class AddCourseActivity extends AppCompatActivity {
    private Calendar calendar;
    private Button button;
    private int CalendarHour, CalendarMinute;

    private String format = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView();
    }

    private void createView() {
        setContentView(R.layout.addcourse);
        createDayGetter();
        createTimeGetter();
    }

    private void createDayGetter(){
        final String[] select_qualification = {
                "Select Days", "Monday", "Tuesday", "Wednesday", "Thursday",
                "Friday", "Saturday", "Sunday"};
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        ArrayList<DaysCheckList> listVOs = new ArrayList<>();

        for (int i = 0; i < select_qualification.length; i++) {
            DaysCheckList stateVO = new DaysCheckList();
            stateVO.setTitle(select_qualification[i]);
            stateVO.setSelected(false);
            listVOs.add(stateVO);
        }
        DaysAdapter myAdapter = new DaysAdapter(AddCourseActivity.this, 0, listVOs);
        spinner.setAdapter(myAdapter);
    }

    private void createTimeGetter(){
        button = (Button)findViewById(R.id.startTime);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);
                TimePickerDialog timepickerdialog = new TimePickerDialog(AddCourseActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                if (hourOfDay == 0) {
                                    hourOfDay += 12;
                                    format = "AM";
                                } else if (hourOfDay == 12) {
                                    format = "PM";
                                } else if (hourOfDay > 12) {
                                    hourOfDay -= 12;
                                    format = "PM";
                                } else {
                                    format = "AM";
                                }
                                button.setText(hourOfDay + ":" + minute + format);
                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();

            }
        });
    }
}
