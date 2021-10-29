package com.bwell.modules.fitwell.activities.repository;

import com.bwell.modules.fitwell.activities.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
