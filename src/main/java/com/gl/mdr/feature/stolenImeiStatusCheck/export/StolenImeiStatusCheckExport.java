package com.gl.mdr.feature.stolenImeiStatusCheck.export;

import com.gl.mdr.configuration.PropertiesReader;
import com.gl.mdr.exceptions.ResourceServicesException;
import com.gl.mdr.feature.stolenImeiStatusCheck.csv_FileModel.StolenImeiStatusCheckRequestFileModel;
import com.gl.mdr.feature.stolenImeiStatusCheck.model.StolenImeiStatusCheckRequest;
import com.gl.mdr.feature.stolenImeiStatusCheck.service.StolenImeiStatusCheckService;
import com.gl.mdr.model.app.SearchImeiByPoliceMgmt;
import com.gl.mdr.model.app.SystemConfigurationDb;
import com.gl.mdr.model.app.User;
import com.gl.mdr.model.audit.AuditTrail;
import com.gl.mdr.model.file.FileDetails;
import com.gl.mdr.repo.app.SystemConfigurationDbRepository;
import com.gl.mdr.repo.app.UserRepository;
import com.gl.mdr.repo.audit.AuditTrailRepository;
import com.gl.mdr.util.CustomMappingStrategy;
import com.opencsv.CSVWriter;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class StolenImeiStatusCheckExport {
    private static final Logger logger = LogManager.getLogger(StolenImeiStatusCheckService.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuditTrailRepository auditTrailRepository;

    @Autowired
    PropertiesReader propertiesReader;

    @Autowired
    SystemConfigurationDbRepository systemConfigurationDbRepository;

    private StolenImeiStatusCheckService stolenImeiStatusCheckService;
    public  StolenImeiStatusCheckExport (StolenImeiStatusCheckService stolenImeiStatusCheckService) {
        this.stolenImeiStatusCheckService = stolenImeiStatusCheckService;
    }

    public FileDetails exportData(StolenImeiStatusCheckRequest stolenRequest) {
        logger.info("inside export Stolen Device Feature service");
        logger.info("Export Request:  {}", stolenRequest);
        String fileName = null;
        Writer writer = null;
        StolenImeiStatusCheckRequestFileModel uPFm = null;
        SystemConfigurationDb dowlonadDir = systemConfigurationDbRepository.getByTag("file.download-dir");
        SystemConfigurationDb dowlonadLink = systemConfigurationDbRepository.getByTag("file.download-link");
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

        Integer pageNo = 0;
        Integer pageSize = Integer.valueOf(systemConfigurationDbRepository.getByTag("file.max-file-record").getValue());
        String filePath = dowlonadDir.getValue();
        StatefulBeanToCsvBuilder<StolenImeiStatusCheckRequestFileModel> builder = null;
        StatefulBeanToCsv<StolenImeiStatusCheckRequestFileModel> csvWriter = null;
        List<StolenImeiStatusCheckRequestFileModel> fileRecords = null;
        MappingStrategy<StolenImeiStatusCheckRequestFileModel> mapStrategy = new CustomMappingStrategy<>();

        AuditTrail auditTrail = new AuditTrail();
        auditTrail.setFeatureName("Stolen IMEI status check");
        auditTrail.setSubFeature("Export");
        auditTrail.setPublicIp(stolenRequest.getPublicIp());
        auditTrail.setBrowser(stolenRequest.getBrowser());
        if (Objects.nonNull(stolenRequest.getUserId())) {
            User user = userRepository.getByid(stolenRequest.getUserId());
            auditTrail.setUserId(stolenRequest.getUserId());
            auditTrail.setUserName(user.getUsername());
        } else {
            auditTrail.setUserName("NA");
        }
        if (Objects.nonNull(stolenRequest.getUserType())) {
            auditTrail.setUserType(stolenRequest.getUserType());
            auditTrail.setRoleType(stolenRequest.getUserType());
        } else {
            auditTrail.setUserType("NA");
            auditTrail.setRoleType("NA");
        }
        auditTrail.setTxnId("NA");
        auditTrailRepository.save(auditTrail);

        try {
            stolenRequest.setSort("");
            mapStrategy.setType(StolenImeiStatusCheckRequestFileModel.class);
            List<SearchImeiByPoliceMgmt> list = stolenImeiStatusCheckService.getStolenDevicesDetails(stolenRequest, pageNo, pageSize, "Export").getContent();
            if (list.size() > 0) {
                fileName = LocalDateTime.now().format(dtf2).replace(" ", "_") + "_stolen_device.csv";
            } else {
                fileName = LocalDateTime.now().format(dtf2).replace(" ", "_") + "_stolen_device.csv";
            }
            logger.info(" file path plus file name: {}", Paths.get(filePath + fileName));
            writer = Files.newBufferedWriter(Paths.get(filePath + fileName));
            builder = new StatefulBeanToCsvBuilder<>(writer);

            csvWriter = builder.withMappingStrategy(mapStrategy).withSeparator(',').withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();
            if (list.size() > 0) {
                fileRecords = new ArrayList<StolenImeiStatusCheckRequestFileModel>();
                for (SearchImeiByPoliceMgmt searchImeiByPoliceMgmt : list) {
                    uPFm = new StolenImeiStatusCheckRequestFileModel();
                    uPFm.setCreatedOn(searchImeiByPoliceMgmt.getCreatedOn().format(dtf2));
                    uPFm.setModifiedOn(searchImeiByPoliceMgmt.getModifiedOn().format(dtf2));
                    uPFm.setTransactionId(searchImeiByPoliceMgmt.getTransactionId());
                    uPFm.setRequestMode(searchImeiByPoliceMgmt.getRequestMode());
                    uPFm.setImei1(searchImeiByPoliceMgmt.getImei1());
                    uPFm.setFileName(searchImeiByPoliceMgmt.getFileName());
                    uPFm.setFileRecordCount(searchImeiByPoliceMgmt.getFileRecordCount());
                    uPFm.setCountFoundInLost(searchImeiByPoliceMgmt.getCountFoundInLost());
                    uPFm.setStatus(searchImeiByPoliceMgmt.getStatus());
                    uPFm.setRemark(searchImeiByPoliceMgmt.getRemark());
                    fileRecords.add(uPFm);
                }
                csvWriter.write(fileRecords);
            }
            logger.info("fileName::{}", fileName);
            logger.info("filePath::::{}", filePath);
            logger.info("link:::{}", dowlonadLink.getValue());
            return new FileDetails(fileName, filePath, dowlonadLink.getValue().replace("$LOCAL_IP", propertiesReader.localIp) + fileName);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {

            }
        }

    }

}
