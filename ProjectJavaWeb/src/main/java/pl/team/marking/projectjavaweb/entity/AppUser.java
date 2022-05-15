package pl.team.marking.projectjavaweb.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class AppUser {

    @Id
    private String login;
    @Column(name = "name", nullable = false)
    @Length(min = 3,max = 20, message = "Imie musi mieć od 3 do 20 znaków")
    private String name;
    @Column(name = "surname", nullable = false)
    @Length(min = 3,max = 50, message = " musi mieć od 3 do 50 znaków")
    private String surname;
    @Column(name = "password", nullable = false)
    @Length(min = 5, message = "Hasło musi mieć conajmniej 5 znaków")
    private String password;
    @Column(name = "age", nullable = false)
    private int age;

}
