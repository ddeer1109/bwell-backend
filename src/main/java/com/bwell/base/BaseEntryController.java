package com.bwell.base;

import com.bwell.base.entry.Entry;
import com.bwell.modules.eatwell.recipes.model.Recipe;
import com.bwell.security.CurrentUser;
import com.bwell.security.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin("${FRONTEND_HOST}")
@RequestMapping("/api/v1/entry")
public class BaseEntryController {
    private IBaseService baseService;

    @Autowired
    public BaseEntryController(IBaseService baseService) {
        this.baseService = baseService;
    }

    @GetMapping("/{id}")
    public Recipe getEntryByIdIfAuthorised(@CurrentUser UserPrincipal userPrincipal, @PathVariable long id){
        if (!baseService.isAuthor(userPrincipal, id)){
            return null;
        }
        return (Recipe) baseService.findEntryById(id);

    }

}
