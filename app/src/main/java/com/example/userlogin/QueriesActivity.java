package com.example.userlogin;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

public class QueriesActivity extends AppCompatActivity {
    private TextView mTextMessage;

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    Spinner LabSpinner,ComputerSpinner,DeviceSpinner;
    EditText DescriptionText;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    return true;
                case R.id.navigation_dashboard:
                    startActivity(new Intent(QueriesActivity.this,ShowAllQueriesActivity.class));
                    return true;
                case R.id.navigation_notifications:
                   // mTextMessage.setText("NOTIFICATION");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queries);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        mAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();

        DescriptionText=findViewById(R.id.DescriptionText);

        LabSpinner=findViewById(R.id.LabSpinner);
        ArrayAdapter<CharSequence> labAdapter=ArrayAdapter.createFromResource(this,R.array.Lab,android.R.layout.simple_spinner_item);
        labAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        LabSpinner.setAdapter(labAdapter);

        ComputerSpinner=findViewById(R.id.ComputerSpinner);
        ArrayAdapter<CharSequence> computerAdapter=ArrayAdapter.createFromResource(this,R.array.Computer,android.R.layout.simple_spinner_item);
        computerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ComputerSpinner.setAdapter(computerAdapter);

        DeviceSpinner=findViewById(R.id.DeviceSpinner);
        ArrayAdapter<CharSequence> deviceAdapter=ArrayAdapter.createFromResource(this,R.array.Device,android.R.layout.simple_spinner_item);
        deviceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        DeviceSpinner.setAdapter(deviceAdapter);

    }


    public void submitButtonClick(View v)
    {

        if(DescriptionText.getText().toString().isEmpty())
        {
            DescriptionText.setError("Password Required");
            DescriptionText.requestFocus();
            return;
        }
        String lab=LabSpinner.getSelectedItem().toString();
        String computer=ComputerSpinner.getSelectedItem().toString();
        String device=DeviceSpinner.getSelectedItem().toString();
        String description=DescriptionText.getText().toString().trim();

        Queries queries=new Queries(lab,computer,device,description,"INCOMPLETE");
        CollectionReference dbQueries=db.collection(mAuth.getUid().toString());
        dbQueries.add(queries).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(QueriesActivity.this,"Query submitted",Toast.LENGTH_SHORT).show();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(QueriesActivity.this,"Error: "+e.toString(),Toast.LENGTH_SHORT).show();
                    }
                });

    }


}
