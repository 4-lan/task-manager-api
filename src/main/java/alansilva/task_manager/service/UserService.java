package alansilva.task_manager.service;

import alansilva.task_manager.dtos.request.LoginRequestDTO;
import alansilva.task_manager.dtos.request.UserRequestDTO;
import alansilva.task_manager.dtos.request.UserUpdateDTO;
import alansilva.task_manager.dtos.response.LoginResponseDTO;
import alansilva.task_manager.dtos.response.UserResponseDTO;
import alansilva.task_manager.entity.User;
import alansilva.task_manager.exceptions.InvalidCredentialsException;
import alansilva.task_manager.exceptions.ResourceNotFoundException;
import alansilva.task_manager.repositories.UserRepository;
import alansilva.task_manager.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public void createUser(UserRequestDTO dto) {

        User user = User.builder()
                .name(dto.name())
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .build();

        userRepository.save(user);
    }

    public LoginResponseDTO login(LoginRequestDTO dto) {

        User user = userRepository.findByEmail(dto.email())
                .orElseThrow(() ->
                        new InvalidCredentialsException("Usuário ou senha inválidos"));

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new InvalidCredentialsException("Usuário ou senha inválidos");
        }

        String token = jwtService.generateToken(user);

        return new LoginResponseDTO(token);
    }

    public UserResponseDTO getMe() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuário não encontrado")
                );

        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public UserResponseDTO updateMe(UserUpdateDTO dto) {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuário não encontrado")
                );

        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));

        User updatedUser = userRepository.save(user);

        return UserResponseDTO.builder()
                .id(updatedUser.getId())
                .name(updatedUser.getName())
                .email(updatedUser.getEmail())
                .build();
    }

    public void deleteMe() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuário não encontrado")
                );

        userRepository.delete(user);
    }
}