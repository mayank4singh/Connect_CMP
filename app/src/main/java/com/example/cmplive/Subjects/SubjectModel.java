package com.example.cmplive.Subjects;

public class SubjectModel {
    private String subjectCode;
    private String name;
    private String semester;
    private String department;
    private String assignedTeacher;

    public SubjectModel(){}

    public SubjectModel(String subjectCode, String name, String semester, String department, String assignedTeacher){
        this.subjectCode = subjectCode;
        this.name = name;
        this.semester = semester;
        this.department = department;
        this.assignedTeacher = assignedTeacher;

    }

    public String getSubjectCode() {
        return subjectCode;
    }
    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getSemester(){
        return semester;
    }
    public void setSemester(String semester){
        this.semester = semester;
    }
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
    public String getAssignedTeacher() {
        return assignedTeacher;
    }

    public void setAssignedTeacher(String assignedTeacher) {
        this.assignedTeacher = assignedTeacher;
    }
}
