package com.example.adminPTask.repos;

import com.example.adminPTask.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserAccount, Long> {
    UserAccount findByUsername(String username);
}
