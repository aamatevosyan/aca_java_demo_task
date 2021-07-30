package com.ama.aca_demo_task.repository;

import com.ama.aca_demo_task.model.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {
    List<Course> findByNameIgnoreCaseContaining(String name);
}