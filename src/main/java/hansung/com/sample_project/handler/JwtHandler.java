package hansung.com.sample_project.handler;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtHandler {

    private String type = "Bearer ";

    public String createToken(String encodedKey, String subject, long maxAgeSeconds) {
        Date now = new Date();
        return type + Jwts.builder() // jwt 빌드
                .setSubject(subject) // 토큰에 저장될 데이터를 지정
                .setIssuedAt(now)   // 토큰 발급일 지정
                .setExpiration(new Date(now.getTime() + maxAgeSeconds * 1000L)) // 토큰 만료 일자 지정
                .signWith(SignatureAlgorithm.HS256, encodedKey) // 파라미터로 받은 key로 SHA-256 알고리즘을 사용해 서명
                .compact(); // 주어진 정보로 토큰을 생성
    }

    // 토큰에서 subject 추출. 토큰을 파싱하고, 바디에서 subject를 꺼내올 수 있음
    public String extractSubject(String encodedKey, String token) {
        return parse(encodedKey, token).getBody().getSubject();
    }

    // 토큰의 유효성을 검증함
    public boolean validate(String encodedKey, String token) {
        try {
            parse(encodedKey, token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    private Jws<Claims> parse(String key, String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(untype(token));
    }

    private String untype(String token) {
        return token.substring(type.length());
    }

}