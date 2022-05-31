package pl.team.marking.projectjavaweb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "information")
@NoArgsConstructor
@AllArgsConstructor
public class Information {

    @Id
    @SequenceGenerator(
            name = "information_sequence",
            sequenceName = "information_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "information_sequence")
    @Column(name = "information_id")
    private Long informationId;

    @Column(name = "title", nullable = false)
//    @Length(min = 3, max = 20, message = "Tytuł musi mieć od 3 do 20 znaków")
    private String title;

    @Column(name = "content", nullable = false)
//    @Length(min = 5, max = 500, message = "Zawartość musi mieć od 5 do 500 znaków")
    private String content;

    @Column(name = "link")
    private String link;

    @Column(name = "link_uuid")
    private String linkUuid;

    // TODO format we want is 'dd-mm-yyyy'
    @Column(name = "adding_date")
    @CreationTimestamp
    private LocalDate addingDate;

    @Column(name = "remind_date")
    private LocalDate remindDate;

    @ManyToOne
    @JoinColumn(
            referencedColumnName = "category_id"
    )
    private Category category;

    @ManyToOne
    @JoinColumn(
            referencedColumnName = "login"
    )
    private UserApp user;

    @ManyToMany
    private List<UserApp> publishedUser = new ArrayList<>();
}
