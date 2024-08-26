package com.gl.mdr.repo.app;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gl.mdr.model.app.GreyListDeviceHistory;

public interface GreyListHistoryRepo extends JpaRepository<GreyListDeviceHistory, Long>, JpaSpecificationExecutor<GreyListDeviceHistory> {

	List<GreyListDeviceHistory> findByImei(String imei);

	List<GreyListDeviceHistory> findTop1ByImei(String imei);

	List<GreyListDeviceHistory> findByImeiAndImsi(String imei, String imsi);

}
