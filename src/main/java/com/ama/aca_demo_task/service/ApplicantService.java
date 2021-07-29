package com.ama.aca_demo_task.service;

import com.ama.aca_demo_task.model.Applicant;

import java.util.List;

public interface ApplicantService {
    List<Applicant> getApplicants();

    Applicant getApplicantById(Long id);

    Applicant insert(Applicant applicant);

    void updateApplicant(Long id, Applicant applicant);

    void deleteApplicant(Long applicantId);
}
