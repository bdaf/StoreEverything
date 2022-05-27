package pl.team.marking.projectjavaweb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

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

    @Length(min = 3,max = 20, message = "Login musi mieć od 3 do 20 znaków")
    @Column(name = "login", nullable = false)
    // TODO login should always be lower cased!
    private String login;

    @Column(name = "name", nullable = false)
    @Length(min = 3,max = 20, message = "Imie musi mieć od 3 do 20 znaków")
    // TODO First character in name should be upper cased
    private String name;

    @Column(name = "surname", nullable = false)
    @Length(min = 3,max = 50, message = "Nazwisko musi mieć od 3 do 50 znaków")
    // TODO First character in surname should be upper cased
    private String surname;

    @Column(name = "password", nullable = false)
    @Length(min = 5, message = "Hasło musi mieć conajmniej 5 znaków")
    private String password;

    @Column(name = "age", nullable = false)
    // TODO age should be 18 years or older)
    private int age;

    @Column(name = "role", nullable = false)
    private String role = LIMITED_USER;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
}
