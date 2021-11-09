package com.bwell.modules.fitwell.activities;

import com.bwell.modules.base.entry.Entry;
import com.bwell.modules.fitwell.activities.model.Activity;
import com.bwell.modules.fitwell.activities.service.ActivitiesService;
import com.bwell.modules.fitwell.activities.service.IActivitiesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin("https://bwell-frontend.herokuapp.com/")
@RequestMapping("/api/v1/fitwell/activities")
public class ActivitiesController {

    private final IActivitiesService service;


    @Autowired
    public ActivitiesController(ActivitiesService service) {
        this.service = service;
    }

    @GetMapping("/")
    public List<Entry> getAllActivities() {
        return service.getAllActivities();
    }

    @GetMapping("/{id}")
    public Activity getActivity(@PathVariable("id") Long id) {
        return service.getActivity(id);
    }

    @PostMapping("/")
    public Activity addActivity(@RequestBody Activity activity) {
        return service.addActivity(activity);
    }

    @DeleteMapping("/{id}")
    public boolean deleteActivity(@PathVariable("id") Long id) {
        return service.deleteActivity(id);
    }
}
