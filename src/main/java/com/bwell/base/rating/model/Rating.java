package com.bwell.base.rating.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private int up;
    private int down;

    public void voteUp(int voteValue) {
        up += voteValue;
    }
    public void voteDown(int voteValue) {
        down += voteValue;
    }

    public void vote(UserVote.Vote voteValue, boolean isUndo){
        int value = isUndo ? -1 : 1;

        if (voteValue.equals(UserVote.Vote.UP))
            voteUp(value);
        else if (voteValue.equals(UserVote.Vote.DOWN))
            voteDown(value);
    }

}
