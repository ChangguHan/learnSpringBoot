package com.example.mreview.entity;

import lombok.*;

import javax.persistence.*;

/**
 * entity 아닌 값 객체(ElementCollection, Embeddable)로 사용 가능
 * 앞으로 페이지, 조인처리가 많아 엔티티로 사용할것임
 */

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString(exclude="movie") // 연관관계 주
public class MovieImage{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inum;

    private String uuid; // java.util.UUID 이용해 고유 번호 생성해 사용
    private String imgName;

    private String path; // 년/월/일 폴더구조

    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie; // FK

}
