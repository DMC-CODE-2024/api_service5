package com.gl.mdr.repo.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gl.mdr.model.app.BulkCheckIMEIMgmt;

@Repository
public interface BulkIMEIFileUploadRepository extends JpaRepository<BulkCheckIMEIMgmt, Long>{
	
	@SuppressWarnings("unchecked")
	public BulkCheckIMEIMgmt save(BulkCheckIMEIMgmt bulkCheckIMEIMgmt);
	
	public BulkCheckIMEIMgmt findByTxnId(String txnId);
	
	//@Query(value="SELECT count(1) FROM app.bulk_check_imei_mgmt u where u.contact_number=?1 and status='DONE' and date(u.created_on)=date(now())",nativeQuery = true)
	
	@Query(value="SELECT count(1) FROM app.bulk_check_imei_mgmt u where u.contact_number=?1 and date(u.created_on)=date(now())",nativeQuery = true)
	public long findCount(String contactNumber);

}
