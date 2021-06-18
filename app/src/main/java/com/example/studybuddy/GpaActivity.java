package com.example.studybuddy;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.time.chrono.Era;
public class GpaActivity
        extends AppCompatActivity {
    private EditText Credit,Grade;
    private Button addCourse,seeGpa,erase;
    private TextView textView;
    double counter=0,total_credit=0,credit=0,grade=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpa);
        Credit = findViewById(R.id.editText1);
        Grade = findViewById(R.id.editText2);
        addCourse = findViewById(R.id.button);
        seeGpa = findViewById(R.id.button2);
        erase = findViewById(R.id.button3);
        textView = findViewById(R.id.textView);
        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                credit = Double.parseDouble(Credit.getText().toString());
                //grade = Double.parseDouble(Grade.getText().toString());
                String abc=Grade.getText().toString();

                //String acb="A";
                if(abc.equals("A") )
                {
                    grade=4;
                }
                else if(abc.equals("A-"))
                {
                    grade=3.75;
                }
                else if(abc.equals("B+"))
                {
                    grade=3.33;
                }
                else if(abc.equals("B"))
                {
                    grade=3.00;
                }
                else if(abc.equals("B-"))
                {
                    grade=2.75;
                }
                else if(abc.equals("C+"))
                {
                    grade=2.33;
                }
                else if(abc.equals("C"))
                {
                    grade=2.00;
                }
                else if(abc.equals("C-"))
                {
                    grade=1.75;
                }
                else
                {
                    grade=0.00;
                }
                counter += (grade*credit);
                total_credit+=credit;
                Toast.makeText(getApplicationContext(),"Credit : "+credit+"\ngrade : "+grade,Toast.LENGTH_LONG).show();
                Credit.setText("");
                Grade.setText("");
            }
        });
        seeGpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double result = counter/total_credit;
                textView.setText("Your GPA :"+result);

            }
        });
        erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter=0;
                total_credit=0;
                credit=0;
                grade=0;
                Credit.setText("");
                Grade.setText("");
                textView.setText("");
            }
        });

    }
}