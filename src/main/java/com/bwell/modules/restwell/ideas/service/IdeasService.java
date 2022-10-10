package com.bwell.modules.restwell.ideas.service;

import com.bwell.base.*;
import com.bwell.base.content.ContentRepository;
import com.bwell.base.entry.Entry;
import com.bwell.base.entry.EntryRepository;
import com.bwell.base.rating.repository.RatingRepository;
import com.bwell.modules.restwell.ideas.model.Idea;
import com.bwell.user.data.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class IdeasService extends BaseService implements IIdeasService {

    @Autowired
    public IdeasService(ContentRepository content, EntryRepository entry, RatingRepository rating, UserService userService) {
        super(content, entry, rating, userService);
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

    @Override
    public boolean deleteIdea(Long id) {
        entry.deleteById(id);
        return true;
    }

    @Override
    public Idea getIdea(Long id) {
        return (Idea) entry
                .findById(id)
                .orElseThrow();
    }
}
