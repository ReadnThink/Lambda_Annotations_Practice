package com.lambda.practice.service;

import com.lambda.practice.domain.User;
import com.lambda.practice.domain.UserRole;
import com.lambda.practice.domain.dto.UserJoinRequest;
import com.lambda.practice.domain.dto.UserLoginRequest;
import com.lambda.practice.exception.ErrorCode;
import com.lambda.practice.exception.HospitalException;
import com.lambda.practice.repository.UserRepository;
import com.lambda.practice.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@Repository
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.token.secret}")
    private String key;
    //1시간
    private long expireTimeMs = 1000 * 60 * 60L; //1000Ms = 1초이다.

    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }


    public void join(UserJoinRequest userJoinRequest) {
        //중복체크
        userRepository.findByUserName(userJoinRequest.getUserName())
                .ifPresent((user)-> {
                    throw new HospitalException(ErrorCode.DUPLICATE_USER_NAME, "해당 Id가 이미 있습니다.");
                    });
        //저장

        User user = User.builder()
                .userName(userJoinRequest.getUserName())
                .password(encoder.encode(userJoinRequest.getPassword()))
                .role(UserRole.ADMIN)
                .build();
        userRepository.save(user);
    }


    public String login(UserLoginRequest userLoginRequest) {
        // userName 없음
        User selectedUser = userRepository.findByUserName(userLoginRequest.getUserName())
                .orElseThrow(()-> new HospitalException(ErrorCode.USERNAME_NOT_FOUND,userLoginRequest.getUserName() + "이 없습니다."));
        // password 틀림
        if (!encoder.matches(userLoginRequest.getPassword(), selectedUser.getPassword())) {
            throw new HospitalException(ErrorCode.INVALID_PASSWORD, "패스워드를 잘못 입력하였습니다.");
        }

        //두가지 예외가 없다면 토큰발급
        String token = JwtTokenUtil.createToken(selectedUser.getUserName(), key, expireTimeMs);
        return token;
    }

    public User getUserByUserName(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new HospitalException(ErrorCode.USERNAME_NOT_FOUND, ""));
    }
}
