package academy.devdojo.spring_boot2.util;

import academy.devdojo.spring_boot2.request.AnimePostRequestBody;
import academy.devdojo.spring_boot2.request.AnimePutRequestBody;

public class TestAnimePutRequestBody {
    public static AnimePutRequestBody createdAnimePutRequestBody(){
        return AnimePutRequestBody.builder()
                .name(CreateAnime.createdAnimeUpdate().getName())
                .build();
    }
}
