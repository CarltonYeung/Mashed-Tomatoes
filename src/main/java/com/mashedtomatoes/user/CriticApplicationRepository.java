package com.mashedtomatoes.user;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CriticApplicationRepository extends CrudRepository<CriticApplication, Long> {
  public boolean existsByApplicant_Id(long id);

  public Optional<CriticApplication> findFirstByApplicant_Id(long id);
}
