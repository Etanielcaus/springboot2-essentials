package academy.devdojo.spring_boot2.controller;

import academy.devdojo.spring_boot2.dominio.Anime;
import academy.devdojo.spring_boot2.service.AnimeService;
import academy.devdojo.spring_boot2.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("animes")
@Log4j2
@RequiredArgsConstructor
public class AnimeController {

   private final DateUtil dateUtil;

   private final AnimeService animeService;

    @GetMapping()
    public ResponseEntity<List<Anime>> animeList(){
        log.info(dateUtil.localTimeFormater(LocalDateTime.now()));
        return new ResponseEntity<>(animeService.listAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Anime> findById(@PathVariable long id){
        return new ResponseEntity<>(animeService.findById(id), HttpStatus.OK);
    }
}
