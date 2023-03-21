package com.nassafy.core.respository;

import com.nassafy.core.entity.Stamp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StampRepository extends JpaRepository<Stamp, Long> {
    List<Stamp> findByMemberId(Long memberId);

    List<Stamp> findByAttractionNation(String nation);



    Optional<Stamp> findByAttraction_nationAndAttraction_attractionNameAndMemberId(String nation, String attractionName, Long memberId);
}