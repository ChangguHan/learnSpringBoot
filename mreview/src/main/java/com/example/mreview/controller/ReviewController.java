package com.example.mreview.controller;

import com.example.mreview.dto.MovieDTO;
import com.example.mreview.dto.PageRequestDTO;
import com.example.mreview.dto.PageResultDTO;
import com.example.mreview.dto.ReviewDTO;
import com.example.mreview.entity.Review;
import com.example.mreview.service.MovieService;
import com.example.mreview.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reviews")
@Log4j2
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{mno}/all")
    public PageResultDTO<ReviewDTO, Review> getList(@PathVariable("mno") Long mno, PageRequestDTO requestDTO) {
        log.info("----------list------------");
        log.info("MNO: "+mno);

//        Map<String, Object> rs = new HashMap<>();
//
//        rs.put("result",requestDTO);
//        rs.put("dtoList", reviewService.getListOfMovie(mno, requestDTO));

        return reviewService.getListOfMovie(mno, requestDTO);
    }

//    @GetMapping("/{mno}/all")
//    public ResponseEntity<List<ReviewDTO>> getList(@PathVariable("mno") Long mno, PageRequestDTO requestDTO) {
//        log.info("----------list------------");
//        log.info("MNO: "+mno);
//
//        List<ReviewDTO> reviewDTOList = reviewService.getListOfMovie(mno, requestDTO);
//
//        return new ResponseEntity<>(reviewDTOList, HttpStatus.OK);
//    }


    @PostMapping("/{mno}")
    public ResponseEntity<Long> addReview(@RequestBody ReviewDTO movieReviewDTO) {
        log.info("----------add MovieReview------------");
        log.info("reviewDTO: "+movieReviewDTO);

        Long reviewnum = reviewService.register(movieReviewDTO);

        return new ResponseEntity<>(reviewnum, HttpStatus.OK);
    }

    @PutMapping("/{mno}/{reviewnum}")
    public ResponseEntity<Long> modifyReview(@PathVariable Long reviewnum,
                                             @RequestBody ReviewDTO movieReviewDTO) {


        log.info("----------modify MovieReview------------");
        log.info("reviewDTO: "+movieReviewDTO);

        reviewService.modify(movieReviewDTO);

        return new ResponseEntity<>(reviewnum, HttpStatus.OK);
    }

    @DeleteMapping("/{mno}/{reviewnum}")
    public ResponseEntity<Long> removeReview(@PathVariable Long reviewnum) {
        log.info("----------remove MovieReview------------");
        log.info("reviewnum: "+reviewnum);

        reviewService.remove(reviewnum);
        return new ResponseEntity<>(reviewnum, HttpStatus.OK);

    }
}
