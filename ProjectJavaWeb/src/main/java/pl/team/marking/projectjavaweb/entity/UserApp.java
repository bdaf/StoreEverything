package pl.team.marking.projectjavaweb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private Long id;

    @NotNull(message = "Login can't be blank!")
//    @Length(min = 3,max = 20, message = "Login musi mieć od 3 do 20 znaków")
    @Length(min = 3,max = 20, message = "Login has to have from 3 to 20 characters.")
    @Column(name = "login", nullable = false)
    // TODO login should always be lower cased!
    private String login;

    @NotNull(message = "Name can't be blank!")
    @Column(name = "name", nullable = false)
//    @Length(min = 3,max = 20, message = "Imie musi mieć od 3 do 20 znaków")
    @Length(min = 3,max = 20, message = "Surname has to have from 3 to 20 characters.")
    // TODO First character in name should be upper cased - regex
    private String name;

    @NotNull(message = "Login can't be blank!")
    @Column(name = "surname", nullable = false)
//    @Length(min = 3,max = 50, message = "Nazwisko musi mieć od 3 do 50 znaków")
    @Length(min = 3,max = 50, message = "Surname has to have from 3 to 50 characters.")
    // TODO First character in surname should be upper cased - regex
    private String surname;

    @NotNull(message = "Login can't be blank!")
    @Column(name = "password", nullable = false)
    @Length(min = 5, message = "Password has to be at least 5 characters length.")
    private String password;

    @NotNull(message = "Age can't be blank!")
    @Column(name = "age", nullable = false)
    @Min(value = 18, message = "You have to be adult to create account on this app!")
    private int age;

    @Column(name = "role", nullable = false)
    private String role = LIMITED_USER;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
}
