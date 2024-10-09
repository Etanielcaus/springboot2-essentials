package academy.devdojo.spring_boot2.controller;

import academy.devdojo.spring_boot2.dominio.Anime;
import academy.devdojo.spring_boot2.service.AnimeService;
import academy.devdojo.spring_boot2.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("animes")
// @AllArgsConstructor
@Log4j2
public class AnimeController {

//    @Autowired
   private DateUtil dateUtil;

//    @Autowired
   private AnimeService animeService;

//    Forma recomendada é fazer a injeção de dependencias é com Construtores
    public AnimeController(DateUtil dateUtil, AnimeService animeService) {
        this.dateUtil = dateUtil;
        this.animeService = animeService;
    }

    @GetMapping()
    public ResponseEntity<List<Anime>> animeList(){
        log.info(dateUtil.localTimeFormater(LocalDateTime.now()));
        return new ResponseEntity<>(animeService.listAll(), HttpStatus.OK);
    }


    @GetMapping(path = "/{id}")
    public ResponseEntity<Anime> findById(@PathVariable long id){
        return new ResponseEntity<>(animeService.findById(id), HttpStatus.OK);
    }


    @GetMapping(path = "/findByName/{name}")
    public ResponseEntity<Anime> findAnimeWithName(@PathVariable String name){
        return new ResponseEntity<>(animeService.findAnimeWithName(name), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Anime> save(@RequestBody Anime anime){
        log.info("Saving anime: " + anime.getName());
        return new ResponseEntity<>(animeService.save(anime), HttpStatus.CREATED);
    }


    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        log.info("Delete anime.");
        animeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
