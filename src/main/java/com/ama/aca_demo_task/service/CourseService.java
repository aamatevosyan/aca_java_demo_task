package com.ama.aca_demo_task.service;

import com.ama.aca_demo_task.model.Course;

import javax.persistence.OneToMany;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface CourseService {
    List<Course> getCourses(String name);

    Course getCourseById(Long id);

    Course insert(Course course);

    void updateCourse(Long id, Course course);

    void deleteCourse(Long courseId);

    void getCertificates(Long id, HttpServletResponse response);
}
