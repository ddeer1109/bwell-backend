package com.bwell.modules.eatwell.recipes;

import com.bwell.modules.base.ContentElement;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/v1/eatwell/recipes")
public class RecipesController {
    private final ObjectMapper mapper = new ObjectMapper();
    Logger logger = LoggerFactory.getLogger(com.bwell.modules.eatwell.recipes.RecipesController.class);

    @Autowired
    public RecipesController() {


    }

    @GetMapping("/")
    public ContentElement check(@RequestBody ContentElement element){

        return element;
    }

}
