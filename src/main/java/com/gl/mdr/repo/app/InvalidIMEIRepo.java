package com.gl.mdr.repo.app;


import com.gl.mdr.model.app.EirsInvalidImei;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InvalidIMEIRepo extends JpaRepository<EirsInvalidImei, Long>, JpaSpecificationExecutor<EirsInvalidImei> {

    Boolean existsByImei(String IMEI);
}
