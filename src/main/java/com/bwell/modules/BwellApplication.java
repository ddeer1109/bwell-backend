package com.bwell.modules;

import com.bwell.modules.base.BaseService;
import com.bwell.modules.base.entry.Entry;
import com.bwell.modules.user.data.model.User;
import com.bwell.modules.user.data.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SpringBootApplication
public class BwellApplication {

    public static void main(String[] args) {
        SpringApplication.run(BwellApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(BaseService baseService, UserService userService) {
        return args -> {
            // read json and write to db
            User defaultUser = userService.saveUser(UserService.createEmptyUser());
            defaultUser.getDietPlan().setUser(defaultUser);
            User.defaultUserId = defaultUser.getId();
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Entry>> typeReference = new TypeReference<List<Entry>>(){};
            InputStream inputStream = TypeReference.class.getResourceAsStream("/json/allEntries.json");
            try {
                List<Entry> entries = mapper.readValue(inputStream,typeReference);
                baseService.saveAll(entries);
                System.out.println("Entrys Saved!");
            } catch (IOException e){
                System.out.println("Unable to save users: " + e.getMessage());
            }
        };
    }

}
