package com.lambda.practice.configuration;

import com.lambda.practice.domain.User;
import com.lambda.practice.service.UserService;
import com.lambda.practice.utils.JwtTokenUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter { //OncePerRequestFilter는 로그인 할떄마다 토큰을 확인하는 방식
    private final UserService userService;
    private final String secretKey; // 티켓이다. 러시아 월드컵 티켓인지, 카타르 월드컵 티켓인지 확인하기 위함

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //권한 주기 (월드컵경기장 입구라고 보면 된다.)
        //개찰구 역할, 현재는 모두 닫혀있다.

        /*
        * 언제 막아야 할까?
        * 1. 티켓이 없을경우
        * 2. 적절하지 않은 티켓일 경우
        * 3. 기간이 지난 티켓을 가져오는 경우
        * */

        //문닫기
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION); // token은 값이 변경되면 안되니 final로 선언
        log.info("authorizationHeader:{}", authorizationHeader);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        //token 분리
        String token;
        try {
            token = authorizationHeader.split(" ")[1];
        } catch (Exception e) {
            log.error("token 추출에 실패했습니다.");
           filterChain.doFilter(request,response);
           return;
        }

        // 토큰이 기간이 만료되었는지 확인
        if (JwtTokenUtil.isExpired(token, secretKey)) {
            filterChain.doFilter(request,response);
            return;
        }

        //Token에서 UserName꺼내기
        String userName = JwtTokenUtil.getUserName(token,secretKey);
        log.info(userName);
        //userDetail 가져오기
        User user = userService.getUserByUserName(userName);
        log.info("userRole {}:", user.getRole());

        //문열어주기 Role바인딩
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken
                (user.getUserName(), null, List.of(new SimpleGrantedAuthority(user.getRole().name())) );
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken); //권한 부여
        filterChain.doFilter(request,response);
    }
}
