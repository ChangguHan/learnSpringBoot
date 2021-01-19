package com.example.guestbook.repository;

import com.example.guestbook.entity.Guestbook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

// Querydsl 이용하면 QuerydslPredicateExecutor 인터페이스 추가 상속
public interface GuestbookRepository extends JpaRepository<Guestbook, Long>, QuerydslPredicateExecutor<Guestbook> {
}
