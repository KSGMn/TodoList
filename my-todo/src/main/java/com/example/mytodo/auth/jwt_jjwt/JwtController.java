package com.example.mytodo.auth.jwt_jjwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtController {

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

    @Autowired
    private AuthenticationManager authenticationManager;
    private final JwtTokenService tokenService;

    public JwtController(JwtTokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Token> authenticate(@RequestBody JwtTokenRequest jwtTokenRequest) {
        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(jwtTokenRequest.username(), jwtTokenRequest.password()));

            String username = jwtTokenRequest.username();
            Token accessToken = tokenService.generateAccessToken(username);
            Token refreshToken = tokenService.generateRefreshToken(username);

            Token responseToken = Token.builder()
                    .grantType("Bearer")
                    .accessToken(accessToken.getAccessToken())
                    .refreshToken(refreshToken.getRefreshToken())
                    .build();

            return ResponseEntity.ok(responseToken);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<Token> refreshToken(@RequestBody Token refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        // 리프레시 토큰의 유효성 검사 및 사용자 이름 추출
        if (tokenService.validateRefreshToken(refreshToken)) {
            String username = tokenService.getUsernameFromToken(refreshToken);
            Token newAccessToken = tokenService.generateAccessToken(username);

            // 새 액세스 토큰으로 Token 객체 생성 (리프레시 토큰은 재발급하지 않음)
            Token responseToken = Token.builder()
                    .grantType("Bearer")
                    .accessToken(newAccessToken.getAccessToken())
                    .build();

            return ResponseEntity.ok(responseToken);
        } else {
            return ResponseEntity.status(401).body(null); // 유효하지 않은 리프레시 토큰 처리
        }
    }
}
