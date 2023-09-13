package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class pack_add_pack extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pack_add_pack);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        TextInputEditText pName = findViewById(R.id.firstNameET);
        TextInputEditText IsRecieve = findViewById(R.id.IsRecieveET);
        TextInputEditText pcontent = findViewById(R.id.pcontentET);
        MaterialButton addpack = findViewById(R.id.addpack);


        addpack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> user = new HashMap<>();

                user.put("pName",Objects.requireNonNull(pName.getText()).toString());
                user.put("IsRecieve",Objects.requireNonNull(IsRecieve.getText()).toString());
                user.put("pcontent",Objects.requireNonNull(pcontent.getText()).toString());


                db.collection("pack").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(pack_add_pack.this,"Pack added success",Toast.LENGTH_LONG).show();
                        finish();
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(pack_add_pack.this,"Failed to add pack",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }
    }



