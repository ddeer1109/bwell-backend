package com.bwell.modules.thinkwell.exercises.service;

import com.bwell.modules.base.*;
import com.bwell.modules.base.content.ContentRepository;
import com.bwell.modules.base.entry.Entry;
import com.bwell.modules.base.entry.EntryRepository;
import com.bwell.modules.base.rating.RatingRepository;
import com.bwell.modules.thinkwell.exercises.model.Exercise;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ExerciseService extends BaseService implements IExerciseService{

    @Autowired
    public ExerciseService(ContentRepository content, EntryRepository entry, RatingRepository rating) {
        super(content, entry, rating);
    }

    @Override
    public Exercise addExercise(Exercise exercise) {
        rating.save(exercise.getRating());
        content.saveAll(exercise.getContent());
        Exercise tempExercise = entry.save(exercise);

        log.info("Returning {}", tempExercise);

        return tempExercise;
    }

    @Override
    public List<Entry> getAllExercises() {
        return entry.findAllByModuleEquals("exercise");
    }

    @Override
    public boolean deleteExercise(Long id) {
        entry.deleteById(id);
        return true;
    }

    @Override
    public Exercise getExercise(Long id) {
        return (Exercise) entry
                .findById(id)
                .orElseThrow();
    }
}
