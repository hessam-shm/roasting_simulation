package com.cropster.roastingsimulation.repository;

import com.cropster.roastingsimulation.entity.RoastingProcess;
import org.springframework.data.repository.CrudRepository;

public interface RoastingProcessRepository extends CrudRepository<RoastingProcess,Long> {
}
