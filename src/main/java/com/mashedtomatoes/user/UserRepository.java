package com.mashedtomatoes.user;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Iterable<? extends User> findAllByType(UserType userType);

    Optional<User> findFirstByCredentials_Email(String email);

    boolean existsByCredentials_Email(String email);
}
