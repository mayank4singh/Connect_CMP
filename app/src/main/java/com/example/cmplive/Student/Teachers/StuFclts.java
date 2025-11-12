package com.example.cmplive.Student.Teachers;

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


public class StuFclts extends Fragment {

    View view;

    private RecyclerView fclts;
    private LinearLayout layout;
    private List<TeacherData> list;
    private DatabaseReference reference, dbRef;
    private StFcltsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_stu_fclts, container, false);

        fclts = view.findViewById(R.id.fclt);
        layout = view.findViewById(R.id.noData);


        String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dbRef = FirebaseDatabase.getInstance().getReference().child("Students").child(userUid);
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Get the department of the current user
                    String department = snapshot.child("department").getValue(String.class);
                    reference = FirebaseDatabase.getInstance().getReference().child("Teachers");
                    Query query = reference.orderByChild("department").equalTo(department);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            list = new ArrayList<>();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                TeacherData data = dataSnapshot.getValue(TeacherData.class);
                                list.add(data);
                            }
                            if (!list.isEmpty()) {
                                fclts.setVisibility(View.VISIBLE);
                                layout.setVisibility(View.GONE);
                                fclts.setHasFixedSize(true);
                                fclts.setLayoutManager(new LinearLayoutManager(requireContext()));
                                adapter = new StFcltsAdapter(list, requireContext());
                                fclts.setAdapter(adapter);
                            } else {
                                layout.setVisibility(View.VISIBLE);
                                fclts.setVisibility(View.GONE);
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
                Toast.makeText(requireContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });



        return view;

    }


}