package com.hoaittm.task_manager.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table (name = "tasks")
@Data
public class Task {
    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    private String id;
    private String title;
    private String description;
    private LocalDate due_date;
    private boolean complete;
    private int user_id;
}
