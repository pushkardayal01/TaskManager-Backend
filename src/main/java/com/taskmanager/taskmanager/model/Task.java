package com.taskmanager.taskmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long taskId;

    private String title;

    private boolean isCompleted;

    private String status;


    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
