package com.mashedtomatoes.http;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class RegisterRequest {

    private String email;
    private String password;
    private String displayName;

    public RegisterRequest() {
    }

    @NotEmpty
    @Size(max = 255)
    @Email
    public String getEmail() {
        return email;
    }

    @NotEmpty
    public String getPassword() {
        return password;
    }

    @NotBlank
    @Size(max = 255)
    public String getDisplayName() {
        return displayName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
