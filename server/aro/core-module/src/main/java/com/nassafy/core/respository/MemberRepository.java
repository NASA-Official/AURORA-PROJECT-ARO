package com.nassafy.core.respository;

import com.nassafy.core.DTO.ProviderType;
import com.nassafy.core.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    Optional<Member> findByEmail(String email);
    Optional<Member> findByEmailAndAndProviderType(String email, ProviderType providerType);

    Optional<Member> findById(Long id);

    int deleteByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.nickname = :nickname WHERE m.email = :email")
    int updateNickname(@Param("email") String email, @Param("nickname") String nickname);

    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.email = :email WHERE m.refreshToken = :refreshToken")
    int updateRefreshToken(@Param("email") String email, @Param("refreshToken") String refreshToken);

}
