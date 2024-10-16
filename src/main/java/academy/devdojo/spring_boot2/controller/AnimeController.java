package academy.devdojo.spring_boot2.controller;

import academy.devdojo.spring_boot2.dominio.Anime;
import academy.devdojo.spring_boot2.request.AnimePutRequestBody;
import academy.devdojo.spring_boot2.request.AnimePostRequestBody;
import academy.devdojo.spring_boot2.service.AnimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(path = "/find")
    public ResponseEntity<List<Anime>> findByName(@RequestParam String name){
        return new ResponseEntity<>(animeService.findByName(name), HttpStatus.OK);
    }

    @GetMapping(path = "/findcontaining")
    public ResponseEntity<List<Anime>> findByNameContaining(@RequestParam String name){
        return new ResponseEntity<>(animeService.findByNameContaining(name), HttpStatus.OK);
    }


    @GetMapping(path = "/{id}")
    public ResponseEntity<Anime> findById(@PathVariable long id){
        return new ResponseEntity<>(animeService.findByIdOrThrowBadRequest(id), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Anime> save(@RequestBody AnimePostRequestBody animePostRequestBody){
        return new ResponseEntity<>(animeService.save(animePostRequestBody), HttpStatus.CREATED);
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
