package com.nassafy.core.respository;

import com.nassafy.core.entity.Probability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProbabilityRepository extends JpaRepository<Probability, Long> {

    List<Probability> findByAttractionId(Long attraction);
    Optional<Probability> findByAttractionIdAndDateTime(Long attractionId, LocalDateTime dateTime);

}
