package com.example.mytodo.auth.jwt_jjwt;

import java.security.KeyPair;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JwtTokenService {

        @Autowired
        private final KeyPair keyPair;

        public Token generateAccessToken(String username) {
                long expirationTime = TimeUnit.MINUTES.toMillis(15); // 15분
                String accessToken = Jwts.builder()
                                .setSubject(username)
                                .setIssuedAt(new Date())
                                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                                .signWith(keyPair.getPrivate(), SignatureAlgorithm.RS256) // 개인키로 서명
                                .compact();

                return Token.builder()
                                .grantType("Bearer")
                                .accessToken(accessToken)
                                .build();
        }

        public Token generateRefreshToken(String username) {
                long expirationTime = TimeUnit.HOURS.toMillis(15); // 8시간
                String refreshToken = Jwts.builder()
                                .setSubject(username)
                                .setIssuedAt(new Date())
                                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                                .signWith(keyPair.getPrivate(), SignatureAlgorithm.RS256) // 개인키로 서명
                                .compact();

                return Token.builder()
                                .grantType("Bearer")
                                .refreshToken(refreshToken)
                                .build();
        }

        public String getUsernameFromToken(String token) {
                Claims claims = Jwts.parserBuilder()
                                .setSigningKey(keyPair.getPublic()) // 공개키로 검증
                                .build()
                                .parseClaimsJws(token)
                                .getBody();

                return claims.getSubject();
        }

        public boolean validateRefreshToken(String token) {
                try {
                        Jws<Claims> claims = Jwts.parserBuilder()
                                        .setSigningKey(keyPair.getPublic()) // 공개키로 검증
                                        .build()
                                        .parseClaimsJws(token);

                        // 만료 날짜 검사
                        return !claims.getBody().getExpiration().before(new Date());
                } catch (SignatureException ex) {
                        // 서명 검증 실패
                        return false;
                } catch (Exception e) {
                        // 다른 모든 예외 처리
                        return false;
                }
        }

}
