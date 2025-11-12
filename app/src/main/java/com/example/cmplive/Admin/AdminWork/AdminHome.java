package com.example.cmplive.Admin.AdminWork;

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

import com.example.cmplive.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.snapshot.NamedNode;

import org.w3c.dom.CDATASection;

import java.util.ArrayList;
import java.util.List;


public class AdminHome extends Fragment {

   View view;
   private RecyclerView admin;
   private LinearLayout NoData;
   private List<AdminData> list;
   private DatabaseReference databaseReference;
   private AdminAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_home, container, false);

        admin = view.findViewById(R.id.admindata);
        NoData = view.findViewById(R.id.NoData);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Admin");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                if(!snapshot.exists()){
                    NoData.setVisibility(View.VISIBLE);
                    admin.setVisibility(View.GONE);
                }else{
                    NoData.setVisibility(View.GONE);
                    admin.setVisibility(View.VISIBLE);
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        AdminData data = dataSnapshot.getValue(AdminData.class);
                        list.add(data);
                    }
                    admin.setHasFixedSize(true);
                    admin.setLayoutManager(new LinearLayoutManager(requireContext()));
                    adapter = new AdminAdapter(list,requireContext());
                    admin.setAdapter(adapter);
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