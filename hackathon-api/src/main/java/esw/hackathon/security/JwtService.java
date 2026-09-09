package esw.hackathon.security;

import esw.hackathon.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey chave;
    private final long expiracao;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expiracao) {

        this.chave = Keys.hmacShaKeyFor(
            secret.getBytes(StandardCharsets.UTF_8)
        );

        this.expiracao = expiracao;
    }

    public String gerar(Usuario usuario) {
        var agora = Instant.now();

        return Jwts.builder()
            .subject(usuario.getEmail())
            .claim("usuarioId", usuario.getId())
            .issuedAt(Date.from(agora))
            .expiration(
                Date.from(
                    agora.plusMillis(expiracao)
                )
            )
            .signWith(chave)
            .compact();
    }

    public String extrairEmail(String token) {
        return Jwts.parser()
            .verifyWith(chave)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
    }

    public boolean valido(String token) {
        try {
            Jwts.parser()
                .verifyWith(chave)
                .build()
                .parseSignedClaims(token);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}