package com.example.redditclone.repository;

import com.example.redditclone.model.Admin;
import com.example.redditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findOneByUserId (Long id);
}
