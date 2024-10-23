package academy.devdojo.spring_boot2.repository;

import academy.devdojo.spring_boot2.dominio.Anime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for Anime Repository")
class AnimeRepositoryTest {

    @Autowired
    private AnimeRepository animeRepository;

    @Test
    @DisplayName("Save Anime When Susceful")
    public void save_Anime_WhenSusceful(){

        Anime animeToBeSave = createdAnime();

        Anime savedAnime = this.animeRepository.save(animeToBeSave);
        Assertions.assertThat(savedAnime).isNotNull();
        Assertions.assertThat(savedAnime.getId()).isNotNull();
        Assertions.assertThat(savedAnime.getName()).isEqualTo(animeToBeSave.getName());
    }

    @Test
    @DisplayName("Save and Update Anime When Susceful")
    public void save_Update_Anime_WhenSusceful(){

        Anime animeToBeSave = createdAnime();
        Anime savedAnime = this.animeRepository.save(animeToBeSave);
        
        savedAnime.setName("Naruto");

        Anime animeUpdate = this.animeRepository.save(savedAnime);

        Assertions.assertThat(animeUpdate).isNotNull();
        Assertions.assertThat(animeUpdate.getId()).isNotNull();
        Assertions.assertThat(animeUpdate.getName()).isEqualTo(animeToBeSave.getName());
    }

    @Test
    @DisplayName("Save and Remove Anime When Susceful")
    public void save_RemoveAnime_WhenSusceful(){

        Anime animeToBeSave = createdAnime();
        Anime savedAnime = this.animeRepository.save(animeToBeSave);

        this.animeRepository.delete(savedAnime);

        Optional<Anime> animeOptional = this.animeRepository.findById(savedAnime.getId());

        Assertions.assertThat(animeOptional).isEmpty();
    }

//    Fora do curso.
    @Test
    @DisplayName("Find Anime With name, return a animes list when succesful")
    public void findAnimeWithName(){
        Anime animeToSearch = createdAnime();
        Anime savedAnimeToSearch = this.animeRepository.save(animeToSearch);
        List<Anime> byName = this.animeRepository.findByName(animeToSearch.getName());

        Assertions.assertThat(byName).contains(savedAnimeToSearch);
    }

    @Test
    @DisplayName("Find Anime With name, return a empty list when Anime is not found")
    public void find_Anime_ReturnEmptyList(){
        List<Anime> byName = this.animeRepository.findByName("xuxa");
        Assertions.assertThat(byName).isEmpty();
    }

    @Test
    @DisplayName("Save throw ConstraitionViolationExcpetion when name is empty")
    public void throw_nameisEmpty_ContraintViolationExcpetion(){

        Anime anime = new Anime();
//        Assertions.assertThatThrownBy(() -> this.animeRepository.save(anime))
//                .isInstanceOf(ConstraintViolationException.class);

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.animeRepository.save(anime));
    }

    private Anime createdAnime(){
        return Anime.builder()
                .name("Hajime")
                .build();
    }
}