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
import androidx.annotation.NonNull;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;


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

    //use this one in manifest ca-app-pub-9569967355870559~5155250766
    //private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712";
    //The upper one is Google ki sample ID. Mine is below
    private static final String AD_UNIT_ID ="ca-app-pub-9569967355870559~6276760747";
    private static final String TAG = "MyActivity";
    private InterstitialAd interstitialAd;

    ArrayList<DaysCheckList> listVOs = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });
        loadAd();
        createView();
    }

    public void loadAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this, AD_UNIT_ID, adRequest, new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        AddCourseActivity.this.interstitialAd = interstitialAd;
                        showInterstitial();
                        interstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        // Called when fullscreen content is dismissed.
                                        AddCourseActivity.this.interstitialAd = null;
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        // Called when fullscreen content failed to show.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        AddCourseActivity.this.interstitialAd = null;
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        // Called when fullscreen content is shown.
                                    }
                                });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        interstitialAd = null;

                        String error =
                                String.format(
                                        "domain: %s, code: %d, message: %s",
                                        loadAdError.getDomain(), loadAdError.getCode(), loadAdError.getMessage());;
                    }
                });
    }

    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (interstitialAd != null) {
            interstitialAd.show(this);
        }
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
                                s_hour = hourOfDay;
                                s_minute = minute;
                                timeButton.setText(s_hour + ":" + s_minute + format);
                            }
                        }, CalendarHour, CalendarMinute, true);
                timepickerdialog.show();
            }
        });
    }
}
