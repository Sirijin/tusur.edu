package ru.tusur.edu.security.authentication;


import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.tusur.edu.security.entity.Role;
import ru.tusur.edu.security.entity.User;
import ru.tusur.edu.security.service.RoleService;
import ru.tusur.edu.security.type.RoleSet;

import java.security.Key;
import java.security.Principal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expirationInMillis}")
    private Long expirationInMillis;

    private static final String HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final RoleService roleService;

    public String createToken(User user) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(user.getId()));
        claims.put("role", Set.of(user.getRole()));
        claims.put("username", user.getUsername());

        return Jwts
                .builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationInMillis))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts
                    .parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("JWT has expired");
        } catch (MalformedJwtException e) {
            throw new RuntimeException("JWT has invalid structure");
        } catch (SignatureException e) {
            throw new RuntimeException("SignatureException has been thrown");
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("IllegalArgumentException has been thrown");
        }
    }

    public Optional<Authentication> getAuthentication(String token) {
        TokenInfo tokenInfo = getTokenInfo(token);
        Principal principal = new CustomPrincipal(tokenInfo.getId(), tokenInfo.getUsername());
        return Optional.of(new UsernamePasswordAuthenticationToken(principal, "", Set.of(tokenInfo.getRole())));
    }

    public Optional<String> getTokenFromHeader(HttpServletRequest request) {
        return request.getHeader(HEADER).substring(BEARER_PREFIX.length()).describeConstable();
    }

    private TokenInfo getTokenInfo(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return TokenInfo
                .builder()
                .id(Long.valueOf(claims.getSubject()))
                .username(claims.get("username", String.class))
                .startDate(claims.getIssuedAt())
                .expiresAt(claims.getExpiration())
                .role(getRoleFromClaims(claims))
                .build();
    }

    @SuppressWarnings("unchecked")
    private Role getRoleFromClaims(Claims claims) {
        List<Map<String, Object>> roles = claims.get("role", List.class);

        return roles.stream()
                .map(roleMap -> (String) roleMap.get("name"))
                .map(RoleSet::valueOf)
                .map(roleService::findByName)
                .filter(Objects::nonNull)
                .flatMap(Optional::stream)
                .findFirst()
                .orElse(null);
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }
}
