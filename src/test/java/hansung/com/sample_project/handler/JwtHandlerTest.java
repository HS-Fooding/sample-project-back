package hansung.com.sample_project.handler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Base64;

class JwtHandlerTest {
    JwtHandler jwtHandler = new JwtHandler(); // JwtHandler의 기능에 테스트를 위한 의존성

    @Test
    void createTokenTest() {
        // given, when
        String encodedKey = Base64.getEncoder().encodeToString("myKey".getBytes());
        String subject = "subject";
        String token = createToken(encodedKey, subject, 60L);

        // then
        Assertions.assertTrue(token.contains("Bearer "), token);
    }

    @Test
    void extractSubjectTest() {
        // given
        String encodedKey = Base64.getEncoder().encodeToString("myKey".getBytes());
        String subject = "subject";
        String token = createToken(encodedKey, subject, 60L);

        // when
        String extractedSubject = jwtHandler.extractSubject(encodedKey, token);

        // then
        Assertions.assertEquals(extractedSubject, subject);
    }

    @Test
    void validateTest() {
        // given
        String encodedKey = Base64.getEncoder().encodeToString("myKey".getBytes());
        String token = createToken(encodedKey, "subject", 60L);

        // when
        boolean isValid = jwtHandler.validate(encodedKey, token);

        // then
        Assertions.assertTrue(isValid);
    }

    @Test
    void invalidateByInvalidKeyTest() {
        // given
        String encodedKey = Base64.getEncoder().encodeToString("myKey".getBytes());
        String token = createToken(encodedKey, "subject", 60L);

        // when
        boolean isValid = jwtHandler.validate("invalid", token);

        // then
        Assertions.assertFalse(isValid);
    }

    @Test
    void invalidateByExpiredTokenTest() {
        // given
        String encodedKey = Base64.getEncoder().encodeToString("myKey".getBytes());
        String token = createToken(encodedKey, "subject", 0L);

        // when
        boolean isValid = jwtHandler.validate(encodedKey, token);

        // then
        Assertions.assertFalse(isValid);
    }

    private String createToken(String encodedKey, String subject, long maxAgeSeconds) {
        return jwtHandler.createToken(
                encodedKey,
                subject,
                maxAgeSeconds);
    }
}