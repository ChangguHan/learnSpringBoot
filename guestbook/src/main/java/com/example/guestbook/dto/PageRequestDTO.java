package com.example.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * PageRequestDTO :
 * - 화면에서 전달되는 목록 관련 데이터 처리
 * - 목록 페이지 요청할때 사용하는 데이터 재사용하기 쉽게 만듬
 * - 목록 처리 작업은 모든 게시판에서 사용될 가능성이 높아, 재사용 가능한 구조로 생성하는 것이 좋음
 *      - 페이지 번호나 한 페이지당 몇개 출력, 검색조건 같은 공통적 부분
 * - 파라미터를 DTO로 선언하고 나중에 재사용
 *
 *
 */

@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {
    // 목적 : JPA에서 사용하는 Pageable 객체 생
    private int page;
    private int size;
    // 검색기능 구현 위해 추가
    private String type;
    private String keyword;

    public PageRequestDTO() {
        this.page = 1;
        this.size = 10;
    }

    public Pageable getPageable(Sort sort) {
        return PageRequest.of(page-1, size, sort); // JPA는 페이지번호가 0부터 시작
    }
}
