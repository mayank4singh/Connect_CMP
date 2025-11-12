package com.example.cmplive.Admin.AdminWork;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cmplive.Admin.Faculty.AdFaculti;
import com.example.cmplive.Admin.Notice.AdNotice;
import com.example.cmplive.Admin.Student.AdStudent;
import com.example.cmplive.Subjects.AddSubject;
import com.example.cmplive.Chatting.ChatSplash;
import com.example.cmplive.Contact;
import com.example.cmplive.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class AdminDash extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


 AdminHome adminHome = new AdminHome();
 AdFaculti adFaculti = new AdFaculti();
 AdNotice adNotice = new AdNotice();
 Adcrte adcrte = new Adcrte();
 AssignSubject assignSubject = new AssignSubject();
 AdStudent adStudent = new AdStudent();
 Contact contact = new Contact();
 BottomNavigationView bottomNavigationView;
 NavigationView navigationView;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);



        bottomNavigationView = findViewById(R.id.BottomNavigation);
        navigationView = findViewById(R.id.navdrwr);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.container,adminHome).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.Home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,adminHome).commit();
                        return true;

                    case R.id.chat:
                        Intent intent = new Intent(AdminDash.this, ChatSplash.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nwadmn:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,adcrte).commit();
                break;
            case R.id.fclts:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,adFaculti).commit();
                break;
            case R.id.subject:
                Intent intent = new Intent(AdminDash.this, AddSubject.class);
                startActivity(intent);
                break;
            case R.id.assignsubject:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,assignSubject).commit();
                break;
            case R.id.stu:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,adStudent).commit();
                break;
            case R.id.Notice:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,adNotice).commit();
                break;
            case R.id.cnt:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,contact).commit();
                break;


        }
        return false;
    }

}