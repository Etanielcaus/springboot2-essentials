package academy.devdojo.spring_boot2.repository;

import academy.devdojo.spring_boot2.dominio.Anime;

import java.util.List;

public interface AnimeRepository {
    List<Anime> listAll();
}
