package com.mashedtomatoes.shell;

import javax.persistence.EntityManager;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class IndexComponent {
  @Autowired EntityManager em;

  @ShellMethod("Index database for searching")
  public void index() {
    System.out.println("Indexing db");
    em = em.getEntityManagerFactory().createEntityManager();
    try {
      FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
      fullTextEntityManager.createIndexer().startAndWait();
    } catch (InterruptedException ex) {
    }
  }
}
