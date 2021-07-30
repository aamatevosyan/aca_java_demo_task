package com.ama.aca_demo_task.controller;

import com.ama.aca_demo_task.model.Course;
import com.ama.aca_demo_task.service.CourseService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {
    CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses(@RequestParam(value = "name", required = false) String name) {
        List<Course> courses = courseService.getCourses(name);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping({"/{courseId}"})
    public ResponseEntity<Course> getCourse(@PathVariable Long courseId) {
        return new ResponseEntity<>(courseService.getCourseById(courseId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Course> saveCourse(@Valid @RequestBody Course course) {
        Course course1 = courseService.insert(course);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("course", "/api/v1/course/" + course1.getId().toString());
        return new ResponseEntity<>(course1, httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping({"/{courseId}"})
    public ResponseEntity<Course> updateCourse(@PathVariable("courseId") Long courseId, @Valid @RequestBody Course course) {
        courseService.updateCourse(courseId, course);
        return new ResponseEntity<>(courseService.getCourseById(courseId), HttpStatus.OK);
    }

    @DeleteMapping({"/{courseId}"})
    public ResponseEntity<Course> deleteCourse(@PathVariable("courseId") Long courseId) {
        courseService.deleteCourse(courseId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping({"/{courseId}/certificates"})
    public void getCertificates(@PathVariable("courseId") Long courseId, HttpServletResponse response) {
        courseService.getCertificates(courseId, response);
    }
}
