package org.poolc.springpractice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Calendar> calendars = new HashSet<>();

    private boolean isMember;
    private boolean isAdmin;

    public User() {}
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
