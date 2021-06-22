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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class LoginActivity extends AppCompatActivity {

    private EditText emailET,passwordET;
    TextView loginSignUpTv ;
    private FirebaseAuth mAuth;
    private CircularProgressButton loginbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        emailET= findViewById(R.id.emailET);
        passwordET= findViewById(R.id.pwdEt);
        loginbtn= findViewById(R.id.loginbtn);
        loginSignUpTv= findViewById(R.id.loginSignUpTv);
        loginSignUpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent= new Intent(LoginActivity.this, RegisterActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mainIntent);
                finish();
            }
        });


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginbtn.startAnimation();
                String user_email = emailET.getText().toString();
                String user_password = passwordET.getText().toString();
                try{
                    if(user_email.isEmpty() || user_password.isEmpty()){
                        throw new Exception("All fields must be filled");
                    }
                    if (!(Patterns.EMAIL_ADDRESS.matcher(user_email).matches())){
                        throw new Exception("Invalid Email");
                    }
                    loginUser(user_email,user_password);
                    loginbtn.doneLoadingAnimation(Color.parseColor("#00BCD4"), BitmapFactory.decodeResource(getResources(),R.drawable.ic_done_white_48dp));

                }
                catch (Exception e){

                    loginbtn.revertAnimation();
                }
            }
        });



    }

    public void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent mainIntent= new Intent(LoginActivity.this, MainActivity.class);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(mainIntent);
                            finish();
                           // Toast.makeText(LoginActivity.this, "am here", Toast.LENGTH_LONG).show();
                        }
                        else{
                            if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                                Toast.makeText(LoginActivity.this, "Mot de passe erroné", Toast.LENGTH_LONG).show();
                            }
                            else if(task.getException() instanceof FirebaseAuthInvalidUserException){
                                Toast.makeText(LoginActivity.this, "Email n existe pas ", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }
}