package com.nassafy.core.respository;

import com.nassafy.core.entity.Meteor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeteorRepository extends JpaRepository<Meteor, Long> {

    List<Meteor> findByNation(String nation);
}
