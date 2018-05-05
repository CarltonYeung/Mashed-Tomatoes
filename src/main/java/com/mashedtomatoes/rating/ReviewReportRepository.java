package com.mashedtomatoes.rating;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ReviewReportRepository extends CrudRepository<ReviewReport, Long> {

    ReviewReport findFirstByRating_Id(long id);

    @Transactional
    void deleteFirstByRating_Id(long id);

}
