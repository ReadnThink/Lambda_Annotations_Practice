package com.lambda.practice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    //필드값을 생성자로 받아 넣는다.
    DUPLICATE_USER_NAME(HttpStatus.CONFLICT,""),
    USERNAME_NOT_FOUND(HttpStatus.NOT_FOUND,""),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "")
    ;

    //자바의 HttpStatus를 사용한다.
    HttpStatus httpStatus;
    String message; //내가만들 로직의 설명을 해주는 것이다. (내가 만든 로직이라는 뜻이다.)

    //생성자를 달아줘야 한다. AllArgsConstructors로 대체 가능
    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
