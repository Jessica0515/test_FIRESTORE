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

public class pack_main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pack);

        FirebaseApp.initializeApp(pack_main.this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        RecyclerView recyclerView = findViewById(R.id.recycler);

        FloatingActionButton add = findViewById(R.id.addUser);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(pack_main.this,pack_add_pack.class));
            }
        });

        db.collection("pack").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    ArrayList<pack_test> arrayList = new ArrayList<>();
                    for (QueryDocumentSnapshot doc: task.getResult()){
                        pack_test pack_test = doc.toObject(pack_test.class);
                        pack_test.setId(doc.getId());
                        arrayList.add(pack_test);
                    }
                    pack_adapter adapter = new pack_adapter(pack_main.this,arrayList);
                    recyclerView.setAdapter(adapter);
                    adapter.setOnItemClickListener(new  pack_adapter.OnItemClickListener() {
                        @Override
                        public View.OnClickListener onClick(pack_test pack_test) {
                            pack_app.pack_test= pack_test;
                            startActivity(new Intent(pack_main.this,pack_edit_pack.class));
                            return null;
                        }
                    });
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(pack_main.this,"Failed to get all pack list",Toast.LENGTH_LONG).show();
            }
        });
        FloatingActionButton refresh  =findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("pack").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            ArrayList<pack_test> arrayList = new ArrayList<>();
                            for (QueryDocumentSnapshot doc: task.getResult()){
                                pack_test pack = doc.toObject(pack_test.class);
                                pack.setId(doc.getId());
                                arrayList.add(pack);
                            }
                            pack_adapter adapter = new pack_adapter(pack_main.this,arrayList);
                            recyclerView.setAdapter(adapter);
                            adapter.setOnItemClickListener(new pack_adapter.OnItemClickListener() {
                                @Override
                                public View.OnClickListener onClick(pack_test pack) {
                                    pack_app.pack_test= pack;
                                    startActivity(new Intent(pack_main.this,pack_edit_pack.class));
                                    return null;
                                }
                            });
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(pack_main.this,"Failed to get all pack list",Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }
    }