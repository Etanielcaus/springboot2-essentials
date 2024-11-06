package academy.devdojo.spring_boot2.controller;

import academy.devdojo.spring_boot2.dominio.Anime;
import academy.devdojo.spring_boot2.request.AnimePostRequestBody;
import academy.devdojo.spring_boot2.service.AnimeService;
import academy.devdojo.spring_boot2.util.CreateAnime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AnimeControllerTest {

    @InjectMocks
    private AnimeController animeController;

    @Mock
    private AnimeService animeService;

    @BeforeEach
    void setUp(){
        PageImpl<Anime> animePage = new PageImpl<>(List.of(CreateAnime.createdNormalAnime()));
        when(animeService.listAll(any()))
                .thenReturn(animePage);

        when(animeService.listAllNonPageable()).thenReturn(List.of(CreateAnime.createdNormalAnime()));

        when(animeService.findByIdOrThrowBadRequest(ArgumentMatchers.anyLong()))
                .thenReturn(CreateAnime.createdNormalAnime());

    }



    @Test
    @DisplayName("List return list of animes inside page object when successful")
    void list_ReturnListOfAnimesInsidePageObject_WhenSuccessfull(){
        String nameExcpected = CreateAnime.createdNormalAnime().getName();
        Page<Anime> animePage = animeController.animeList(null).getBody();

        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage).isNotEmpty();
        Assertions.assertThat(animePage).hasSize(1);
        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(nameExcpected);
    }

    @Test
    @DisplayName("Return a list of Anime when succesfuly")
    void list_ReturnListOfAnimes_WhenSuccessfull(){
        String nameExcpected = CreateAnime.createdNormalAnime().getName();
        List<Anime> animeList = animeController.animeListAll().getBody();

        Assertions.assertThat(animeList).isNotNull();
        Assertions.assertThat(animeList).isNotEmpty();
        Assertions.assertThat(animeList).hasSize(1);
        Assertions.assertThat(animeList.get(0).getName()).isEqualTo(nameExcpected);
    }

    @Test
    @DisplayName("Find by name")
    void findBYName_ReturnListWhenSuccessfull(){
        Anime anime = CreateAnime.createdNormalAnime();
        String animeString = CreateAnime.createdNormalAnime().getName();

        List<Anime> animeList = List.of(anime);

        when(animeService.findByName(animeString)).thenReturn(animeList);
        List<Anime> body = animeController.findByName(animeString).getBody();

        Assertions.assertThat(body).isNotEmpty();
        Assertions.assertThat(body).extracting(Anime::getName).contains(animeString);
    }

    @Test
    @DisplayName("Find By Id if sucessfuly")
    void findById_Return_Anime_WhenSuccessfull(){
        Anime anime = CreateAnime.createdNormalAnime();

        Anime animeTest = animeController.findById(anime.getId()).getBody();

        Assertions.assertThat(animeTest).isNotNull();
        Assertions.assertThat(animeTest.getName()).isNotNull().isEqualTo(anime.getName());
    }



}