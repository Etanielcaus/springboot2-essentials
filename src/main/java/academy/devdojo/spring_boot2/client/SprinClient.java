package academy.devdojo.spring_boot2.client;

import academy.devdojo.spring_boot2.dominio.Anime;
import javassist.bytecode.analysis.Analyzer;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SprinClient {
    public static void main(String[] args) {
        ResponseEntity<Anime> forEntity = new RestTemplate().getForEntity("http://localhost:8080/animes/2", Anime.class);
        log.info(forEntity);

        Anime[] forObject = new RestTemplate().getForObject("http://localhost:8080/animes/listAll", Anime[].class);
        log.info(Arrays.toString(forObject));

        ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange("http://localhost:8080/animes/listAll", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Anime>>() {
                });

        log.info(exchange.getBody());
    }
}
