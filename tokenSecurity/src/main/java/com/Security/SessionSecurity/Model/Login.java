package com.Security.SessionSecurity.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;


@Entity
@Table(name = "users")
@Data
@Builder
public class Login {
    @Id
    @Column(name = "email", length = 100, unique = true)
    @Email
    private String email;
    @Column(name = "first_name", length = 100)
    @NotNull
    private String first_name;
    @Column(name = "last_name", length = 100)
    @NotNull
    private String last_name;
    @Column(name = "password", length = 300)
    @NotNull
    private String password;
}
