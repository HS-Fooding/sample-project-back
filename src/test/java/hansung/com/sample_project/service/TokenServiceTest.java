package hansung.com.sample_project.service;

import hansung.com.sample_project.handler.JwtHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class TokenServiceTest {
    @InjectMocks TokenService tokenService;
    @Mock JwtHandler jwtHandler;

    @BeforeEach
    void beforeEach() {
        ReflectionTestUtils.setField(tokenService, "accessTokenMaxAgeSeconds", 10L);
        ReflectionTestUtils.setField(tokenService, "refreshTokenMaxAgeSeconds", 10L);
        ReflectionTestUtils.setField(tokenService, "accessKey", "accessKey");
        ReflectionTestUtils.setField(tokenService, "refreshKey", "refreshKey");
    }

    @Test
    void createAccessTokenTest() {
        // given
        given(jwtHandler.createToken(anyString(), anyString(), anyLong())).willReturn("access");

        // when
        String token = tokenService.createAccessToken("subject");

        // then
        Assertions.assertEquals(token, "access");
        verify(jwtHandler).createToken(anyString(), anyString(), anyLong());
    }

    @Test
    void createRefreshTokenTest() {
        // given
        given(jwtHandler.createToken(anyString(), anyString(), anyLong())).willReturn("refresh");

        // when
        String token = tokenService.createRefreshToken("subject");

        // then
        Assertions.assertEquals(token, "refresh");
        verify(jwtHandler).createToken(anyString(), anyString(), anyLong());
    }

}