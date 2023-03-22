package com.nassafy.core.respository;

import com.nassafy.core.entity.Interest;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public interface InterestRepository extends JpaRepository<Interest, Long> {

    Optional<List<Interest>> findAllByMemberId(Long memberId);

}
