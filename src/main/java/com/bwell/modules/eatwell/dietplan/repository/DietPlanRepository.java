package com.bwell.modules.eatwell.dietplan.repository;

import com.bwell.modules.eatwell.dietplan.model.DietPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DietPlanRepository extends JpaRepository<DietPlan, Long> {
}
