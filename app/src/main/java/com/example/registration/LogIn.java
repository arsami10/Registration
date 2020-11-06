package com.example.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogIn extends AppCompatActivity {
    EditText emailId, password;
    Button btnSignIn;
    TextView tvSignUp;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mFirebaseAuth= FirebaseAuth.getInstance();
        emailId= findViewById(R.id.editText);
        password= findViewById(R.id.editText2);
        btnSignIn= findViewById(R.id.button);
        tvSignUp=findViewById(R.id.textView);

        mAuthStateListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser= mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser !=null){
                    Toast.makeText(LogIn.this, "You are logged in", Toast.LENGTH_SHORT).show();
                    Intent i= new Intent(LogIn.this, Home.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(LogIn.this, "Please Login", Toast.LENGTH_SHORT).show();
                }
            }
        };

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email= emailId.getText().toString();
                String pwd= password.getText().toString();
                if(email.isEmpty()){
                    emailId.setError("Please enter an email id");
                    emailId.requestFocus();
                }
                else if(pwd.isEmpty()){
                    password.setError("Please enter your Password");
                    password.requestFocus();
                }
                if(email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(LogIn.this, "Fields are empty!", Toast.LENGTH_SHORT).show();
                }
                if(!email.isEmpty() && !pwd.isEmpty()){
                    mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(LogIn.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(LogIn.this, "Login error, Please try again", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Intent intToHome= new Intent(LogIn.this, Home.class);
                                startActivity(intToHome);
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(LogIn.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intSignUp= new Intent(LogIn.this,MainActivity.class);
                startActivity(intSignUp);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}