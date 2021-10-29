package com.bwell.modules.fitwell.activities.service;

import com.bwell.modules.base.Entry;
import com.bwell.modules.fitwell.activities.model.Activity;

import java.util.List;

public interface IActivitiesService {
    List<Entry> getAllActivities();
    Activity addActivity(Activity activity);
}
