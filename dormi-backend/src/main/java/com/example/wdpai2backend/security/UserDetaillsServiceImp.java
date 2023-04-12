package com.example.wdpai2backend.security;

import com.example.wdpai2backend.entity.AppUser;
import com.example.wdpai2backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetaillsServiceImp implements UserDetailsService {

    private UserRepository userRepository;

        @Autowired
    public UserDetaillsServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser= userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("user not found"));
            return   new User(appUser.getUsername(), appUser.getPassword(), appUser.getAuthorities());
    }


}
