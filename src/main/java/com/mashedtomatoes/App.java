package com.mashedtomatoes;

import javax.persistence.EntityManager;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.event.EventListener;


@SpringBootApplication
@EnableCaching
public class App {
  @Autowired EntityManager em;

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }

  @EventListener(ApplicationReadyEvent.class)
  public void indexDatabase() {
    System.out.println("Indexing db");
    em = em.getEntityManagerFactory().createEntityManager();
    try {
      FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
      fullTextEntityManager.createIndexer().startAndWait();
    } catch (InterruptedException ex) {}
  }
}
