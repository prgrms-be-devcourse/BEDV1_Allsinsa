package com.progm.allsinsa.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.progm.allsinsa.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    Optional<Member> findByName(String name);
}
