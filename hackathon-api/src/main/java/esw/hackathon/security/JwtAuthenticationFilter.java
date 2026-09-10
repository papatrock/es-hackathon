package esw.hackathon.security;

import esw.hackathon.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import esw.hackathon.model.Organizador;
import esw.hackathon.model.Jurado;
import esw.hackathon.model.Participante;
import esw.hackathon.model.Mentor;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {

    private final JwtService jwt;
    private final UsuarioRepository usuarios;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        var authorization =
            request.getHeader("Authorization");

        if (authorization == null
                || !authorization.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        var token = authorization.substring(7);

        if (!jwt.valido(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        var email = jwt.extrairEmail(token);

        var usuario = usuarios.findByEmail(email)
            .orElse(null);

        if (usuario != null
                && SecurityContextHolder
                    .getContext()
                    .getAuthentication() == null) {
            
            List<SimpleGrantedAuthority> authorities = switch (usuario) {
                case Organizador o ->
                    List.of(new SimpleGrantedAuthority("ROLE_ORGANIZADOR"));

                case Jurado j ->
                    List.of(new SimpleGrantedAuthority("ROLE_JURADO"));

                case Participante p ->
                    List.of(new SimpleGrantedAuthority("ROLE_PARTICIPANTE"));
                
                case Mentor m ->
                    List.of(new SimpleGrantedAuthority("ROLE_MENTOR"));

                default ->
                    List.of();
            };

            var authentication =
                new UsernamePasswordAuthenticationToken(
                    usuario,
                    null,
                    authorities
                );

            SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}