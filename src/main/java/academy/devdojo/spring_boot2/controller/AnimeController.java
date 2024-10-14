package academy.devdojo.spring_boot2.controller;

import academy.devdojo.spring_boot2.dominio.Anime;
import academy.devdojo.spring_boot2.request.AnimePutRequestBody;
import academy.devdojo.spring_boot2.request.AnimeRequestBody;
import academy.devdojo.spring_boot2.service.AnimeService;
import academy.devdojo.spring_boot2.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("animes")
// @AllArgsConstructor
@Log4j2
public class AnimeController {

//    @Autowired
   private final AnimeService animeService;

    @GetMapping()
    public ResponseEntity<List<Anime>> animeList(){
        return new ResponseEntity<>(animeService.listAll(), HttpStatus.OK);
    }


    @GetMapping(path = "/{id}")
    public ResponseEntity<Anime> findById(@PathVariable long id){
        return new ResponseEntity<>(animeService.findByIdOrThrowBadRequest(id), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Anime> save(@RequestBody AnimeRequestBody animeRequestBody){
        return new ResponseEntity<>(animeService.save(animeRequestBody), HttpStatus.CREATED);
    }


    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        log.info("Delete anime.");
        animeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody AnimePutRequestBody anime){
        animeService.replace(anime);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
