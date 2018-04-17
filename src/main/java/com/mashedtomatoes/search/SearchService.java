package com.mashedtomatoes.search;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Service
public class SearchService {
  public static final int MAX_RESULTS_PER_PAGE = 10;

  @Autowired
  EntityManagerFactory emf;

  @Cacheable("searchResults")
  public List search(Class klass, String field, String value, int page) {
    EntityManager em = emf.createEntityManager();
    FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(em);
    em.getTransaction().begin();

    QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(klass).get();
    org.apache.lucene.search.Query query = qb
            .keyword()
            .onField(field)
            .matching(value)
            .createQuery();

    javax.persistence.Query persistenceQuery =
            fullTextEntityManager.createFullTextQuery(query, klass);

    // use page 0 to return everything
    if (page > 0) {
      persistenceQuery.setFirstResult(MAX_RESULTS_PER_PAGE * (page - 1));
      persistenceQuery.setMaxResults(MAX_RESULTS_PER_PAGE);
    }

    List result = persistenceQuery.getResultList();

    em.getTransaction().commit();
    em.close();

    return result;
  }

  @Cacheable("searchResultsCount")
  public int count(Class klass, String field, String value) {
    EntityManager em = emf.createEntityManager();
    FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(em);
    em.getTransaction().begin();

    QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(klass).get();
    org.apache.lucene.search.Query query = qb
            .keyword()
            .onField(field)
            .matching(value)
            .createQuery();

    javax.persistence.Query persistenceQuery =
            fullTextEntityManager.createFullTextQuery(query, klass);

    int numOfResults = ((FullTextQuery) persistenceQuery).getResultSize();

    em.getTransaction().commit();
    em.close();

    return numOfResults;
  }

}
