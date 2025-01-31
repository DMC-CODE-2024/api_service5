package com.gl.mdr.service.impl;


import com.gl.mdr.model.audit.AuditTrail;
import com.gl.mdr.repo.audit.AuditTrailRepository;
import com.gl.mdr.util.AuditTrailModel;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

@Service
public class AuditTrailService1 {
    private static final Logger logger = LogManager.getLogger(AuditTrailService1.class);
    @Autowired
    private AuditTrailRepository auditTrailRepository;

    @Transactional(rollbackOn = {SQLException.class})
    public <T extends AuditTrailModel> void auditTrailOperation1(T t, String feature, String subFeature) {
        Optional<T> optional = Optional.ofNullable(t);
        if (optional.isPresent()) {
            T param = optional.get();
            Long userId = Objects.isNull(param.getUserId()) ? 0L : param.getUserId();
            String userName = Objects.isNull(param.getUserName()) ? "NA" : param.getUserName();
            Long userTypeId = Objects.isNull(param.getUserTypeId()) ? 0L : param.getUserTypeId();
            String userType = Objects.isNull(param.getUserType()) ? "NA" : param.getUserType();
            Long featureId = Objects.isNull(param.getFeatureId()) ? 0L : param.getFeatureId();
            String roleType = Objects.isNull(param.getRoleType()) ? "NA" : param.getRoleType();
            String publicIp = Objects.isNull(param.getPublicIp()) ? "NA" : param.getPublicIp();
            String browser = Objects.isNull(param.getBrowser()) ? "NA" : param.getBrowser();
            AuditTrail auditTrailPayload = new AuditTrail(Math.toIntExact(userId), userName, Math.toIntExact(userTypeId), userType, Math.toIntExact(featureId), feature, subFeature, "", "NA", roleType, publicIp, browser);
            logger.info("auditTrail saved for Feature in [{}] and Sub Feature [{}] with payload {}", feature, subFeature, auditTrailPayload);
            auditTrailRepository.save(auditTrailPayload);
        }
    }
}

