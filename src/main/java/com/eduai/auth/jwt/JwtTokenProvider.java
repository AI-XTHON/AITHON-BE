
package com.eduai.auth.jwt;

import com.eduai.auth.application.dto.TokenInfo;
import com.eduai.user.domain.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    private final SecretKey key;
    private final long accessTokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey,
                            @Value("${jwt.access-token-validity-in-seconds}") long accessTokenValidity,
                            @Value("${jwt.refresh-token-validity-in-seconds}") long refreshTokenValidity) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenValidityInMilliseconds = accessTokenValidity * 1000;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidity * 1000;
    }

    public TokenInfo generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        Date accessTokenExpiresIn = new Date(now + accessTokenValidityInMilliseconds);
        String email = ((DefaultOAuth2User) authentication.getPrincipal()).getAttribute("email");
        String accessToken = Jwts.builder()
                .subject(email)
                .claim("auth", authorities)
                .expiration(accessTokenExpiresIn)
                .signWith(key)
                .compact();

        Date refreshTokenExpiresIn = new Date(now + refreshTokenValidityInMilliseconds);
        String refreshToken = Jwts.builder()
                .expiration(refreshTokenExpiresIn)
                .signWith(key)
                .compact();

        return TokenInfo.of(accessToken, refreshToken);
    }

    public String createAccessToken(String email, Role role) {
        LocalDateTime dateTime = LocalDateTime.now().plusSeconds(accessTokenValidityInMilliseconds);
        Date expiration = Date.from(dateTime.toInstant(ZoneOffset.of("+09:00")));
        return Jwts.builder()
                .subject(email)
                .claim("auth", role)
                .expiration(expiration)
                .signWith(key)
                .compact();
    }

    public String createRefreshToken() {
        LocalDateTime dateTime = LocalDateTime.now().plusSeconds(refreshTokenValidityInMilliseconds);
        Date expiration = Date.from(dateTime.toInstant(ZoneOffset.of("+09:00")));

        return Jwts.builder()
                .expiration(expiration)
                .signWith(key)
                .compact();
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(claims.getSubject(), "", authorities);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parser().verifyWith(key).build().parseSignedClaims(accessToken).getPayload();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
