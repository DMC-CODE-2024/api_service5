package com.gl.mdr.repo.app;

import com.gl.mdr.feature.stolenImeiStatusCheck.model.StolenViewResponse;
import com.gl.mdr.model.app.SearchImeiByPoliceMgmt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchImeiByPoliceMgmtRepository extends
        JpaRepository<SearchImeiByPoliceMgmt, Long>, JpaSpecificationExecutor<SearchImeiByPoliceMgmt> {

    @Query(value="select distinct(status) from search_imei_by_police_mgmt",nativeQuery = true)
    List<String> findDistinctByStatus();
}
