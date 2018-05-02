package com.mashedtomatoes.user;

import org.springframework.data.repository.CrudRepository;

public interface CriticApplicationRepository extends CrudRepository<CriticApplication, Long> {
  public boolean existsByApplicant_Id(long id);
}
