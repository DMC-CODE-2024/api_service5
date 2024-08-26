package com.gl.mdr.repo.oam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.gl.mdr.model.oam.RequestHeaders;

@Repository
public interface RequestHeadersRepository extends 
JpaRepository<RequestHeaders, Integer>, JpaSpecificationExecutor<RequestHeaders>{
	
	@SuppressWarnings("unchecked")
	public RequestHeaders save(RequestHeaders requestHeaders);
	
		
}
