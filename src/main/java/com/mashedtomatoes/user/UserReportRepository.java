package com.mashedtomatoes.user;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserReportRepository extends CrudRepository<UserReport, Long> {
  public boolean existsByUser_Id(long id);

  public Optional<UserReport> findFirstByUser_Id(long id);
}
