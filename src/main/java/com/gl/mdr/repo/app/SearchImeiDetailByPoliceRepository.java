package com.gl.mdr.repo.app;

import com.gl.mdr.feature.stolenImeiStatusCheck.model.StolenViewResponse;
import com.gl.mdr.model.app.SearchImeiDetailByPolice;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SearchImeiDetailByPoliceRepository extends
        JpaRepository<SearchImeiDetailByPolice, Long>, JpaSpecificationExecutor<SearchImeiDetailByPolice> {

    /*@Query("SELECT s.requestId, COUNT(s) FROM SearchImeiDetailByPolice s WHERE s.transactionId = :transactionId GROUP BY s.requestId")
    List<SearchImeiDetailByPolice> findGroupedByRequestId(@Param("transactionId") String transactionId);*/

    @Query("SELECT new com.gl.mdr.feature.stolenImeiStatusCheck.model.StolenViewResponse(s.requestId, s.contactNumber, s.deviceOwnerName, s.requestMode, s.deviceOwnerAddress, deviceLostPoliceStation, transactionId, COUNT(s)) " +
            "FROM SearchImeiDetailByPolice s " +
            "WHERE s.transactionId = :transactionId " +
            "GROUP BY s.requestId, s.contactNumber, s.deviceOwnerName, s.requestMode, s.deviceOwnerAddress, deviceLostPoliceStation, transactionId")
    List<StolenViewResponse> findGroupedByRequestId(@Param("transactionId") String transactionId);


    List<SearchImeiDetailByPolice> findByRequestId(String requestId);
}
