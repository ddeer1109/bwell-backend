package com.bwell.modules.restwell.ideas.service;

import com.bwell.modules.base.Entry;
import com.bwell.modules.restwell.ideas.model.Idea;

import java.util.List;

public interface IIdeasService {
    List<Entry> getAllIdeas();
    Idea addIdea(Idea idea);
    boolean deleteIdea(Long id);

    Idea getIdea(Long id);
}
