package com.imene.miamiam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class RegisterActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;

    private EditText pseudoET , emailET, passwordET ,confirmPassET ;

    private CircularProgressButton registerbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        registerbtn = findViewById(R.id.registerBtn);
        emailET = findViewById(R.id.registerEmailEt);
        pseudoET = findViewById(R.id.username);
        passwordET = findViewById(R.id.registerPasswordEt);
        confirmPassET = findViewById(R.id.registerConfirmPasswordEt);


        mAuth = FirebaseAuth.getInstance();


        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();
                String username = pseudoET.getText().toString();
                String confirmpass = confirmPassET.getText().toString();
                try{
                    if(email.isEmpty() || password.isEmpty()){
                        throw new Exception("All fields must be filled");
                    }
                    if(password.length()<6){
                        throw new Exception("Password length should be more than 6 characters");
                    }
                    if (!(Patterns.EMAIL_ADDRESS.matcher(email).matches())){
                        throw new Exception("Invalid Email");
                    }
                    registerUser(email,password);
                    registerbtn.doneLoadingAnimation(Color.parseColor("#5856EC"), BitmapFactory.decodeResource(getResources(),R.drawable.ic_done_white_48dp));

                }
                catch (Exception e){
                    Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    registerbtn.revertAnimation();

                }
            }
        });

    }
    private void registerUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent mainIntent= new Intent(RegisterActivity.this, MainActivity.class);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(mainIntent);
                            finish(); // The user can't come back to this page
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(e instanceof FirebaseAuthUserCollisionException){
                    Toast.makeText(RegisterActivity.this, "Email already exists", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}