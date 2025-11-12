package com.example.cmplive.Admin.Student;

public class StudentData {


        private String name, email, pass, img,department;
     private boolean selected; // Add selected property

    public StudentData() {

        }




    public StudentData( String name, String email, String pass, String img, String department) {
            this.name = name;
            this.email = email;
            this.pass = pass;
            this.img = img;
            this.department = department;
            this.selected = false; // Initialize selected state to false by default

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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}

