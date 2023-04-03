package com.nassafy.core.respository;

import com.nassafy.core.entity.MeteorInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeteorInterestRepository extends JpaRepository<MeteorInterest, Long>{
}
