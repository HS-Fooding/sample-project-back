package hansung.com.sample_project.service;

import hansung.com.sample_project.domain.Role;
import hansung.com.sample_project.domain.User;
import hansung.com.sample_project.dto.SignInRequest;
import hansung.com.sample_project.dto.SignInResponse;
import hansung.com.sample_project.dto.SignUpRequest;
import hansung.com.sample_project.exception.LoginFailureException;
import hansung.com.sample_project.exception.UserEmailAlreadyExistsException;
import hansung.com.sample_project.exception.UserIdExistsException;
import hansung.com.sample_project.exception.UserNickNameExistsException;
import hansung.com.sample_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    // 회원 가입
    @Transactional
    public void signUp(SignUpRequest req)
            throws UserEmailAlreadyExistsException, UserNickNameExistsException, UserIdExistsException {
        validateSignUpInfo(req);
        User user = new User(req);

        // generate new Bcrypt hash
        String encryptedPassword = passwordEncoder.encode(user.getUserPassword());

        if(req.getRole().equals(Role.ROLE_ADMIN))
            user.setPasswordAndRole(encryptedPassword, Role.ROLE_ADMIN);
        else if(req.getRole().equals(Role.ROLE_USER))
            user.setPasswordAndRole(encryptedPassword, Role.ROLE_USER);

        userRepository.save(user);
    }

    public void validateSignUpInfo(SignUpRequest req)
            throws UserEmailAlreadyExistsException, UserNickNameExistsException, UserIdExistsException {
        if(!userRepository.existByEmail(req.getEmail()))
            throw new UserEmailAlreadyExistsException(req.getEmail());
        if(!userRepository.existByNickName(req.getNickName()))
            throw new UserNickNameExistsException(req.getNickName());
        if(!userRepository.existByUserId(req.getUserId()))
            throw new UserIdExistsException(req.getUserId());
    }

    @Transactional
    public SignInResponse signIn(SignInRequest req) throws LoginFailureException {
        User user = userRepository.findByUserId(req.getUserId());
        validatePassword(req, user); // 1
        String subject = createSubject(user); // 2
        String accessToken = tokenService.createAccessToken(subject); // 3
        String refreshToken = tokenService.createRefreshToken(subject); // 4
        return new SignInResponse(accessToken, refreshToken); // 5
    }

    private String createSubject(User user) {
        return String.valueOf(user.getId());
    }

    private void validatePassword(SignInRequest req, User user) throws LoginFailureException {
        if(!passwordEncoder.matches(req.getUserPassword(), user.getUserPassword())) {
            throw new LoginFailureException();
        }
    }

    // 단일 회원 조회
    public User findUser(Long userId) {
        return userRepository.findById(userId);
    }

    // 전체 회원 조회
    public List<User> findUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(userId);
        System.out.println("######################fuck");
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getUserPassword(), getAuthorities());
    }
    private Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }


  /*  private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
        String[] userRoles = user.getRoles()
                .stream()
                .map((role) -> role.getRoleName())
                .toArray(String[]::new);

        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
        return authorities;
    }*/
}
