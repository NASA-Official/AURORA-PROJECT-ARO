package com.nassafy.core.respository;

import com.nassafy.core.entity.Attraction;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AttractionRepository {
    private final EntityManager em;

    public void save(Attraction attraction) {
        if (attraction.getId() == null) {
            em.persist(attraction);
        } else {
            em.merge(attraction);
        }
    }

    public Attraction findOne(Long id) {
        return em.find(Attraction.class, id);
    }


    public List<Attraction> findAll() {
        return em.createQuery("select a from Attraction a", Attraction.class)
                .getResultList();
    }

    // 국가명 중복 안되게
    public List<String> findAllNation() {
        return em.createQuery("select distinct a.nation from Attraction a", String.class)
                .getResultList();
    }

    public List<Attraction> findByNation(String nation) {
        return em.createQuery("select a from Attraction a where a.nation = :nation", Attraction.class)
                .setParameter("nation", nation)
                .getResultList();

    }

    public Optional<Attraction> findById(Long attractionId) {
        return Optional.ofNullable(em.find(Attraction.class, attractionId));
    }

    public Optional<Attraction> findByLatLong(Float lat, Float lng) {
        try {
            Attraction attraction = em.createQuery("select a from Attraction a where a.latitude = :lat and a.longitude = :lng", Attraction.class)
                    .setParameter("lat", lat)
                    .setParameter("lng", lng)
                    .getSingleResult();
            return Optional.ofNullable(attraction);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }


}
