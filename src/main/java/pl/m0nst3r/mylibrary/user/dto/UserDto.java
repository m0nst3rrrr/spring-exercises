package pl.m0nst3r.mylibrary.user.dto;

import java.util.Set;

public class UserDto {
    private final String email;
    private final String password;
    private final Set<String> roles;

    public UserDto(String email, String password, Set<String> roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Set<String> getRoles() {
        return roles;
    }
}
