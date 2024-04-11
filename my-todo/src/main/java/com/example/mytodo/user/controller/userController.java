package com.example.mytodo.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.mytodo.user.dto.UserDto;
import com.example.mytodo.user.entities.User;
import com.example.mytodo.user.repository.UserRepository;

import lombok.AllArgsConstructor;

/*
@RequestParam
@RequestParam은 주로 GET 요청의 쿼리 매개변수에서 값을 추출할 때 사용됩니다. 폼 데이터를 처리할 때도 사용될 수 있습니다.
요청 URL에서 ?key=value 형태로 전달된 매개변수를 컨트롤러 메소드의 인자로 매핑할 때 사용됩니다.
선택적으로 필수 여부를 지정할 수 있으며(required 속성), 기본값을 설정할 수도 있습니다(defaultValue 속성).

@PathVariable
@PathVariable은 URI의 일부를 변수로 추출할 때 사용됩니다. 주로 RESTful 서비스에서 리소스를 지정하는 데 사용됩니다.
경로 변수를 메소드의 파라미터로 바인딩할 때 사용되며, URI 템플릿에 지정된 변수 이름과 메소드 파라미터의 이름이 일치해야 합니다.

@RequestBody
@RequestBody는 주로 POST 또는 PUT 요청의 HTTP 요청 본문(body)에서 값을 추출할 때 사용됩니다. 클라이언트가 서버로 보내는 JSON, XML 같은 데이터를 Java 객체로 바인딩할 때 사용됩니다.
요청 본문의 내용을 메소드의 파라미터로 직접 매핑하기 위해 사용됩니다. 주로 JSON이나 XML과 같은 복잡한 데이터 구조를 전송할 때 사용됩니다.
@RequestBody로 받은 데이터는 스프링의 HttpMessageConverter에 의해 자동으로 해당 타입의 객체로 변환됩니다.
 */

@RestController
@AllArgsConstructor
public class userController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private UserRepository userRepository;

    @GetMapping("/find/{username}")
    public User findByUsername(@PathVariable String username) {

        return userRepository.findByUsername(username);
    }

    // JSON형태의 요청 본문을 사용하기 위해 @RequestBody를 사용하자
    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {// @RequestBody는 하나만 쓰자

        User findUser = userRepository.findByUsername(userDto.getUsername());

        if (findUser != null) {
            logger.info("중복된 이름: " + userDto.getUsername());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: " + userDto.getUsername() + "은 중복된 이름입니다");
        }
        User newUser = new User(userDto.getUsername(), userDto.getPassword(), "USER,ADMIN");
        // rolse를 USER로 하드코딩
        User saveUser = userRepository.save(newUser);
        return ResponseEntity.ok(saveUser);

    }

}
