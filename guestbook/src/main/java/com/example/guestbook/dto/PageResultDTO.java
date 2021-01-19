package com.example.guestbook.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * PageResultDTO
 * - 페이지 처리 결과를 Page<Entity>로 반환
 * - 다양한 곳에서 사용할 수 있도록, 제네릭 타입 이용
 * - DTO, ENtity
 */
@Data
public class PageResultDTO<DTO, EN> {
    // DTO리스트
    private List<DTO> dtoList;

    // 총페이지 번호
    private int totalPage;

    // 현재 페이지 번호
    private int page;

    // 목록 사이즈
    private int size;

    // 시작 페이지 번호, 끝 페이지 번호
    private int start, end;

    // 이전, 다음
    private boolean prev, next;

    // 페이지 번호 목록
    private List<Integer> pageList;

    // Page<Entity> 타입 이용해 생성 가능
    // Function<EN,DTO> : 엔티티 객체를 DTO로 변환시켜줌
    // 제네릭 사용하면 : 어떤 종류의 Page<E> 생성되어도 PageResultDTO 이용해 처리 가능
    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {
        dtoList = result.stream().map(fn).collect(Collectors.toList());
        totalPage = result.getTotalPages();
        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable) {
        this.page = pageable.getPageNumber()+1; // 0부터 시작하므로 1 추가
        this.size = pageable.getPageSize();

        int tempEnd = (int)(Math.ceil(page/10.0) * 10);
        start = tempEnd - 9;
        prev = start > 1;
        end = totalPage > tempEnd ? tempEnd : totalPage;
        next = totalPage > tempEnd;
        pageList = IntStream.rangeClosed(start,end).boxed().collect(Collectors.toList());
    }
}
