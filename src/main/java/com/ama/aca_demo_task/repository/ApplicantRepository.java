package com.ama.aca_demo_task.repository;

import com.ama.aca_demo_task.model.Applicant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicantRepository extends CrudRepository<Applicant, Long> {
    List<Applicant> findByName(String name);
    List<Applicant> findByEmail(String email);
    List<Applicant> findByCourseId(Long courseId);
    List<Applicant> findByNameAndEmail(String name, String email);
    List<Applicant> findByNameAndCourseId(String name, Long courseId);
    List<Applicant> findByEmailAndCourseId(String email, Long courseId);
    List<Applicant> findByNameAndEmailAndCourseId(String name, String email, Long courseId);
}
