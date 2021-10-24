package com.bwell.modules.eatwell.recipes;

import com.bwell.modules.base.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;


@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/v1/eatwell/recipes")
public class RecipesController {
    private final ObjectMapper mapper = new ObjectMapper();
    Logger logger = LoggerFactory.getLogger(com.bwell.modules.eatwell.recipes.RecipesController.class);

    ContentRepository content;
    EntryRepository entry;
//    RecipeRepository recipes;
    RatingRepository rating;
//    ActivityRepository activity;
    @Autowired
    public RecipesController(ContentRepository content, EntryRepository entry, RatingRepository rating) {
        this.content = content;
        this.entry = entry;
        this.rating = rating;
    }

//    @Autowired
//    public RecipesController(ContentRepository content, EntryRepository entry, RecipeRepository recipes, RatingRepository rating, ActivityRepository activity) {
//        this.content = content;
//        this.entry = entry;
//        this.recipes = recipes;
//        this.rating = rating;
//        this.activity = activity;
//    }


    @GetMapping("/")
    public ContentElement check(@RequestBody ContentElement element){
        ContentElement save = content.save(element);
        return save;
    }

    @GetMapping("/entry")
    public Entry entry(@RequestBody Entry element){

        rating.save(element.getRating());
        logger.error("=---------------->               {}", element);
//        Recipe save = recipes.save(element);
//        content.saveAll(save.getContents());

        content.saveAll(element.getContent());
        Entry save = entry.save(element);

        logger.error("=--------------dfasdfad-->               {}", save);
        logger.error("=--------------dfasdfad-->               {}", save);
        logger.error("=--------------dfasdfad-->               {}", save);

        return save;
    }

}
