package com.bwell.modules.restwell.ideas.service;

import com.bwell.modules.base.ContentRepository;
import com.bwell.modules.base.Entry;
import com.bwell.modules.base.EntryRepository;
import com.bwell.modules.base.RatingRepository;
import com.bwell.modules.fitwell.activities.model.Activity;
import com.bwell.modules.restwell.ideas.model.Idea;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class IdeasService implements IIdeasService {
    private final ContentRepository content;
    private final EntryRepository entry;
    private final RatingRepository rating;

    public IdeasService(ContentRepository content, EntryRepository entry, RatingRepository rating) {
        this.content = content;
        this.entry = entry;
        this.rating = rating;
    }

    @Override
    public List<Entry> getAllIdeas() {
        return entry.findAllByModuleEquals("idea");
    }

    @Override
    public Idea addIdea(Idea idea) {
        rating.save(idea.getRating());
        content.saveAll(idea.getContent());
        Idea tempIdea = entry.save(idea);

        log.info("Returning {}", tempIdea);

        return tempIdea;
    }
}
