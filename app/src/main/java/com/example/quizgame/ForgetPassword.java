package com.example.quizgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {

    ImageView image;
    EditText Email;
    Button bt;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        image = findViewById(R.id.img1);
        Email = findViewById(R.id.ed1);
        bt = findViewById(R.id.bt1);

        //password reset process
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userMail = Email.getText().toString();
                resetPassword(userMail);
            }
        });
    }
    //for pass word reset
    public void resetPassword(String userMail){
        auth.sendPasswordResetEmail(userMail).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgetPassword.this, "We sent An Email to Your Email Address", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(ForgetPassword.this, "An Error Occured ", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}