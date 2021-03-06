package com.google.mlkit.vision.demo.java.chatpapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.mlkit.vision.demo.R;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth auth;

    Button signUpButton;
    Button signInButton;
    EditText login_email, login_password;
    public static String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth=FirebaseAuth.getInstance();
        //we are connecting the applcation to the main connection.
        signUpButton=findViewById(R.id.button_register);
        login_email=findViewById(R.id.login_email);
        login_password=findViewById(R.id.login_password);
        signInButton=findViewById(R.id.button_login);
        ActionBar actionBar=getActionBar();


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email, password;
                email=login_email.getText().toString().trim();
                password=login_password.getText().toString().trim();

                //we are doing validations here for all the values.
                if(email.isEmpty()){
                    login_email.setError("Email is required");
                    login_email.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    login_email.setError("Please enter a valid email");
                    login_email.requestFocus();
                    return;
                }
                if(password.isEmpty()){
                    login_password.setError("Password is required");
                    login_password.requestFocus();
                }
                if(password.length()<6){
                    login_password.setError("Min password length is 6 characters");
                    login_password.requestFocus();
                    return;
                }
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Intent i;
                        if (task.isSuccessful()) {
                            // Log.i("checking login:", String.valueOf(auth.getUid()));
                            user = email;
                            i = new Intent(LoginActivity.this, HomeActivity.class);
                            i.putExtra("UserID", auth.getUid());
                            startActivity(i);
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Error in Login", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        //once the user pressed the button the onCLick listener is activated
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //we are diverting to the Register page.
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            };
        });


    }

    public static Bitmap getScreenShot(View view) {
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }
}