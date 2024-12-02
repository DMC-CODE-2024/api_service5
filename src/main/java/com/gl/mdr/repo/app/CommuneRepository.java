package com.gl.mdr.repo.app;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.gl.mdr.model.app.CommuneDb;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;


@Repository
@Transactional(rollbackOn = {SQLException.class})
public interface CommuneRepository extends JpaRepository<CommuneDb, Long>, JpaSpecificationExecutor<CommuneDb> {
    
	List<CommuneDb> findByDistrictId(long id);
	List<CommuneDb> findAll();
	CommuneDb findById(long id);

}
