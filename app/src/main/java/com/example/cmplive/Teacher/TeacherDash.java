package com.example.cmplive.Teacher;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cmplive.Admin.Notice.AdNotice;
import com.example.cmplive.Chatting.ChatSplash;
import com.example.cmplive.Contact;
import com.example.cmplive.R;
import com.example.cmplive.Teacher.Faculties.Faculty;
import com.example.cmplive.Teacher.Students.Scholar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TeacherDash extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
 TeachHome teachHome = new TeachHome();
 Contact contact = new Contact();
 AdNotice adNotice = new AdNotice();
 MarkAttendance attendance = new MarkAttendance();
 Faculty faculty = new Faculty();
 Scholar scholar = new Scholar();
 BottomNavigationView bottomNavigationView;
 NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_teacher_dash);

        bottomNavigationView = findViewById(R.id.BottomNavigation);
        navigationView = findViewById(R.id.navdrwr);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,teachHome).commit();

        View headerView = navigationView.getHeaderView(0);
        TextView username = headerView.findViewById(R.id.HeaderText);

        String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference teachersRef = FirebaseDatabase.getInstance().getReference().child("Teachers");
        teachersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot dataSnapshot = snapshot.child(userUid);
                String name = dataSnapshot.child("name").getValue(String.class);

                // Check if the name is not null before using it
                if (name != null) {
                    username.setText(name);
                } else {
                    System.out.println("Name is null for this teacher.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        getSupportFragmentManager().beginTransaction().replace(R.id.container,teachHome).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.Home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,teachHome).commit();
                        break;
                    case R.id.attend:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,attendance).commit();
                        break;
                    case R.id.chat:
                        Intent intent = new Intent(TeacherDash.this, ChatSplash.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.stu:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,scholar).commit();
                return true;
            case R.id.fclts:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,faculty).commit();
                return true;
            case R.id.notice:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,adNotice).commit();
                return true;
            case R.id.cnt:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,contact).commit();
                return true;
        }
        return false;
    }


}