package com.example.cmplive.Admin.Faculty;

import android.widget.Spinner;

public class TeacherData {
    private String name, email, post, pass, img,department;

    public TeacherData() {

    }



    public TeacherData(String name, String email, String post, String pass, String img,  String department) {
        this.name = name;
        this.email = email;
        this.post = post;
        this.pass = pass;
        this.img = img;
        this.department = department;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }


}
