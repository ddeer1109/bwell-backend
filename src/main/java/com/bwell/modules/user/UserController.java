package com.bwell.modules.user;

import com.bwell.modules.user.data.model.User;
import com.bwell.modules.user.data.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return service.getUserById(id);
    }

    @PostMapping("/")
    public User addUser(@RequestBody User user) {
        return service.saveUser(user);
    }

    @PutMapping("/")
    public User updateUser(@RequestBody User user){
        return service.saveUser(user);
    }

}
