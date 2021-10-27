package com.bwell.modules.thinkwell.exercises.service;

import com.bwell.modules.base.Entry;
import com.bwell.modules.fitwell.activities.model.Activity;
import com.bwell.modules.thinkwell.exercises.model.Exercise;

import java.util.List;

public interface IExerciseService {
    Exercise addExercise(Exercise exercise);
    List<Entry> getAllExercises();
    boolean deleteExercise(Long id);
}
