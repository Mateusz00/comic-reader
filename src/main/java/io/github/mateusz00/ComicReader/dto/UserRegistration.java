package io.github.mateusz00.ComicReader.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserRegistration
{
    @NotBlank
    private String username;

    @NotBlank
    @Size(max = 72, message = "Your password cannot be longer than 72 characters")
    private String password;

    @NotBlank
    @Size(max = 72, message = "Your password cannot be longer than 72 characters")
    private String matchingPassword;

    @NotBlank
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
