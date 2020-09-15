package com.example.userlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if(!isNetworkAvailable())
        {
            Toast.makeText(this,"Turn on Internet",Toast.LENGTH_SHORT).show();
        }

    }

    public void queryButtonClick(View v)
    {
        if(!isNetworkAvailable())
        {
            Toast.makeText(this,"Turn on Internet",Toast.LENGTH_SHORT).show();
        }
        else
            startActivity(new Intent(this,QueriesActivity.class));

    }
    public void logoutButtonClick(View v)
    {
        if(!isNetworkAvailable())
        {
            Toast.makeText(this,"Turn on Internet",Toast.LENGTH_SHORT).show();
        }
        else
        {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this,MainActivity.class));
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
