package com.example.cmplive.Student.Schlors;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.cmplive.Admin.Student.StAdapter;
import com.example.cmplive.Admin.Student.StudentData;
import com.example.cmplive.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class StuClassm extends Fragment {

  View view;
  private RecyclerView mates;
  private LinearLayout layout;
  private List<StudentData> list;
  private StAdapter adapter;
  private DatabaseReference reference, dbRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_stu_classm, container, false);

        mates = view.findViewById(R.id.classm);
        layout = view.findViewById(R.id.nodata);

        String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dbRef = FirebaseDatabase.getInstance().getReference().child("Students").child(userUid);
        //reference = FirebaseDatabase.getInstance().getReference().child("Students");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Get the department of the current user
                    String department = snapshot.child("department").getValue(String.class);

                    // Query the database to get classmates from the same department
                    reference = FirebaseDatabase.getInstance().getReference().child("Students");
                    Query query = reference.orderByChild("department").equalTo(department);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            list = new ArrayList<>();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                StudentData data = dataSnapshot.getValue(StudentData.class);
                                list.add(data);
                            }
                            if (!list.isEmpty()) {
                                mates.setVisibility(View.VISIBLE);
                                layout.setVisibility(View.GONE);
                                mates.setHasFixedSize(true);
                                mates.setLayoutManager(new LinearLayoutManager(requireContext()));
                                adapter = new StAdapter(list, requireContext());
                                mates.setAdapter(adapter);
                            } else {
                                layout.setVisibility(View.VISIBLE);
                                mates.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(requireContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    // Handle the case when user data doesn't exist
                    Toast.makeText(requireContext(), "User data not found", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });



        return view;
    }
}