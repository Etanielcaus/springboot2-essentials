package academy.devdojo.spring_boot2.integration;

import academy.devdojo.spring_boot2.dominio.Anime;
import academy.devdojo.spring_boot2.repository.AnimeRepository;
import academy.devdojo.spring_boot2.request.AnimePostRequestBody;
import academy.devdojo.spring_boot2.util.CreateAnime;
import academy.devdojo.spring_boot2.util.TestAnimePostRequestBody;
import academy.devdojo.spring_boot2.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AnimeControllerIT {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @LocalServerPort
    private int port;
    @Autowired
    private AnimeRepository animeRepository;
    @Test
    @DisplayName("list returns list of anime inside page object when successful")
    void list_ReturnsListOfAnimesInsidePageObject_WhenSuccessful(){
        Anime savedAnime = animeRepository.save(CreateAnime.createdNormalAnime());

        String expectedName = savedAnime.getName();

        PageableResponse<Anime> animePage = testRestTemplate.exchange("/animes", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Anime>>() {
                }).getBody();

        Assertions.assertThat(animePage).isNotNull();

        Assertions.assertThat(animePage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("Return a list of Anime when succesfuly")
    void list_ReturnListOfAnimes_WhenSuccessfull(){
        Anime savedAnime = animeRepository.save(CreateAnime.createdNormalAnime());

        String nameExcpected = savedAnime.getName();
        List<Anime> animeList = testRestTemplate.exchange("/animes/listAll/", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Anime>>() {
                }).getBody();

        Assertions.assertThat(animeList).isNotNull();
        Assertions.assertThat(animeList).isNotEmpty();
        Assertions.assertThat(animeList).hasSize(1);
//        Assertions.assertThat(animeList.get(0).getName()).isEqualTo(nameExcpected);
    }


    @DisplayName("findById_Return_List_Anime_WhenSuccessfuly")
    @Test
    void findById_Return_List_Anime_WhenSuccessfuly(){
        Anime save = animeRepository.save(CreateAnime.createdNormalAnime());
        Long expectedId = save.getId();

        Anime animeTest = testRestTemplate.getForObject("/animes/{id}/", Anime.class, expectedId);

        Assertions.assertThat(animeTest).isNotNull();
        Assertions.assertThat(animeTest.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("Find by name")
    void findBYName_ReturnListWhenSuccessfull(){
        Anime save = animeRepository.save(CreateAnime.createdNormalAnime());
        String expectedName = save.getName();

        String url = String.format("/animes/find?name=%s", expectedName);

        List<Anime> animeList = testRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Anime>>() {
                }).getBody();

        Assertions.assertThat(animeList).isNotEmpty();
        Assertions.assertThat(animeList.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("Anime Not Found")
    void findBYName_ReturnEmptyList_WhenNotFound(){
        List<Anime> animeList = testRestTemplate.exchange("/animes/find?name=dbz", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Anime>>() {
                }).getBody();

        Assertions.assertThat(animeList).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("save_Anime_ReturnListWhenSuccessfull")
    void save_Anime_ReturnListWhenSuccessfull(){
        AnimePostRequestBody animePostRequestBody1 = TestAnimePostRequestBody.createdAnimePostRequestBody();

        ResponseEntity<Anime> animeResponseEntity = testRestTemplate.postForEntity("/animes/", animePostRequestBody1, Anime.class);


        Assertions.assertThat(animeResponseEntity).isNotNull();
        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(animeResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(animeResponseEntity.getBody().getId()).isNotNull();
    }

    @Test
    @DisplayName("Replace and Update Anime")
    void replace_Update_Anime_ReturnListWhenSuccessfull(){

        Anime save = animeRepository.save(CreateAnime.createdNormalAnime());
        save.setName("dbz");

        ResponseEntity<Void> exchange = testRestTemplate.exchange("/animes", HttpMethod.PUT, new HttpEntity<>(save), Void.class);

        Assertions.assertThat(exchange).isNotNull();
        Assertions.assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }

    @Test
    @DisplayName("Delete And Remove Anime")
    void delete_Remove_Anime_ReturnListWhenSuccessfull(){

        Anime save = animeRepository.save(CreateAnime.createdNormalAnime());

        ResponseEntity<Void> exchange = testRestTemplate.exchange("/animes/{id}", HttpMethod.DELETE,
                null, Void.class, save.getId());

        Assertions.assertThat(exchange).isNotNull();
        Assertions.assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}