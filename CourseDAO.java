package com.lms.dao;

import com.lms.entity.Course;
import com.lms.entity.CourseContent;
import com.lms.util.DataInitializer;
import com.lms.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class CourseDAO {
    public List<Course> getAllCourses() {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        if (factory != null) {
            try (Session session = factory.openSession()) {
                String hql = "FROM Course c ORDER BY c.courseId ASC";
                Query<Course> query = session.createQuery(hql, Course.class);
                List<Course> courses = query.getResultList();
                if (courses != null && !courses.isEmpty()) {
                    return courses;
                }
            } catch (Exception e) {
                System.err.println("Error fetching courses from Oracle DB, using fallback list: " + e.getMessage());
            }
        }
        return DataInitializer.getFallbackCourses();
    }

    public Course getCourseById(int courseId) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        if (factory != null) {
            try (Session session = factory.openSession()) {
                String hql = "FROM Course c LEFT JOIN FETCH c.contents WHERE c.courseId = :courseId";
                Query<Course> query = session.createQuery(hql, Course.class);
                query.setParameter("courseId", courseId);
                Course course = query.uniqueResult();
                if (course != null) {
                    return course;
                }
            } catch (Exception e) {
                System.err.println("Error fetching course details from Oracle DB, using fallback: " + e.getMessage());
            }
        }

        for (Course c : DataInitializer.getFallbackCourses()) {
            if (c.getCourseId() == courseId) {
                return c;
            }
        }
        return null;
    }

    public boolean addCourseContent(int courseId, String topicTitle, String topicDescription) {
        if (topicTitle == null || topicTitle.trim().isEmpty() ||
            topicDescription == null || topicDescription.trim().isEmpty()) {
            return false;
        }

        String cleanTitle = topicTitle.trim();
        String cleanDesc = topicDescription.trim();
        boolean savedInDb = false;

        SessionFactory factory = HibernateUtil.getSessionFactory();
        if (factory != null) {
            Transaction tx = null;
            try (Session session = factory.openSession()) {
                tx = session.beginTransaction();

                Course course = session.get(Course.class, courseId);
                if (course != null) {
                    Integer maxId = session.createQuery("SELECT COALESCE(MAX(cc.contentId), 0) FROM CourseContent cc", Integer.class)
                                           .uniqueResult();
                    int nextContentId = (maxId != null ? maxId : 0) + 1;

                    CourseContent newContent = new CourseContent(nextContentId, course, cleanTitle, cleanDesc);
                    session.persist(newContent);
                    course.getContents().add(newContent);

                    tx.commit();
                    savedInDb = true;
                }
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                System.err.println("Error saving new topic to Oracle DB, saving to fallback memory: " + e.getMessage());
            }
        }

        // Also update fallback list so offline mode reflects newly added topics immediately
        int maxFallbackId = 100;
        for (Course c : DataInitializer.getFallbackCourses()) {
            for (CourseContent cc : c.getContents()) {
                if (cc.getContentId() > maxFallbackId) {
                    maxFallbackId = cc.getContentId();
                }
            }
        }
        for (Course c : DataInitializer.getFallbackCourses()) {
            if (c.getCourseId() == courseId) {
                if (!savedInDb) {
                    c.getContents().add(new CourseContent(maxFallbackId + 1, c, cleanTitle, cleanDesc));
                }
                return true;
            }
        }

        return savedInDb;
    }
}
