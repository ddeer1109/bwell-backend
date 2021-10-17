package com.example.students.eatwell.recipes.ingredients.repositories;

import com.example.students.eatwell.recipes.ingredients.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
//    @Query("select new com.foo.bar.entity.Document(d.docId, d.filename) from Document d where d.filterCol = ?1")
    public Optional<Unit> findUnitByNameEquals(String name);


    @Query(
            value =
                    "insert into ingredient_unit (ingredient_id, unit_id) values " +
                            "(:ingredient_id, :unit_id) returning ingredient_id as id;",
            nativeQuery = true)
    public long insertUnitIngredientPair(@Param("ingredient_id") long ingredientId, @Param("unit_id") long unitId);
    @Query(
            value =
                    "SELECT ingredient_id as id, u.name as name FROM ingredient_unit INNER JOIN unit u on ingredient_unit.unit_id = u.id WHERE id=:ingredient_id ",
            nativeQuery = true)
    public Set<Unit> getAllByIngredientId(@Param("ingredient_id") long ingredientId);

}
