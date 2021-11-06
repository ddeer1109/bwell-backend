package com.bwell.modules.eatwell.recipes.ingredients.repositories;

import com.bwell.modules.eatwell.recipes.ingredients.model.DetailedIngredient;
import com.bwell.modules.eatwell.recipes.ingredients.model.Ingredient;
import com.bwell.modules.eatwell.recipes.ingredients.model.Unit;
import com.bwell.modules.eatwell.recipes.ingredients.nutrition.Nutrients;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class IngredientRepositoryImpl implements CommonIngredientsRepository {
    private final Logger logger = LoggerFactory.getLogger(IngredientRepositoryImpl.class);
    private final DetailedIngredientsRepository detailedIngredientsRepository;

    private final IngredientsRepository ingredientsRepository;
    private final UnitRepository unitRepository;
    private final NutrientsRepository nutrientsRepository;
    @Autowired
    public IngredientRepositoryImpl(DetailedIngredientsRepository custom,
                                    IngredientsRepository standard,
                                    UnitRepository unit, NutrientsRepository nutrientsRepository) {
        this.detailedIngredientsRepository = custom;
        this.ingredientsRepository = standard;
        this.unitRepository = unit;
        this.nutrientsRepository = nutrientsRepository;
    }

    @Override
    public DetailedIngredient persistentSave(DetailedIngredient detailedIngr) {
        Ingredient ingredientCompliantWithDB = persistentSave(detailedIngr.getIngredient());

        detailedIngr.setUnit(
                unitRepository
                        .findUnitByNameEquals(detailedIngr.getUnit().getName())
                        .orElse(ingredientCompliantWithDB
                            .getUnits().stream()
                            .filter(unit -> unit.equals(detailedIngr.getUnit()))
                            .findFirst()
                            .orElse(detailedIngr.getUnit())
                    )
        );
        DetailedIngredient byId = detailedIngredientsRepository
                .findById(detailedIngr.getId())
                .orElseGet(() -> {
                    long id = detailedIngredientsRepository
                            .insertWithExistingIngredient(
                                    detailedIngr.getIngredientId(),
                                    detailedIngr.getAmount().doubleValue(),
                                    detailedIngr.getUnit().getId());
                    return detailedIngredientsRepository.getById(id);
                });

        Nutrients nutrition = detailedIngr.getNutrition();
//        nutrition.setIngredientId(byId.getIngredientId());
        nutrition.setIngredient(byId);
        Nutrients nutrientsSaved = nutrientsRepository.save(nutrition);
        byId.setNutrition(nutrientsSaved);

        try{
            return detailedIngredientsRepository.save(byId);
        }catch (Exception e){
            log.error("Saving Detailed ingredient ====== > {} ======> {}", byId.getName(), e.getLocalizedMessage());
            e.printStackTrace();
            return byId;
        }
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

            logger.warn("END UNIT ----> {} ", unitCompliantWithDb);
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
            logger.debug("CHECKING SELECT BY PAIR : ${} ${}", ingredientId, unitId);
            Optional<Long> idByIngredientUnitIdPair = unitRepository.getIdByIngredientUnitIdPair(ingredientId, unitId);
            logger.debug("CHECKING SELECT BY PAIR ---- RESULT : ${}", idByIngredientUnitIdPair);
            idByIngredientUnitIdPair
                    .ifPresentOrElse(
                            (id) -> System.out.println("ALready present ${} " + id),
                            () -> {
                                logger.info("Check those ids: ${} ${}", ingredientId, unitId);
                                unitRepository.insertUnitIngredientPair(ingredientId, unitId);
                            }
                    );

        } catch (Exception e){
            logger.debug("FAILED TO INSERT PAIR ${}", e.toString());
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
