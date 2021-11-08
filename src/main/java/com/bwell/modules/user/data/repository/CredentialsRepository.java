package com.bwell.modules.user.data.repository;

import com.bwell.modules.user.data.model.Credentials;
import com.bwell.modules.user.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredentialsRepository extends JpaRepository<Credentials, String> {
    Optional<Credentials> findByEmail(String email);
}
