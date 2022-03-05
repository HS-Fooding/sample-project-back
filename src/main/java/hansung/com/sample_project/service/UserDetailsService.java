package hansung.com.sample_project.service;

import hansung.com.sample_project.domain.User;
import hansung.com.sample_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        List<User> userTmp = userRepository.findByUserId(userId);
        User user = userTmp.get(0);

        System.out.println("!!!!!!!!!!!!!!!!!!!!I'm here bro!!!!!!!!!!!!!!!");
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(String.valueOf(user.getRole())));

        return new org.springframework.security.core.userdetails.User(user.getUserId(), user.getUserPassword(), authorities);
    }
}
