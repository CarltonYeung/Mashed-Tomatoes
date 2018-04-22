package com.mashedtomatoes.media;

import com.mashedtomatoes.search.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class TVShowService {

    @Autowired
    TVShowRepository tvShowRepository;
    @Autowired
    SearchService searchService;

    @Cacheable("TVShows")
    public Iterable<TVShow> getAllTVShows(String expr) {
        if (expr == null || expr.trim().length() == 0)
            return tvShowRepository.findAll();

        return searchService.search(TVShow.class, "title", expr, 0);
    }

}
