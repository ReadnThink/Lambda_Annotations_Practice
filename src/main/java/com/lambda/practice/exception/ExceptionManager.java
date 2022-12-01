package com.lambda.practice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionManager {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> runtimeExceptionHandler(RuntimeException e) {
        //RuntimeException이 나면 Controller대신 이곳에서 리턴을 해줍니다.
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage()); //바디를 통해 사용자에게 message를 날릴 수 있다.
        //INTERNAL_SERVER_ERROR를 리턴하고 ResponseBody에 e.getMessage()를 추가해서 보내준다.
    }

    @ExceptionHandler(HospitalException.class)
    public ResponseEntity<?> HospitalExceptionHandler(HospitalException hospitalException) {
        //내가 만들 어플리케이션에서 특정 에러코드(꼭 숫자가 아니여도 됨)를 frontEnd에 알려주고 싶다면?
        return ResponseEntity.status(hospitalException.getErrorCode().getHttpStatus())
                .body(hospitalException.errorCode.name() + " " +  hospitalException.getMessage());
    }
}
