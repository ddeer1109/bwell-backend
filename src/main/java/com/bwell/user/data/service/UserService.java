package com.bwell.user.data.service;

import com.bwell.modules.eatwell.dietplan.model.DietPlan;
import com.bwell.exception.ResourceNotFoundException;
import com.bwell.security.UserPrincipal;
import com.bwell.user.data.model.Credentials;
import com.bwell.user.data.model.User;
import com.bwell.user.data.repository.CredentialsRepository;
import com.bwell.user.data.repository.UserRepository;
import com.bwell.user.favourites.model.Favourites;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j
@Service
public class UserService implements IUserService {

    UserRepository repository;

    CredentialsRepository credRepo;

    @Autowired
    public UserService(UserRepository repository, CredentialsRepository credRepo) {
        this.repository = repository;
        this.credRepo = credRepo;
    }

    public User getUserById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public Credentials getCredentialsById(String id){
        return credRepo.findById(id).orElseThrow();
    }

    public User saveUser(User user) {
        Optional<User> inDb = repository.findById(user.getId());
        if (inDb.isPresent()){
            User userInDb = inDb.get();
            userInDb.setFavourites(user.getFavourites());
            userInDb.setDietPlan(user.getDietPlan());
            userInDb.setCalculatorData(user.getCalculatorData());
            userInDb.setNutrientsDemand(user.getNutrientsDemand());
            return repository.save(userInDb);
        }
        return repository.save(user);
    }

    @Override
    public UserDetails loadById(String id) {
        Credentials user = credRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User ", "id", id));
        return UserPrincipal.create(user);
    }

    public static User createEmptyUser(){
        User user = new User();
        user.setId(0l);
        user.setDietPlan( new DietPlan());
        user.setFavourites(new Favourites());
        return user;
    }


}
