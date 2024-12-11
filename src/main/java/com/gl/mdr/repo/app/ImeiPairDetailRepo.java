package com.gl.mdr.repo.app;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gl.mdr.model.app.ImeiPairDetail;

@Repository
public interface ImeiPairDetailRepo extends JpaRepository<ImeiPairDetail, Long>{
	public List<ImeiPairDetail> findByImei(String imei);

	public List<ImeiPairDetail> findByImeiAndImsi(String imei, String imsi);

	//public Optional<ImeiPairDetail> findByImei(Long id);

	public List<ImeiPairDetail> findTop1ByImei(String imei);
}