package com.gl.mdr.repo.app;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gl.mdr.model.app.LostDeviceDetailHistory;

@Repository
public interface LostDeviceDetailHistoryRepo extends JpaRepository<LostDeviceDetailHistory, Long>{

	List<LostDeviceDetailHistory> findByImei(String imei);

	List<LostDeviceDetailHistory> findTop1ByImei(String imei);
	

}
