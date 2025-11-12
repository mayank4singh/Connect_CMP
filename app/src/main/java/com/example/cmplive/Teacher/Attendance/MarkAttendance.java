package com.example.cmplive.Teacher.Attendance;

import android.app.DatePickerDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmplive.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MarkAttendance extends Fragment {

    private Spinner spinnerSubjects;
    private Button btnSelectDate, btnSaveAttendance;
    private TextView tvSelectedDate;
    private ListView listViewStudents;

    private ArrayList<String> subjectCodes = new ArrayList<>();
    private ArrayList<String> subjectNames = new ArrayList<>();
    private ArrayList<String> studentIds = new ArrayList<>();
    private ArrayList<String> studentNames = new ArrayList<>();
    private ArrayList<Boolean> presentStatus = new ArrayList<>();

    private DatabaseReference teacherRef, subjectsRef, studentsRef, attendanceRef;
    private String selectedDate = "";

    public MarkAttendance() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mark_attendance, container, false);

        spinnerSubjects = view.findViewById(R.id.spinnerSubjects);
        btnSelectDate = view.findViewById(R.id.btnSelectDate);
        btnSaveAttendance = view.findViewById(R.id.btnSaveAttendance);
        tvSelectedDate = view.findViewById(R.id.tvSelectedDate);
        listViewStudents = view.findViewById(R.id.listViewStudents);

        String teacherUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        teacherRef = FirebaseDatabase.getInstance().getReference("Teachers").child(teacherUID);
        subjectsRef = FirebaseDatabase.getInstance().getReference("Subjects");
        studentsRef = FirebaseDatabase.getInstance().getReference("Students");
        attendanceRef = FirebaseDatabase.getInstance().getReference("Attendance");

        loadSubjectsForTeacher(teacherUID);

        btnSelectDate.setOnClickListener(v -> showDatePicker());
        btnSaveAttendance.setOnClickListener(v -> saveAttendance());

        return view;
    }

    private void loadSubjectsForTeacher(String teacherUID) {
        teacherRef.child("subjects").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                subjectCodes.clear();
                subjectNames.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    String subjectCode = ds.getKey();
                    subjectCodes.add(subjectCode);
                }

                // Load subject names
                subjectsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (String code : subjectCodes) {
                            String name = snapshot.child(code).child("name").getValue(String.class);
                            subjectNames.add(name != null ? name : code);
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                                android.R.layout.simple_spinner_dropdown_item, subjectNames);
                        spinnerSubjects.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(requireContext(), (view, year, month, dayOfMonth) -> {
            Calendar selected = Calendar.getInstance();
            selected.set(year, month, dayOfMonth);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            selectedDate = sdf.format(selected.getTime());
            tvSelectedDate.setText("Selected: " + selectedDate);
            loadStudentsList();
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    private void loadStudentsList() {
        studentsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                studentIds.clear();
                studentNames.clear();
                presentStatus.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    String name = ds.child("name").getValue(String.class);
                    studentIds.add(ds.getKey());
                    studentNames.add(name);
                    presentStatus.add(true); // Default present
                }

                AttendanceAdapter adapter = new AttendanceAdapter(requireContext(), studentNames, presentStatus);
                listViewStudents.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void saveAttendance() {
        if (selectedDate.isEmpty()) {
            Toast.makeText(requireContext(), "Please select a date first", Toast.LENGTH_SHORT).show();
            return;
        }

        int pos = spinnerSubjects.getSelectedItemPosition();
        if (pos < 0) {
            Toast.makeText(requireContext(), "Please select a subject", Toast.LENGTH_SHORT).show();
            return;
        }

        String subjectCode = subjectCodes.get(pos);
        Map<String, Object> attendanceMap = new HashMap<>();

        for (int i = 0; i < studentIds.size(); i++) {
            String studentId = studentIds.get(i);
            attendanceMap.put(studentId, presentStatus.get(i) ? "Present" : "Absent");
        }

        attendanceRef.child(subjectCode).child(selectedDate).setValue(attendanceMap)
                .addOnSuccessListener(unused -> Toast.makeText(requireContext(), "Attendance saved successfully!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(requireContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
