package com.bwell.modules.fitwell.activities.service;

import com.bwell.base.IBaseService;
import com.bwell.base.entry.Entry;
import com.bwell.modules.fitwell.activities.model.Activity;

import java.util.List;

public interface IActivitiesService extends IBaseService {
    List<Entry> getAllActivities();
    Activity addActivity(Activity activity);
    boolean deleteActivity(Long id);

    Activity getActivity(Long id);
}
