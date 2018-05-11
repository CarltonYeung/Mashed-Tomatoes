package com.mashedtomatoes.media;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class TVShowService {
  @Autowired TVShowRepository tvShowRepository;

  public Iterable<TVShow> getAllTVShows() {
    return tvShowRepository.findAll();
  }

  private ArrayList<TVShowRatingQuery> makeTVShowRatingQueryList(Iterator<Object[]> rows) {
    ArrayList<TVShowRatingQuery> tvShowRatingQueries = new ArrayList<TVShowRatingQuery>();
    while (rows.hasNext()) {
      Object[] columns = rows.next();
      TVShowRatingQuery tempTVShowRatingQuery =
          new TVShowRatingQuery(
              (String) columns[0],
              ((BigInteger) columns[1]).intValue(),
              ((BigDecimal) columns[2]).doubleValue(),
              (String) columns[3]);
      tvShowRatingQueries.add(tempTVShowRatingQuery);
    }
    return tvShowRatingQueries;
  }

  public Iterable<TVShowRatingQuery> getNowAiringTVShows(int limit) {
    Page<Object[]> pageTVShow =
        tvShowRepository.findNowAiringTVShows(PageRequest.of(0, limit), new Date());
    ArrayList<TVShowRatingQuery> tvShowRatingQueries =
        makeTVShowRatingQueryList(pageTVShow.iterator());
    return tvShowRatingQueries;
  }

  public Iterable<TVShowRatingQuery> getTopRatedTVShows(int limit) {
    Page<Object[]> pageTVShow = tvShowRepository.findTopRatedTVShows(PageRequest.of(0, limit));
    ArrayList<TVShowRatingQuery> tvShowRatingQueries =
        makeTVShowRatingQueryList(pageTVShow.iterator());
    return tvShowRatingQueries;
  }

  public Iterable<TVShowRatingQuery> getTVAiringToday(int limit){
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    int month = calendar.get(Calendar.MONTH);
    int year = calendar.get(Calendar.YEAR);
    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
    calendar.clear();
    calendar.set(Calendar.MONTH, month);
    calendar.set(Calendar.YEAR, year);
    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    Date date = calendar.getTime();
    Page<Object[]> pageTVShow = tvShowRepository.findTVAiringToday(PageRequest.of(0, limit), date);
    ArrayList<TVShowRatingQuery> tvShowRatingQueries = makeTVShowRatingQueryList(pageTVShow.iterator());
    return tvShowRatingQueries;

  }

  public Iterable<TVShow> getFilteredTVShows(String genre, String sort, int page, int limit, int timeInterval){
    Iterable<TVShow> tvShows = new ArrayList<TVShow>();
    switch (sort){
      case "most-popular":
        if(genre.equals("all"))
          tvShows = tvShowRepository.findTVShowByMostPopularOrderByMostPopularDesc(PageRequest.of(page, limit));
        else
          tvShows = tvShowRepository.findTVShowByMostPopularAndGenreOrderByMostPopularDesc(PageRequest.of(page, limit), Genre.valueOf(genre).getName());
        break;
      case "critic-rating":
        if(genre.equals("all"))
          tvShows = tvShowRepository.findTVShowByCriticRatingOrderByCriticRatingDesc(PageRequest.of(page,limit));
        else
          tvShows = tvShowRepository.findTVShowByCriticRatingAndGenreOrderByCriticRatingDesc(PageRequest.of(page, limit), Genre.valueOf(genre).getName());
        break;
      case "new-shows":
        Date beforeDate = new Date(Instant.now().minus(timeInterval, ChronoUnit.DAYS).toEpochMilli());
        Date afterDate = new Date(Instant.now().plus(timeInterval, ChronoUnit.DAYS).toEpochMilli());

        if(genre.equals("all"))
          tvShows = tvShowRepository.findAllByStartDateIsBetweenOrderByStartDateDesc(PageRequest.of(page, limit), beforeDate, afterDate);
        else
          tvShows = tvShowRepository.findAllByStartDateIsBetweenAndGenresContainingOrderByStartDateDesc(PageRequest.of(page, limit), beforeDate, afterDate, Genre.valueOf(genre));
        break;
      case "all":
        if(genre.equals("all"))
          tvShows = tvShowRepository.findAllByOrderByStartDateDesc(PageRequest.of(page, limit));
        else
          tvShows = tvShowRepository.findAllByGenresContainingOrderByStartDateDesc(PageRequest.of(page, limit), Genre.valueOf(genre));
        break;
    }
    return tvShows;
  }

  TVShow getTVShowById(long id) {
    return tvShowRepository.findFirstById(id);
  }
}
