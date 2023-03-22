package com.nassafy.core.respository;

import com.nassafy.core.entity.StampImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StampImageRepository extends JpaRepository<StampImage, Long> {

    List<StampImage> findByStampId(Long stampId);

    Optional<StampImage> findByImage(String image);
}
