package alansilva.task_manager.entity;

import alansilva.task_manager.entity.enums.TaskPriority;
import alansilva.task_manager.entity.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String description;

    @Column
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;



}
