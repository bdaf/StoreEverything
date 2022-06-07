package pl.team.marking.projectjavaweb.DTO;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PublishedUserDTO {

    @NotBlank(message = "Login can't be blank!")
    @Size(min = 3,max = 20, message = "Login has to have from 3 to 20 characters.")
    @Pattern(regexp = "^[a-z][a-z0-9_-]+$", message = "Characters should be lowercase, first one can't be number.")
    private String login;
}
