package com.assignment.projectassignment3;

import java.io.Serializable;

public class Course implements Serializable {

    private int uniqueColumnId;
    private String courseId;
    private String courseName;
    private String prerequisite;
    private String term;
    private String imageUrl;

    public Course(int uniqueColumnId, String courseName, String courseId, String prerequisite, String term, String imageUrl) {
        this.uniqueColumnId = uniqueColumnId;
        this.courseName = courseName;
        this.courseId = courseId;
        this.prerequisite = prerequisite;
        this.term = term;
        this.imageUrl = imageUrl;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getPrerequisite() {
        return prerequisite;
    }

    public int getUniqueColumnId() {
        return uniqueColumnId;
    }

    public void setUniqueColumnId(int uniqueColumnId) {
        this.uniqueColumnId = uniqueColumnId;
    }


    public void setPrerequisite(String prerequisite) {
        this.prerequisite = prerequisite;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
