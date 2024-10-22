package academy.devdojo.spring_boot2.service;

import academy.devdojo.spring_boot2.dominio.Anime;
import academy.devdojo.spring_boot2.exception.BadRequestException;
import academy.devdojo.spring_boot2.mapper.AnimeMapper;
import academy.devdojo.spring_boot2.repository.AnimeRepository;
import academy.devdojo.spring_boot2.request.AnimePutRequestBody;
import academy.devdojo.spring_boot2.request.AnimePostRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AnimeService {

    private final AnimeRepository animeRepository;

    public List<Anime> listAllNonPageable() {
        return animeRepository.findAll();
    }

    public Page<Anime> listAll(Pageable pageable){
        return animeRepository.findAll(pageable);
    }

    public List<Anime> findByName(String name){
        return animeRepository.findByName(name);
    }

    public List<Anime> findByNameContaining(String name){
        return animeRepository.findByNameContaining(name);
    }



    public Anime findByIdOrThrowBadRequest(Long id){
        return animeRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Anime not Found"));
    }

    @Transactional
    public Anime save(AnimePostRequestBody animePostRequestBody) {
        return animeRepository.save(AnimeMapper.INSTANCE.toAnime(animePostRequestBody));
    }

    public void delete(Long id) {
        animeRepository.delete(findByIdOrThrowBadRequest(id));
    }

    public void replace(AnimePutRequestBody animePutRequestBody) {
        Anime savedAnime = findByIdOrThrowBadRequest(animePutRequestBody.getId());
        Anime anime = AnimeMapper.INSTANCE.toAnime(animePutRequestBody);
        anime.setId(savedAnime.getId());
        animeRepository.save(anime);
    }
}
