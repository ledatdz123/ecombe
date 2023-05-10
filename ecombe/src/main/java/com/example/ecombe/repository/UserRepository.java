package com.example.ecombe.repository;

import com.example.ecombe.entities.User;
import com.example.ecombe.payload.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserId(int uid);
    public Optional<User> findByEmail(String email);
}
