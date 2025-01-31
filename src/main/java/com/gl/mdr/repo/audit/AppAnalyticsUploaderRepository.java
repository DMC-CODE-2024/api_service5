package com.gl.mdr.repo.audit;


import com.gl.mdr.model.audit.AppAnalyticsEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
@Transactional(rollbackOn = {SQLException.class})
public interface AppAnalyticsUploaderRepository extends JpaRepository<AppAnalyticsEntity, Long>, JpaSpecificationExecutor<AppAnalyticsEntity> {

/*    AppAnalyticsEntity findByUploadedOnAndUploadedByAndOsTypeAndReportTypeAndInsertCountAndSourceFileNameAndStatus(String uploadedBy, Timestamp uploadedOn, String osType, String reportType, String insertCount, String sourceFileName, String status);
    @Modifying
    @Query("UPDATE AppAnalyticsEntity SET uploadedBy =:#{#e.uploadedBy}, uploadedOn =:#{#e.uploadedOn}, osType =:#{#e.osType}, reportType =:#{#e.reportType}, insertCount =:#{#e.insertCount} , status =:#{#e.status}, sourceFileName =:#{#e.sourceFileName} WHERE id =:#{#e.id}")

    int updateColumns(@Param("e") AppAnalyticsEntity appAnalyticsEntity);*/
}
