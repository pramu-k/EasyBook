package com.pramu_k.easybook.repository;

import com.pramu_k.easybook.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
    User save(User user);
}
