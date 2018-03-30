package com.mashedtomatoes.service;

public interface HashService {
    String hash(String s);
    boolean matches(String s, String p);
}