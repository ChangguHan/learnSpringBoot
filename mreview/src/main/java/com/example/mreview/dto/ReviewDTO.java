package com.example.mreview.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private Long reviewnum;

    // Review가 Movie, Member 참조
    // 엔티티와 달리 단순 문자열(mid, mno) 참조
    private Long mno;

    private Long mid;
    private String nickname;
    private String email;

    private int grade;
    private String text;
    private LocalDateTime regDate, modDate;
}
