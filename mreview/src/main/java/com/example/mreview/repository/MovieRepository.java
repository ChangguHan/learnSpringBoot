package com.example.mreview.repository;

import com.example.mreview.entity.Movie;
import com.example.mreview.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    // JPQL 이용해서 group by 적용 시 리뷰 개수와 평균 평점 구할 수 있음
    @Query("select m, mi, avg(coalesce(r.grade,0)), count(r) from Movie m "
            + "left outer join MovieImage mi on mi.movie = m "
            + "left outer join Review r on r.movie = m group by m")
    Page<Object[]> getListPage(Pageable pageable);

    // 특정 영화 조회
    @Query("select m, mi, avg(coalesce(r.grade,0)), count(r) " +
            "from Movie m " +
            "left outer join MovieImage mi on mi.movie = m " +
            "left outer join Review r on r.movie = m " +
            "where m.mno = :mno group by mi")
    List<Object[]> getMovieWithAll(Long mno);



}
