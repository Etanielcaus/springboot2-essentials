package academy.devdojo.spring_boot2.repository;

import academy.devdojo.spring_boot2.dominio.Anime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@DisplayName("Tests for Anime Repository")
class AnimeRepositoryTest {

    @Autowired
    private AnimeRepository animeRepository;

    @Test
    public void save_Anime_WhenSusceful(){

        Anime animeToBeSave = createdAnime();

        Anime savedAnime = this.animeRepository.save(animeToBeSave);
        Assertions.assertThat(savedAnime).isNotNull();
        Assertions.assertThat(savedAnime.getId()).isNotNull();
        Assertions.assertThat(savedAnime.getName()).isEqualTo(animeToBeSave.getName());
    }

    private Anime createdAnime(){
        Anime build = Anime.builder()
                .name("Hajime")
                .build();
        return build;
    }
}