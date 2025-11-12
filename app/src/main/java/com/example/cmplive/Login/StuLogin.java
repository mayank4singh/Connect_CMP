package com.example.cmplive.Login;

import android.app.ProgressDialog;
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

import com.example.cmplive.Admin.AdminWork.AdminDash;
import com.example.cmplive.R;
import com.example.cmplive.Student.StudentDash;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StuLogin extends Fragment {
    View view;
    EditText mail, pass;
    Button log;
    TextView text;
    FirebaseAuth fAuth;
    DatabaseReference databaseReference;
    ProgressDialog pd;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.login_stu, container, false);
        mail = view.findViewById(R.id.Stemail);
        pass = view.findViewById(R.id.Stpass);
        text = view.findViewById(R.id.StfrgtPass);
        log = view.findViewById(R.id.StlogBtn);

        fAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Students");
        pd = new ProgressDialog(requireContext());

        log.setBackgroundColor(getResources().getColor(R.color.sky));
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String memail = mail.getText().toString();
                String mpass = pass.getText().toString();

                fAuth.signInWithEmailAndPassword(memail,mpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            pd.setMessage("Uploading...");
                            pd.show();
                            Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(requireContext(), StudentDash.class));// Close login activity
                        } else {
                            pd.dismiss();
                            Toast.makeText(requireContext(), "Authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return view;
    }
}
