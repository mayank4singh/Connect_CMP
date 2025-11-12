package com.example.cmplive.Teacher;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.cmplive.Admin.Faculty.TeacherAdapter;
import com.example.cmplive.Admin.Faculty.TeacherData;
import com.example.cmplive.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class TeachHome extends Fragment {

    View view;
    private RecyclerView recyclerView;
    private LinearLayout layout;
    private List<TeacherData> list;
    private TeacherAdapter adapter;
    private DatabaseReference reference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_teach_home, container, false);

        recyclerView = view.findViewById(R.id.Profile);
        layout = view.findViewById(R.id.NoData);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new TeacherAdapter(new ArrayList<>(), requireContext());
        recyclerView.setAdapter(adapter);

        // Get the UID of the currently logged-in user
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Reference to the current user's data under "Teachers" node
        reference = FirebaseDatabase.getInstance().getReference().child("Teachers").child(userId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                // Check if data exists for the current user
                if (snapshot.exists()) {
                    // Data exists, retrieve and display it
                    TeacherData data = snapshot.getValue(TeacherData.class);
                    list.add(data);
                    adapter.updateData(list);
                    recyclerView.setVisibility(View.VISIBLE);
                    layout.setVisibility(View.GONE);
                } else {
                    // No data found for the current user
                    recyclerView.setVisibility(View.GONE);
                    layout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
                Log.e("TeachHome", "Error fetching teacher data: " + error.getMessage());
            }
        });
        return view;
    }
}