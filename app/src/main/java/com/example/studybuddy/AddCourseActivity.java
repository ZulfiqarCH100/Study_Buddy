package com.example.studybuddy;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
public class AddCourseActivity extends AppCompatActivity {
    private Calendar calendar;
    private Button timeButton, saveButton;
    private Spinner spinner;
    private int s_hour = -1, s_minute = -1, selectedId = -1;
    private String format = "";
    private String courseName, teacherName, section, venue, classDuration;
    private ArrayList<String> classDays;
    EditText courseNameText, teacherNameText, sectionText, venueText;
    RadioGroup rGroup;


    ArrayList<DaysCheckList> listVOs = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView();
    }

    private void createView() {
        setContentView(R.layout.addcourse);
        createDayGetter();
        createTimeGetter();
        courseNameText = (EditText) findViewById(R.id.courseName);
        teacherNameText = (EditText) findViewById(R.id.teacherName);
        sectionText = (EditText) findViewById(R.id.section);
        venueText = (EditText) findViewById(R.id.venueInput);
        saveButton = (Button)findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserInput();
                if(validateData() == true) {
                    Toast t = Toast.makeText(getApplicationContext(), "Saving Course...", Toast.LENGTH_SHORT);
                    t.show();

                    Database db = new SQLite(getApplicationContext());
                    db.SaveCourse(courseName, teacherName, section, venue, s_hour, s_minute, classDuration);
                    for (int i = 0; i < classDays.size(); i++){
                        db.SaveDays(classDays.get(i), courseName);
                    }
                    AddCourseActivity.super.onBackPressed();
                }
                else{
                    Toast t = Toast.makeText(getApplicationContext(), "Incomplete Data!", Toast.LENGTH_SHORT);
                    t.show();
                    Database db = new SQLite(getApplicationContext());
                    db.GetTodaysClasses("Saturday");
                }
            }
        });
    }

    private boolean validateData(){
        boolean flag = true;
        if(isEmpty(courseNameText)){
            flag = false;
            courseNameText.setError("Required");
        }
        if(isEmpty(teacherNameText)){
            flag = false;
            teacherNameText.setError("Required");
        }
        if(isEmpty(sectionText)){
            flag = false;
            sectionText.setError("Required");
        }
        if(isEmpty(venueText)){
            flag = false;
            venueText.setError("Required");
        }
        TextView t = (TextView)findViewById(R.id.dayError);
        if(classDays.size() == 0){
            flag = false;
            t.setError("Required");
        }else{
            t.setError(null);
        }

        if(timeButton.getText().toString().toUpperCase().equals("SET TIME")){
            flag = false;
            timeButton.setError("Required");
        }
        else{
            timeButton.setError(null);
        }
        RadioButton r = (RadioButton)findViewById(R.id.radioButton4);
        if(rGroup.getCheckedRadioButtonId() == -1) {
            flag = false;
            r.setError("Required");
        }
        else{
            r.setError(null);
        }

        return flag;
    }

    private void getUserInput(){
        classDays = new ArrayList<>();
        courseName = courseNameText.getText().toString();
        teacherName = teacherNameText.getText().toString();
        section = sectionText.getText().toString();
        venue = venueText.getText().toString();
        rGroup = (RadioGroup)findViewById(R.id.radioGroup);
        selectedId = rGroup.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton selectedRadioButton = (RadioButton) findViewById(selectedId);
            classDuration = selectedRadioButton.getText().toString();
        }
        for(int i = 0; i < listVOs.size(); i++){
            if(listVOs.get(i).isSelected() == true){
                classDays.add(listVOs.get(i).getTitle());
            }
        }
    }

    private boolean isEmpty(EditText e){
        CharSequence str = e.getText().toString();
        return TextUtils.isEmpty(str);
    }

    private void createDayGetter(){
        final String[] selection = {
                "Select Days", "Monday", "Tuesday", "Wednesday", "Thursday",
                "Friday", "Saturday", "Sunday"};
        spinner = (Spinner) findViewById(R.id.spinner);
        for (int i = 0; i < selection.length; i++) {
            DaysCheckList stateVO = new DaysCheckList();
            stateVO.setTitle(selection[i]);
            stateVO.setSelected(false);
            listVOs.add(stateVO);
        }
        DaysAdapter myAdapter = new DaysAdapter(AddCourseActivity.this, 0, listVOs);
        spinner.setAdapter(myAdapter);
    }

    private void createTimeGetter(){
        timeButton = (Button)findViewById(R.id.startTime);
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int CalendarHour, CalendarMinute;
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
                                s_hour = hourOfDay;
                                s_minute = minute;
                                timeButton.setText(s_hour + ":" + s_minute + format);
                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();
            }
        });
    }
}
