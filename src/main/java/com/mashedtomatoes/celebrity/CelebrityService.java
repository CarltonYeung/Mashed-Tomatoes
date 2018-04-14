package com.mashedtomatoes.celebrity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CelebrityService {

    @Autowired
    CelebrityRepository celebrityRepository;

    Celebrity getCelebrityByID(long ID) {
        Optional<Celebrity> optional = celebrityRepository.findById(ID);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }
}
