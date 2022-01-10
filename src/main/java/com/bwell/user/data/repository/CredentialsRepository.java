package com.bwell.user.data.repository;

import com.bwell.user.data.model.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredentialsRepository extends JpaRepository<Credentials, String> {
    Optional<Credentials> findByEmail(String email);
}
