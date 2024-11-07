package academy.devdojo.spring_boot2.controller;

import academy.devdojo.spring_boot2.dominio.Anime;
import academy.devdojo.spring_boot2.request.AnimePostRequestBody;
import academy.devdojo.spring_boot2.request.AnimePutRequestBody;
import academy.devdojo.spring_boot2.service.AnimeService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
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

        when(animeService.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(CreateAnime.createdNormalAnime()));

        when(animeService.save(ArgumentMatchers.any(AnimePostRequestBody.class)))
                .thenReturn(CreateAnime.savedAnime());

        doNothing().when(animeService).replace(ArgumentMatchers.any(AnimePutRequestBody.class));

        doNothing().when(animeService).delete(ArgumentMatchers.anyLong());

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


    @DisplayName("indById_Return_List_Anime_WhenSuccessfuly")
    @Test
    void findById_Return_List_Anime_WhenSuccessfuly(){
        Long normalAnimeId = CreateAnime.createdNormalAnime().getId();

        Anime body = animeController.findById(normalAnimeId).getBody();

        Assertions.assertThat(body).isNotNull();
        Assertions.assertThat(body.getId()).isEqualTo(normalAnimeId);
    }

    @Test
    @DisplayName("Find by name")
    void findBYName_ReturnListWhenSuccessfull(){
        String animeString = CreateAnime.createdNormalAnime().getName();

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

    @Test
    @DisplayName("Anime Not Found")
    void findBYName_ReturnEmptyList_WhenNotFound(){
        when(animeService.findByName(anyString()))
                .thenReturn(Collections.emptyList());

        String animeString = CreateAnime.createdNormalAnime().getName();

        List<Anime> body = animeController.findByName(animeString).getBody();

        Assertions.assertThat(body).isEmpty();
    }


    @Test
    @DisplayName("save_Anime_ReturnListWhenSuccessfull")
    void save_Anime_ReturnListWhenSuccessfull(){
        Anime animeExpected = CreateAnime.savedAnime();
        AnimePostRequestBody animePostRequestBody = TestAnimePostRequestBody.createdAnimePostRequestBody();

        Anime body = animeController.save(animePostRequestBody).getBody();

        Assertions.assertThat(body).isNotNull();
        Assertions.assertThat(body.getName()).isEqualTo(animeExpected.getName());
    }

    @Test
    @DisplayName("Replace and Update Anime")
    void replace_Update_Anime_ReturnListWhenSuccessfull(){

        Assertions.assertThatCode(()-> animeController.replace(TestAnimePutRequestBody.createdAnimePutRequestBody()))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = animeController.replace(TestAnimePutRequestBody.createdAnimePutRequestBody());

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }

    @Test
    @DisplayName("Delete And Remove Anime")
    void delete_Remove_Anime_ReturnListWhenSuccessfull(){

        Assertions.assertThatCode(()-> animeController.delete(1L)).doesNotThrowAnyException();

        ResponseEntity<Void> entity = animeController.delete(1L);

        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }

}