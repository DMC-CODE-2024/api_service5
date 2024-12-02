package com.gl.mdr.repo.app;



import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gl.mdr.model.app.ProvinceDb;

public interface ProvinceRepository extends JpaRepository<ProvinceDb, Long>, JpaSpecificationExecutor<ProvinceDb> {
	 // Custom query to find a Province by ID
	ProvinceDb findById(long id);

    // Retrieves all ProvinceDb records
    List<ProvinceDb> findAll();
    
   // ProvinceDb findByIds(long id);
}

