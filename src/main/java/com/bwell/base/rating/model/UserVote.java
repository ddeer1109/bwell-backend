package com.bwell.base.rating.model;

import com.bwell.base.entry.Entry;
import com.bwell.user.data.model.User;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class UserVote {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Entry entry;

    private Vote voteValue;

    public UserVote(User user, Entry entry, Vote voteValue) {
        this.user = user;
        this.entry = entry;
        this.voteValue = voteValue;
    }

    public UserVote(){}



    public enum Vote {
        DOWN,UP
    }

    public static UserVote create(User user, Entry entry, Vote voteValue){
        return new UserVote(user, entry, voteValue);
    }

}
