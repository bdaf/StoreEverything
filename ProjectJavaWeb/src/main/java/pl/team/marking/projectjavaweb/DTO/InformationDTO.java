package pl.team.marking.projectjavaweb.DTO;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InformationDTO {

    @NotBlank(message = "Title can't be blank!")
    @Size(min = 3, max = 20, message = "Title has to have from 3 to 20 characters.")
    private String title;

    @NotBlank(message = "Content can't be blank!")
    @Size(min = 5, max = 500, message = "Content has to have from 5 to 500 characters.")
    private String content;

    @Future(message = "Remind date must be in future.")
    @NotNull(message = "Remind date can't be blank!")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate remindDate;

    @NotNull(message = "Category can't be blank!")
    private Long categoryId;
}
