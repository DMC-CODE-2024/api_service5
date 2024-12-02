package com.gl.mdr.repo.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gl.mdr.model.app.DistrictDb;
import java.util.List;


@Repository
public interface DistrictRepository extends JpaRepository<DistrictDb, Long> {
	
	DistrictDb findById(long id);
    List<DistrictDb> findByProvinceId(long id);
    List<DistrictDb> findAll();
    
}