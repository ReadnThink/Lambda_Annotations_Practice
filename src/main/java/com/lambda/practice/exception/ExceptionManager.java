package com.lambda.practice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionManager {
    @ExceptionHandler(RuntimeException.class)//@ExceptionHandler 덕분에 RuntimeException이나면 Controller대신 이곳에서 리턴.
    public ResponseEntity<?> runtimeExceptionHandler(RuntimeException e) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)//에러 상태코드를 나타낸다
                .body(e.getMessage()); //바디를 통해 사용자에게 message를 날릴 수 있다.
        //INTERNAL_SERVER_ERROR를 리턴하고 ResponseBody에 e.getMessage()를 추가해서 보내준다.
    }

    @ExceptionHandler(HospitalException.class)//@ExceptionHandler 덕분에 직접 구현한 HospitalException에러가나면 Controller대신 이곳에서 리턴.
    public ResponseEntity<?> HospitalExceptionHandler(HospitalException hospitalException) { //HospitalException를 받아온다.
        //내가 만들 어플리케이션에서 특정 에러코드(꼭 숫자가 아니여도 됨)를 frontEnd에 알려주고 싶다면?
        return ResponseEntity.status(hospitalException.getErrorCode().getHttpStatus()) //상태코드를 나타낸다
                .body(hospitalException.errorCode.name() + " " +  hospitalException.getMessage()); //에러의 이름과 내용을 body로 뿌려줍니다.
    }
}
