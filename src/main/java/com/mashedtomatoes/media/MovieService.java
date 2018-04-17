package com.mashedtomatoes.media;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Service
public class MovieService {
  private static final int MAX_MOVIE_SEARCH_COUNT = 10;
  private static final String URL_SPACE_DELIM = "+";

  @Autowired
  MovieRepository movieRepository;

  @Autowired
  EntityManagerFactory emf;

  @Cacheable("movies")
  public Iterable<Movie> getAllMovies(String expr) {
    if (expr == null)
      return movieRepository.findAll();

    return hibernateSearch(expr);
  }

  private List<Movie> hibernateSearch(String expr) {
    EntityManager em = emf.createEntityManager();
    FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(em);
    em.getTransaction().begin();

    QueryBuilder qb = fullTextEntityManager.getSearchFactory()
            .buildQueryBuilder().forEntity(Movie.class).get();
    org.apache.lucene.search.Query query = qb
            .keyword()
              .fuzzy()
            .onFields("title")
            .matching(expr)
            .createQuery();

    javax.persistence.Query persistenceQuery =
            fullTextEntityManager.createFullTextQuery(query, Movie.class);

    List result = persistenceQuery.getResultList();

    em.getTransaction().commit();
    em.close();

    return result;
  }

  Movie getMovieBySlug(String slug) {
    return movieRepository.findFirstBySlug(slug);
  }

  void addMovie(Movie movie) {
    movieRepository.save(movie);
  }

  void updateMovie(Movie movie) {
    addMovie(movie);
  }

  void deleteMovie(Movie movie) {
    movieRepository.delete(movie);
  }

  Boolean deleteMovieBySlug(String slug) {
    Movie movie = getMovieBySlug(slug);
    if (movie == null) {
      return false;
    }

    deleteMovie(movie);
    return true;
  }
}
