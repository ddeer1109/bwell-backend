package com.bwell.modules.eatwell.recipes.service;

import com.bwell.modules.base.*;
import com.bwell.modules.eatwell.recipes.model.Recipe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RecipesService extends BaseService implements IRecipesService {

    @Autowired
    public RecipesService(ContentRepository content, EntryRepository entry, RatingRepository rating) {
        super(content, entry, rating);
    }

    @Override
    public Recipe getRecipe(Long id) {
        return (Recipe)entry.getById(id);
    }

    @Override
    public List<Entry> getAllRecipes() {
        return entry.findAllByModuleEquals("recipe");
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
