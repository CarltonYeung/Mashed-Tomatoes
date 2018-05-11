package com.mashedtomatoes.user;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Administrators")
public class Administrator extends User {

  public Administrator() {
    super(UserType.ADMINISTRATOR);
  }

  public Administrator(String displayName, String email, String hashedPassword) {
    this();
    super.getCredentials().setEmail(email);
    super.getCredentials().setPassword(hashedPassword);
    super.setDisplayName(displayName);
  }
}
