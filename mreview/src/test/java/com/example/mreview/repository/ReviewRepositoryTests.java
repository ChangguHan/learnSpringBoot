package com.example.mreview.repository;

import com.example.mreview.entity.Member;
import com.example.mreview.entity.Movie;
import com.example.mreview.entity.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class ReviewRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void insertMoviewReviews() {
        // 200개 리뷰 등록
        IntStream.rangeClosed(1,200).forEach(i -> {
            //Movie, Reviewer는 임의의값으로 생성
            // 영화번호
//            Long mno = (long)(Math.random() * 100) +1;
            Long mno = (long)213;

            // 리뷰어번호
            Long mid = ((long)(Math.random()*300) + 101);
            Member member = Member.builder().mid(mid).build();

            Review movieReview = Review.builder()
                    .member(member)
                    .movie(Movie.builder().mno(mno).build())
                    .grade((int)(Math.random()*5)+1)
                    .text("이 영화에 대한 느낌..." + i)
                    .build();

            reviewRepository.save(movieReview);
        });
    }


    @Test
    public void testGetMovieReviews() {
        Movie movie = Movie.builder().mno(92L).build();

        List<Review> result = reviewRepository.findByMovie(movie);

        result.forEach(movieReview -> {
            System.out.println(movieReview.getReviewnum());
            System.out.println("\t" + movieReview.getGrade());
            System.out.println("\t" + movieReview.getText());
            System.out.println("\t" + movieReview.getMember().getEmail());
            System.out.println("------------------------");

        });
    }

    @Commit
    @Transactional
    @Test
    public void testDeleteMember() {
        Long mid = 2L;
        Member member = Member.builder().mid(mid).build();

        // review가 FK로 member 참조하고 있으므로
        // review 먼저 삭제 후 member 삭제 필요 && 트랜잭션 처리 필요
        reviewRepository.deleteByMember(member);
        memberRepository.deleteById(mid);

    }

}
