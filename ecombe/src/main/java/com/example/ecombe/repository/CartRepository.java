package com.example.ecombe.repository;

import com.example.ecombe.entities.Cart;
import com.example.ecombe.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    public Optional<Cart> findByUser(User user);
}
