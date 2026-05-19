package alansilva.task_manager.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(

        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 3, message = "O campo nome deve ter acima de 3 caracteres.")
        String name,

        @Email(message = "O campo E-mail precisa ser válido")
        @NotBlank(message = "O campo E-mail é obrigatório")
        String email,

        @NotBlank(message = "Insira uma senha válida")
        @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres.")
        String password
) {
}
