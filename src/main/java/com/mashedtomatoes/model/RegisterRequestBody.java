package com.mashedtomatoes.model;

public class RegisterRequestBody {

    private String email;

    private String password;

    private long dateOfBirth;

    public RegisterRequestBody(String email, String password, long dateOfBirth){
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
    }


}
