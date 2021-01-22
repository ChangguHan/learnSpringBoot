package com.example.mreview.service;

import com.example.mreview.dto.ReviewDTO;
import com.example.mreview.entity.Review;
import com.example.mreview.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class ReviewServiceTests {
    @Autowired
    private ReviewService service;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void testGetListOfMovie() {
        System.out.println("============ TEST START ============");

//        IntStream.rangeClosed(1,10).forEach(i-> {
//            long count = (long)(Math.random() * 100) + 1; // 1,2,3,4
//            for(ReviewDTO reviewDTO : service.getListOfMovie(count)) {
//                System.out.println(reviewDTO);
//            }
//        });

        System.out.println("============ TEST EBD ============");
    }

//    @Test
//    public void testRegister() {
//        System.out.println("============ TEST START ============");
//
//
//        long count = (long)(Math.random() * 100) + 1; // 1,2,3,4
//
//        Optional<Review> result = reviewRepository.findById(count);
//
//        if(result.isPresent()) {
//
//            Review movieReview = result.get();
//            System.out.println(movieReview);
//            ReviewDTO reviewDTO = service.entitiyToDTO(movieReview);
//            reviewDTO.setReviewnum(count + 100);
//            System.out.println(service.register(reviewDTO));
//
//        }
//
//        System.out.println("============ TEST EBD ============");
//    }
//

}
