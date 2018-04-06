package com.mashedtomatoes.repository.user;

import com.mashedtomatoes.model.user.UserCredentials;
import org.springframework.data.repository.CrudRepository;

public interface UserCredentialsRepository extends CrudRepository<UserCredentials, Long> {
}
