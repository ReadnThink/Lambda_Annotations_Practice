package com.lambda.practice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HospitalException extends RuntimeException{
    ErrorCode errorCode;
    String message;

    @Override
    public String toString() {
        return errorCode.name(); //Enum 내자 메소드
    }
}
