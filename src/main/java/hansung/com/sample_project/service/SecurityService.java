package hansung.com.sample_project.service;

import hansung.com.sample_project.domain.User;
import hansung.com.sample_project.dto.UserInfo;
import hansung.com.sample_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class SecurityService {
    @Autowired
    private UserRepository userRepository;

    public User getUser(HttpSession httpSession){
        //Todo findByUserId == null??
        UserInfo userInfo = (UserInfo)httpSession.getAttribute("userInfo");
        return userRepository.findByUserId(userInfo.getUserId()).get(0);
    }
}
