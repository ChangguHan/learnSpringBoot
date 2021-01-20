package com.example.mreview.repository;

import com.example.mreview.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public class MemberRepository extends JpaRepository<Member, Long> {
}
