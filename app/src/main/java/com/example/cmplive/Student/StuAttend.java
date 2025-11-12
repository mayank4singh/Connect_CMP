package com.example.cmplive.Student;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cmplive.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StuAttend extends Fragment {

    private PieChart pieChart;
    private TextView tvSummary;
    private DatabaseReference attendanceRef;
    private String studentUid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stu_attend, container, false);

        pieChart = view.findViewById(R.id.attendancePieChart);
        tvSummary = view.findViewById(R.id.tvAttendanceSummary);

        attendanceRef = FirebaseDatabase.getInstance().getReference("Attendance");

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            tvSummary.setText("Error: No user logged in.");
        } else {
            studentUid = currentUser.getUid();
            fetchAttendanceData();
        }

        return view;
    }

    private void fetchAttendanceData() {
        attendanceRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int totalDays = 0;
                int presentDays = 0;

                for (DataSnapshot subjectSnap : snapshot.getChildren()) {
                    for (DataSnapshot dateSnap : subjectSnap.getChildren()) {
                        if (dateSnap.hasChild(studentUid)) {
                            String status = dateSnap.child(studentUid).getValue(String.class);
                            if (status != null) {
                                totalDays++;
                                if (status.equalsIgnoreCase("Present")) {
                                    presentDays++;
                                }
                            }
                        }
                    }
                }

                if (totalDays > 0) {
                    showAttendanceChart(presentDays, totalDays);
                } else {
                    tvSummary.setText("No attendance data found.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                tvSummary.setText("Error loading data: " + error.getMessage());
            }
        });
    }

    private void showAttendanceChart(int present, int total) {
        int absent = total - present;
        float presentPercent = (present * 100f / total);
        float absentPercent = 100f - presentPercent;

        tvSummary.setText(String.format("Attendance: %.1f%% (%d / %d days)",
                presentPercent, present, total));

        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(presentPercent, "Present"));
        entries.add(new PieEntry(absentPercent, "Absent"));

        PieDataSet dataSet = new PieDataSet(entries, "Attendance Summary");
        dataSet.setColors(new int[]{Color.parseColor("#4CAF50"), Color.parseColor("#F44336")});
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(14f);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setHoleRadius(40f);
        pieChart.setTransparentCircleRadius(45f);
        pieChart.animateY(1000);

        Legend legend = pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);

        pieChart.invalidate();
    }
}
