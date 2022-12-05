package com.lambda.practice.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtTokenUtil {

    public static Claims extractClaims(String token, String key) { // claims = 일종의 맵입니다.
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }

    public static String getUserName(String token, String key) {
        return extractClaims(token, key).get("userName").toString();
    }

    public static boolean isExpired(String token, String secretKey) {
        // expire
        Date expiredDate = extractClaims(token, secretKey).getExpiration();// expire timeStamp를 return
        return expiredDate.before(new Date()); //현재보다 전인지 check 한다.
    }

    //언제까지 유효한지
    public static String createToken(String userName, String key, long expiredTimeMs){
        Claims claims = Jwts.claims(); //일종의 Map이다 Claims로 정보를 담을 수 있다.
        claims.put("userName", userName); //userName을 claims에 담는다

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis())) //시작시간
                .setExpiration(new Date(System.currentTimeMillis() + expiredTimeMs)) //만료시간
                .signWith(SignatureAlgorithm.HS256,key) //어떤 알고리즘을 사용할지
                .compact();
    }
}
