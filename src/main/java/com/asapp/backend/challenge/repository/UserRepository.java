package com.asapp.backend.challenge.repository;

import com.asapp.backend.challenge.resources.UserResource;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserResource, Long> {
    Optional<UserResource> findByUsername(String username);
}
