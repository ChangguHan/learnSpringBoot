package com.example.mreview.service;

import com.example.mreview.dto.MovieDTO;
import com.example.mreview.dto.PageResultDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MovieServiceTests {
    @Autowired
    private MovieService service;

    @Test
    public void testGetMovie() {
        MovieDTO movieDTO = service.getMovie(102L);
        System.out.println(movieDTO);

    }
}
