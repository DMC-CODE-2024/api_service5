package com.gl.mdr.repo.app;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.gl.mdr.model.app.GdceData;

@Repository
public interface GdceDataRepository extends 
JpaRepository<GdceData, Long>, JpaSpecificationExecutor<GdceData>{
	public List<GdceData> findByImei(String imei);

	public List<GdceData> findTop1ByImei(String imei);
}
