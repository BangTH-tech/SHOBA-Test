package com.project_shoba_test.SHOBA_TEST.service.user.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project_shoba_test.SHOBA_TEST.exception.UnauthorizedException;
import com.project_shoba_test.SHOBA_TEST.model.entity.UserPrincipal;
import com.project_shoba_test.SHOBA_TEST.model.entity.Users;
import com.project_shoba_test.SHOBA_TEST.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.existsByUsernameOrEmail(username)
                .orElseThrow(() -> new UnauthorizedException("User not found", "User not found"));
        UserDetails userDetails = new UserPrincipal(user);
        return userDetails;

    }

}