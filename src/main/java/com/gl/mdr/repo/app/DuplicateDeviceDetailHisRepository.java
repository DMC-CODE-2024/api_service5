package com.gl.mdr.repo.app;

import com.gl.mdr.model.app.DuplicateDeviceDetailHis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface DuplicateDeviceDetailHisRepository extends JpaRepository<DuplicateDeviceDetailHis, Long>, JpaSpecificationExecutor<DuplicateDeviceDetailHis> {

    List<DuplicateDeviceDetailHis> findByImei(String imei);
}
