package com.mashedtomatoes.media;

import com.mashedtomatoes.celebrity.Celebrity;
import com.mashedtomatoes.celebrity.CelebrityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CelebrityAPIController {

    @Autowired
    CelebrityService celebrityService;

    @GetMapping("/api/celebrity/{ID}")
    public Celebrity getMovie(@PathVariable long ID) {
        for (Media m : celebrityService.getCelebrityByID(ID).getMedia()) {
            System.out.println(m.title);
        }
        return celebrityService.getCelebrityByID(ID);
    }
}