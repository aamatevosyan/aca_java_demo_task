package com.ama.aca_demo_task.service;

import com.ama.aca_demo_task.exception.EntityNotFoundException;
import com.ama.aca_demo_task.model.Applicant;
import com.ama.aca_demo_task.repository.ApplicantRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicantServiceImpl implements ApplicantService {
    ApplicantRepository applicantRepository;

    public ApplicantServiceImpl(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }

    @Override
    public List<Applicant> getApplicants(String name, String email, Long courseId) {
        List<Applicant> applicants = new ArrayList<>();
        if (name == null && email == null && courseId == null) {
            applicantRepository.findAll().forEach(applicants::add);
        } else if (name == null && email == null) {
            applicants.addAll(applicantRepository.findByCourseId(courseId));
        } else if (name == null && courseId == null) {
            applicants.addAll(applicantRepository.findByEmail(email));
        } else if (email == null && courseId == null) {
            applicants.addAll(applicantRepository.findByName(name));
        } else if (name == null) {
            applicants.addAll(applicantRepository.findByEmailAndCourseId(email, courseId));
        } else if (email == null) {
            applicants.addAll(applicantRepository.findByNameAndCourseId(name, courseId));
        } else if (courseId == null) {
            applicants.addAll(applicantRepository.findByNameAndEmail(name, email));
        } else {
            applicants.addAll(applicantRepository.findByNameAndEmailAndCourseId(name, email, courseId));
        }

        return applicants;
    }

    @Override
    public Applicant getApplicantById(Long id) {
        return applicantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Applicant.class, "id", id.toString()));
    }

    @Override
    public Applicant insert(Applicant applicant) {
        return applicantRepository.save(applicant);
    }

    @Override
    public void updateApplicant(Long id, Applicant applicant) {
        Applicant oldApplicant = applicantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Applicant.class, "id", id.toString()));

        oldApplicant.setName(applicant.getName());
        oldApplicant.setEmail(applicant.getEmail());
        oldApplicant.setPhone(applicant.getPhone());
        oldApplicant.setAddress(applicant.getAddress());
        oldApplicant.setStatus(applicant.getStatus());

        applicantRepository.save(oldApplicant);
    }

    @Override
    public void deleteApplicant(Long todoId) {
        applicantRepository.deleteById(todoId);
    }
}
