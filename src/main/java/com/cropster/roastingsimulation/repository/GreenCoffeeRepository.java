package com.cropster.roastingsimulation.repository;

import com.cropster.roastingsimulation.entity.GreenCoffee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GreenCoffeeRepository extends PagingAndSortingRepository<GreenCoffee,Long> {

    Page<GreenCoffee> findAllByFacility_Id(long id, Pageable pageable);
    long countAllByFacility_Id(long id);
    GreenCoffee findByNameAndFacility_Id(String name, long facilityId);
}
