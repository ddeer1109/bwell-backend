package com.bwell.modules.restwell.ideas;

import com.bwell.base.entry.Entry;
import com.bwell.modules.restwell.ideas.model.Idea;
import com.bwell.modules.restwell.ideas.service.IIdeasService;
import com.bwell.modules.restwell.ideas.service.IdeasService;
import com.bwell.security.CurrentUser;
import com.bwell.security.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin("${FRONTEND_HOST}")
@RequestMapping("/api/v1/restwell/ideas")
public class IdeasController {

    private final IIdeasService service;

    @Autowired
    public IdeasController(IdeasService service) {
        this.service = service;
    }

    @GetMapping("/")
    public List<Entry> getAllIdeas() {
        return service.getAllIdeas();
    }

    @GetMapping("/{id}")
    public Idea getIdea(@PathVariable("id") Long id) {
        return service.getIdea(id);
    }

    @PostMapping("/")
    public Idea addIdea(@RequestBody Idea idea) {
        return service.addIdea(idea);
    }

    @DeleteMapping("/{id}")
    public boolean deleteIdea(@PathVariable("id") Long id, @CurrentUser UserPrincipal user) {
        return service.isAuthor(user, id) && service.deleteIdea(id);
    }
}
