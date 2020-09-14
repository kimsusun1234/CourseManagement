package com.vilect.asm.model;

import java.util.ArrayList;

public class SemesterModel {
    private String id;
    private ArrayList<CourseModel> courses = new ArrayList<>();

    public SemesterModel()
    {

    }

    public SemesterModel(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<CourseModel> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<CourseModel> courses) {
        this.courses = courses;
    }
}
