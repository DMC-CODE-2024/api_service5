package com.gl.mdr.feature.appanalyticsuploader.paging;


import com.gl.mdr.configuration.PropertiesReader;
import com.gl.mdr.configuration.SortDirection;
import com.gl.mdr.exceptions.ResourceServicesException;
import com.gl.mdr.model.audit.AppAnalyticsEntity;
import com.gl.mdr.model.constants.Datatype;
import com.gl.mdr.model.constants.SearchOperation;
import com.gl.mdr.model.generic.SearchCriteria;
import com.gl.mdr.repo.audit.AppAnalyticsUploaderRepository;
import com.gl.mdr.specificationsbuilder.GenericSpecificationBuilder;
import com.gl.mdr.util.AuditTrailModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AppAnalyticsUploaderPaging {
    private final Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private PropertiesReader propertiesReader;
    private AppAnalyticsUploaderRepository appAnalyticsUploaderRepository;
    /*private HelperUtility helperUtility;*/

    public AppAnalyticsUploaderPaging(AppAnalyticsUploaderRepository appAnalyticsUploaderRepository) {
        this.appAnalyticsUploaderRepository = appAnalyticsUploaderRepository;
        /*this.helperUtility = helperUtility;*/
    }


    public Page<AppAnalyticsEntity> findAll(AppAnalyticsEntity appAnalyticsEntity, int pageNo, int pageSize) {

        try {
            logger.info("request received : " + appAnalyticsEntity + " [pageNo] ...." + pageNo + " [pageSize]...." + pageSize);
            String sort = null, orderColumn = null;
            if (Objects.nonNull(appAnalyticsEntity.getAuditTrailModel())) {
                sort = Objects.nonNull(appAnalyticsEntity.getAuditTrailModel().getSort()) ? appAnalyticsEntity.getAuditTrailModel().getSort() : "desc";
                orderColumn = Objects.nonNull(appAnalyticsEntity.getAuditTrailModel().getColumnName()) ? appAnalyticsEntity.getAuditTrailModel().getColumnName() : "Modified On";
            } else {
                sort = "desc";
                orderColumn = "Modified On";
            }
            orderColumn = validateColumnName(orderColumn);
            /*orderColumn = sortColumnName(orderColumn);*/
            Sort.Direction direction = SortDirection.getSortDirection(sort);
            logger.info("request received : " + appAnalyticsEntity + " [pageNo] ...." + pageNo + " [pageSize]...." + pageSize);

            logger.info("orderColumn is : " + orderColumn + " & direction is : " + direction);

            Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, orderColumn));
            Page<AppAnalyticsEntity> page = appAnalyticsUploaderRepository.findAll(buildSpecification(appAnalyticsEntity).build(), pageable);
            logger.info("paging API response [" + page + "]");
            return page;

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
        }
    }

    private String validateColumnName(String columnName) {
        List<String> validColumns = Arrays.asList("uploadedOn", "osType", "reportType", "status", "transactionId", "insertCount", "sourceFileName", "reason", "uploadedBy");

        // Default column if the provided column is invalid
        String defaultColumn = "uploadedOn";

        if (validColumns.contains(columnName)) {
            return columnName;
        } else {
            logger.warn("Invalid column name provided: " + columnName + ". Using default column: " + defaultColumn);
            return defaultColumn;
        }
    }

    public String sortColumnName(String columnName) {
        Map<String, String> map = new HashMap<>();
        if (Objects.nonNull(columnName) && !columnName.isEmpty()) {
            map.put("Uploaded On", "uploadedOn");
            map.put("OS Type", "osType");
            map.put("Report Type", "reportType");
            map.put("Status", "status");
            map.put("Transaction ID", "transactionId");
            map.put("Insert Count", "insertCount");
            map.put("Source File Name", "sourceFileName");
            map.put("Reason", "reason");
            map.put("Uploaded By", "uploadedBy");
        }
        return map.get(columnName);
    }

    private GenericSpecificationBuilder<AppAnalyticsEntity> buildSpecification(AppAnalyticsEntity appAnalyticsEntity) {
        logger.info("FilterRequest payload : [" + appAnalyticsEntity + "]");
        GenericSpecificationBuilder<AppAnalyticsEntity> cmsb = new GenericSpecificationBuilder<>(propertiesReader.dialect);

        Optional<AuditTrailModel> optional = Optional.ofNullable(appAnalyticsEntity.getAuditTrailModel());
     /*   if (optional.isPresent()) {
            cmsb.with(new SearchCriteria("createdOn", optional.get().getStartDate(), SearchOperation.GREATER_THAN, Datatype.DATE));
            cmsb.with(new SearchCriteria("createdOn", optional.get().getEndDate(), SearchOperation.LESS_THAN, Datatype.DATE));
        }*/
        /*if (optional.isPresent()) {
            cmsb.with(new SearchCriteria("uploadedOn", optional.get().getStartDate(), SearchOperation.GREATER_THAN, Datatype.DATE));
            cmsb.with(new SearchCriteria("uploadedOn", optional.get().getEndDate(), SearchOperation.LESS_THAN, Datatype.DATE));
        }
        cmsb.with(new SearchCriteria("osType", appAnalyticsEntity.getOsType(), SearchOperation.LIKE, Datatype.STRING));
        cmsb.with(new SearchCriteria("reportType", appAnalyticsEntity.getReportType(), SearchOperation.LIKE, Datatype.STRING));
        cmsb.with(new SearchCriteria("status", appAnalyticsEntity.getStatus(), SearchOperation.LIKE, Datatype.STRING));
        cmsb.with(new SearchCriteria("transactionId", appAnalyticsEntity.getTransactionId(), SearchOperation.LIKE, Datatype.STRING));
*/
        if (optional.isPresent()) {
            if (Objects.nonNull(appAnalyticsEntity.getUploadedOn()) && !appAnalyticsEntity.getUploadedOn().equals(""))
                cmsb.with(new SearchCriteria("uploadedOn", appAnalyticsEntity.getUploadedOn(), SearchOperation.GREATER_THAN, Datatype.DATE));

            if (Objects.nonNull(appAnalyticsEntity.getUploadedOn()) && !appAnalyticsEntity.getUploadedOn().equals(""))
                cmsb.with(new SearchCriteria("uploadedOn", appAnalyticsEntity.getUploadedOn(), SearchOperation.LESS_THAN, Datatype.DATE));
        }
        if (Objects.nonNull(appAnalyticsEntity.getOsType()) && !appAnalyticsEntity.getOsType().equals(""))
            cmsb.with(new SearchCriteria("osType", appAnalyticsEntity.getOsType(), SearchOperation.LIKE, Datatype.STRING));

        if (Objects.nonNull(appAnalyticsEntity.getTransactionId()) && !appAnalyticsEntity.getTransactionId().equals(""))
            cmsb.with(new SearchCriteria("transactionId", appAnalyticsEntity.getTransactionId(), SearchOperation.LIKE, Datatype.STRING));

        if (Objects.nonNull(appAnalyticsEntity.getStatus()) && !appAnalyticsEntity.getStatus().equals(""))
            cmsb.with(new SearchCriteria("status", appAnalyticsEntity.getStatus(), SearchOperation.LIKE, Datatype.STRING));

        if (Objects.nonNull(appAnalyticsEntity.getReportType()) && !appAnalyticsEntity.getReportType().equals(""))
            cmsb.with(new SearchCriteria("reportType", appAnalyticsEntity.getReportType(), SearchOperation.LIKE, Datatype.STRING));
        return cmsb;
    }

/*    public Optional<EIRSListManagementEntity> find(Long id) {
        return eirsListManagementRepository.findById(id);
    }*/
}
