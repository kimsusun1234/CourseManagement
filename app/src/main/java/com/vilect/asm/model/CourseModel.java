package com.vilect.asm.model;

import android.os.Parcelable;

public class CourseModel {

    private String id;
    private String name;
    private String beginDate;
    private String endDate;

    public CourseModel ()
    {

    }

    public CourseModel(String id, String name, String beginDate, String endDate) {
        this.id = id;
        this.name = name;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
