package com.example.cmplive.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.cmplive.R;
import com.example.cmplive.Teacher.TeacherDash;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TeachLogin extends Fragment {
    View view;
    EditText mail, pass;
    Button log;
    TextView text;
    FirebaseAuth fAuth;
    DatabaseReference databaseReference;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.login_teach, container, false);
        mail = view.findViewById(R.id.Teemail);
        pass = view.findViewById(R.id.Tepass);
        text = view.findViewById(R.id.TefrgtPass);
        log = view.findViewById(R.id.TelogBtn);
        log.setBackgroundColor(getResources().getColor(R.color.sky));
        fAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Teachers");


        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String memail = mail.getText().toString();
                String mpass = pass.getText().toString();
                fAuth.signInWithEmailAndPassword(memail,mpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Login success
                            Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(requireContext(), TeacherDash.class));// Close login activity
                        } else {
                            // Login failed
                            Toast.makeText(requireContext(), "Authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetPass resetPass = new ResetPass();
                FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, resetPass);
                text.setVisibility(View.GONE);
                mail.setVisibility(View.GONE);
                pass.setVisibility(View.GONE);
                log.setVisibility(View.GONE);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }

}
