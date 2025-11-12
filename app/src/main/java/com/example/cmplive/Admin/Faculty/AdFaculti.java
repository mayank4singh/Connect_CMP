package com.example.cmplive.Admin.Faculty;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.cmplive.Admin.Faculty.AdAddFaculty;
import com.example.cmplive.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class AdFaculti extends Fragment {
View view;
FloatingActionButton floatingActionButton;
private RecyclerView csBCA, csBCAMCA, csMCA , csO;
private LinearLayout bcaNo, BMno, Ono, Mcano;
private List<TeacherData> list1, list2, list3, list4;
private DatabaseReference reference, dbRef;
private TeacherAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ad_faculti, container, false);

        csBCA = view.findViewById(R.id.bca);
        csBCAMCA = view.findViewById(R.id.bcaMca);
        csMCA = view.findViewById(R.id.mca);
        csO = view.findViewById(R.id.csDepartment);

        bcaNo = view.findViewById(R.id.csNoData);
        BMno = view.findViewById(R.id.csBCAMCA);
        Mcano = view.findViewById(R.id.csMCA);
        Ono = view.findViewById(R.id.csNodata);

        reference = FirebaseDatabase.getInstance().getReference().child("Teachers");



        csBCA();
        csBCAMCA();
        csMCA();
        csO();

        floatingActionButton = view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdAddFaculty adAddFaculty = new AdAddFaculty();
                FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container,adAddFaculty);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


            }
        });
        return view;
    }

    private void csMCA() {
        Query query = reference.orderByChild("department").equalTo("MCA");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list3 = new ArrayList<>();
                if (!snapshot.exists()){
                    Mcano.setVisibility(View.VISIBLE);
                    csMCA.setVisibility(View.GONE);
                }else{
                    Mcano.setVisibility(View.GONE);
                    csMCA.setVisibility(View.VISIBLE);
                    for(DataSnapshot dataSnapshot : snapshot.getChildren() ){
                        TeacherData data = dataSnapshot.getValue(TeacherData.class);
                        list3.add(data);
                    }
                    csMCA.setHasFixedSize(true);
                    csMCA.setLayoutManager(new LinearLayoutManager(requireContext()));
                    adapter = new TeacherAdapter(list3,requireContext());
                    csMCA.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    private void csBCAMCA() {

        Query query= reference.orderByChild("department").equalTo("BCA+MCA");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2 = new ArrayList<>();
                if (!snapshot.exists()){
                    BMno.setVisibility(View.VISIBLE);
                    csBCAMCA.setVisibility(View.GONE);
                }else{
                    BMno.setVisibility(View.GONE);
                    csBCAMCA.setVisibility(View.VISIBLE);
                    for(DataSnapshot dataSnapshot : snapshot.getChildren() ){
                        TeacherData data = dataSnapshot.getValue(TeacherData.class);
                        list2.add(data);
                    }
                    csBCAMCA.setHasFixedSize(true);
                    csBCAMCA.setLayoutManager(new LinearLayoutManager(requireContext()));
                    adapter = new TeacherAdapter(list2,requireContext());
                    csBCAMCA.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void csO() {

        Query query = reference.orderByChild("department").equalTo("O LEVEL");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list4 = new ArrayList<>();
                if (!snapshot.exists()){
                    Ono.setVisibility(View.VISIBLE);
                    csO.setVisibility(View.GONE);
                }else{
                    Ono.setVisibility(View.GONE);
                    csO.setVisibility(View.VISIBLE);
                    for(DataSnapshot dataSnapshot : snapshot.getChildren() ){
                        TeacherData data = dataSnapshot.getValue(TeacherData.class);
                        list4.add(data);
                    }
                    csO.setHasFixedSize(true);
                    csO.setLayoutManager(new LinearLayoutManager(requireContext()));
                    adapter = new TeacherAdapter(list4,requireContext());
                    csO.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void csBCA() {
        Query query = reference.orderByChild("department").equalTo("BCA");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1 = new ArrayList<>();
                if (!snapshot.exists()){
                    bcaNo.setVisibility(View.VISIBLE);
                    csBCA.setVisibility(View.GONE);
                }else{
                    bcaNo.setVisibility(View.GONE);
                    csBCA.setVisibility(View.VISIBLE);
                    for(DataSnapshot dataSnapshot : snapshot.getChildren() ){
                        TeacherData data = dataSnapshot.getValue(TeacherData.class);
                        list1.add(data);
                    }
                    csBCA.setHasFixedSize(true);
                    csBCA.setLayoutManager(new LinearLayoutManager(requireContext()));
                    adapter = new TeacherAdapter(list1,requireContext());
                    csBCA.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}