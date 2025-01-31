package com.gl.mdr.feature.appanalyticsuploader.service;


import com.gl.mdr.feature.appanalyticsuploader.export.AppAnalyticsUploaderExport;
import com.gl.mdr.feature.appanalyticsuploader.extras.FeaturesEnum;
import com.gl.mdr.model.audit.AppAnalyticsEntity;
import com.gl.mdr.model.file.FileDetails;
import com.gl.mdr.service.impl.AuditTrailService1;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;



@Service
public class AppAnalyticsUploaderExportService {

    private final Logger logger = LogManager.getLogger(this.getClass());
    @PersistenceContext
    private EntityManager em;
    private AppAnalyticsUploaderExport appAnalyticsUploaderExport;

    private AuditTrailService1 auditTrailService1;

    public AppAnalyticsUploaderExportService(AppAnalyticsUploaderExport appAnalyticsUploaderExport, AuditTrailService1 auditTrailService1) {
        this.appAnalyticsUploaderExport = appAnalyticsUploaderExport;
        this.auditTrailService1 = auditTrailService1;
    }

    public MappingJacksonValue export(AppAnalyticsEntity appAnalyticsEntity) {

        String requestType = "APP_ANALYTICS_EXPORT";
        FileDetails export = appAnalyticsUploaderExport.export(appAnalyticsEntity, FeaturesEnum.getFeatureName(requestType).replace(" ", "_"));
        logger.info("requestType [" + requestType + "]");
        auditTrailService1.auditTrailOperation1(appAnalyticsEntity.getAuditTrailModel(), FeaturesEnum.getFeatureName(requestType), FeaturesEnum.getSubFeatureName(requestType));
        return new MappingJacksonValue(export);
    }
}
