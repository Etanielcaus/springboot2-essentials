package academy.devdojo.spring_boot2.request;

import lombok.Data;

@Data
public class AnimePutRequestBody {
    private String name;
    private Long id;
}
