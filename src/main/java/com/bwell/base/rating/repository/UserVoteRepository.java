package com.bwell.base.rating.repository;

import com.bwell.base.rating.model.UserVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserVoteRepository extends JpaRepository<UserVote, Long> {
    Optional<UserVote> getByEntry_IdAndUser_Id(Long entryId, Long userId);
}
