package com.gl.mdr.repo.app;


import com.gl.mdr.model.app.MDRModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MDRRepo extends JpaRepository<MDRModel, Long>, JpaSpecificationExecutor<MDRModel> {
	public MDRModel findByDeviceId(String tac);
}
