package com.nassafy.core.respository;

import com.nassafy.core.entity.Interest;
import com.nassafy.core.entity.Member;
import com.nassafy.core.entity.MeteorInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeteorInterestRepository extends JpaRepository<MeteorInterest, Long>{
    Optional<MeteorInterest> findByMemberId(Long memberId);

    @Modifying
    @Transactional
    void deleteByMemberId(Long memberId);
}
