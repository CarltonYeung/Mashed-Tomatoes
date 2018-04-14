package com.mashedtomatoes.user;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
  Iterable<? extends User> findAllByType(UserType userType);

  Optional<User> findFirstByCredentials_Email(String email);

  boolean existsByCredentials_Email(String email);
}
