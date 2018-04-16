package com.mashedtomatoes.media;

import com.mashedtomatoes.util.FuzzyStringMatchComparator;
import com.mashedtomatoes.util.RegexBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class TVShowService {
    private static final int MAX_TVSHOW_SEARCH_COUNT = 10;
    private static final String URL_SPACE_DELIM = "+";

    @Autowired
    TVShowRepository tvShowRepository;

    public Iterable<TVShow> getAllTVShows(String expr) {
        if (expr == null) {
            return tvShowRepository.findAll();
        }

        List<String> parts = Arrays.asList(expr.split("/" + URL_SPACE_DELIM)); // escape regex meta character
        String regex = RegexBuilder.buildMySQLRegex(parts);
        List<TVShow> tvShows = tvShowRepository.findSimilarTVShows(regex);
        String originalExpr = expr.replace(URL_SPACE_DELIM, " ");
        FuzzyStringMatchComparator<TVShow> tvShowComparator =
                new FuzzyStringMatchComparator<>(originalExpr, TVShow::getTitle);
        Collections.sort(tvShows, tvShowComparator);

        if (tvShows.size() < MAX_TVSHOW_SEARCH_COUNT) {
            return tvShows;
        }

        return tvShows.subList(0, MAX_TVSHOW_SEARCH_COUNT);
    }

}
