// package com.example.mytodo.auth.jwt;

// import java.time.Instant;
// import java.time.temporal.ChronoUnit;
// import java.util.stream.Collectors;

// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.oauth2.jwt.JwtClaimsSet;
// import org.springframework.security.oauth2.jwt.JwtEncoder;
// import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
// import org.springframework.stereotype.Service;

// import lombok.AllArgsConstructor;

// @Service
// @AllArgsConstructor // 생성자 주입을 통해 JwtEncoder 인스턴스를 주입 JwtEncoder는 JWT를
// 인코딩(생성)하는데 사용된다
// public class JwtTokenService {

// private final JwtEncoder jwtEncoder;

// // 인증 객체를 받아 JWT토큰을 생성하는 메서드
// public String generateToken(Authentication authentication) {
// // 인증된 사용자의 권한을 스트림으로 변환하여 공백으로 구분된 문자열로 결합
// // ex: "ROLE_USER ROLE_ADMIN"
// var scope = authentication
// .getAuthorities()
// .stream()
// .map(GrantedAuthority::getAuthority)
// .collect(Collectors.joining(" "));

// // JWT 클레임 세트를 생성 클레임 세트는 JWT의 payload에 해당된다
// var claims = JwtClaimsSet.builder()
// .issuer("self")// 발행자
// .issuedAt(Instant.now())// 발행 시간
// .expiresAt(Instant.now().plus(90, ChronoUnit.MINUTES))// 만료 시간(발행 후 90분)
// .subject(authentication.getName())// 주제(여기서는 사용자 이름)
// .claim("scope", scope)// 추가 클레임으로 사용자의 권한을 포함
// .build();

// // JwtEncoder를 사용하여 JWT를 인코딩하고 토큰 값을 반환
// return this.jwtEncoder
// .encode(JwtEncoderParameters.from(claims))
// .getTokenValue();
// }

// }
