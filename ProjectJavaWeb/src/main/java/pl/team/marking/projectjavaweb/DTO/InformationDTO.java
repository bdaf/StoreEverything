package pl.team.marking.projectjavaweb.DTO;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InformationDTO {

    @NotBlank(message = "Login can't be blank!")
    @Size(min = 3, max = 20, message = "Login has to have from 3 to 20 characters.")
    private String title;

    @NotBlank(message = "Login can't be blank!")
    @Size(min = 5, max = 500, message = "Login has to have from 3 to 20 characters.")
    private String content;

    @Future(message = "Remind date must be in future.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate remindDate;

    private Long categoryId;

    private String login;
}
