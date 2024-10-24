package academy.devdojo.spring_boot2.util;

import academy.devdojo.spring_boot2.dominio.Anime;

public class CreateAnime {
    public static Anime savedAnime(){
        return Anime.builder()
                .name("Hajime")
                .build();
    }
    public static Anime createdNormalAnime(){
        return Anime.builder()
                .name("Hajime")
                .id(1L)
                .build();
    }
    public static Anime createdAnimeUpdate(){
        return Anime.builder()
                .name("Hajime 2")
                .id(1L)
                .build();
    }
}
