package com.bwell.modules.eatwell.recipes.ingredients.repositories;

import com.bwell.modules.eatwell.recipes.ingredients.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
//    @Query("select new com.foo.bar.entity.Document(d.docId, d.filename) from Document d where d.filterCol = ?1")
    Optional<Unit> findUnitByNameEquals(String name);

    @Query(
            value =
                    "insert into ingredient_unit (ingredient_id, unit_id) values (:ingr_id, :unit_id) returning ingredient_id as id ",
            nativeQuery = true)
    long insertUnitIngredientPair(@Param("ingr_id") long ingredient_id, @Param("unit_id") long unit_id);


    @Query(
            value =
                    "SELECT unit_id FROM ingredient_unit WHERE unit_id=:unit_id AND ingredient_id=:ingr_id ",
            nativeQuery = true)
    Optional<Long> getIdByIngredientUnitIdPair(@Param("ingr_id") long ingredient_id, @Param("unit_id") long unit_id);


    @Query(
            value =
                    "SELECT ingredient_id as id, u.name as name FROM ingredient_unit INNER JOIN unit u on ingredient_unit.unit_id = u.id WHERE id=:ingredient_id ",
            nativeQuery = true)
    Set<Unit> getAllByIngredientId(@Param("ingredient_id") long ingredientId);

}
