package com.mashedtomatoes.user;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Administrators")
public class Administrator extends User {

  public Administrator() {
  }

  public Administrator(String displayName, String email, String hashedPassword) {
    super(UserType.ADMINISTRATOR);
    super.getCredentials().setEmail(email);
    super.getCredentials().setPassword(hashedPassword);
    super.setDisplayName(displayName);
  }
}
