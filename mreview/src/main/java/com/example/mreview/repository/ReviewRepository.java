package com.example.mreview.repository;

import com.example.mreview.entity.Member;
import com.example.mreview.entity.Movie;
import com.example.mreview.entity.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // Review 처리할때 @EntityGraph 적용해서 Member도 같이 로딩하도록 변경
    // type이 FETCH이므로, member를 EAGER로, 나머지를 LAZY로 처리
    @EntityGraph(attributePaths={"member"}, type=EntityGraph.EntityGraphType.FETCH)
    List<Review> findByMovie(Movie movie);

    // M:N 주의할점
    // '명사'에 해당하는 데이터 삭제시 매핑 테이블의 데이터 삭제 필요
    // member 삭제 시, review 삭제 후 member 삭제해야함(트랜잭션으로 관리)

    // 회원 삭제 메서드
    // JpaRepository 기능만으로 삭제 가능
    // 그냥 JPA 사용할 경우, 쿼리 여러번 반복하여 각자 리뷰를 삭제함
    // 그래서 @Query로 where 지정시 효율적으로 처리 가능
    // @Modifying : update나 delete 이용
    @Modifying
    @Query("delete " +
            "from Review mr " +
            "where mr.member = :member ")
    void deleteByMember(Member member);
}
