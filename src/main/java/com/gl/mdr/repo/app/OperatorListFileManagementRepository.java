package com.gl.mdr.repo.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.gl.mdr.model.app.ListFileManagementModel;

@Repository
public interface OperatorListFileManagementRepository  extends 
JpaRepository<ListFileManagementModel, Long>, JpaSpecificationExecutor<ListFileManagementModel>{ 
	
	@SuppressWarnings("unchecked")
	public ListFileManagementModel save(ListFileManagementModel listFileManagementModel);

	
}
