package com.lambda.practice.controller;

//import com.lambda.practice.domain.dto.UserJoinRequest;
//import com.lambda.practice.service.UserService;
//import com.lambda.practice.service.TeacherService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;

//@RestController
//@RequestMapping("/api/v1/teacher")
//public class TeacherController {
//
//    @Autowired
//    TeacherService teacherService;
//
//    TeacherService teacherService1 = new TeacherService();
//    //controller가 호출될때마다 new를 많이하면 힙영역에 객체 생성이 많이되고, GC가 많이 돌게된다.
//    //이때 Bean을 통해 싱글톤 패턴으로 new를 단 한번만 하게되어 비용을 줄일 수 있다.
//    //Service나, Configuration에 Bean이 있거나, Component가 달려있으면 new를 하여서 applicationContext에 Autowird가 등록을 한다.
//    //private fianl하고 생성자가 있으면 Autowired역할을 한다.
//    //@RequiredArgsConstructor(Lombok) = RequiredArgs -> (final)만 DI해준다.
//
//    //WebMvc Test -> Spring을 안띄우고도 Service만 Test할 수 있다.
//    //SpringBoot쓰면 Test가 어려워지는 구간이 발생한다. Why? new를 많이 하기때문에 -> WebMvc로 필요한것들만 new를 해서 Test할 수 있다.
//    //Service에서 Spring종속성이 안들어가게 개발해야 한다. -> Dto를 써서 직접 Entity, Repository 등에 의존하지 않게 한다.
//
//    @GetMapping("/{userName}")
//    public String get(@PathVariable String userName){
//        String s = teacherService.world(userName);
//        return s + "Hello";
//    }
//
//    @Autowired
//    UserService userService;
//
//    @PostMapping("/join")
//    public String join(@RequestBody UserJoinRequest userJoinRequest) {
//        userService.join(userJoinRequest);
//        return "회원가입이 되었습니다.";
//    }
//}
