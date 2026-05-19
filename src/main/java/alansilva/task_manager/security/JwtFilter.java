package alansilva.task_manager.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        System.out.println("HEADER: " + header);

        if (header != null && header.startsWith("Bearer ")) {

            String token = header.substring(7);

            System.out.println("TOKEN: " + token);

            String email = jwtService.extractUsername(token);

            UserDetails userDetails =
                    customUserDetailsService.loadUserByUsername(email);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

            // ESSA LINHA É A MAIS IMPORTANTE
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);

            System.out.println(
                    SecurityContextHolder.getContext().getAuthentication()
            );
        }

        filterChain.doFilter(request, response);
    }
}