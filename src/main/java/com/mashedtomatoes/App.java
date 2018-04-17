package com.mashedtomatoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import javax.persistence.EntityManager;


@SpringBootApplication
@EnableCaching
public class App {
  @Autowired EntityManager em;

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }

//  @EventListener(ApplicationReadyEvent.class)
//  public void indexDatabase() {
//    System.out.println("Indexing db");
//    em = em.getEntityManagerFactory().createEntityManager();
//    try {
//      FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
//      fullTextEntityManager.createIndexer().startAndWait();
//    } catch (InterruptedException ex) {}
//  }
}
