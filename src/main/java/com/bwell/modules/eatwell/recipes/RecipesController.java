package com.bwell.modules.eatwell.recipes;

import com.bwell.modules.base.Entry;
import com.bwell.modules.eatwell.recipes.model.Recipe;
import com.bwell.modules.eatwell.recipes.service.IRecipesService;
import com.bwell.modules.eatwell.recipes.service.RecipesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/v1/eatwell/recipes")
public class RecipesController {

    private final IRecipesService service;

    @Autowired
    public RecipesController(RecipesService service) {
        this.service = service;
    }

    @GetMapping("/")
    public List<Entry> getAllRecipes(){
        return service.getAllRecipes();
    }

    @GetMapping("/{id}")
    public Recipe getAllRecipes(@PathVariable("id") Long id){
        return service.getRecipe(id);
    }

    @PostMapping("/")
    public Recipe addRecipe(@RequestBody Recipe recipe){

//        rating.save(element.getRating());
//        log.error("=---------------->               {}", element);
//        Recipe save = recipes.save(element);
////        content.saveAll(save.getContents());
//
//        content.saveAll(element.getContent());
//        Entry save = entry.save(element);
//
//        log.error("=--------------dfasdfad-->               {}", save);
//        log.error("=--------------dfasdfad-->               {}", save);
//        log.error("=--------------dfasdfad-->               {}", save);

        return service.addRecipe(recipe);
    }



}
