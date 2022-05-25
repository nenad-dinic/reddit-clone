package com.example.redditclone.repository;

import com.example.redditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    //@Query(value = "SELECT * FROM user WHERE username=data.getUsername() and password=hashPassword;")
    User findOneByUsernameAndPassword(String username, String password);

    User findOneByUsername(String username);
}
