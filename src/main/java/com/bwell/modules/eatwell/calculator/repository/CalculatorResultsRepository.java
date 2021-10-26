package com.bwell.modules.eatwell.calculator.repository;

import com.bwell.modules.eatwell.calculator.model.dtos.NutrientsDemandDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalculatorResultsRepository extends JpaRepository<NutrientsDemandDao, Long> {

}
