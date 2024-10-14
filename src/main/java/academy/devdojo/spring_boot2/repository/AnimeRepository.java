package academy.devdojo.spring_boot2.repository;

import academy.devdojo.spring_boot2.dominio.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimeRepository extends JpaRepository<Anime, Long> {
}
