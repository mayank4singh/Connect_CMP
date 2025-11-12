package com.example.cmplive.Subjects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.cmplive.R;

public class AddSubject extends AppCompatActivity {

    EditText etCode, etName, etSemester, etDepartment;
    Button btnAddSubject;
    DatabaseReference subjectsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        etCode = findViewById(R.id.etSubjectCode);
        etName = findViewById(R.id.etSubjectName);
        etSemester = findViewById(R.id.etSemester);
        etDepartment = findViewById(R.id.etDepartment);
        btnAddSubject = findViewById(R.id.btnAddSubject);

        subjectsRef = FirebaseDatabase.getInstance().getReference("Subjects");

        btnAddSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSubjectToFirebase();
            }
        });
    }

    private void addSubjectToFirebase() {
        String code = etCode.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String sem = etSemester.getText().toString().trim();
        String dept = etDepartment.getText().toString().trim();

        if (code.isEmpty() || name.isEmpty() || sem.isEmpty() || dept.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        SubjectModel subject = new SubjectModel(code, name, sem, dept, "");
        subjectsRef.child(code).setValue(subject)
                .addOnSuccessListener(unused -> Toast.makeText(this, "Subject added successfully", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
