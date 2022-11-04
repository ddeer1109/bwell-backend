package com.bwell.user;

import com.bwell.security.CurrentUser;
import com.bwell.security.UserPrincipal;
import com.bwell.user.data.model.Credentials;
import com.bwell.user.data.model.User;
import com.bwell.user.data.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Random;

@Slf4j
@RestController
@CrossOrigin("${FRONTEND_HOST}")
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService service;


    @GetMapping("/profile")
//    @PreAuthorize("hasRole('USER')")
    public Credentials getCurrentUser(@CurrentUser UserPrincipal principal) {

        Credentials credentialsById = service.getCredentialsById(principal);
        return credentialsById;
    }

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        try{
            return service.getUserById(id);
        } catch (ResponseStatusException e)
        {
            return null;
        }
    }

    @GetMapping("/default")
    public User getDefaultUser() {
        try{
            return service.getUserById(User.defaultUserId);
        } catch (Exception e)
        {
            User emptyUser = UserService.createEmptyUser();
            emptyUser.setId(new Random().nextLong());
            return emptyUser;
        }
    }

    @PostMapping("/{id}")
    public User addUser(@RequestBody User user) {
        System.out.println(user);
        return service.saveUser(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@RequestBody User user){

        System.out.println(user);

        return service.saveUser(user);
    }


}
