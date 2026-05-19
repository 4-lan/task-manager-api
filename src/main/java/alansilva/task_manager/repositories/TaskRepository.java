package alansilva.task_manager.repositories;

import alansilva.task_manager.entity.Task;
import alansilva.task_manager.entity.User;
import alansilva.task_manager.entity.enums.TaskPriority;
import alansilva.task_manager.entity.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findByUser(
            User user,
            Pageable pageable
    );

    Optional<Task> findByIdAndUser(
            Long id,
            User user
    );

    Page<Task> findByUserAndStatusAndPriority(
            User user,
            TaskStatus status,
            TaskPriority priority,
            Pageable pageable
    );
}

