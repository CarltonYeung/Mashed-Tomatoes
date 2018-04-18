package com.mashedtomatoes.media;

import com.mashedtomatoes.util.FuzzyStringMatchComparator;
import com.mashedtomatoes.util.RegexBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@Service
public class TVShowService {
    private static final int MAX_TVSHOW_SEARCH_COUNT = 10;
    private static final String URL_SPACE_DELIM = "+";

    @Autowired
    TVShowRepository tvShowRepository;

    @Cacheable("TVShows")
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

    private ArrayList<TVShowRatingQuery> makeTVShowRatingQueryList(Iterator<Object[]> rows){
        ArrayList<TVShowRatingQuery> tvShowRatingQueries = new ArrayList<TVShowRatingQuery>();
        while(rows.hasNext()){
            Object [] columns = rows.next();
            TVShowRatingQuery tempTVShowRatingQuery = new TVShowRatingQuery((String)columns[0], ((BigInteger)columns[1]).intValue(), ((BigDecimal)columns[2]).doubleValue());
            tvShowRatingQueries.add(tempTVShowRatingQuery);
        }
        return tvShowRatingQueries;
    }

    public Iterable<TVShowRatingQuery> getNowAiringTVShows(int limit){
        Page<Object[]> pageTVShow = tvShowRepository.findNowAiringTVShows(PageRequest.of(0,limit), new Date());
        ArrayList<TVShowRatingQuery> tvShowRatingQueries= makeTVShowRatingQueryList(pageTVShow.iterator());
        return tvShowRatingQueries;
    }

    public Iterable<TVShowRatingQuery> getTopRatedTVShows(int limit){
        Page<Object[]> pageTVShow = tvShowRepository.findTopRatedTVShows(PageRequest.of(0, limit));
        ArrayList<TVShowRatingQuery> tvShowRatingQueries= makeTVShowRatingQueryList(pageTVShow.iterator());
        return tvShowRatingQueries;
    }

}
