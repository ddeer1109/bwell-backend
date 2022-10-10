package com.bwell.modules.fitwell.activities.service;

import com.bwell.base.*;
import com.bwell.base.content.ContentRepository;
import com.bwell.base.entry.Entry;
import com.bwell.base.entry.EntryRepository;
import com.bwell.base.rating.repository.RatingRepository;
import com.bwell.modules.fitwell.activities.model.Activity;
import com.bwell.user.data.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ActivitiesService extends BaseService implements IActivitiesService {

    @Autowired
    public ActivitiesService(ContentRepository content, EntryRepository entry, RatingRepository rating, UserService userService) {
        super(content, entry, rating, userService);
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

    @Override
    public Activity getActivity(Long id) {
        return (Activity) entry
                .findById(id)
                .orElseThrow();
    }
}
