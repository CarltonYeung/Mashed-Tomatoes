package com.mashedtomatoes.shell.deserializer;

class Util {
    public static String slugify(String s) {
        return s.toLowerCase().replace(' ', '-');
    }
}