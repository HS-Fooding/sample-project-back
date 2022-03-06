package hansung.com.sample_project.provider;

import hansung.com.sample_project.dto.SignInRequest;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;

import java.time.Duration;
import java.util.Date;

public class JwtTokenProvider {
    private static final String JWT_SECRET ="secretKey";
    private static final int JWT_EXPIRE_MS = (int)Duration.ofMinutes(30).toMillis();

    public static String generate(SignInRequest signInRequest) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRE_MS);

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer("fooding")
                .setSubject((String) signInRequest.getUserId())
                .setIssuedAt(now)
                .claim("userId", signInRequest.getUserId())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();
    }


    public static Claims getClaims(String token){
        return Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    public static boolean validate(String token){
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
            return true;
        } catch (SignatureException | MalformedJwtException |
                ExpiredJwtException | UnsupportedJwtException |
                IllegalArgumentException e){
            System.out.println("##########validateFailed############");
            e.printStackTrace();
        }
        return false;
    }
}
