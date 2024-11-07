package academy.devdojo.spring_boot2.util;

import academy.devdojo.spring_boot2.request.AnimePostRequestBody;

public class TestAnimePostRequestBody {
    public static AnimePostRequestBody createdAnimePostRequestBody(){
        return AnimePostRequestBody.builder()
                .name(CreateAnime.createdNormalAnime().getName())
                .build();
    }
}
