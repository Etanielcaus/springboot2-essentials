package academy.devdojo.spring_boot2.service;

import academy.devdojo.spring_boot2.dominio.Anime;
import academy.devdojo.spring_boot2.repository.AnimeRepository;
import academy.devdojo.spring_boot2.request.AnimePutRequestBody;
import academy.devdojo.spring_boot2.request.AnimeRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AnimeService {

    private final AnimeRepository animeRepository;

    public List<Anime> listAll(){
        return animeRepository.findAll();
    }

    public Anime findByIdOrThrowBadRequest(Long id){
        return animeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime not Found"));
    }

    public Anime save(AnimeRequestBody animeRequestBody) {
        Anime build = Anime.builder().name(animeRequestBody.getName()).build();
        return animeRepository.save(build);
    }

    public void delete(Long id) {
        animeRepository.delete(findByIdOrThrowBadRequest(id));
    }

    public void replace(AnimePutRequestBody animePutRequestBody) {
        Anime savedAnime = findByIdOrThrowBadRequest(animePutRequestBody.getId());
        Anime build = Anime.builder()
                .name(animePutRequestBody.getName())
                .id(savedAnime.getId()).build();
        animeRepository.save(build);
    }

}
