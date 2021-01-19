package com.example.guestbook.repository;

import com.example.guestbook.entity.Guestbook;
import com.example.guestbook.entity.QGuestbook;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class GuestbookRepositoryTests {
    @Autowired
    private GuestbookRepository guestbookRepository;

    @Test
    public void insertDummies() {
        IntStream.rangeClosed(1,300).forEach(i-> {
            Guestbook guestbook = Guestbook.builder()
                    .title("Title...." + i)
                    .content("Content..."+i)
                    .writer("user"+(i%10))
                    .build();
            System.out.println(guestbookRepository.save(guestbook));
        });
    }

    @Test
    public void updateTest() {
        Optional<Guestbook> result = guestbookRepository.findById(300L); // 존재번호

        if(result.isPresent()) {
            Guestbook guestbook = result.get();
            guestbook.changeTitle("Changed Title...");
            guestbook.changeContent("Changed Content...");

            guestbookRepository.save(guestbook);
        }
    }

    /**
     * Querydsl 사용법
     * -  BooleanBuilder 생성
     * - 조건에 맞는 구ㅂ문은 Predicate 함수 생성
     * - BooleanBuilder에 작성된 Predicate 추가 및 실행
     */
    @Test
    public void testQuery1() {
        Pageable pageable = PageRequest.of(0,10, Sort.by("gno").descending());

        QGuestbook qGuestbook = QGuestbook.guestbook; // Q도메인 클래스 가져옴, 클래스에 선언된 필드를 변수 사용가능
        String keyword = "1";
        BooleanBuilder builder = new BooleanBuilder(); // BooleanBuilder : where문 조건을 넣어주는 컨테이너
        BooleanExpression expression = qGuestbook.title.contains(keyword); // Predicate 타입이어야함
        builder.and(expression);
        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable); // QuerydslPredicateExecutor 인터페이스의 findAll 사용
    }

    /**
     * 다중항목 검색
     */
    @Test
    public void testQuery2() {
        Pageable pageable = PageRequest.of(0,10,Sort.by("gno").descending());

        QGuestbook qGuestbook = QGuestbook.guestbook;
        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder();

        BooleanExpression exTitle = qGuestbook.title.contains(keyword);
        BooleanExpression exContent = qGuestbook.content.contains(keyword);
        BooleanExpression exAll = exTitle.or(exContent);

        builder.and(exAll);
        builder.and(qGuestbook.gno.gt(0L)); // gno가 0보다 크다

        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);

        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }

}
