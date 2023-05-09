package org.poolc.springpractice.model;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Getter
public class User {
    @Id @GeneratedValue
    private Long id;

    @NotBlank(message = "Username is required for registration.")
    @Size(min = 4, max = 12)
    private String username;

    @NotEmpty(message = "Password is required for registration.")
    @Size(min = 8)
    private String password;

    @NotEmpty(message = "Email is required for registration.")
    @Email
    private String email;

    private boolean isMember;
    private boolean isAdmin;

    public User() {}
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
