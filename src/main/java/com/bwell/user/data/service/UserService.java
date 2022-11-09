package com.bwell.user.data.service;

import com.bwell.modules.eatwell.dietplan.model.DietPlan;
import com.bwell.exception.ResourceNotFoundException;
import com.bwell.security.UserPrincipal;
import com.bwell.user.data.model.Credentials;
import com.bwell.user.data.model.User;
import com.bwell.user.data.repository.CredentialsRepository;
import com.bwell.user.data.repository.UserRepository;
import com.bwell.user.favourites.Favourites;
import com.bwell.user.favourites.FavouritesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
public class UserService implements IUserService {

    private final UserRepository repository;
    private final FavouritesRepository favouritesRepository;
    private final CredentialsRepository credRepo;

    @Autowired
    public UserService(UserRepository repository, FavouritesRepository favouritesRepository, CredentialsRepository credRepo) {
        this.repository = repository;
        this.favouritesRepository = favouritesRepository;
        this.credRepo = credRepo;
    }

    public User getUserById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }


    public Credentials  getCredentialsById(String id){
        if (id == null) {
            throw new UsernameNotFoundException("No service principal available");
        }
        return credRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User {} {} not found", "ID", id));
    }

    public Credentials  getCredentialsById(UserPrincipal principal){
        if (principal == null || principal.getId() == null) {
            throw new UsernameNotFoundException("No service principal available");
        }
        return credRepo.findById(principal.getId()).orElseThrow(() -> new ResourceNotFoundException("User {} {} not found", "ID", principal.getId()));
    }

    public Favourites saveFavourites(Favourites favs){
        return favouritesRepository.save(favs);
    }
    public User saveUser(User user) {
        log.info("user - {}", user);
       return  repository.saveAndFlush(user);
//        return repository.save(user);
//        return repository.findById(user.getId())
//                .stream()
//                .map(dbUsr -> {
//                    dbUsr.setFavourites(user.getFavourites());
//                    dbUsr.setDietPlan(user.getDietPlan());
//                    dbUsr.setCalculatorData(user.getCalculatorData());
//                    dbUsr.setNutrientsDemand(user.getNutrientsDemand());
//                    return repository.save(dbUsr);
//                })
//                .findFirst()
//                .orElse(repository.save(user));
    }

    @Override
    public UserDetails loadById(String id) {
//        Credentials user = credRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User ", "id", id));
//        Credentials user =
        return UserPrincipal.create(credRepo.findById(id).orElse(null));
    }

    public static User createEmptyUser(){
        User user = new User();
        user.setId(0l);
        user.setDietPlan( new DietPlan());
        user.setFavourites(new Favourites());
        return user;
    }


}
