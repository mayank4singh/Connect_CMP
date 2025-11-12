package com.example.cmplive.Admin.Notice;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cmplive.Admin.Faculty.AdAddFaculty;
import com.example.cmplive.R;
import com.example.cmplive.Student.Notice.StNoticeAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class AdNotice extends Fragment {

    FloatingActionButton fab;
   View view;
   private RecyclerView noticeRecycler;
   private ArrayList<NoticeData> list;
   private NoticeAdapter adapter;
   private ProgressBar progressBar;
   private DatabaseReference reference,databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ad_notice, container, false);
        fab = view.findViewById(R.id.fab);

        noticeRecycler = view.findViewById(R.id.deleteNoticeRecycler);
        progressBar = view.findViewById(R.id.progressBar);

        if (isAdded()) {
            // Initialize RecyclerView and adapter
            noticeRecycler.setHasFixedSize(true);
            noticeRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
            adapter = new NoticeAdapter(new ArrayList<>(), requireContext());
            noticeRecycler.setAdapter(adapter);
        }

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Notices");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    NoticeData data = dataSnapshot.getValue(NoticeData.class);
                    list.add(data);
                }
                // Update adapter data and hide progress bar
                adapter.updateData(list);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled
            }
        });






        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdAddNotice adAddNotice = new AdAddNotice();
                FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container,adAddNotice);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }


    }
