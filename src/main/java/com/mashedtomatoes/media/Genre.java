package com.mashedtomatoes.media;

import java.util.Collection;

public enum Genre {
    ACTION("Action"),
    ADVENTURE("Adventure"),
    DRAMA("Drama");

    private String name;

    Genre(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static String toComma(Collection<Genre> genres) {
        return "genres, genres,";
    }

}
