package com.lms.entity;

import jakarta.persistence.*;
@Entity
@Table(name = "COURSE_CONTENT")
public class CourseContent {

    @Id
    @Column(name = "content_id")
    private int contentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "topic_title", length = 150, nullable = false)
    private String topicTitle;

    @Column(name = "topic_description", length = 1000, nullable = false)
    private String topicDescription;

    public CourseContent() {
    }

    public CourseContent(int contentId, Course course, String topicTitle, String topicDescription) {
        this.contentId = contentId;
        this.course = course;
        this.topicTitle = topicTitle;
        this.topicDescription = topicDescription;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getTopicTitle() {
        return topicTitle;
    }

    public void setTopicTitle(String topicTitle) {
        this.topicTitle = topicTitle;
    }

    public String getTopicDescription() {
        return topicDescription;
    }

    public void setTopicDescription(String topicDescription) {
        this.topicDescription = topicDescription;
    }
}
