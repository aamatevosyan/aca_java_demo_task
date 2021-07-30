package com.ama.aca_demo_task.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "applicants")
public class Applicant {
    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    Long id;

    @Column(nullable = false)
    @NotNull
    String name;

    @NotNull
    @Column(nullable = false)
    @Email(regexp = ".*@.*\\..*", message = "Email should be valid")
    String email;

    @NotNull
    @Pattern(regexp="(^$|[0-9]{11})")
    @Column(nullable = false)
    String phone;

    @NotNull
    @Column(nullable = false)
    String address;

    @NotNull
    @Column(nullable = false)
    ApplicantStatus status;

    @NotNull
    @Column(nullable = false, name = "course_id")
    Long courseId;
}
