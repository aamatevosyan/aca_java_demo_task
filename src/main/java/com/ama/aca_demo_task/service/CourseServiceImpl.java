package com.ama.aca_demo_task.service;

import com.ama.aca_demo_task.exception.EntityNotFoundException;
import com.ama.aca_demo_task.model.Course;
import com.ama.aca_demo_task.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> getCourses() {
        List<Course> courses = new ArrayList<>();
        courseRepository.findAll().forEach(courses::add);
        return courses;
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Course.class, "id", id.toString()));
    }

    @Override
    public Course insert(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public void updateCourse(Long id, Course course) {
        Course oldCourse = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Course.class, "id", id.toString()));

        oldCourse.setName(course.getName());
        oldCourse.setStartDate(course.getStartDate());
        oldCourse.setEndDate(course.getEndDate());
        oldCourse.setTeacherName(course.getTeacherName());
        oldCourse.setDescription(course.getDescription());

        courseRepository.save(oldCourse);
    }

    @Override
    public void deleteCourse(Long todoId) {
        courseRepository.deleteById(todoId);
    }
}
