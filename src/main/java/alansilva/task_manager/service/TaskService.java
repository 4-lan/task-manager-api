package alansilva.task_manager.service;

import alansilva.task_manager.dtos.request.TaskRequestDTO;
import alansilva.task_manager.dtos.response.TaskResponseDTO;
import alansilva.task_manager.entity.Task;
import alansilva.task_manager.entity.User;
import alansilva.task_manager.entity.enums.TaskPriority;
import alansilva.task_manager.entity.enums.TaskStatus;
import alansilva.task_manager.exceptions.ResourceNotFoundException;
import alansilva.task_manager.exceptions.UserNotFoundException;
import alansilva.task_manager.repositories.TaskRepository;
import alansilva.task_manager.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public void createTask(TaskRequestDTO dto) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("Usuario não encontrado")
                );

        Task task = Task.builder()
                .title(dto.title())
                .description(dto.description())
                .status(dto.status())
                .priority(dto.priority())
                .user(user)
                .build();

        taskRepository.save(task);
    }

    public Page<TaskResponseDTO> getMyTasks(
            int page,
            int size,

            String sortBy,
            String direction,

            TaskStatus status,
            TaskPriority priority
    ) {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        String email = authentication.getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("Usuário não encontrado!")
                );

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        direction.equalsIgnoreCase("desc")
                                ? Sort.Direction.DESC
                                : Sort.Direction.ASC,
                        sortBy
                );

        Page<Task> tasks;

        if (status != null && priority != null) {
            tasks = taskRepository
                    .findByUserAndStatusAndPriority(
                            user,
                            status,
                            priority,
                            pageable
                    );
        } else {
            tasks = taskRepository.findByUser(
                    user,
                    pageable
            );
            
        }
        return tasks.map(task -> TaskResponseDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .createdAt(task.getCreatedAt())
                .build()
        );
    }

    public TaskResponseDTO getTaskById(Long id) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("Usuário não encontrado")
                );

        Task task = taskRepository
                .findByIdAndUser(id, user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task não encontrada")
                );

        return TaskResponseDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .createdAt(task.getCreatedAt())
                .build();
    }

    public TaskResponseDTO updateTask(
            Long id,
            TaskRequestDTO dto
    ) {
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow();

        Task task = taskRepository
                .findByIdAndUser(id, user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task não encontrada!")
                );

        task.setTitle(dto.title());
        task.setDescription(dto.description());
        task.setStatus(dto.status());
        task.setPriority(dto.priority());

        Task updateTask = taskRepository.save(task);

        return TaskResponseDTO.builder()
                .id(updateTask.getId())
                .title(updateTask.getTitle())
                .description(updateTask.getDescription())
                .status(updateTask.getStatus())
                .priority(updateTask.getPriority())
                .createdAt(updateTask.getCreatedAt())
                .build();
    }

    public void deleteTask(Long id) {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuário não encontrado!")
                );

        Task task = taskRepository
                .findByIdAndUser(id, user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task não encontrada!")
                );
        taskRepository.delete(task);
    }
}
