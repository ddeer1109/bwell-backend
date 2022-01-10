package com.bwell.user.data.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface IUserService {
    UserDetails loadById(String id);
}
