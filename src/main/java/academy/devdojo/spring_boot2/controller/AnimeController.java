package academy.devdojo.spring_boot2.controller;

import academy.devdojo.spring_boot2.dominio.Anime;
import academy.devdojo.spring_boot2.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("anime")
@Log4j2
@RequiredArgsConstructor
public class AnimeController {

   private final DateUtil dateUtil;

    @GetMapping(path = "list")
    public List<Anime> animeList(){
        log.info(dateUtil.localTimeFormater(LocalDateTime.now()));
        return List.of(new Anime("DBZ"), new Anime("Naruto"));
    }
}
