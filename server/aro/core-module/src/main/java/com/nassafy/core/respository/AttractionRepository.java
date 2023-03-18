package com.nassafy.core.respository;

import com.nassafy.core.entity.Attraction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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

    public Attraction findOne(Long id) { return em.find(Attraction.class, id);}


    public List<Attraction> findAll(){
        return em.createQuery("select a from Attraction a", Attraction.class)
                .getResultList();
    }

    // 국가명 중복 안되게
    public List<String> findAllNation(){
        return em.createQuery("select distinct a.nation from Attraction a", String.class)
                .getResultList();
    }

    public List<Attraction> findByNation(String nation){
        return em.createQuery("select a from Attraction a where a.nation = :nation", Attraction.class)
                .setParameter("nation", nation)
                .getResultList();

    }

    public Optional<Attraction> findById(Long attractionId) {
        return Optional.ofNullable(em.find(Attraction.class, attractionId));
    }
}
