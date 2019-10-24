package com.mikovic.demoshopinternet.services;


import com.mikovic.demoshopinternet.entities.SystemUser;
import com.mikovic.demoshopinternet.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findByUserName(String userName);
    void save(SystemUser  systemUser);
    void addToSecurityContext(SystemUser systemUser);
}
