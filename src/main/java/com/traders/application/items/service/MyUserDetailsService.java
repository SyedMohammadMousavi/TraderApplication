package com.traders.application.items.service;

import com.traders.application.items.entity.Users;
import com.traders.application.items.repository.UserRepository;
import com.traders.application.items.util.MyUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Users> byUsername = userRepository.findByUsername(username);

        byUsername.orElseThrow(() -> new UsernameNotFoundException("Username Does not Exists!" + username));

        return byUsername.map((Users user) -> new MyUserDetail(user)).get();
    }
}
