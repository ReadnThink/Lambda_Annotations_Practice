package com.lambda.practice.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Teacher {
    private String name;
    private boolean isLikeAlgorithm;
    private boolean isLikeSpringBoot;
    //이름의 자리수를 알려주는 매소드
    public int cntNameDigit(){
        return this.name.length();
    }
    //islikeSpringBoot가 true인 것을 리턴하는 메소드
    public int cntIsLike(){
        if(isLikeSpringBoot == true) return 1;
        else  return 0;
    }
}
