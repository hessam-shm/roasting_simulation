package com.cropster.roastingsimulation.greencoffee.repository;

import com.cropster.roastingsimulation.greencoffee.entity.GreenCoffee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GreenCoffeeRepository extends PagingAndSortingRepository<GreenCoffee,Long> {

    Page<GreenCoffee> findAllByFacility_Id(long id, Pageable pageable);
    Iterable<GreenCoffee> findAllByFacility_Id(long facilityId);
    long countAllByFacility_Id(long id);
    GreenCoffee findByNameAndFacility_Id(String name, long facilityId);
}
