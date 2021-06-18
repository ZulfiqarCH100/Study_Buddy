package com.example.studybuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;

import java.time.temporal.ChronoField;

public class PomodoroActivity extends AppCompatActivity {
    Button btnstart;
    Button btnstop;
    ImageView icanchor;
    Animation roundingalone;
    Chronometer timerHere;
    Button button1;
    int var=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro);
        button1=findViewById(R.id.button);

        icanchor=findViewById(R.id.icanchor);
        timerHere=findViewById(R.id.timerHere);
        roundingalone= AnimationUtils.loadAnimation(this,R.anim.roundingalone);

        //Typeface MMedium=Typeface.createFromAsset(getAssets(),"fonts/MMedium.ttf");

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(var==0)
                {
                icanchor.startAnimation(roundingalone);
                button1.setText("stop");

                Drawable myDrawable = getResources().getDrawable(R.drawable.pause);
                button1.setCompoundDrawablesWithIntrinsicBounds(null, null, null, myDrawable);

                timerHere.setBase(SystemClock.elapsedRealtime());
                timerHere.start();
                var=1;
             }
                else
                {

                    Drawable myDrawable = getResources().getDrawable(R.drawable.play);
                    button1.setCompoundDrawablesWithIntrinsicBounds(null, null, null, myDrawable);
                    icanchor.clearAnimation();
                    button1.setText("start");

                    var=0;
                    timerHere.stop();

                }

                }
            }
        );
    }
}