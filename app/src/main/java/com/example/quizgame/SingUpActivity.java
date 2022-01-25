package com.example.quizgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SingUpActivity extends AppCompatActivity {

    ImageView img;
    EditText email;
    EditText password;
    Button singup;
    ProgressBar pp;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        img = findViewById(R.id.img1);
        email = findViewById(R.id.ed1);
        password = findViewById(R.id.ed2);
        singup = findViewById(R.id.bt1);
        pp = findViewById(R.id.p1);
        pp.setVisibility(View.INVISIBLE);
        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //singup.setClickable(false);//more than one we cannot singup
                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();
                singupFirebase(userEmail, userPassword);

            }
        });
    }
    //sing up with firebase
    public void singupFirebase(String userEmail, String userPassword){
        pp.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SingUpActivity.this, "Your Account is created Succesfully", Toast.LENGTH_LONG).show();
                    finish();
                    pp.setVisibility(View.INVISIBLE);
                }
                else{
                    Toast.makeText(SingUpActivity.this, "There is an error. Please Try again Later", Toast.LENGTH_LONG).show();

                }

            }
        });
    }


}