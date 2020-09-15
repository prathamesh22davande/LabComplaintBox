package com.example.userlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText UsernameText, PasswordText;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        UsernameText = findViewById(R.id.UsernameText);
        PasswordText = findViewById(R.id.PasswordText);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        try {
            if (!mAuth.getUid().isEmpty())
                startActivity(new Intent(this, HomeActivity.class));
        }
        catch (Exception e)
        {

        }
    }


    public void SignUpButtonClick(View v) {
        startActivity(new Intent(this, CreateAccount.class));
    }


    public void LoginButtonClick(View v) {

        if(!isNetworkAvailable())
        {
            Toast.makeText(this,"Turn on Internet",Toast.LENGTH_SHORT).show();
        }
        else
        {

            String username = UsernameText.getText().toString().trim();
            String password = PasswordText.getText().toString().trim();
            ///////////
            if (username.isEmpty()) {
                UsernameText.setError("Username Required");
                UsernameText.requestFocus();
                return;
            }
            if (password.isEmpty()) {
                PasswordText.setError("Password Required");
                PasswordText.requestFocus();
                return;
            }
            /////////////
            if (password.length() < 6) {
                PasswordText.setError("Min 6 length");
                PasswordText.requestFocus();
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
                UsernameText.setError("Valid  Username Required");
                UsernameText.requestFocus();
                return;
            }
            loginUser(username, password);
            /////////
        }

    }

    public void loginUser(String username, String password) {
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(MainActivity.this, HomeActivity.class));
                        } else {
                            Toast.makeText(MainActivity.this, "Error Occured", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Error" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        progressBar.setVisibility(View.INVISIBLE);


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }
}


