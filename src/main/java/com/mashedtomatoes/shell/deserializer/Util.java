package com.mashedtomatoes.shell.deserializer;

public class Util {
    public static String slugify(String s) {
        return s.toLowerCase().replace(' ', '-');
    }
}