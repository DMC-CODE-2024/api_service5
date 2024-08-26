package com.gl.mdr.repo.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gl.mdr.model.app.WebActionDb;

@Repository
public interface WebActionDbRepository extends JpaRepository<WebActionDb, Long>{
	
	@SuppressWarnings("unchecked")
	public WebActionDb save(WebActionDb webActionDb);

}
