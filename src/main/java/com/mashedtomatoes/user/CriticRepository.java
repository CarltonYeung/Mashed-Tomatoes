package com.mashedtomatoes.user;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CriticRepository extends CrudRepository<Critic, Long> {

    List<Critic> findAllByOrderByFirstName(Pageable pageable);

    List<Critic> findAllByTopCriticTrueOrderByFirstName(Pageable pageable);

}
