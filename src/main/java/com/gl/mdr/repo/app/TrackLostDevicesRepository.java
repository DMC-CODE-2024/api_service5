package com.gl.mdr.repo.app;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.gl.mdr.model.app.TrackLostDevices;

@Repository
public interface TrackLostDevicesRepository  extends 
JpaRepository<TrackLostDevices, Long>, JpaSpecificationExecutor<TrackLostDevices>{ 
//public interface TrackLostDevicesRepository extends JpaRepository<TrackLostDevices, Long>{
	
	@SuppressWarnings("unchecked")
	public TrackLostDevices save(TrackLostDevices trackLostDevices);

	@Query(value="SELECT DISTINCT u.operator FROM app.track_lost_devices u where u.operator<>'' AND u.request_id<>''",nativeQuery = true)
	public List<String> findDistinctOperator();

	@Query(value="SELECT DISTINCT u.status FROM app.track_lost_devices u where u.status<>''  AND u.request_id<>'' ",nativeQuery = true)
	public List<String> findDistinctStatus();

	@Query(value="SELECT DISTINCT u.request_type FROM app.track_lost_devices u where u.request_type<>''",nativeQuery = true)
	public List<String> findDistinctRequestType();

}
