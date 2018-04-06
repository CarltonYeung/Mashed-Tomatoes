package com.mashedtomatoes.repository.user;

import com.mashedtomatoes.model.user.User;
import com.mashedtomatoes.model.user.UserType;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    public Iterable<? extends User> findAllByType(UserType userType);
}
