package com.cropster.roastingsimulation.repository;

import com.cropster.roastingsimulation.entity.Facility;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilityRepository extends PagingAndSortingRepository<Facility,Long> {

    Facility findByName(String name);
    @Query(nativeQuery = true, value = "SELECT * FROM facilities ORDER BY random() LIMIT 1")
    Facility retrieveRandom();
}
