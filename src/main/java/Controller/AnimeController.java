package Controller;

import Domain.Anime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("anime")
@RestController
public class AnimeController {

    @GetMapping(path = "list")
    public List<Anime> animeList(){
        return List.of(new Anime("DBZ"), new Anime("Naruto"));
    }
}
