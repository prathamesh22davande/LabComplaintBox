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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CreateAccount extends AppCompatActivity {

    FirebaseAuth mAuth;

    EditText UsernameText, PasswordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mAuth = FirebaseAuth.getInstance();

        UsernameText = findViewById(R.id.UsernameText);
        PasswordText = findViewById(R.id.PasswordText);
    }

    public void CreateAccountButtonClick(View v) {

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
                UsernameText.setError("Valid Username(email) Required");
                UsernameText.requestFocus();
                return;
            }

            registerUser(username, password);
        }

    }

    public void registerUser(String username, String password) {

        try {
            mAuth.createUserWithEmailAndPassword(username, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(CreateAccount.this, "User Created", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(CreateAccount.this, HomeActivity.class));
                            } else {
                                Toast.makeText(CreateAccount.this, "Error Occured", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } catch (Exception e) {
            Toast.makeText(this, "Error : " + e.toString(), Toast.LENGTH_SHORT);
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }
}
