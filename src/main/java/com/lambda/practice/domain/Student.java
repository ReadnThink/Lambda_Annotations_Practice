package com.lambda.practice.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Student {
    private String name;
    private boolean isLikeAlgorithm;
    private boolean isLikeSpringBoot;
}
