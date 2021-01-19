package com.example.guestbook.service;

import com.example.guestbook.dto.GuestbookDTO;
import com.example.guestbook.dto.PageRequestDTO;
import com.example.guestbook.dto.PageResultDTO;
import com.example.guestbook.entity.Guestbook;

public interface GuestbookService {
    Long register(GuestbookDTO dto);

    // getList : PageReqDTO를 통해 PageResultDTO를 만듬
    // param : PageRequestDTO
    // return : PageResultDTO
    PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO);

    default Guestbook dtoToEntity(GuestbookDTO dto) {
        Guestbook entity = Guestbook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return entity;
    }

    //entityToDto : entity -> DTO
    default GuestbookDTO entityToDto(Guestbook entity) {
        GuestbookDTO dto = GuestbookDTO.builder()
                .gno(entity.getGno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();

        return dto;
    }

    /**
     * 새로운 기능 추가 순서
     * 1. DTO
     * 2. 서비스
     * 3. 서비스 테스트
     * 4. 컨트롤러
     * 5. 페이지
     */
    // 조회기능
    GuestbookDTO read(Long gno);

    // 삭제
    void remove(Long gno);

    // 수정
    void modify(GuestbookDTO dto);

}
