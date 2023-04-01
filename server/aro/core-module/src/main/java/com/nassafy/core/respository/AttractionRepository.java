package com.nassafy.core.respository;

import com.nassafy.core.DTO.WeatherAndProbDTO;
import com.nassafy.core.entity.Attraction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
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

    public List<WeatherAndProbDTO> findWeatherAndProbList(LocalDateTime dateTime, Long memberId) {
        Query query = em.createNativeQuery("SELECT attraction_name, main, prob FROM attraction JOIN interest ON interest.attraction_id = attraction.attraction_id AND interest.member_id = :memberId" +
                        " JOIN weather ON ABS(attraction.latitude - weather.latitude) <= 1e-2 AND ABS(attraction.longitude - weather.longitude) <= 1e-2 AND weather.date_time = :dateTime" +
                        " JOIN probability ON probability.attraction_id = attraction.attraction_id AND probability.date_time = :dateTime")
                .setParameter("dateTime", dateTime)
                .setParameter("memberId", memberId);

        JpaResultMapper jpaResultMapper = new JpaResultMapper();

        return jpaResultMapper.list(query, WeatherAndProbDTO.class);
    }



}
