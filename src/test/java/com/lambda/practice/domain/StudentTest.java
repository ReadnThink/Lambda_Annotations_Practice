package com.lambda.practice.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    @Test
    void name(){
        List<Teacher> teachers = new ArrayList<>();
        Teacher Kyeongrok = new Teacher("김경록", true, true);
        Teacher Kyeonghwan = new Teacher("김경환", true, false);
        Teacher sujin = new Teacher("강소현", true, true);
        Teacher sohyun = new Teacher("김수진", false, true);
        teachers.add(Kyeongrok);
        teachers.add(Kyeonghwan);
        teachers.add(sujin);
        teachers.add(sohyun);
        teachers.add(new Teacher("김경",false,true));

        //.stream().filter를 쓰기 전
        List<Teacher> likeAlgorithmTeacher0 = new ArrayList<>();
        for (int i = 0; i < teachers.size(); i++) {
            if(teachers.get(i).isLikeAlgorithm()){
                likeAlgorithmTeacher0.add(teachers.get(i));
            }
        }

        for (Teacher teacher : likeAlgorithmTeacher0) {
            System.out.println(teacher.getName());
        }

        System.out.println("---------------stream 사용----------------");
        //DB에서 읽어왔습니다.
        List<Teacher> likeAlgorithmTeachers = teachers.stream()
                .filter(teacher -> teacher.isLikeAlgorithm() == true)
                .collect(Collectors.toList());

        //List<Teacher>에서 stream을 통해 받을때 새로운 객체가 생성되는줄 알았는데 new를 하지 않아서 객체의 주소만 넘겨주는것이였다.
        //새로 객체를 생성하여 공간복잡도가 더 올라갈줄 알았지만 전혀 그렇지 않았다.
        System.out.printf("두 객체가 서로 같은지 %b\n", likeAlgorithmTeachers.get(0) == Kyeongrok);

        for (Teacher teacher : likeAlgorithmTeachers) {
            System.out.println(teacher.getName());
        }

        System.out.println("--------------springboot true, filter, map사용--------------");
        List<String> likeSpringBootTeachers = teachers.stream()
                .filter(teacher -> teacher.isLikeSpringBoot() == true)
                .map(teacher -> teacher.getName())
                .collect(Collectors.toList());

        for (String teacher : likeSpringBootTeachers) {
            System.out.println(teacher);
        }

        //supplier 사용할 경우
        Optional<Teacher> optionalTeacher = Optional.of(Kyeongrok);
        optionalTeacher.orElseThrow(()-> new RuntimeException());

    }

    @Test
    void filterPredicate(){
        //숫자 num을 넣으면 10보다 큰지 true, false로 리턴해주는 내장 인터페이스
        Predicate<Integer> predicate = num -> num > 10;
        System.out.println(predicate.test(2));
    }

    @Test
    void map(){
        //맵을 사용하면 타입을 Collection단위로(ex List) 바꿀 수 있습니다.
        String[] list = {"1", "2", "3"};
        List<Integer> nums = Arrays.stream(list)
                .map(strNum -> Integer.parseInt(strNum))
                .collect(Collectors.toList());
        System.out.println(nums);
    }



    @Test
    void reduce(){
        String[] list = {"1", "2", "3"};
        List<Integer> nums = Arrays.stream(list)
                .map(strNum -> Integer.parseInt(strNum))
                .collect(Collectors.toList());
        //for문 사용해서 cnt 구하기
//        int sum = 0;
//        for (int i = 0; i < nums.size(); i++) {
//            sum += nums.get(i);
//        }
//        System.out.println("for문 sum"+sum);

        int reduceSum = Arrays.stream(list)
                .map(strNum -> Integer.parseInt(strNum))
                .reduce(0,(a,b) -> a + b); //재귀와 비슷하다.
        System.out.println("reduce sum"+reduceSum);
    }

    @Test
    void SpringBootLikeCnt(){
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(new Teacher("김경록", true, true));
        teachers.add(new Teacher("김경환", true, false));
        teachers.add(new Teacher("강소현", true, true));
        teachers.add(new Teacher("김수진", false, true));
        teachers.add(new Teacher("김경",false,true));

        //숫자를 확인하기 위해 int 변수선언
        //map을 통해 Teacher객체에서 int로 변환
        //reduce를 통해 합구하기
        int SpringBootLikeCnt = teachers.stream()
                .filter(teacher -> teacher.isLikeSpringBoot() == true)
                .map(teacher -> teacher.cntIsLike())
                .reduce(0,(a,b) -> a + b);
        System.out.println(SpringBootLikeCnt);
    }
     @Test
    void isLikeSpringBoot(){
        //teachers에서 isLikeSpringBoot()이 true인 선생님의 이름의 자릿수를 List로 바꿔보세요
        //map을통해 Integer로 바꿀 수 있다.
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(new Teacher("김경록", true, true));
        teachers.add(new Teacher("김경환", true, false));
        teachers.add(new Teacher("강소현", true, true));
        teachers.add(new Teacher("김수진", false, true));
        teachers.add(new Teacher("김경",false,true));

        List<Integer> digitsOfNames = teachers.stream()
                .filter(Teacher::isLikeSpringBoot) //.filter(teacher -> teacher.isLikeSpringBoot() == true)
                .map(Teacher::cntNameDigit) //.map(teacher -> teacher.cntNameDigit())
                .collect(Collectors.toList()); //연산 결과를 list로 다시 만들어 준다.
        System.out.println(digitsOfNames);
    }

    @Test
    void optionalTest(){
        Optional<Teacher> optionalTeacher = Optional.of(new Teacher("k", true, true));
        Optional<Teacher> emptyTeacher = Optional.empty();

        //get
        Optional<Teacher> optionalTeacher1 = Optional.of(new Teacher("k", true, true));
        Teacher teacher = optionalTeacher1.get(); //값이 있는경우
        System.out.println("teacher name: " + teacher.getName());

        //값이 없는 경우 get
        Optional<Teacher> emptyTeacher2 = Optional.empty();
//        emptyTeacher2.get(); // 값이 없다. 오류 발생
//        emptyTeacher2.orElseThrow(RuntimeException::new); // 값이 없을경우 orElseThrow를 통해 예상하는 에러를 발생시킬 수 있다.
//        Teacher teacher1 = emptyTeacher2.orElseThrow(() -> new RuntimeException("값이 없습니다."));


        //값이 있는경우 무언가 처리하고 싶을때
        Optional<Teacher> optionalTeacher2 = Optional.of(new Teacher("ifPresent값이 있어 에러출력", true, true));
//        optionalTeacher2.ifPresent(sth -> {
//            throw new RuntimeException(sth.getName());// throw new HospitalReviewAppException(ErrorCode.DUPLICATED_USER_NAME, String.format("UserName:%s", request.getUserName()));
//        });

        // .ofNullable, orElse()
        Optional<Teacher> optionalTeacher3 = Optional.of(new Teacher(null, true, true));
        String name = Optional.ofNullable(optionalTeacher3.get().getName()).orElse("이름이 없습니다.");
        System.out.println(name);
    }
}