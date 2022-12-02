package com.lambda.practice.service;

import com.lambda.practice.domain.User;
import com.lambda.practice.domain.dto.UserJoinRequest;
import com.lambda.practice.domain.dto.UserLoginRequest;
import com.lambda.practice.exception.ErrorCode;
import com.lambda.practice.exception.HospitalException;
import com.lambda.practice.repository.HospitalRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final HospitalRepository hospitalRepository;
    private final BCryptPasswordEncoder encoder;

    public UserService(HospitalRepository hospitalRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.hospitalRepository = hospitalRepository;
        this.encoder = bCryptPasswordEncoder;
    }

    public void join(UserJoinRequest userJoinRequest) {
        //중복체크
        hospitalRepository.findByUserName(userJoinRequest.getUserName())
                .ifPresent((user)-> {
                    throw new HospitalException(ErrorCode.DUPLICATE_USER_NAME, "해당 Id가 이미 있습니다.");
                    });
        //저장

        User user = User.builder()
                .userName(userJoinRequest.getUserName())
                .password(encoder.encode(userJoinRequest.getPassword()))
                .build();
        hospitalRepository.save(user);
    }


    public String login(UserLoginRequest userLoginRequest) {
        return null;
    }
}
