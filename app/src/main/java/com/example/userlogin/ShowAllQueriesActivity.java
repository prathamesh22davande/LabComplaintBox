package com.example.userlogin;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ShowAllQueriesActivity extends AppCompatActivity {
    private TextView mTextMessage;

    FirebaseAuth mAuth;
    FirebaseFirestore db;

    QueryAdapter adapter;

    List<Queries> queryList;
    RecyclerView recyclerView;
    static ProgressBar progressBar = null;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    startActivity(new Intent(ShowAllQueriesActivity.this,QueriesActivity.class));
                    return true;
                case R.id.navigation_dashboard:

                    return true;
                case R.id.navigation_notifications:

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_queries);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        try {


            mAuth = FirebaseAuth.getInstance();
            db = FirebaseFirestore.getInstance();

            progressBar = findViewById(R.id.progressBar);

            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            queryList = new ArrayList<>();
            adapter = new QueryAdapter(this, queryList);
            recyclerView.setAdapter(adapter);

            db.collection(mAuth.getUid().toString()).get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {


                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                           // progressBar.setVisibility(View.GONE);
                            if (!queryDocumentSnapshots.isEmpty()) {
                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                                for (DocumentSnapshot d : list) {
                                    Queries q = d.toObject(Queries.class);
                                    queryList.add(q);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });

        }
        catch (Exception e)
        {
            Toast.makeText(this,"Error Occured : "+e.toString(),Toast.LENGTH_SHORT);
        }
    }

}
