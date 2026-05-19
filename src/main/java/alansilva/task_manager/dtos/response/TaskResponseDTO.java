package alansilva.task_manager.dtos.response;

import alansilva.task_manager.entity.enums.TaskPriority;
import alansilva.task_manager.entity.enums.TaskStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TaskResponseDTO(
        Long id,
        String title,
        String description,
        TaskStatus status,
        TaskPriority priority,
        LocalDateTime createdAt
) {
}
