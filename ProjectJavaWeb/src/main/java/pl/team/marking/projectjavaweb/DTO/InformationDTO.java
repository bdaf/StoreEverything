package pl.team.marking.projectjavaweb.DTO;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Future;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InformationDTO {
    @Length(min = 3, max = 20, message = "Tytuł musi mieć od 3 do 20 znaków")
    private String title;

    @Length(min = 5, max = 500, message = "Zawartość musi mieć od 5 do 500 znaków")
    private String content;

    @Future
    private String remindDate;

    private Long categoryId;

    private String login;
}
