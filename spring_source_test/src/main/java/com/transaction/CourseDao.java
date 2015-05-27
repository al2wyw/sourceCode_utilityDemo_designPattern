package com.transaction;

/**
 * Created by apple on 1/05/2015.
 */
import com.model.Course;

public interface CourseDao extends TableDao {
    public Course getCourseById(String id);
    public void insertCourse(Course p);
}
