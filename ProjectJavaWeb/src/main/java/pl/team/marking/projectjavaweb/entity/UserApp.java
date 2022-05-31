package pl.team.marking.projectjavaweb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Data
@Table(name = "user_app")
@NoArgsConstructor
@AllArgsConstructor
public class UserApp {
    public static final String LIMITED_USER = "LIMITED_USER";
    public static final String FULL_USER = "FULL_USER";
    public static final String ADMIN = "ADMIN";

    @Id
    @NotBlank(message = "Login can't be blank!")
    @Size(min = 3,max = 20, message = "Login has to have from 3 to 20 characters.")
    @Pattern(regexp = "^[a-z][a-z0-9_-]+$", message = "Characters should be lowercase, first one can't be number.")
    @Column(name = "login", nullable = false)
    private String login;

    @NotBlank(message = "Name can't be blank!")
    @Size(min = 3,max = 20, message = "Surname has to have from 3 to 20 characters.")
    @Pattern(regexp = "^[A-Z][a-z]+$", message = "First letter shall be uppercase, others must be letters!")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "Login can't be blank!")
    @Size(min = 3,max = 50, message = "Surname has to have from 3 to 50 characters.")
    @Pattern(regexp = "^[A-Z][a-z]+$", message = "First letter shall be uppercase, others must be letters!")
    @Column(name = "surname", nullable = false)
    private String surname;

    @NotBlank(message = "Login can't be blank!")
    @Size(min = 5, message = "Password has to be at least 5 characters length.")
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull(message = "Age can't be blank!")
    @Min(value = 18, message = "You have to be adult to create account on this app!")
    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "role", nullable = false)
    private String role = LIMITED_USER;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
}
