package com.gl.mdr.repo.app;


import com.gl.mdr.model.app.LostDeviceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface StolenLostDetailRepo extends JpaRepository<LostDeviceDetail, Long>, JpaSpecificationExecutor<LostDeviceDetail> {

	
/*	@Modifying
	 @Query("update LostStolenMgmt a  set a.requestType='Recovery' ,MODIFIED_ON=SYSTIMESTAMP where a.requestId=:requestId")
		public void updateByRequestId(String requestId);*/
	public LostDeviceDetail findByRequestId(String requestId);
	public LostDeviceDetail findByImei(String imei);
}
