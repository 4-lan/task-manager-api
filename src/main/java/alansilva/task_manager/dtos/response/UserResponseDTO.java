package alansilva.task_manager.dtos.response;

import lombok.Builder;

@Builder
public record UserResponseDTO(
        Long id,
        String name,
        String email
) {

}
