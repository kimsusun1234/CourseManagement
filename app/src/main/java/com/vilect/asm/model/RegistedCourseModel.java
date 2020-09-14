package com.vilect.asm.model;

public class RegistedCourseModel {

    private int id;
    private int studentId;
    private int courseId;
    private String regDate;

    public RegistedCourseModel(int id, int studentId, int courseId, String regDate) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.regDate = regDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }
}
