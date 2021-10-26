package com.bwell.modules.thinkwell.exercises;

import com.bwell.modules.thinkwell.exercises.service.ExerciseService;
import com.bwell.modules.thinkwell.exercises.service.IExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExercisesController {

    private final IExerciseService service;

    @Autowired
    public ExercisesController(ExerciseService service) {
        this.service = service;
    }
}
