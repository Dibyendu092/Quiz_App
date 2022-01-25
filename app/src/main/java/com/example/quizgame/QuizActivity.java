package com.example.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QuizActivity extends AppCompatActivity {

    TextView Timer1;
    TextView Timer2;
    TextView Correct1;
    TextView Correct2;
    TextView QuestionNo;
    TextView Question;
    TextView GivenQuestion;
    TextView A;
    TextView B;
    TextView C;
    TextView D;
    Button next;
    Button prev;
    Button finish;
    TextView Ca;
    TextView Cb;
    TextView Cc;
    TextView Cd;
    TextView WrongAnswer;
    TextView WrongAnserNo;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference().child("Question");//for data base//we acces through it only the question child of database

    String QuizQuestion;//in the data base their are 6 data point.for fetch the data we have to take 6 String value
    String QuizAnswerA;
    String QuizAnswerB;
    String QuizAnswerC;
    String QuizAnswerD;
    String QuizAnwerCorrect;

    int totalQuestion;
    int Qn =1;//for Question no


    String UserAnswer;//for the coreect ans and wrong ans count, user ans etc
    int a=0;
    int b = 0;
    int x=0;

    CountDownTimer ct;//for  the timer part
    private static final long Total_time=30000;
    Boolean Timer;
    long timeleft = Total_time;

    //for the score part
    //for the cuurent user
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user1 = auth.getCurrentUser();
    DatabaseReference databaseReference2 = database.getReference();






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        getSupportActionBar().hide();

        Timer1 = findViewById(R.id.Time1);
        Timer2 = findViewById(R.id.Time2);

        Correct1 = findViewById(R.id.TXT1);
        Correct2 = findViewById(R.id.TXT2);

       WrongAnswer = findViewById(R.id.TXT3);
       WrongAnserNo = findViewById(R.id.TXT4);

        Question = findViewById(R.id.txtqn);
        QuestionNo = findViewById(R.id.txtqnp);

        GivenQuestion = findViewById(R.id.mainQ);

        A = findViewById(R.id.txtoa);
        B = findViewById(R.id.txtob);
        C = findViewById(R.id.txtoc);
        D = findViewById(R.id.txtod);

        next = findViewById(R.id.btne);
        prev = findViewById(R.id.btprev);
        finish = findViewById(R.id.btf);

        Ca = findViewById(R.id.A);
        Cb = findViewById(R.id.B);
        Cc = findViewById(R.id.C);
        Cd = findViewById(R.id.D);

        QuizData();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
                QuizData();

            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SendScore();
                Intent i = new Intent(QuizActivity.this, ResultPage.class);
                startActivity(i);
                finish();
            }
        });
        A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
                UserAnswer = "A";
                if(QuizAnwerCorrect.equals(UserAnswer)){
                    A.setBackgroundColor(Color.GREEN);
                    a++;
                    Correct2.setText(""+a);

                }
                else{
                    A.setBackgroundColor(Color.RED);
                    b++;
                    WrongAnserNo.setText(""+b);
                    AlwayasMarkCorrect();
                }
            }
        });
        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
                UserAnswer = "B";
                if(QuizAnwerCorrect.equals(UserAnswer)){
                    B.setBackgroundColor(Color.GREEN);
                    a++;
                    Correct2.setText(""+a);

                }
                else{
                    B.setBackgroundColor(Color.RED);
                    b++;
                    WrongAnserNo.setText(""+b);
                    AlwayasMarkCorrect();
                }
            }
        });
        C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
                UserAnswer = "C";
                if(QuizAnwerCorrect.equals(UserAnswer)){
                    C.setBackgroundColor(Color.GREEN);
                    a++;
                    Correct2.setText(""+a);

                }
                else{
                    C.setBackgroundColor(Color.RED);
                    b++;
                    WrongAnserNo.setText(""+b);
                    AlwayasMarkCorrect();
                }

            }
        });
        D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
                UserAnswer = "D";
                if(QuizAnwerCorrect.equals(UserAnswer)){
                    D.setBackgroundColor(Color.GREEN);
                    a++;
                    Correct2.setText(""+a);


                }
                else{
                    D.setBackgroundColor(Color.RED);
                    b++;
                    WrongAnserNo.setText(""+b);
                    AlwayasMarkCorrect();
                }

            }
        });

    }
    public void QuizData(){
        startTimer();
        A.setBackgroundColor(Color.WHITE);
        B.setBackgroundColor(Color.WHITE);
        C.setBackgroundColor(Color.WHITE);

        D.setBackgroundColor(Color.WHITE);

    // Read from the database copy from firebse assistance
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                totalQuestion = (int)dataSnapshot.getChildrenCount();//count Total count of Question
                QuizQuestion = dataSnapshot.child(String.valueOf(Qn)).child("Q").getValue().toString();//Qn is Question no
                QuizAnswerA = dataSnapshot.child(String.valueOf(Qn)).child("A").getValue().toString();
                QuizAnswerB = dataSnapshot.child(String.valueOf(Qn)).child("B").getValue().toString();
                QuizAnswerC = dataSnapshot.child(String.valueOf(Qn)).child("C").getValue().toString();
                QuizAnswerD = dataSnapshot.child(String.valueOf(Qn)).child("D").getValue().toString();
                QuizAnwerCorrect = dataSnapshot.child(String.valueOf(Qn)).child("Answer").getValue().toString();

                GivenQuestion.setText(QuizQuestion);
                A.setText(QuizAnswerA);
                B.setText(QuizAnswerB);
                C.setText(QuizAnswerC);
                D.setText(QuizAnswerD);

                if(Qn < totalQuestion) {
                    Qn++;
                }
                else{
                    Toast.makeText(QuizActivity.this, "Please Press Finish Button to Submit and View Score", Toast.LENGTH_SHORT).show();

                }
                x++;
                QuestionNo.setText(""+x);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(QuizActivity.this, "Sorry Their Is A problem", Toast.LENGTH_SHORT).show();
            }
        });
    }
       //after responding te user always show the correct answer
    public void AlwayasMarkCorrect(){
        if(QuizAnwerCorrect .equals("A")){
            A.setBackgroundColor(Color.GREEN);
        }
        else if(QuizAnwerCorrect .equals("B")){
            B.setBackgroundColor(Color.GREEN);
        }
        else if(QuizAnwerCorrect .equals("C")){
            C.setBackgroundColor(Color.GREEN);
        }
        else if(QuizAnwerCorrect .equals("D")){
            D.setBackgroundColor(Color.GREEN);
        }
    }
    public void startTimer(){
        ct = new CountDownTimer(timeleft, 1000) {//10 is interval
            @Override
            public void onTick(long millisUntilFinished) {
                timeleft = millisUntilFinished;//show remaining time
                UpdateCountDownText();
            }

            @Override
            public void onFinish() {
                Timer = false;
                pauseTimer();
                GivenQuestion.setText("Sorry Time is up");
                A.setText("NULL");
                B.setText("NULL");
                C.setText("NULL");
                D.setText("NULL");
            }
        }.start();
        Timer = true;
    }
    public void resetTimer(){
        timeleft = Total_time;
        UpdateCountDownText();
    }
    public void UpdateCountDownText(){

        int se =(int)(timeleft / 1000)%60;
        Timer2.setText(""+se);
    }
    public void pauseTimer(){

        ct.cancel();
        Timer = false;
    }
    public void SendScore(){
        String UserUid = user1.getUid();
        databaseReference2.child("Scores").child(UserUid).child("Correct").setValue(a).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(QuizActivity.this, "Score was Succesfully Submited..", Toast.LENGTH_SHORT).show();
            }
        });
        databaseReference2.child("Scores").child(UserUid).child("Wrong").setValue(b);

    }




}