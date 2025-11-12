package com.example.cmplive.Admin.AdminWork;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cmplive.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AssignSubject extends Fragment {

    private Spinner spinnerTeachers, spinnerSubjects;
    private Button btnAssign;

    private DatabaseReference teachersRef, subjectsRef;
    private ArrayList<String> teacherNames = new ArrayList<>();
    private ArrayList<String> teacherIds = new ArrayList<>();
    private ArrayList<String> subjectNames = new ArrayList<>();
    private ArrayList<String> subjectCodes = new ArrayList<>();

    public AssignSubject() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assign_subject, container, false);

        spinnerTeachers = view.findViewById(R.id.spinnerTeachers);
        spinnerSubjects = view.findViewById(R.id.spinnerSubjects);
        btnAssign = view.findViewById(R.id.btnAssign);

        teachersRef = FirebaseDatabase.getInstance().getReference("Teachers");
        subjectsRef = FirebaseDatabase.getInstance().getReference("Subjects");

        loadTeachers();
        loadSubjects();

        btnAssign.setOnClickListener(v -> assignSubjectToTeacher());

        return view;
    }

    private void loadTeachers() {
        teachersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                teacherNames.clear();
                teacherIds.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    String name = ds.child("name").getValue(String.class);
                    teacherNames.add(name);
                    teacherIds.add(ds.getKey());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                        android.R.layout.simple_spinner_dropdown_item, teacherNames);
                spinnerTeachers.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void loadSubjects() {
        subjectsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                subjectNames.clear();
                subjectCodes.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    String name = ds.child("name").getValue(String.class);
                    subjectNames.add(name);
                    subjectCodes.add(ds.getKey());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                        android.R.layout.simple_spinner_dropdown_item, subjectNames);
                spinnerSubjects.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void assignSubjectToTeacher() {
        int tPos = spinnerTeachers.getSelectedItemPosition();
        int sPos = spinnerSubjects.getSelectedItemPosition();

        if (tPos < 0 || sPos < 0) {
            Toast.makeText(requireContext(), "Please select both teacher and subject", Toast.LENGTH_SHORT).show();
            return;
        }

        String teacherId = teacherIds.get(tPos);
        String subjectCode = subjectCodes.get(sPos);

        Map<String, Object> updates = new HashMap<>();
        updates.put("Subjects/" + subjectCode + "/assignedTeacher", teacherId);
        updates.put("Teachers/" + teacherId + "/subjects/" + subjectCode, true);

        FirebaseDatabase.getInstance().getReference().updateChildren(updates)
                .addOnSuccessListener(unused ->
                        Toast.makeText(requireContext(), "Subject assigned successfully!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(requireContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
