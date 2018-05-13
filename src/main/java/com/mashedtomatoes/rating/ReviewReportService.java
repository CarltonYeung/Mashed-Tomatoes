package com.mashedtomatoes.rating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewReportService {

    @Autowired private ReviewReportRepository reviewReportRepository;

    public Iterable<ReviewReport> findAll() {
        return reviewReportRepository.findAll();
    }
}
