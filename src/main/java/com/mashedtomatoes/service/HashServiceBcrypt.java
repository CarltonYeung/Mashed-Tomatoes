package com.mashedtomatoes.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class HashServiceBcrypt implements HashService{

        @Value("${mt.auth.salt}")
        private String salt;
    @Override
    public String hash(String s) {
        //Hashing
        String pw_hash= BCrypt.hashpw(s,BCrypt.gensalt());
        System.err.println(pw_hash);
        return pw_hash;
    }

    @Override
    public boolean matches(String s, String p) {
        return BCrypt.checkpw(s,p);
    }
}
