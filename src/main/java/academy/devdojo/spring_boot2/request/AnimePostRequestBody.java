package academy.devdojo.spring_boot2.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class AnimePostRequestBody {
    @NotEmpty(message = "The anime name cannot be empty.")
    @NotBlank(message = "The anime name cannot be empty.")
    private String name;
}
