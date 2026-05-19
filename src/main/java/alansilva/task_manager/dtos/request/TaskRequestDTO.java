package alansilva.task_manager.dtos.request;

import alansilva.task_manager.entity.enums.TaskPriority;
import alansilva.task_manager.entity.enums.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskRequestDTO(

        @NotBlank
        @Schema(description = "Título da task")
        String title,

        @Schema(description = "Descrição da task")
        String description,

        @NotNull
        @Schema(description = "Status da task")
        TaskStatus status,

        @NotNull
        @Schema(description = "Prioridade da task")
        TaskPriority priority
) {
}
