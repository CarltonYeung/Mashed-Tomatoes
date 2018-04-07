package com.mashedtomatoes.service.security;

public interface HashService {
    String hash(String s);
    boolean matches(String s, String p);
}