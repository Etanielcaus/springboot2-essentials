package com.devdojo.academy.controller;

import com.devdojo.academy.util.DateUtil;
import com.devdojo.academy.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("anime")
@RestController
@Log4j2
public class AnimeController {

    @Autowired
    private DateUtil dateUtil;

    @GetMapping(path = "list")
    public List<Anime> animeList(){
        String s = dateUtil.localDateUtilTimeFormatter(LocalDateTime.now());
        log.info(s);
        return List.of(new Anime("DBZ"), new Anime("Naruto"));
    }
}
