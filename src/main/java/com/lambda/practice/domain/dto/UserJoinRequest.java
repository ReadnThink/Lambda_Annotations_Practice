package com.lambda.practice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserJoinRequest {
    private Long id;
    private String userName;
    private String password;

}
