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

public class EditUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        TextInputEditText firstName = findViewById(R.id.firstNameET);
        TextInputEditText lastName = findViewById(R.id.lastNameET);
        TextInputEditText phone = findViewById(R.id.phoneNumberET);
        TextInputEditText bio = findViewById(R.id.bioET);
        MaterialButton save = findViewById(R.id.save);
        MaterialButton delete = findViewById(R.id.delete);


        firstName.setText(App.test.getFirstName());
        lastName.setText(App.test.getLastName());
        bio.setText(App.test.getBio());
        phone.setText(App.test.getPhone());

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("test").document(App.test.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(EditUserActivity.this,"User deleted success",Toast.LENGTH_LONG).show();
                        finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditUserActivity.this,"Failed to delete User",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> user =new HashMap<>();
                user.put("firstName",Objects.requireNonNull(firstName.getText()).toString());
                user.put("lastName",Objects.requireNonNull(lastName.getText()).toString());
                user.put("phone",Objects.requireNonNull(phone.getText()).toString());
                user.put("bio",Objects.requireNonNull(bio.getText()).toString());

                db.collection("test").document(App.test.getId()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(EditUserActivity.this,"Save success",Toast.LENGTH_LONG).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditUserActivity.this,"Save failed",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}