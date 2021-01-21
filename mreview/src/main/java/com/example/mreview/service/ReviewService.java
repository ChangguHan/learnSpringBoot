package com.example.mreview.service;

import com.example.mreview.dto.*;
import com.example.mreview.entity.Member;
import com.example.mreview.entity.Movie;
import com.example.mreview.entity.MovieImage;
import com.example.mreview.entity.Review;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface ReviewService {

    // 영화 모든 리뷰 가져옴
    PageResultDTO<ReviewDTO, Review> getListOfMovie(Long mno, PageRequestDTO requestDTO);

    // 영화 리뷰 추가
    Long register(ReviewDTO movieReviewDTO);

    // 특정 리뷰 수정
    void modify(ReviewDTO movieReviewDTO);

    // 리뷰삭제
    void remove(Long reviewnum);

    default Review dtoToEntity(ReviewDTO movieReviewDTO) {

        Review review = Review.builder()
                .reviewnum(movieReviewDTO.getReviewnum())
                .movie(Movie.builder()
                        .mno(movieReviewDTO.getMno()).build())
                .member(Member.builder()
                        .mid(movieReviewDTO.getMid()).build())
                .grade(movieReviewDTO.getGrade())
                .text(movieReviewDTO.getText())
                .build();

        return review;

    }

    default ReviewDTO entitiyToDTO(Review movieReview) {
        ReviewDTO movieReviewDTO = ReviewDTO.builder()
                .reviewnum(movieReview.getReviewnum())
                .mno(movieReview.getMovie().getMno())
                .mid(movieReview.getMember().getMid())
                .nickname(movieReview.getMember().getNickname())
                .email(movieReview.getMember().getEmail())
                .grade(movieReview.getGrade())
                .text(movieReview.getText())
                .regDate(movieReview.getRegDate())
                .modDate(movieReview.getModDate())
                .build();

        return movieReviewDTO;

    }

}
