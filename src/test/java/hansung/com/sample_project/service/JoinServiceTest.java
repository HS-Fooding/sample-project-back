package hansung.com.sample_project.service;

import hansung.com.sample_project.domain.User;
import hansung.com.sample_project.dto.SignUpRequest;
import hansung.com.sample_project.dto.SignInRequest;
import hansung.com.sample_project.dto.SignInResponse;
import hansung.com.sample_project.exception.LoginFailureException;
import hansung.com.sample_project.exception.UserEmailAlreadyExistsException;
import hansung.com.sample_project.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class JoinServiceTest {
    @InjectMocks JoinService joinService;
    @Mock UserRepository userRepository;
    @Mock RoleRepository roleRepository;
    @Mock PasswordEncoder passwordEncoder;
    @Mock TokenService tokenService;

    @Test
    void signUpTest() throws UserEmailAlreadyExistsException {
        // 정상적인 회원가입 처리 로직
        // given
        SignUpRequest req = createSignUpRequest();
        given(roleRepository.findByRolename("ROLE_NORMAL")).willReturn();

        // when
        joinService.signUp(req);

        // then
        verify(passwordEncoder).encode(req.getUserPassword());
        verify(userRepository).save(any());
    }

    @Test
    void validateSignUpByDuplicateEmailTest() {
        // 이름 중복 테스트
        // given
        given(userRepository.existByName(anyString())).willReturn(true);

        // when, then
        Assertions.assertThatThrownBy(() -> joinService.signUp(createSignUpRequest()))
                .isInstanceOf(UserEmailAlreadyExistsException.class);
    }

    @Test
    void signInTest() throws LoginFailureException {
        // given
        given(userRepository.findByName(any())).willReturn(createUser());
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);
        given(tokenService.createAccessToken(anyString())).willReturn("access");
        given(tokenService.createRefreshToken(anyString())).willReturn("refresh");

        // when
         SignInResponse res = joinService.signIn(new SignInRequest("userId", "password"));

        // then
        Assertions.assertThat(res.getAccessToken()).isEqualTo("access");
        Assertions.assertThat(res.getRefreshToken()).isEqualTo("refresh");
    }

    @Test
    void signInExceptionByNoneMemberTest() {
        // given
        //given(userRepository.findByName(any())).willReturn(Optional.of());

        // when, then
        Assertions.assertThatThrownBy(() -> joinService.signIn(new SignInRequest("userId", "password")))
                .isInstanceOf(LoginFailureException.class);
    }

    @Test
    void signInExceptionByInvalidPasswordTest() {
        // given
        given(userRepository.findByName(any())).willReturn(createUser());
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(false);

        // when, then
        Assertions.assertThatThrownBy(() -> joinService.signIn(new SignInRequest("userId", "password")))
                .isInstanceOf(LoginFailureException.class);
    }


    private SignUpRequest createSignUpRequest() {
        return new SignUpRequest("userId", "userPassword", true, "name", "email@naver.com", 26);
    }

    private User createUser() {
        return new User("userId", "userPassword", true, "name", "email@naver.com", 26);
    }
}