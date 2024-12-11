package com.gl.mdr.service.impl;

import com.gl.mdr.bulk.imei.entity.AuditTrailModel;
import com.gl.mdr.model.audit.AuditTrail;
import com.gl.mdr.repo.audit.AuditTrailRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

@Service
public class AuditTrailService {
    private static final Logger logger = LogManager.getLogger(AuditTrailService.class);
    @Autowired
    private AuditTrailRepository auditTrailRepository;

    @Transactional(rollbackOn = {SQLException.class})
    public <T extends AuditTrailModel> void auditTrailOperation(T t, String feature, String subFeature) {
        Optional<T> optional = Optional.ofNullable(t);
        if (optional.isPresent()) {
            T param = optional.get();
            Integer userId = Objects.isNull(param.getUserId()) ? 0 : param.getUserId();
            String userName = Objects.isNull(param.getUserName()) ? "NA" : param.getUserName();
            Integer userTypeId = Objects.isNull(param.getUserTypeId()) ? 0 : param.getUserTypeId();
            String userType = Objects.isNull(param.getUserType()) ? "NA" : param.getUserType();
            Integer featureId = Objects.isNull(param.getFeatureId()) ? 0 : param.getFeatureId();
            String roleType = Objects.isNull(param.getRoleType()) ? "NA" : param.getRoleType();
            String publicIp = Objects.isNull(param.getPublicIp()) ? "NA" : param.getPublicIp();
            String browser = Objects.isNull(param.getBrowser()) ? "NA" : param.getBrowser();
            AuditTrail auditTrailPayload = new AuditTrail(userId, userName, userTypeId, userType, featureId, feature, subFeature, "", "NA", roleType, publicIp, browser);
            logger.info("auditTrail saved for Feature in [{}] and Sub Feature [{}] with payload {}", feature, subFeature, auditTrailPayload);
            auditTrailRepository.save(auditTrailPayload);
        }
    }
}

