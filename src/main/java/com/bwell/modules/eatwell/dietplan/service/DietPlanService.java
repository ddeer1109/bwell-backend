package com.bwell.modules.eatwell.dietplan.service;

import com.bwell.modules.base.entry.EntryRepository;
import com.bwell.modules.eatwell.dietplan.model.DietPlan;
import com.bwell.modules.eatwell.dietplan.repository.DietPlanRepository;
import com.bwell.modules.eatwell.recipes.model.Recipe;
import com.bwell.modules.user.data.model.User;
import com.bwell.modules.user.data.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DietPlanService implements IDietPlanService{
    private EntryRepository recipesRepo;
    private UserRepository userRepo;
    private DietPlanRepository planRepo;

    public DietPlanService(EntryRepository recipesRepo, UserRepository userRepo, DietPlanRepository planRepo) {
        this.recipesRepo = recipesRepo;
        this.userRepo = userRepo;
        this.planRepo = planRepo;
    }

    @Override
    public DietPlan getDietPlan(long userId) {
        User user = userRepo.findById(userId).orElseThrow();
        return user.getDietPlan();
    }

    @Override
    public Recipe setBreakfast(long recipeId, long userId) {
        User user = userRepo.findById(userId).orElseThrow();
        Recipe recipe = (Recipe) recipesRepo.findById(recipeId).orElse(null);
        user.getDietPlan().setBreakfast(recipe);
        userRepo.save(user);
        return recipe;
    }

    @Override
    public Recipe setLunch(long recipeId, long userId) {
        User user = userRepo.findById(userId).orElseThrow();
        Recipe recipe = (Recipe) recipesRepo.findById(recipeId).orElse(null);
        user.getDietPlan().setLunch(recipe);
        userRepo.save(user);
        return recipe;
    }

    @Override
    public Recipe setDinner(long recipeId, long userId) {
        User user = userRepo.findById(userId).orElseThrow();
        Recipe recipe = (Recipe) recipesRepo.findById(recipeId).orElse(null);
        user.getDietPlan().setDinner(recipe);
        userRepo.save(user);
        return recipe;
    }

    @Override
    public Recipe setSupper(long recipeId, long userId) {
        User user = userRepo.findById(userId).orElseThrow();
        Recipe recipe = (Recipe) recipesRepo.findById(recipeId).orElse(null);
        user.getDietPlan().setSupper(recipe);
        userRepo.save(user);
        return recipe;
    }

    @Override
    public Recipe getBreakfast(long userId) {
        User user = userRepo.findById(userId).orElseThrow();
        return user.getDietPlan().getBreakfast();
    }

    @Override
    public Recipe getLunch(long userId) {
        User user = userRepo.findById(userId).orElseThrow();
        return user.getDietPlan().getLunch();
    }

    @Override
    public Recipe getDinner(long userId) {
        User user = userRepo.findById(userId).orElseThrow();
        return user.getDietPlan().getDinner();
    }

    @Override
    public Recipe getSupper(long userId) {
        User user = userRepo.findById(userId).orElseThrow();
        return user.getDietPlan().getSupper();
    }

    @Override
    public List<Recipe> getAllMeals(long userId) {
        User user = userRepo.findById(userId).orElseThrow();
        DietPlan plan = user.getDietPlan();
        return List.of(plan.getBreakfast(), plan.getLunch(), plan.getDinner(), plan.getSupper());
    }
}
