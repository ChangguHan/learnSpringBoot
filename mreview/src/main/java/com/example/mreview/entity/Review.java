package com.example.mreview.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"movie","member"})
public class Review extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewnum;

    /**
     * p 380
     *
     * Fetch 방식 : LAZY
     * - 문제점
     *      - 한번에 여러 객체를 조회할 수 없음
     *      - @Transactional 적용해도, getMember().getEmail() 처리할때마다, Member 객체 로딩해줘야함
     *      - JPA 사용하는 경우 연관관계 FETCH 속성값은 LAZY로지정
     * - 해결방법
     *      - Repository에서 @Query 이용해서 조인처리
     *      - @EntityGraph 이용해서 Review 객체 가져올때 객체 로딩
     * - @EntityGraph
     *      - 엔티티 특정 속성을 같이 로딩
     *      - LAZY로 지정했을때 EAGER 로딩하도록 지정 가능
     *      - attributePaths 속성 : 로딩 설정을 변경하고 싶은 속성 이름의 배열로 명시
     *      - type : @EntityGraph를 어떤 방식으로 적용할것인지 설정
     *      - FETCH : attributePaths 속성은 EAGER로, 나머지는 LAZY
     *      - LOAD : attributePaths 속성은 EAGER, 나머지는 엔티티 클래스 명시되거나 기본방식으로 처리
     *
    */

    @ManyToOne(fetch= FetchType.LAZY)
    private Movie movie;

    @ManyToOne(fetch= FetchType.LAZY)
    private Member member;

    private int grade;
    private String text;
}
