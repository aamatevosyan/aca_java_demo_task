package com.ama.aca_demo_task.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    Long id;

    @Column(nullable = false)
    @NotNull
    String name;

    @Column(nullable = false, name = "start_date")
    @NotNull
    Date startDate;

    @Column(nullable = false, name = "end_date")
    @NotNull
    Date endDate;

    @Column(nullable = false, name = "teacher_name")
    @NotNull
    String teacherName;

    @Column
    String description;
}
