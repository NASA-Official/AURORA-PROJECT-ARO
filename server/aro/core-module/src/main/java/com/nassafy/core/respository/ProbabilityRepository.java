package com.nassafy.core.respository;

import com.nassafy.core.entity.Probability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ProbabilityRepository extends JpaRepository<Probability, Long> {

    Optional<Probability> findByAttractionIdAndDateTime(Long attractionId, LocalDateTime dateTime);

}
