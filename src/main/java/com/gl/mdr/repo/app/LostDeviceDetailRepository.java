package com.gl.mdr.repo.app;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gl.mdr.model.app.LostDeviceDetail;

@Repository
public interface LostDeviceDetailRepository extends JpaRepository<LostDeviceDetail, Long>{
	
	@SuppressWarnings("unchecked")
	public LostDeviceDetail save(LostDeviceDetail greyList);
	
	public LostDeviceDetail findByImei(String imei);

	public List<LostDeviceDetail> findTop1ByImei(String imei);
	
	public List<LostDeviceDetail> findByImeiIgnoreCase(String imei);
	
	
	
}
