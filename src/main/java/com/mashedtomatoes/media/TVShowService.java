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
    public Iterable<TVShow> getAllTVShows(String expr, int page) {
        if(expr == null){
            return tvShowRepository.findAll();
        }

        List<String> parts = Arrays.asList(expr.split("/" + URL_SPACE_DELIM)); // escape regex meta character
        String regex = RegexBuilder.buildMySQLRegex(parts);
        List<TVShow> tvShows = tvShowRepository.findSimilarTVShows(regex);
        String originalExpr = expr.replace(URL_SPACE_DELIM, " ");
        FuzzyStringMatchComparator<TVShow> celebrityComparator =
                new FuzzyStringMatchComparator<>(originalExpr, TVShow::getTitle);
        Collections.sort(tvShows, celebrityComparator);

        // Pagination
        int start = 0, end = tvShows.size();
        if (page > 0) {
            start = (page - 1) * MAX_TVSHOW_SEARCH_COUNT;

            if (start >= tvShows.size()) {
                return tvShows.subList(0, 0);
            }

            end = start + MAX_TVSHOW_SEARCH_COUNT;
            end = Integer.min(end, tvShows.size());
        }
        return tvShows.subList(start, end);
    }

    public Iterable<TVShow> getAllTVShows(String expr) {
        return getAllTVShows(expr, 0);
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
