package com.gl.mdr.repo.app;


import com.gl.mdr.model.app.ImeiSearchAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMEISearchAuditRepo extends
        JpaRepository<ImeiSearchAudit, Long>, JpaSpecificationExecutor<ImeiSearchAudit> {

}
