package com.vilect.asm.model;

import java.util.ArrayList;

public class StudentModel
{
    private String id;
    private String password;
    private String name;
    private String birthday;
    private ArrayList<SemesterModel> regCourses = new ArrayList<>();

    public StudentModel()
    {

    }

    public StudentModel(String id, String password, String name, String birthday, ArrayList<SemesterModel> regCourses) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.birthday = birthday;
        this.regCourses = regCourses;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public ArrayList<SemesterModel> getRegCourses() {
        return regCourses;
    }

    public void setRegCourses(ArrayList<SemesterModel> regCourses) {
        this.regCourses = regCourses;
    }
}
