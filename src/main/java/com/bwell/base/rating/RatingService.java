package com.bwell.base.rating;

import com.bwell.base.entry.Entry;
import com.bwell.base.entry.EntryRepository;
import com.bwell.base.rating.model.Rating;
import com.bwell.base.rating.model.UserVote;
import com.bwell.base.rating.repository.RatingRepository;
import com.bwell.base.rating.repository.UserVoteRepository;
import com.bwell.security.UserPrincipal;
import com.bwell.user.data.model.User;
import com.bwell.user.data.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class RatingService implements IRatingService {
    private EntryRepository entryRepo;
    private RatingRepository ratingRepo;
    private UserVoteRepository userVoteRepo;
    private UserService userService;

    @Autowired
    public RatingService(EntryRepository entryRepo, RatingRepository ratingRepo, UserVoteRepository userVoteRepo, UserService userService) {
        this.entryRepo = entryRepo;
        this.ratingRepo = ratingRepo;
        this.userVoteRepo = userVoteRepo;
        this.userService = userService;
    }

    @Override
    public List<UserVote> getUserVotes(User user) {
        return null;
    }

    @Override
    public Rating voteForEntry(User user, Entry entry, UserVote.Vote voteValue) {
        Optional<UserVote> userVote = userVoteRepo.getByEntry_IdAndUser_Id(entry.getId(), user.getId());
        Rating rating1 = userVote
                .map(vote -> {
                    Rating rating = undoVote(vote);
                    vote.setVoteValue(voteValue);

                    rating.vote(voteValue, false);

                    Rating rating2 = ratingRepo.save(rating);
                    UserVote userVote1 = userVoteRepo.save(vote);
                    return rating2;

                })
                .orElseGet(() -> {
                    Rating rating = entry.getRating();
                    rating.vote(voteValue, false);
                    Rating newRating = ratingRepo.save(rating);

                    UserVote newVote = UserVote.create(user, entry, voteValue);
                    userVoteRepo.save(newVote);

                    return newRating;
        });
        return rating1;
    }


    @Override
    public Rating undoVote(UserVote vote) {
        Rating rating = vote.getEntry().getRating();
        rating.vote(vote.getVoteValue(), true);

        return ratingRepo.save(rating);
    }

    @Override
    public UserVote getUserVoteOnEntry(UserPrincipal user, Long entryId) {
        if (user == null) {
            throw new UsernameNotFoundException("unloggd user dont have votes");
        }
        return Optional
                .ofNullable(user)
                .flatMap(userPrincipal -> userVoteRepo.getByEntry_IdAndUser_Id(entryId, userService.getCredentialsById(userPrincipal.getId()).getUser().getId()))
                .orElse(null);

    }

}
