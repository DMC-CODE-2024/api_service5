package com.gl.mdr.repo.audit;


import com.gl.mdr.model.audit.ImeiSearchAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IMEISearchAuditRepo extends
        JpaRepository<ImeiSearchAudit, Long>, JpaSpecificationExecutor<ImeiSearchAudit> {

}
