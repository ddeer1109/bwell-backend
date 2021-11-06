package com.bwell.modules.eatwell.calculator.repository;

import com.bwell.modules.eatwell.calculator.model.CalculatorData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalculationDataRepository extends JpaRepository<CalculatorData, Long> {
}
