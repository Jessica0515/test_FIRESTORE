package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(MainActivity.this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        RecyclerView recyclerView = findViewById(R.id.recycler);

        FloatingActionButton add = findViewById(R.id.addUser);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AddUserActivity.class));
            }
        });

        db.collection("test").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    ArrayList<test> arrayList = new ArrayList<>();
                    for (QueryDocumentSnapshot doc: task.getResult()){
                        test user = doc.toObject(test.class);
                        user.setId(doc.getId());
                        arrayList.add(user);
                    }
                    UserAdapter adapter = new UserAdapter(MainActivity.this,arrayList);
                    recyclerView.setAdapter(adapter);
                    adapter.setOnItemClickListener(new UserAdapter.OnItemClickListener() {
                        @Override
                        public View.OnClickListener onClick(test user) {
                            App.test= user;
                            startActivity(new Intent(MainActivity.this,EditUserActivity.class));
                            return null;
                        }
                    });
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,"Failed to get all users list",Toast.LENGTH_LONG).show();
            }
        });

        FloatingActionButton refresh  =findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("test").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            ArrayList<test> arrayList = new ArrayList<>();
                            for (QueryDocumentSnapshot doc: task.getResult()){
                                test user = doc.toObject(test.class);
                                user.setId(doc.getId());
                                arrayList.add(user);
                            }
                            UserAdapter adapter = new UserAdapter(MainActivity.this,arrayList);
                            recyclerView.setAdapter(adapter);
                            adapter.setOnItemClickListener(new UserAdapter.OnItemClickListener() {
                                @Override
                                public View.OnClickListener onClick(test user) {
                                    App.test= user;
                                    startActivity(new Intent(MainActivity.this,EditUserActivity.class));
                                    return null;
                                }
                            });
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this,"Failed to get all users list",Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

    }
}