package com.gl.mdr.feature.appanalyticsuploader.service;


import com.gl.mdr.feature.appanalyticsuploader.extras.FeaturesEnum;
import com.gl.mdr.feature.appanalyticsuploader.paging.AppAnalyticsUploaderPaging;
import com.gl.mdr.model.app.WebActionDb;
import com.gl.mdr.model.audit.AppAnalyticsEntity;
import com.gl.mdr.repo.app.WebActionDbRepository;
import com.gl.mdr.repo.audit.AppAnalyticsUploaderRepository;
import com.gl.mdr.service.impl.AuditTrailService1;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppAnalyticsUploaderPagingService {
    private final Logger logger = LogManager.getLogger(this.getClass());
    @PersistenceContext
    private EntityManager em;
    private AppAnalyticsUploaderPaging appAnalyticsUploaderPaging;

    private AuditTrailService1 auditTrailService1;
    private AppAnalyticsUploaderRepository appAnalyticsUploaderRepository;
    private WebActionDbRepository webActionDBRepository;

    public AppAnalyticsUploaderPagingService(AppAnalyticsUploaderPaging appAnalyticsUploaderPaging,WebActionDbRepository webActionDBRepository, AuditTrailService1 auditTrailService1, AppAnalyticsUploaderRepository appAnalyticsUploaderRepository) {
        this.appAnalyticsUploaderPaging = appAnalyticsUploaderPaging;
        this.auditTrailService1 = auditTrailService1;
        this.appAnalyticsUploaderRepository = appAnalyticsUploaderRepository;
        this.webActionDBRepository = webActionDBRepository;
    }

    public MappingJacksonValue paging(AppAnalyticsEntity appAnalyticsEntity, int pageNo, int pageSize) {
        Page<AppAnalyticsEntity> page = appAnalyticsUploaderPaging.findAll(appAnalyticsEntity, pageNo, pageSize);
        String requestType = "APP_ANALYTICS_VIEWALL";
        logger.info("requestType [" + requestType + "]");
        auditTrailService1.auditTrailOperation1(appAnalyticsEntity.getAuditTrailModel(), FeaturesEnum.getFeatureName(requestType), FeaturesEnum.getSubFeatureName(requestType));
        return new MappingJacksonValue(page);
    }


    public MappingJacksonValue find(AppAnalyticsEntity appAnalyticsEntity) {
        Optional<AppAnalyticsEntity> optional = appAnalyticsUploaderRepository.findById(appAnalyticsEntity.getId());
        if (optional.isPresent()) {
            return new MappingJacksonValue(optional.get());
        }
        return new MappingJacksonValue(null);
    }

    public void saveWebActionDBEntity(AppAnalyticsEntity appAnalyticsEntity) {
        Optional<String> optional = Optional.ofNullable(appAnalyticsEntity.getTransactionId());
        if (optional.isPresent()) {
            String txnID = optional.get();
            logger.info("txnID [" + txnID + "]");
            /*String requestType = appAnalyticsEntity.getRequestType();
            logger.info("requestType [" + requestType + "]");*/
            String feature = "appLoader";
            String subFeature = appAnalyticsEntity.getReportType().replaceAll("[\\s-]+", "_");
            WebActionDb webActionDBEntity = new WebActionDb();
            webActionDBEntity.setFeature(feature);
            webActionDBEntity.setSub_feature(subFeature);
            webActionDBEntity.setState(1);
            webActionDBEntity.setTxnId(txnID);
            logger.info("WebActionDBEntity payload[" + webActionDBEntity + "]");
            webActionDBRepository.save(webActionDBEntity);
        }
    }
}
