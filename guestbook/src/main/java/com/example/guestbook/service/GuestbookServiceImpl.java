package com.example.guestbook.service;

import com.example.guestbook.dto.GuestbookDTO;
import com.example.guestbook.dto.PageRequestDTO;
import com.example.guestbook.dto.PageResultDTO;
import com.example.guestbook.entity.Guestbook;
import com.example.guestbook.repository.GuestbookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor // 의존성 자동주입
public class GuestbookServiceImpl implements GuestbookService{

    private final GuestbookRepository repository; // final로 선언, RequiredArgsConstructor에 의해 자동으로 주

    @Override
    public Long register(GuestbookDTO dto) {
        log.info("DTO------------");
        log.info(dto);

        Guestbook entity = dtoToEntity(dto);
        log.info(entity);

        repository.save(entity);

        return entity.getGno();
    }

    // getList : PageReqDTO를 통해 PageResultDTO를 만듬

    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());

        Page<Guestbook> result = repository.findAll(pageable);

        Function<Guestbook, GuestbookDTO> fn = (entity -> entityToDto(entity));
        return new PageResultDTO<>(result, fn);
    }

    // 조회
    // GuestbookRepository에서 findById() 통해 엔티티 가져와서 DTO로 변환
    // GET방식으로 gno 받아 Model에 GuestbookDTO 담아서 전달
    @Override
    public GuestbookDTO read(Long gno) {
        Optional<Guestbook> result=repository.findById(gno);

        return result.isPresent()? entityToDto(result.get()): null;
    }

    // 삭제
    @Override
    public void remove(Long gno) {
        repository.deleteById(gno);
    }

    // 조회

    @Override
    public void modify(GuestbookDTO dto) {
        // 제목, 내용만 업데이트
        Optional<Guestbook> result = repository.findById(dto.getGno());

        if(result.isPresent()) {
            Guestbook entity = result.get();
            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());
            repository.save(entity);
        }
    }
}
