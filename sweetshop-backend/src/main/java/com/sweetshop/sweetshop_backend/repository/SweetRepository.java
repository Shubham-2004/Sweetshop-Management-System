package com.sweetshop.sweetshop_backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.sweetshop.sweetshop_backend.model.Sweet;

@Repository
public interface SweetRepository extends MongoRepository<Sweet, String> {

}