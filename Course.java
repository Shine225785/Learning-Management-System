package com.lms.entity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "COURSES")
public class Course {

    @Id
    @Column(name = "course_id")
    private int courseId;

    @Column(name = "course_name", length = 100, nullable = false)
    private String courseName;

    @Column(name = "course_description", length = 500, nullable = false)
    private String courseDescription;

    @Column(name = "course_duration", length = 50, nullable = false)
    private String courseDuration;

    @Column(name = "course_level", length = 50, nullable = false)
    private String courseLevel;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderBy("contentId ASC")
    private List<CourseContent> contents = new ArrayList<>();

    public Course() {
    }

    public Course(int courseId, String courseName, String courseDescription, String courseDuration, String courseLevel) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.courseDuration = courseDuration;
        this.courseLevel = courseLevel;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getCourseDuration() {
        return courseDuration;
    }

    public void setCourseDuration(String courseDuration) {
        this.courseDuration = courseDuration;
    }

    public String getCourseLevel() {
        return courseLevel;
    }

    public void setCourseLevel(String courseLevel) {
        this.courseLevel = courseLevel;
    }

    public List<CourseContent> getContents() {
        return contents;
    }

    public void setContents(List<CourseContent> contents) {
        this.contents = contents;
    }
}
