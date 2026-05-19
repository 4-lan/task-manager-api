package alansilva.task_manager.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserUpdateDTO(

        @NotBlank(message = "Nome obrigatório")
        String name,

        @NotBlank(message = "Email obrigatório")
        @Email(message = "Email inválido")
        String email,

        @NotBlank(message = "Senha obrigatória")
        String password
) {
}
