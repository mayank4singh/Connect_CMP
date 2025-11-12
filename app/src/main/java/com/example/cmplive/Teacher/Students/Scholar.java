package com.example.cmplive.Teacher.Students;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.cmplive.Admin.Faculty.TeacherAdapter;
import com.example.cmplive.Admin.Faculty.TeacherData;
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


public class Scholar extends Fragment {

   View view;
   private RecyclerView recyclerView;
   private LinearLayout layout;
   private List<StudentData> list;
   private StAdapter adapter;
   private DatabaseReference reference, dbRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_scholar, container, false);

        recyclerView = view.findViewById(R.id.stdnts);
        layout = view.findViewById(R.id.noData);

        if(isAdded()){
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            adapter = new StAdapter(new ArrayList<>(), requireContext());
            recyclerView.setAdapter(adapter);
        }

        String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("Teachers").child(userUid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Get the department of the current user
                    String department = snapshot.child("department").getValue(String.class);
                    dbRef = FirebaseDatabase.getInstance().getReference().child("Students");
                    Query query = dbRef.orderByChild("department").equalTo(department);
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            list = new ArrayList<>();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                StudentData data = dataSnapshot.getValue(StudentData.class);
                                list.add(data);
                            }
                            if (!list.isEmpty()) {
                                recyclerView.setVisibility(View.VISIBLE);
                                layout.setVisibility(View.GONE);
                                adapter.updateData(list);

                            } else {
                                layout.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(requireContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), "User data not found", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}