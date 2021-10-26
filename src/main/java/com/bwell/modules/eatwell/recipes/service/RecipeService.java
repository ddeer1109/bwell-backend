package com.bwell.modules.eatwell.recipes.service;

import com.bwell.modules.base.ContentRepository;
import com.bwell.modules.base.Entry;
import com.bwell.modules.base.EntryRepository;
import com.bwell.modules.base.RatingRepository;
import com.bwell.modules.eatwell.recipes.model.Recipe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RecipeService implements IRecipeService{

    ContentRepository content;
    EntryRepository entry;
    RatingRepository rating;

    @Autowired
    public RecipeService(ContentRepository content, EntryRepository entry, RatingRepository rating) {
        this.content = content;
        this.entry = entry;
        this.rating = rating;
    }

    @Override
    public Recipe getRecipe(Long id) {
        return null;
    }

    @Override
    public List<Entry> getAllRecipes() {
        return entry.findAll();
    }

    @Override
    public Recipe addRecipe(Recipe recipe) {
        rating.save(recipe.getRating());
        content.saveAll(recipe.getContent());
        Recipe tempRecipe = entry.save(recipe);

        log.info("Returning {}", tempRecipe);

        return tempRecipe;
    }

    @Override
    public boolean deleteRecipe(Long id) {
        return false;
    }
}
