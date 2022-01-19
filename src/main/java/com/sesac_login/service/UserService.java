package com.sesac_login.service;

import com.sesac_login.model.Role;
import com.sesac_login.model.User;
import com.sesac_login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {  //비즈니스 로직

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;



    public User save(User user){
        String encodedPassword = passwordEncoder.encode(user.getPassword());        //사용자가 전달한 비밀번호를 인코딩 암호화
        user.setPassword(encodedPassword);//저장
        user.setEnabled(true);  //활성화
        Role role = new Role();
        role.setId(1l);
        user.getRoles().add(role);
        return userRepository.save(user);
    }

}
