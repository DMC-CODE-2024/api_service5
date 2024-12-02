package com.gl.mdr.repo.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.gl.mdr.model.app.StolenLostModel;

@Repository
public interface ChangeContactNumberDevicesRepository  extends 
JpaRepository<StolenLostModel, Long>, JpaSpecificationExecutor<StolenLostModel>{ 
	
	@SuppressWarnings("unchecked")
	public StolenLostModel save(StolenLostModel stolenLostModel);
	
	public StolenLostModel findByRequestId(String requestId);
}
