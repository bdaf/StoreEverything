package pl.team.marking.projectjavaweb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
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

    @Column(name = "title", nullable = false, length = 20)
    private String title;

    @Column(name = "content", nullable = false, length = 500)
    private String content;

    @Column(name = "link")
    private String link;

    @Column(name = "link_uuid", length = 36, unique = true)
    private String linkUuid;

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
