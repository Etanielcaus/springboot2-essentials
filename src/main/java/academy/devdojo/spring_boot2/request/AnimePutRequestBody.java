package academy.devdojo.spring_boot2.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AnimePutRequestBody {
    private String name;
    private Long id;
}
