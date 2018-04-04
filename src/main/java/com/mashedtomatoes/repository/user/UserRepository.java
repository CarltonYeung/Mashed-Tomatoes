package com.mashedtomatoes.repository.user;

import com.mashedtomatoes.model.user.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
