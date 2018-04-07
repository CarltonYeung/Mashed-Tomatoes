package com.mashedtomatoes.repository.user;

import com.mashedtomatoes.model.user.User;
import com.mashedtomatoes.model.user.UserType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    public Iterable<? extends User> findAllByType(UserType userType);

    public Optional<User> findFirstByCredentials_Email(String email);

    public boolean existsByCredentials_Email(String email);
}
