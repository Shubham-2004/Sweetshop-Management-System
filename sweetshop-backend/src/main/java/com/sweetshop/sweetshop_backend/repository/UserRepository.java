package com.sweetshop.sweetshop_backend.repository;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.sweetshop.sweetshop_backend.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
}