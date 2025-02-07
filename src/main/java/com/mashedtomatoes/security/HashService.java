package com.mashedtomatoes.security;

import org.springframework.stereotype.Service;

@Service
public interface HashService {
  String hash(String plaintextPassword);

  boolean matches(String plaintextPassword, String hashedPassword);
}
