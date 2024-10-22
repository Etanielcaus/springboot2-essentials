package academy.devdojo.spring_boot2.client;

import academy.devdojo.spring_boot2.dominio.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
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

//        Anime buildedAnime = Anime.builder().name("Kingon").build();
//        Anime animeSaved = new RestTemplate().postForObject("http://localhost:8080/animes/", buildedAnime, Anime.class);
//        log.info("anime saved {}", animeSaved);

        Anime buildedNewAnime = Anime.builder().name("collen hoober").build();
        ResponseEntity<Anime> exchange1 = new RestTemplate().exchange("http://localhost:8080/animes/",
                HttpMethod.POST, new HttpEntity<>(buildedNewAnime, httpHeaders()), Anime.class);
        log.info("Anime saved using exchage {}", exchange1);

    }

    private static HttpHeaders httpHeaders(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.getContentType();
        return httpHeaders;
    }
}
