package com.ama.aca_demo_task.controller;

import com.ama.aca_demo_task.model.Applicant;
import com.ama.aca_demo_task.service.ApplicantService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/applicants")
public class ApplicantController {
    ApplicantService applicantService;

    public ApplicantController(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    @GetMapping
    public ResponseEntity<List<Applicant>> getAllApplicants() {
        List<Applicant> applicants = applicantService.getApplicants();
        return new ResponseEntity<>(applicants, HttpStatus.OK);
    }

    @GetMapping({"/{applicantId}"})
    public ResponseEntity<Applicant> getApplicant(@PathVariable Long applicantId) {
        return new ResponseEntity<>(applicantService.getApplicantById(applicantId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Applicant> saveApplicant(@Valid @RequestBody Applicant applicant) {
        Applicant applicant1 = applicantService.insert(applicant);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("applicant", "/api/v1/applicant/" + applicant1.getId().toString());
        return new ResponseEntity<>(applicant1, httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping({"/{applicantId}"})
    public ResponseEntity<Applicant> updateApplicant(@PathVariable("applicantId") Long applicantId, @Valid @RequestBody Applicant applicant) {
        applicantService.updateApplicant(applicantId, applicant);
        return new ResponseEntity<>(applicantService.getApplicantById(applicantId), HttpStatus.OK);
    }

    @DeleteMapping({"/{applicantId}"})
    public ResponseEntity<Applicant> deleteApplicant(@PathVariable("applicantId") Long applicantId) {
        applicantService.deleteApplicant(applicantId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
