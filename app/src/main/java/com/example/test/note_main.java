package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class note_main extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextDes;
    private EditText editTextPriority;
    private EditText editTextTags;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookref = db.collection("Notebook");

    private DocumentSnapshot lastResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        editTextTitle =findViewById(R.id.edit_text_title);
        editTextDes =findViewById(R.id.edit_text_des);
        editTextPriority=findViewById(R.id.edit_text_priority);
        editTextTags = findViewById(R.id.edit_text_tag);

        //updateNestedValue();
        
    }


    public void  addNote(View v){
        String title = editTextTitle.getText().toString();
        String desc = editTextDes.getText().toString();

        if(editTextPriority.length() ==0){
            editTextPriority.setText("0");
        }
        int priority = Integer.parseInt(editTextPriority.getText().toString());
        String tagInput = editTextTags.getText().toString();
        String[] tagArray = tagInput.split("\\s*,\\s*" );
        Map<String,Boolean> tags = new HashMap<>();

        for (String tag : tagArray){
            tags.put(tag, true);
        }

        note note = new note(title,desc,priority,tags);

        notebookref.document("cG5rjA5k3XGQ7rQdjJcI").collection("child note").add(note);

        notebookref.add(note);
    }
    public void loadNotes(View v) {
        notebookref.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String data = "";
                for (QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                    note note =documentSnapshot.toObject(note.class);
                    note.setDocId(documentSnapshot.getId());

                    String docid = note.getDocId();

                    data+="ID : "+docid;
                    for (String tag: note.getTags().keySet()){
                        data += "\n-" +tag;
                    }
                    data+="\n\n";
                }
            }
        });
    }


    private void updateNestedValue() {
        //notebookref.document("0aSNzAxwXnbme8bx77Ij").update("tags",FieldValue.arrayUnion("new tag"))
        //notebookref.document("0aSNzAxwXnbme8bx77Ij").update("tags",FieldValue.arrayRemove("new tag"));
        //notebookref.document("cG5rjA5k3XGQ7rQdjJcI").update("tags.tag1",false);
        notebookref.document("cG5rjA5k3XGQ7rQdjJcI").update("tags.tag1",FieldValue.delete());
    }
}