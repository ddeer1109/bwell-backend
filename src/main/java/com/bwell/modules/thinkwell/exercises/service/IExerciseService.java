package com.bwell.modules.thinkwell.exercises.service;

import com.bwell.modules.base.IBaseService;
import com.bwell.modules.base.entry.Entry;
import com.bwell.modules.thinkwell.exercises.model.Exercise;

import java.util.List;

public interface IExerciseService extends IBaseService {
    Exercise addExercise(Exercise exercise);
    List<Entry> getAllExercises();
    boolean deleteExercise(Long id);

    Exercise getExercise(Long id);
}
