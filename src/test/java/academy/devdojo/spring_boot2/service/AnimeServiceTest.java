package academy.devdojo.spring_boot2.service;

import academy.devdojo.spring_boot2.controller.AnimeController;
import academy.devdojo.spring_boot2.dominio.Anime;
import academy.devdojo.spring_boot2.exception.BadRequestException;
import academy.devdojo.spring_boot2.repository.AnimeRepository;
import academy.devdojo.spring_boot2.request.AnimePostRequestBody;
import academy.devdojo.spring_boot2.request.AnimePutRequestBody;
import academy.devdojo.spring_boot2.util.CreateAnime;
import academy.devdojo.spring_boot2.util.TestAnimePostRequestBody;
import academy.devdojo.spring_boot2.util.TestAnimePutRequestBody;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AnimeServiceTest {

    @InjectMocks
    private AnimeService animeService;

    @Mock
    private AnimeRepository animeRepositoryMock;

    @BeforeEach
    void setUp(){
        PageImpl<Anime> animePage = new PageImpl<>(List.of(CreateAnime.createdNormalAnime()));
        when(animeRepositoryMock.findAll(any(PageRequest.class)))
                .thenReturn(animePage);

        when(animeRepositoryMock.findAll()).thenReturn(List.of(CreateAnime.createdNormalAnime()));

        when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(CreateAnime.createdNormalAnime()));

        when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(CreateAnime.createdNormalAnime()));

        when(animeRepositoryMock.save(any(Anime.class)))
                .thenReturn(CreateAnime.savedAnime());


        doNothing().when(animeRepositoryMock).delete(ArgumentMatchers.any(Anime.class));
    }

    @Test
    @DisplayName("listAll returns list of animes inside a page object when successful")
    void listAll_ReturnsListOfAnimesInsidePageObject_WhenSuccessful() {
        String expectedName = CreateAnime.createdNormalAnime().getName();
        Page<Anime> animePage = animeService.listAll(PageRequest.of(1,1));

        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage).isNotEmpty();
        Assertions.assertThat(animePage).hasSize(1);
        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("listAllNonPageable returns a list of animes when successful")
    void listAllNonPageable_ReturnsListOfAnimes_WhenSuccessful() {
        String expectedName = CreateAnime.createdNormalAnime().getName();
        List<Anime> animeList = animeService.listAllNonPageable();

        Assertions.assertThat(animeList).isNotNull();
        Assertions.assertThat(animeList).isNotEmpty();
        Assertions.assertThat(animeList).hasSize(1);
        Assertions.assertThat(animeList.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById returns an BadRequest when anime is not found")
    void findById_ReturnsBadRequest_WhenAnimeIsNotFound() {
        Long expectedId = CreateAnime.createdNormalAnime().getId();
        when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());
        
        Assertions.assertThatExceptionOfType(BadRequestException.class)
                        .isThrownBy(() -> animeService.findByIdOrThrowBadRequest(1L));

    }

    @Test
    @DisplayName("findById returns an anime when successful")
    void findById_ReturnsAnime_WhenSuccessful() {
        Long expectedId = CreateAnime.createdNormalAnime().getId();
        Anime anime = animeService.findByIdOrThrowBadRequest(expectedId);

        Assertions.assertThat(anime).isNotNull();
        Assertions.assertThat(anime.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByName returns a list of animes when successful")
    void findByName_ReturnsListOfAnimes_WhenSuccessful() {
        String expectedName = CreateAnime.createdNormalAnime().getName();
        List<Anime> animeList = animeService.findByName(expectedName);

        Assertions.assertThat(animeList).isNotEmpty();
        Assertions.assertThat(animeList).extracting(Anime::getName).contains(expectedName);
    }

    @Test
    @DisplayName("findByName returns an empty list when anime is not found")
    void findByName_ReturnsEmptyList_WhenAnimeNotFound() {
        when(animeRepositoryMock.findByName(anyString()))
                .thenReturn(Collections.emptyList());

        String animeName = CreateAnime.createdNormalAnime().getName();
        List<Anime> animeList = animeService.findByName(animeName);

        Assertions.assertThat(animeList).isEmpty();
    }

    @Test
    @DisplayName("save saves and returns an anime when successful")
    void save_SavesAndReturnsAnime_WhenSuccessful() {
        Anime expectedAnime = CreateAnime.savedAnime();
        AnimePostRequestBody requestBody = TestAnimePostRequestBody.createdAnimePostRequestBody();

        Anime anime = animeService.save(requestBody);

        Assertions.assertThat(anime).isNotNull();
        Assertions.assertThat(anime.getName()).isEqualTo(expectedAnime.getName());
    }

    @Test
    @DisplayName("delete removes anime when successful")
    void delete_RemovesAnime_WhenSuccessful() {
        Assertions.assertThatCode(() -> animeService.delete(1L))
                .doesNotThrowAnyException();
    }
}
