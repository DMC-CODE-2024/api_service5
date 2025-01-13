package com.gl.mdr.repo.app;


import com.gl.mdr.model.app.EirsResponseParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;


public interface EirsResponseRepo extends JpaRepository<EirsResponseParam, Long>, JpaSpecificationExecutor<EirsResponseParam> {

	public EirsResponseParam getByTagAndLanguage(String tag, String language);
	@Query(value="select value  from eirs_response_param  where tag=:tag and language=:lang", nativeQuery = true)
	public String getByTag(String tag,String lang);
}
