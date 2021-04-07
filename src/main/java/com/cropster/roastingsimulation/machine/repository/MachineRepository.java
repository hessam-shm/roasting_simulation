package com.cropster.roastingsimulation.machine.repository;

import com.cropster.roastingsimulation.machine.entity.Machine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MachineRepository extends PagingAndSortingRepository<Machine,Long> {

    Page<Machine> findAllByFacility_Id(long id, Pageable pageable);

    Iterable<Machine> findAllByFacility_Id(long id);

    long countAllByFacility_Id(long id);

    Machine findByNameAndFacility_Id(String name, long facilityId);
}
