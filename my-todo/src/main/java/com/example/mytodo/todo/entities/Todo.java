package com.example.mytodo.todo.entities;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// @NoArgsConstructor
// public class MyClass {
//     private int number;
//     private String name;
// }

// @AllArgsConstructor
// public class MyOtherClass {
//     private int number;
//     private String name;
//     }
//     MyClass에는
//     @NoArgsConstructor를
//     사용하여 파라미터가
//     없는 생성자가
//     자동으로 추가됩니다.즉,
//     MyClass 인스턴스를
//     생성할 때 new MyClass()처럼 아무런
//     값도 제공하지
//     않고 호출할
//     수 있습니다.MyOtherClass에는
//     @AllArgsConstructor를
//     사용하여 모든
//     필드를 초기화하는
//     생성자가 자동으로 추가됩니다.즉,
//     MyOtherClass 인스턴스를
//     생성할 때 new MyOtherClass(1,"Name")처럼 모든
//     필드에 해당하는
//     값을 제공해야 합니다.

@Entity // spring-boot-starter-data-jpa의존성 추가 후 사용 가능
@Getter
@Setter
@ToString
@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 생성자 추가
public class Todo {

    @Id
    @GeneratedValue
    private Integer id;
    private String username;
    @NotEmpty(message = "제목을 입력하세요")
    private String title;
    @NotEmpty(message = "내용을 입력하세요")
    private String contents;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate targetDate;

}
