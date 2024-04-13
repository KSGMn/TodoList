// package com.example.mytodo.auth.jwt;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.AuthenticationManager;
// import
// org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.AuthenticationException;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RestController;

// import lombok.AllArgsConstructor;

// @RestController
// @AllArgsConstructor // 생성자를 통한 의존성 주입. JwtTokenService와
// AuthenticationManager를 주입받음.
// public class JwtController {

// private final Logger logger = LoggerFactory.getLogger(getClass());

// private final JwtTokenService tokenService;
// private final AuthenticationManager authenticationManager;

// // /authenticate 경로로 POST 요청이 들어올 때 실행되는 메서드.
// @PostMapping("/authenticate")
// public ResponseEntity<?> generateToken(
// @RequestBody JwtTokenRequest jwtTokenRequest) {
// try {
// // 사용자가 제공한 사용자 이름과 비밀번호를 기반으로 UsernamePasswordAuthenticationToken 객체 생성.
// var authenticationToken = new UsernamePasswordAuthenticationToken(
// jwtTokenRequest.username(),
// jwtTokenRequest.password());

// logger.info("실행됨");

// // AuthenticationManager를 사용하여 인증을 수행. 인증 성공 시 Authentication 객체 반환.
// var authentication = authenticationManager.authenticate(authenticationToken);

// // 인증된 Authentication 객체를 사용하여 JWT 생성.
// var token = tokenService.generateToken(authentication);

// // 생성된 JWT를 응답 본문에 포함하여 반환.
// return ResponseEntity.ok(new JwtTokenResponse(token));

// } catch (AuthenticationException e) {
// // 인증 실패 로그 기록
// logger.error("Authentication failed: {}", e.getMessage());
// // 클라이언트에게 인증 실패 응답 반환
// return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication
// failed");
// }
// }
// }
