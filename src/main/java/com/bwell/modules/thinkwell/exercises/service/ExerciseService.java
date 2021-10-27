package com.bwell.modules.thinkwell.exercises.service;

import com.bwell.modules.base.ContentRepository;
import com.bwell.modules.base.Entry;
import com.bwell.modules.base.EntryRepository;
import com.bwell.modules.base.RatingRepository;
import com.bwell.modules.thinkwell.exercises.model.Exercise;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ExerciseService implements IExerciseService{
    private final ContentRepository content;
    private final EntryRepository entry;
    private final RatingRepository rating;

    public ExerciseService(ContentRepository content, EntryRepository entry, RatingRepository rating) {
        this.content = content;
        this.entry = entry;
        this.rating = rating;
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
}
