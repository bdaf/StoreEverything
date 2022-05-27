package pl.team.marking.projectjavaweb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
@Data
@Table(name = "category")
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @SequenceGenerator(
            name = "category_sequence",
            sequenceName = "category_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "category_sequence")
    @Column(name = "category_id")
    private Long categoryId;

    // TODO name should always be lower cased!
    @Column(name = "name", nullable = false)
    @Length(min = 3,max = 20, message = "Tytuł musi mieć od 3 do 20 znaków")
    private String name;

}
