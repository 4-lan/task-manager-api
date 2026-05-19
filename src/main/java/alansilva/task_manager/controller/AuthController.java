package alansilva.task_manager.controller;

import alansilva.task_manager.dtos.request.LoginRequestDTO;
import alansilva.task_manager.dtos.response.LoginResponseDTO;
import alansilva.task_manager.entity.User;
import alansilva.task_manager.exceptions.InvalidCredentialsException;
import alansilva.task_manager.exceptions.UserNotFoundException;
import alansilva.task_manager.repositories.UserRepository;
import alansilva.task_manager.security.JwtService;
import alansilva.task_manager.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(
        name = "Autenticação de usuário",
        description = "Endpoint para autenticação de usuário"
)
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @PostMapping("/login")
    @Operation(
            summary = "Autenticar usuario",
            description = "Autentica o usuário"
    )
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody @Valid LoginRequestDTO dto
    ) {

        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.email(),
                            dto.password()
                    )
            );
        } catch (BadCredentialsException ex) {
            throw new InvalidCredentialsException(
                    "Usuário ou senha inválidos!"
            );
        }

        User user = userRepository
                .findByEmail(dto.email())
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "Usuário não encontrado!"
                        )
                );

        String token = jwtService.generateToken(user);

        return ResponseEntity.ok(
                new LoginResponseDTO(token)
        );
    }
}
