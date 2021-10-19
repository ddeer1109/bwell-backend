package com.bwell.modules.eatwell.recipes.ingredients.repositories;

import com.bwell.modules.eatwell.recipes.ingredients.model.DetailedIngredient;
import com.bwell.modules.eatwell.recipes.ingredients.model.Ingredient;
import com.bwell.modules.eatwell.recipes.ingredients.model.Unit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class IngredientRepositoryImpl implements CommonIngredientsRepository {
    private final Logger logger = LoggerFactory.getLogger(IngredientRepositoryImpl.class);
    private final DetailedIngredientsRepository detailedIngredientsRepository;

    private final IngredientsRepository ingredientsRepository;

    private final UnitRepository unitRepository;

    @Autowired
    public IngredientRepositoryImpl(DetailedIngredientsRepository custom, IngredientsRepository standard, UnitRepository unit) {
        this.detailedIngredientsRepository = custom;
        this.ingredientsRepository = standard;
        this.unitRepository = unit;
    }

    @Override
    public DetailedIngredient persistentSave(DetailedIngredient detailedIngr) {
        Ingredient ingredientCompliantWithDB = persistentSave(detailedIngr.getIngredient());

        detailedIngr.setUnit(
                unitRepository.findUnitByNameEquals(detailedIngr.getUnit().getName())
                    .orElse(ingredientCompliantWithDB
                            .getUnits().stream()
                            .filter(unit -> unit.equals(detailedIngr.getUnit()))
                            .findFirst()
                            .orElse(detailedIngr.getUnit())
                    )
        );
        long id = detailedIngredientsRepository.insertWithExistingIngredient(
                detailedIngr.getIngredientId(),
                detailedIngr.getAmount().doubleValue(),
                detailedIngr.getUnit().getId()
        );
        DetailedIngredient byId = detailedIngredientsRepository
                .findById(id)
                .orElse(detailedIngr);
        byId.setNutrition(detailedIngr.getNutrition());

        return byId;
    }
    @Override
    public List<Ingredient> persistentSave(List<Ingredient> ingredients) {
        return ingredients
                .stream()
                .map(this::persistentSave)
                .collect(Collectors.toList());
    }
    @Override
    public Ingredient persistentSave(Ingredient ingredient) {
        Ingredient ingredientCompliantWithDb = saveIngredientIfNotExist(ingredient);

        Set<Unit> unitsCompliantWithDb = persistentSave(ingredientCompliantWithDb.getId(), ingredient.getUnits());
        ingredientCompliantWithDb.setUnits(unitsCompliantWithDb);

        return ingredientCompliantWithDb;
    }
    @Override
    public Set<Unit> persistentSave(long compliantIngredientId, Set<Unit> ingredientUnits) {
        return ingredientUnits.stream().map(unit -> {

            Unit unitCompliantWithDb = unitRepository
                    .findUnitByNameEquals(unit.getName())
                    .orElseGet(() -> saveUnit(unit));

            saveIngredientUnitPair(compliantIngredientId, unitCompliantWithDb.getId());

            logger.warn("END UNIT ----> ", unitCompliantWithDb);
            return unitCompliantWithDb;

        }).collect(Collectors.toSet());
    }
    private Ingredient saveIngredientIfNotExist(Ingredient ingredient) {
        return ingredientsRepository
                .findById(ingredient.getId())
                .orElseGet(() -> {
                    long id = ingredientsRepository
                            .insertWithoutUnits(ingredient.getId(), ingredient.getName());
                    return ingredientsRepository
                            .findById(id)
                            .orElse(ingredient);
        });
    }
    private void saveIngredientUnitPair(long ingredientId, long unitId) {
        try {
            unitRepository.getIdByIngredientUnitIdPair(ingredientId, unitId)
                    .ifPresentOrElse(
                            (id) -> System.out.println("ALready present " + id),
                            () -> unitRepository.insertUnitIngredientPair(ingredientId, unitId)
                    );

        } catch (Exception e){
            logger.error("FAILED TO INSERT PAIR ", e);
        }
    }
    private Unit saveUnit(Unit unit) {
        try{
            return unitRepository.save(unit);
        } catch (Exception e){
            e.printStackTrace();
            logger.error("Error!!!", e);
            return unit;
        }
    }
}
