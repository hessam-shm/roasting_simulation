package com.cropster.roastingsimulation.roastingprocess.repository;

import com.cropster.roastingsimulation.roastingprocess.entity.RoastingProcess;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface RoastingProcessRepository extends CrudRepository<RoastingProcess,Long> {

    @Query("SELECT rp.id FROM RoastingProcess rp WHERE rp.id = :id " +
            "AND (rp.startTime > :endTime OR rp.endTime < :startTime)")
    List<Long> findCollidingTime(long id, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
