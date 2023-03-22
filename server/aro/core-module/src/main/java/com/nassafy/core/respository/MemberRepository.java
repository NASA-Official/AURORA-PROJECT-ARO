package com.nassafy.core.respository;

import com.nassafy.core.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<Member> findByEmail(String email);

    Optional<Member> findById(Long id);

    int deleteByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.nickname = :nickname WHERE m.email = :email")
    int updateNickname(String email, String nickname);

}
