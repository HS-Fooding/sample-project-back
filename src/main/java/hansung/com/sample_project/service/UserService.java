package hansung.com.sample_project.service;

import hansung.com.sample_project.domain.User;
import hansung.com.sample_project.dto.SignInRequest;
import hansung.com.sample_project.dto.SignUpRequest;
import hansung.com.sample_project.exception.LoginFailureException;
import hansung.com.sample_project.exception.UserEmailAlreadyExistsException;
import hansung.com.sample_project.exception.UserIdExistsException;
import hansung.com.sample_project.exception.UserNickNameExistsException;
import hansung.com.sample_project.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 단일 회원 조회
    public User findUser(Long userId) {
        return userRepository.findById(userId);
    }

    // 전체 회원 조회
    public List<User> findUsers() {
        return userRepository.findAll();
    }

    // 회원 가입
    @Transactional
    public void join(SignUpRequest req)
            throws UserIdExistsException, UserEmailAlreadyExistsException, UserNickNameExistsException {
        validateSignUpInfo(req);
        User user = new User(req);
        String encryptedPassword = passwordEncoder.encode(user.getUserPassword());
        user.setUserPassword(encryptedPassword);

        userRepository.save(user);
    }

    public void validateSignUpInfo(SignUpRequest req)
            throws UserEmailAlreadyExistsException, UserNickNameExistsException, UserIdExistsException {
        if(userRepository.existByEmail(req.getEmail()))
            throw new UserEmailAlreadyExistsException(req.getEmail());
        if(userRepository.existByNickName(req.getNickName()))
            throw new UserNickNameExistsException(req.getNickName());
        if(userRepository.existByUserId(req.getUserId()))
            throw new UserIdExistsException(req.getUserId());
    }




    public String makeJwtToken(SignInRequest signInRequest) throws LoginFailureException {
        // 올바른 아이디, 비번인지 체크
        List<User> userTmp = userRepository.findByUserId(signInRequest.getUserId());
        User user = userTmp.get(0);

        if(!passwordEncoder.matches(signInRequest.getUserPassword(), user.getUserPassword())) {
            throw new LoginFailureException();
        }

        // 토큰 발급
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer("fooding")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(30).toMillis()))
                .claim("id", signInRequest.getUserId())
                .claim("password", signInRequest.getUserPassword())
                .signWith(SignatureAlgorithm.HS256, "secret")
                .compact();
    }

}
