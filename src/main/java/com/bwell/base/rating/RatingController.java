package com.bwell.base.rating;

import com.bwell.base.rating.model.Rating;
import com.bwell.base.rating.model.UserVote;
import com.bwell.security.CurrentUser;
import com.bwell.security.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin("${FRONTEND_HOST}")
@RequestMapping("/api/v1/vote")
public class RatingController {
    private IRatingService service;

    @Autowired
    public RatingController(IRatingService service) {
        this.service = service;
    }

    @PostMapping("/")
    public Rating vote(@RequestBody UserVote vote){
        log.info("{}, ", vote);
        return service.voteForEntry(vote.getUser(), vote.getEntry(), vote.getVoteValue());
    }

    @GetMapping("/{entryId}")
    public UserVote getCurrentVote(@CurrentUser UserPrincipal user, @PathVariable("entryId") Long entryId){
        UserVote userVoteOnEntry = service.getUserVoteOnEntry(user, entryId);
        log.info("{}", userVoteOnEntry);
        return userVoteOnEntry;
    }
}
