package academy.devdojo.spring_boot2.service;

import academy.devdojo.spring_boot2.dominio.Anime;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AnimeService {

    private static List<Anime> listAnimes = new ArrayList<>(List.of(new Anime(1L, "DBZ"), new Anime(2L, "Naruto")));

    public List<Anime> listAll(){
        return listAnimes;
    }

    public Anime findById(Long id){
        return listAnimes.stream()
                .filter(listAnimes -> listAnimes.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime not Found"));
    }

    public Anime findAnimeWithName(String name) {
        return listAnimes.stream()
                .filter(anime -> name != null && name.equals(anime.getName())) // Prevenção de null
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime not Found"));
    }

    public Anime save(Anime anime) {
        anime.setId(ThreadLocalRandom.current().nextLong(3L, 1000L));
        listAnimes.add(anime);
        return anime;
    }

    public void delete(Long id) {
        listAnimes.remove(findById(id));
    }

    public void replace(Anime anime) {
        delete(anime.getId());
        listAnimes.add(anime);
    }

}
