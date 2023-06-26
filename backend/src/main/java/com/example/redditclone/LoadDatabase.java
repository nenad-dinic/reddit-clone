package com.example.redditclone;

import com.example.redditclone.repository.*;
import org.slf4j.LoggerFactory;

import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initUser(UserRepository repository) {
        return args -> {log.info("initialized users");};
    }


    @Bean
    CommandLineRunner initModerator(ModeratorRepository repository) {
        return args -> {log.info("Initialized moderator");};
    }

    @Bean
    CommandLineRunner initAdmin(AdminRepository repository) {
        return args -> {log.info("Initialized admin");};
    }

    @Bean
    CommandLineRunner initReaction(ReactionRepository repository) {
        return args -> {log.info("Initialized reaction");};
    }
}
