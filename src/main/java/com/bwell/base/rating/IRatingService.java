package com.bwell.base.rating;

import com.bwell.base.entry.Entry;
import com.bwell.base.rating.model.Rating;
import com.bwell.base.rating.model.UserVote;
import com.bwell.security.UserPrincipal;
import com.bwell.user.data.model.User;

import java.util.List;

public interface IRatingService {
    List<UserVote> getUserVotes(User user);
    Rating voteForEntry(User user, Entry entry, UserVote.Vote voteValue);
    Rating undoVote(UserVote vote);
    UserVote getUserVoteOnEntry(UserPrincipal user, Long entryId);
}
