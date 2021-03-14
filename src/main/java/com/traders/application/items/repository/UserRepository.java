package com.traders.application.items.repository;

import com.traders.application.items.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Integer> {

    public Optional<Users> findByUsername(String username);
}
