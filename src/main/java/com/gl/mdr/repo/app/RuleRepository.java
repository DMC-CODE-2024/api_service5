package com.gl.mdr.repo.app;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gl.mdr.model.app.RuleDb;


@Repository
public interface RuleRepository extends CrudRepository<RuleDb, Long>, 
JpaRepository<RuleDb, Long>, JpaSpecificationExecutor<RuleDb>{
	
	public RuleDb findByName(String name);
	
}
