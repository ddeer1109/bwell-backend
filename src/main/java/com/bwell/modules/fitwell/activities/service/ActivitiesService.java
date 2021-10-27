package com.bwell.modules.fitwell.activities.service;

import com.bwell.modules.base.ContentRepository;
import com.bwell.modules.base.Entry;
import com.bwell.modules.base.EntryRepository;
import com.bwell.modules.base.RatingRepository;
import com.bwell.modules.fitwell.activities.model.Activity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ActivitiesService implements IActivitiesService {
    private final ContentRepository content;
    private final EntryRepository entry;
    private final RatingRepository rating;

    @Autowired
    public ActivitiesService(ContentRepository content, EntryRepository entry, RatingRepository rating) {
        this.content = content;
        this.entry = entry;
        this.rating = rating;
    }

    @Override
    public List<Entry> getAllActivities() {
        return entry.findAllByModuleEquals("activity");
    }

    @Override
    public Activity addActivity(Activity activity) {
        rating.save(activity.getRating());
        content.saveAll(activity.getContent());
        Activity tempActivity = entry.save(activity);

        log.info("Returning {}", tempActivity);

        return tempActivity;
    }

    @Override
    public boolean deleteActivity(Long id) {
        entry.deleteById(id);

        return true;
    }
}
