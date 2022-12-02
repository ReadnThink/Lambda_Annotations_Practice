package com.lambda.practice.controller;

import com.lambda.practice.domain.dto.UserJoinRequest;
import com.lambda.practice.domain.dto.UserLoginRequest;
import com.lambda.practice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/join")
    public String join(@RequestBody UserJoinRequest userJoinRequest) {
        userService.join(userJoinRequest);
        return "회원가입이 되었습니다.";
    }

    @PostMapping("/login")
    public String login(@RequestBody UserLoginRequest userLoginRequest) {
        String token = userService.login(userLoginRequest);
        return token;
    }
}
