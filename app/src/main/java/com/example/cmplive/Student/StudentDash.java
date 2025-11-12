package com.example.cmplive.Student;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cmplive.Chatting.Chat;
import com.example.cmplive.Chatting.ChatSplash;
import com.example.cmplive.Contact;
import com.example.cmplive.R;
import com.example.cmplive.Student.Notice.NoticeStu;
import com.example.cmplive.Student.Schlors.StuClassm;
import com.example.cmplive.Student.Teachers.StuFclts;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class StudentDash extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    StuHome stuHome = new StuHome();
    StuAttend stuAttend = new StuAttend();
    StuClassm stuClassm = new StuClassm();
    StuFclts fclts = new StuFclts();
    NoticeStu stNotice = new NoticeStu();
    Contact cntcs = new Contact();
    Chat chat = new Chat();
    BottomNavigationView bottomNavigationView;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_dash);

        bottomNavigationView = findViewById(R.id.BottomNavigation);
        navigationView = findViewById(R.id.navdrwr);

        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, stuHome).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.Home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, stuHome).commit();
                        return true;

                    case R.id.attend:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, stuAttend).commit();
                        return true;
                    case R.id.chat:
                        Intent intent = new Intent(StudentDash.this, ChatSplash.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });

    }

    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.classm:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, stuClassm).commit();
                break;
            case R.id.fclts:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fclts).commit();
                break;
            case R.id.notice:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,stNotice).commit();
                break;
            case R.id.cnt:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, cntcs).commit();
                break;

        }
        return false;
    }
}