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
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.units.qual.A;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class pack_edit_pack extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pack_edit_pack);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        TextInputEditText pName = findViewById(R.id.firstNameET);
        TextInputEditText isRecieve = findViewById(R.id.isRecieveET);
        TextInputEditText pcontent = findViewById(R.id.pcontentET);
        MaterialButton save = findViewById(R.id.save);
        MaterialButton delete = findViewById(R.id.delete);


        pName.setText(pack_app.pack_test.getpname());
        isRecieve.setText(pack_app.pack_test.getisRecieve());
        pcontent.setText(pack_app.pack_test.getpcontent());

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("pack").document(pack_app.pack_test.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(pack_edit_pack.this,"pack deleted success",Toast.LENGTH_LONG).show();
                        finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(pack_edit_pack.this,"Failed to delete pack",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> user =new HashMap<>();
                user.put("pName",Objects.requireNonNull(pName.getText()).toString());
                user.put("pcontent",Objects.requireNonNull(pcontent.getText()).toString());
                user.put("isRecieve",Objects.requireNonNull(isRecieve.getText()).toString());


                db.collection("pack").document(pack_app.pack_test.getId()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(pack_edit_pack.this,"Save success",Toast.LENGTH_LONG).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(pack_edit_pack.this,"Save failed",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }




}


