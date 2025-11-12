package com.example.cmplive.Teacher.Attendance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cmplive.R;

import java.util.ArrayList;

public class AttendanceAdapter extends ArrayAdapter<String> {

    private final ArrayList<String> names;
    private final ArrayList<Boolean> statuses;

    public AttendanceAdapter(@NonNull Context context, ArrayList<String> names, ArrayList<Boolean> statuses) {
        super(context, 0, names);
        this.names = names;
        this.statuses = statuses;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_student_attendance, parent, false);
        }

        TextView name = convertView.findViewById(R.id.tvStudentName);
        CheckBox check = convertView.findViewById(R.id.checkboxPresent);

        name.setText(names.get(position));
        check.setChecked(statuses.get(position));

        check.setOnCheckedChangeListener((buttonView, isChecked) -> statuses.set(position, isChecked));

        return convertView;
    }
}
