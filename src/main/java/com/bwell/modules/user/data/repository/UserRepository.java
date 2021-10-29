package com.bwell.modules.user.data.repository;

import com.bwell.modules.user.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
