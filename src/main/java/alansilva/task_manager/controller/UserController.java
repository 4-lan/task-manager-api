package alansilva.task_manager.controller;


import alansilva.task_manager.dtos.request.UserRequestDTO;
import alansilva.task_manager.dtos.request.UserUpdateDTO;
import alansilva.task_manager.dtos.response.MessageResponseDTO;
import alansilva.task_manager.dtos.response.UserResponseDTO;
import alansilva.task_manager.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(
        name = "Users",
        description = "Endpoints para gerenciamento de usuários"
)
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(
            summary = "Criar usuário",
            description = "Cria um novo usuário"
    )
    public ResponseEntity<MessageResponseDTO> createUser(@RequestBody @Valid UserRequestDTO dto) {
        userService.createUser(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new MessageResponseDTO("Usuário criado com sucesso!"));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getMe() {
        return ResponseEntity.ok(
                userService.getMe()
        );
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponseDTO> updateMe(
            @RequestBody @Valid UserUpdateDTO dto
    ) {
        return ResponseEntity.ok(
                userService.updateMe(dto)
        );
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMe() {
        userService.deleteMe();
        return ResponseEntity.noContent().build();
    }
}
