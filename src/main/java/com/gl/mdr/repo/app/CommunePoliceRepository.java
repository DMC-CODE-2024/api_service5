package com.gl.mdr.repo.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.gl.mdr.model.app.PoliceStationDb;
import com.gl.mdr.model.app.ProvinceDb;

import jakarta.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;

@Repository
@Transactional(rollbackOn = {SQLException.class})
public interface CommunePoliceRepository extends JpaRepository<PoliceStationDb, Long>, JpaSpecificationExecutor<PoliceStationDb> {
    List<PoliceStationDb> findByCommuneId(long id);
    PoliceStationDb findById(long id);
    List<PoliceStationDb> findAll();

}
