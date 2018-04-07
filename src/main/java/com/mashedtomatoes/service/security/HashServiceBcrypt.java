package com.mashedtomatoes.service.security;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class HashServiceBcrypt implements HashService {
    @Override
    public String hash(String s) {
        String pw_hash = BCrypt.hashpw(s, BCrypt.gensalt());
        System.err.println(pw_hash);
        return pw_hash;
    }

    @Override
    public boolean matches(String s, String p) {
        return BCrypt.checkpw(s, p);
    }
}
