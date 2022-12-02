package com.lambda.practice.service;

import com.lambda.practice.domain.Teacher;
import com.lambda.practice.exception.ErrorCode;
import com.lambda.practice.exception.HospitalException;
import org.springframework.stereotype.Service;

import java.util.Optional;

//@Service
//public class TeacherService {
//    public String world(String username){
//        //exception발생 시킴(개발자가)
//        //teacher를 받아와서 이름을 꺼내고 싶다. 그런데 Teacher가 없다면? 의도적으로 에러를 발생시킨다.
//        Optional<Teacher> teacherOptional = Optional.empty();
//        //콘솔에만 에러가 찍힌다.
////        Teacher teacher = teacherOptional.orElseThrow(()-> new RuntimeException("해당 teacher가 없습니다."));
////        Teacher teacher = teacherOptional.orElseThrow(()-> new HospitalException(ErrorCode.USERNAME_NOT_FOUND, "DB에 " + username + "으로 검색했을때 빈 값이 아닙니다."));
//        teacherOptional.orElseThrow(() -> {
//            throw new HospitalException(ErrorCode.DUPLICATE_USER_NAME, "DB에 " + username + "으로 검색했을때 빈 값이 아닙니다.");
//        });
//        //api/v1/teachers페이지에는 안나옵니다.
//
//        //유저에게 Exception이 왜 발생했는지 알려주고 싶을때는?
//        //@RestControllerAdvice를 사용
//        //우리 콘솔에는 에러가 찍히지만 사용자는 어떤에러인지 알 수 없습니다.
//        //그래서 어떤 문제가 발생했는지 알려주기 위해 @RestControllerAdvice를 씁니다.
//        //@RestControllerAdvice는 특정 Exception이 발생하면 그게 어디든 이쪽에서 처리할 수 있습니다.
//        //왜냐하면 Error를 Throw하면 바로 위로만 가기 때문입니다.
//        return "world";
//    }
//}
