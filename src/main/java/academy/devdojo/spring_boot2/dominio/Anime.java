package academy.devdojo.spring_boot2.dominio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data // Gera getters, setters, equals, hashCode e toString
@NoArgsConstructor // Gera o construtor sem argumentos
@AllArgsConstructor // Gera o construtor com todos os argumentos
public class Anime {
    private Long id;
    private String name;
}
