package com.mashedtomatoes.media;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface OscarWinnerSetRepository extends CrudRepository<OscarWinnerSet, Long> {
    OscarWinnerSet findFirstByYear(Date year);
}
