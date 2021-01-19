package com.example.guestbook.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass // 테이블로 생성되지 않음,  해당 클래스 상속 엔티티 클래스로 테이블 생성됨
@EntityListeners(value={AuditingEntityListener.class}) // JPA 내부 엔티티 객체 생성 및 변경 감지 : AuditingEntityListener
@Getter
abstract class BaseEntity {
    @CreatedDate // JPA에서 생성시간 처리, updatable = false >> 엔티티 데이터베이스에 반영할때 컬럼값 변경되지 않음
    @Column(name="regdate", updatable=false)
    private LocalDateTime regDate;

    @LastModifiedDate // JPA에서 최종수정시간 처리
    @Column(name="moddate")
    private LocalDateTime modDate;
}