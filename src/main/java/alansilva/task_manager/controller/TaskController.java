package alansilva.task_manager.controller;


import alansilva.task_manager.dtos.request.TaskRequestDTO;
import alansilva.task_manager.dtos.response.MessageResponseDTO;
import alansilva.task_manager.dtos.response.TaskResponseDTO;
import alansilva.task_manager.entity.enums.TaskPriority;
import alansilva.task_manager.entity.enums.TaskStatus;
import alansilva.task_manager.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@Tag(
        name = "Tasks",
        description = "Endpoints para gerenciamento de tarefas"
)
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @Operation(
            summary = "Criar task",
            description = "Cria uma nova task para o usuário autenticado"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Task criada com sucesso"
    )
    public ResponseEntity<MessageResponseDTO> createTask(
            @RequestBody @Valid TaskRequestDTO dto
    ) {
        taskService.createTask(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new MessageResponseDTO(
                        "Task criada com sucesso!"
                ));
    }

    @GetMapping
    @Operation(
            summary = "Listar tasks",
            description = "Lista todas as tasks do usuário autenticado"
    )
    public ResponseEntity<Page<TaskResponseDTO>> getTasks(
            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size,

            @RequestParam(defaultValue = "createdAt")
            String sortBy,

            @RequestParam(defaultValue = "desc")
            String direction,

            @RequestParam(required = false)
            TaskStatus status,

            @RequestParam(required = false)
            TaskPriority priority
    ) {

        return ResponseEntity.ok(
                taskService.getMyTasks(
                        page,
                        size,
                        sortBy,
                        direction,
                        status,
                        priority
                )
        );
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Busca task por ID",
            description = "Retorna uma task específica do usuário autenticado"
    )
    public ResponseEntity<TaskResponseDTO> getTaskById(
            @PathVariable long id
    ) {
        return ResponseEntity.ok(
                taskService.getTaskById(id)
        );
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar task",
            description = "Atualiza uma task do usuário autenticado"
    )
    public ResponseEntity<TaskResponseDTO> updateTask(
            @PathVariable long id,
            @RequestBody @Valid TaskRequestDTO dto
    ) {
        return ResponseEntity.ok(
                taskService.updateTask(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deletar task",
            description = "Remove uma task do usuário autenticado"
    )
    public ResponseEntity<Void> deleteTask(
            @PathVariable long id
    ) {
        taskService.deleteTask(id);

        return ResponseEntity.noContent().build();
    }
}
