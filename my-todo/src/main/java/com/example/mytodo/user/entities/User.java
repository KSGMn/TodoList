package com.example.mytodo.user.entities;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {

    // 사용자 생성 시 비밀번호를 자동으로 해싱하기
    public User(String username, String password, String rolse) {
        this.username = username;
        this.password = hashPassword(password);
        this.roles = rolse;
    } // final 선언 시 초기화가 불가능하여 직접 생성자를 만들어주자

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String roles;

    // 비밀번호를 해싱하는 메소드
    private String hashPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return "{bcrypt}" + encoder.encode(password); // 앞에 접두사를 추가하지 않으면 500에러가 난다 접두사가 없어도 자동으로 인식이 된다는데 인식이 안된다
    }

}
