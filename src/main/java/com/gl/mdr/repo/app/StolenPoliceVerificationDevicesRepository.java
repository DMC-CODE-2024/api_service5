package com.gl.mdr.repo.app;

import java.util.List;

import com.gl.mdr.feature.stolenImeiStatusCheck.model.StolenMatchedImeiResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gl.mdr.model.app.StolenLostModel;

@Repository
public interface StolenPoliceVerificationDevicesRepository  extends 
JpaRepository<StolenLostModel, Long>, JpaSpecificationExecutor<StolenLostModel>{ 
//public interface TrackLostDevicesRepository extends JpaRepository<TrackLostDevices, Long>{
	
	@SuppressWarnings("unchecked")
	public StolenLostModel save(StolenLostModel stolenLostModel);

	@Query(value="SELECT DISTINCT u.operator FROM app.track_lost_devices u where u.operator<>''",nativeQuery = true)
	public List<String> findDistinctOperator();

	@Query(value="SELECT DISTINCT u.status FROM app.lost_device_mgmt u where u.status<>''",nativeQuery = true)
	public List<String> findDistinctStatus();

	public StolenLostModel findByRequestId(String requestId);


	@Query(value="SELECT DISTINCT u.request_mode FROM app.lost_device_mgmt u where u.request_mode<>''",nativeQuery = true)
	public List<String> findDistinctrequestMode();
	
	@Query(value="SELECT DISTINCT u.request_type FROM app.lost_device_mgmt u where u.request_type<>''",nativeQuery = true)
	public List<String> getDistinctRequestType();

	@Query(value="SELECT DISTINCT trim(u.created_by) FROM app.lost_device_mgmt u where u.created_by<>''",nativeQuery = true)
	public List<String> getDistinctCreatedBy();

	//@Query(value="SELECT l.imei AS lost_device_imei,p.imei1 AS police_mgmt_imei1,p.imei2 AS police_mgmt_imei2,p.imei3 AS police_mgmt_imei3,p.imei4 AS police_mgmt_imei4 FROM LostDeviceDetail l LEFT JOIN SearchImeiByPoliceMgmt p WITH l.imei IN (p.imei1, p.imei2, p.imei3, p.imei4) WHERE l.requestId = :requestId" ,nativeQuery = true)
	//public List<String> getMatchedImei(String requestId);

	/*@Query("SELECT new com.gl.mdr.feature.stolenImeiStatusCheck.model.StolenMatchedImeiResponse" +
			"(l.imei AS lostDeviceImei, p.imei1 AS policeMgmtImei1, p.imei2 AS policeMgmtImei2, p.imei3 AS policeMgmtImei3, p.imei4 AS policeMgmtImei4) " +
			"FROM com.gl.mdr.model.app.LostDeviceDetail l LEFT JOIN com.gl.mdr.model.app.SearchImeiByPoliceMgmt p ON l.imei " +
			"IN (p.imei1, p.imei2, p.imei3, p.imei4) WHERE l.requestId = :requestId")
	public List<StolenMatchedImeiResponse> getMatchedImei_BACKUP(String requestId);*/


/*	@Query("SELECT new com.gl.mdr.feature.stolenImeiStatusCheck.model.StolenMatchedImeiResponse ("
			+ " GROUP_CONCAT(DISTINCT l.imei), "
			+ "GROUP_CONCAT(DISTINCT s.imei)) "
			+ "FROM com.gl.mdr.model.app.LostDeviceDetail l "
			+ "LEFT JOIN com.gl.mdr.model.app.SearchImeiDetailByPolice s ON l.requestId = s.requestId "
			+ "WHERE l.requestId = :requestId "
			+ "GROUP BY l.requestId")*/

	@Query("SELECT new com.gl.mdr.feature.stolenImeiStatusCheck.model.StolenMatchedImeiResponse  ("
			+ " l.imei AS lostDeviceImei, "
			+ " s.imei AS matchedImei )"
			+ "FROM LostDeviceDetail l "
			+ "LEFT JOIN SearchImeiDetailByPolice s ON l.imei = s.imei "
			+ "WHERE l.requestId = :requestId")
	public List<StolenMatchedImeiResponse> getMatchedImei(String requestId);

	@Query(value="SELECT DISTINCT trim(u.device_type) FROM app.lost_device_mgmt u where u.device_type<>''",nativeQuery = true)
	public List<String> findDistinctDeviceType();


}

