package com.bwell.modules.user.data.service;

import com.bwell.modules.user.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface IUserService {
    UserDetails loadById(String id);
}
