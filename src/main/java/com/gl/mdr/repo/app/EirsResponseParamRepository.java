package com.gl.mdr.repo.app;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gl.mdr.model.app.EirsResponseParam;


@Repository
public interface EirsResponseParamRepository extends CrudRepository<EirsResponseParam, Long>, 
JpaRepository<EirsResponseParam, Long>, JpaSpecificationExecutor<EirsResponseParam>{
	
	@Query(value="SELECT * FROM app.eirs_response_param u where u.tag=?1 and LOWER(u.language)=?2",nativeQuery = true)
	public EirsResponseParam findByTagAndLanguage(String name,String language);
	
}
