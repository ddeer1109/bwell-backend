package com.bwell.modules.thinkwell.exercises.service;

import com.bwell.base.*;
import com.bwell.base.content.ContentRepository;
import com.bwell.base.entry.Entry;
import com.bwell.base.entry.EntryRepository;
import com.bwell.base.rating.repository.RatingRepository;
import com.bwell.modules.thinkwell.exercises.model.Exercise;
import com.bwell.user.data.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ExerciseService extends BaseService implements IExerciseService{
    @Autowired
    public ExerciseService(ContentRepository content, EntryRepository entry, RatingRepository rating, UserService userService) {
        super(content, entry, rating, userService);
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
