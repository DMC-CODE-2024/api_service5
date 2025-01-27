package com.gl.mdr.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.gl.mdr.feature.stolenImeiStatusCheck.model.StolenImeiStatusCheckRequest;
import jakarta.transaction.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gl.mdr.model.RedmineParser.MainResponse;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gl.mdr.configuration.PropertiesReader;
import com.gl.mdr.configuration.SortDirection;
import com.gl.mdr.exceptions.ResourceServicesException;
import com.gl.mdr.model.app.DuplicateDeviceDetail;
import com.gl.mdr.model.app.StatesInterpretationDb;
import com.gl.mdr.model.app.SystemConfigurationDb;
import com.gl.mdr.model.app.User;
import com.gl.mdr.model.audit.AuditTrail;
import com.gl.mdr.model.constants.Datatype;
import com.gl.mdr.model.constants.SearchOperation;
import com.gl.mdr.model.file.DuplicateDeviceFileModel;
import com.gl.mdr.model.file.FileDetails;
import com.gl.mdr.model.filter.DuplicateDeviceFilterRequest;
import com.gl.mdr.model.generic.GenricResponse;
import com.gl.mdr.model.generic.SearchCriteria;
import com.gl.mdr.repo.app.AttachedFileInfoRepository;
import com.gl.mdr.repo.app.DuplicateDeviceRepository;
import com.gl.mdr.repo.app.StatesInterpretaionRepository;
import com.gl.mdr.repo.app.SystemConfigListRepository;
import com.gl.mdr.repo.app.SystemConfigurationDbRepository;
import com.gl.mdr.repo.app.UserProfileRepository;
import com.gl.mdr.repo.app.UserRepository;
import com.gl.mdr.repo.audit.AuditTrailRepository;
import com.gl.mdr.specificationsbuilder.GenericSpecificationBuilder;
import com.gl.mdr.util.CustomMappingStrategy;
import com.gl.mdr.util.Utility;
import com.opencsv.CSVWriter;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;


@Service
public class DuplicateDeviceServiceImpl {
    private static final Logger logger = LogManager.getLogger(DuplicateDeviceServiceImpl.class);

    @Autowired
    PropertiesReader propertiesReader;

    @Autowired
    SystemConfigurationDbRepository systemConfigurationDbRepository;

    @Autowired
    AttachedFileInfoRepository attachedFileInfoRepository;

    @Autowired
    AuditTrailRepository auditTrailRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StatesInterpretaionRepository statesInterpretaionRepository;

    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    SystemConfigListRepository systemConfigListRepository;

    @Autowired
    Utility utility;

    @Autowired
    MainResponse mainResponse;

    @Autowired
    PropertiesReader propertyReader;

    @Autowired
    DuplicateDeviceRepository duplicateDeviceRepo;


    public Page<DuplicateDeviceDetail> getDuplicateDevicesDetails(
            DuplicateDeviceFilterRequest duplicateRequest, Integer pageNo, Integer pageSize, String operation) {
        List<StatesInterpretationDb> states = null;
        String stateInterp = null;
        try {
            logger.info("Duplicate Request" + duplicateRequest);
            String orderColumn;
            if (operation.equals("Export")) {
                orderColumn = "modifiedOn";
            } else {
                logger.info("column Name :: " + duplicateRequest.getOrderColumnName());
                orderColumn = "Detection Date".equalsIgnoreCase(duplicateRequest.getOrderColumnName()) ? "edrTime" :
                        "IMEI".equalsIgnoreCase(duplicateRequest.getOrderColumnName()) ? "imei" :
                                "Phone Number".equalsIgnoreCase(duplicateRequest.getOrderColumnName()) ? "msisdn" :
                                        "Ticket ID".equalsIgnoreCase(duplicateRequest.getOrderColumnName()) ? "redmineTktId" :
                                            "Updated By".equalsIgnoreCase(duplicateRequest.getOrderColumnName()) ? "updatedBy" :
                                                "Status".equalsIgnoreCase(duplicateRequest.getOrderColumnName()) ? "status"
                                                        : "modifiedOn";
            }

            logger.info("orderColumn data:  " + orderColumn);
            
            //List<DuplicateDeviceDetail> DuplicateDeviceDetail=duplicateDeviceRepo.findDistinctStatus();
            //logger.info("status List for Duplicate Device"+DuplicateDeviceDetail);

            //states = statesInterpretaionRepository.findByFeatureId(duplicateRequest.getFeatureId());
            //logger.info("state Interp for feature:  "+states);

            Sort.Direction direction;
            if ("modifiedOn".equalsIgnoreCase(orderColumn)) {
                direction = Sort.Direction.DESC;
            } else {
                direction = SortDirection.getSortDirection(duplicateRequest.getSort());
            }


            if ("modifiedOn".equalsIgnoreCase(orderColumn) && SortDirection.getSortDirection(duplicateRequest.getSort()).equals(Sort.Direction.ASC)) {
                direction = Sort.Direction.ASC;
            }
            Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, orderColumn));
            
            //Audit Table Save Method
            AuditTrailDB(duplicateRequest,"View All");

            GenericSpecificationBuilder<DuplicateDeviceDetail> uPSB = new GenericSpecificationBuilder<DuplicateDeviceDetail>(propertiesReader.dialect);

            if (Objects.nonNull(duplicateRequest.getEdrTime()) && !duplicateRequest.getEdrTime().equals(""))
                uPSB.with(new SearchCriteria("edrTime", duplicateRequest.getEdrTime(), SearchOperation.EQUALITY, Datatype.DATE));

            if (Objects.nonNull(duplicateRequest.getImei()) && !duplicateRequest.getImei().equals(""))
                uPSB.with(new SearchCriteria("imei", duplicateRequest.getImei(), SearchOperation.LIKE, Datatype.STRING));

            if (Objects.nonNull(duplicateRequest.getMsisdn()) && !duplicateRequest.getMsisdn().equals(""))
                uPSB.with(new SearchCriteria("msisdn", duplicateRequest.getMsisdn(), SearchOperation.LIKE, Datatype.STRING));

            if (Objects.nonNull(duplicateRequest.getRedmineTktId()) && !duplicateRequest.getRedmineTktId().equals(""))
                uPSB.with(new SearchCriteria("redmineTktId", duplicateRequest.getRedmineTktId(), SearchOperation.LIKE, Datatype.STRING));

            if (Objects.nonNull(duplicateRequest.getUpdatedBy()) && !duplicateRequest.getUpdatedBy().equals(""))
                uPSB.with(new SearchCriteria("updatedBy", duplicateRequest.getUpdatedBy(), SearchOperation.LIKE, Datatype.STRING));


            if (Objects.nonNull(duplicateRequest.getStatus()) && !duplicateRequest.getStatus().equals("")) 
                uPSB.with(new SearchCriteria("status", duplicateRequest.getStatus(), SearchOperation.LIKE, Datatype.STRING));
            
            
            Page<DuplicateDeviceDetail> pageResult = duplicateDeviceRepo.findAll(uPSB.build(), pageable);
			/*
			 * for (DuplicateDeviceDetail detail : pageResult.getContent()) { String
			 * interpretation = getInterpretationForStatus(detail.getStatus(),
			 * Long.valueOf(duplicateRequest.getFeatureId()));
			 * detail.setInterpretation(interpretation); }
			 */

            return pageResult;


        } catch (Exception e) {
            logger.info("Exception found =" + e.getMessage());
            logger.info(e.getClass().getMethods().toString());
            logger.info(e.toString());
            return null;
        }
    }

    public String getInterpretationForStatus(int status, Long featureId) {
        StatesInterpretationDb interpretation = statesInterpretaionRepository.findByStateAndFeatureId(status, featureId);
        return interpretation != null ? interpretation.getInterpretation() : null;
    }


    public FileDetails exportData(DuplicateDeviceFilterRequest duplicateRequest) {
        logger.info("inside export Duplicate Device Feature service");
        logger.info("Export Request:  " + duplicateRequest);
        String fileName = null;
        Writer writer = null;
        DuplicateDeviceFileModel uPFm = null;
        SystemConfigurationDb dowlonadDir = systemConfigurationDbRepository.getByTag("file.download-dir");
        SystemConfigurationDb dowlonadLink = systemConfigurationDbRepository.getByTag("file.download-link");
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

        Integer pageNo = 0;
        Integer pageSize = Integer.valueOf(systemConfigurationDbRepository.getByTag("file.max-file-record").getValue());
        String filePath = dowlonadDir.getValue();
        StatefulBeanToCsvBuilder<DuplicateDeviceFileModel> builder = null;
        StatefulBeanToCsv<DuplicateDeviceFileModel> csvWriter = null;
        List<DuplicateDeviceFileModel> fileRecords = null;
        MappingStrategy<DuplicateDeviceFileModel> mapStrategy = new CustomMappingStrategy<>();

        //Audit Table Save Method
        AuditTrailDB(duplicateRequest,"Export");

        try {
            mapStrategy.setType(DuplicateDeviceFileModel.class);
            duplicateRequest.setSort("");
            List<DuplicateDeviceDetail> list = getDuplicateDevicesDetails(duplicateRequest, pageNo, pageSize, "Export").getContent();
            if (list.size() > 0) {
                fileName = LocalDateTime.now().format(dtf2).replace(" ", "_") + "_duplicate_device.csv";
            } else {
                fileName = LocalDateTime.now().format(dtf2).replace(" ", "_") + "_duplicate_device.csv";
            }
            logger.info(" file path plus file name: " + Paths.get(filePath + fileName));
            writer = Files.newBufferedWriter(Paths.get(filePath + fileName));
            builder = new StatefulBeanToCsvBuilder<>(writer);

            csvWriter = builder.withMappingStrategy(mapStrategy).withSeparator(',').withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();
            if (list.size() > 0) {
                fileRecords = new ArrayList<DuplicateDeviceFileModel>();
                for (DuplicateDeviceDetail duplicateDeviceDetail : list) {
                	uPFm = new DuplicateDeviceFileModel();
                    uPFm.setEdrTime(duplicateDeviceDetail.getEdrTime());
                    uPFm.setImei(duplicateDeviceDetail.getImei());
                    uPFm.setMsisdn(duplicateDeviceDetail.getMsisdn());
                    uPFm.setRedmineTktId(duplicateDeviceDetail.getRedmineTktId());
                    uPFm.setUpdatedBy(duplicateDeviceDetail.getUpdatedBy());
                    uPFm.setStatus(duplicateDeviceDetail.getStatus());
                    fileRecords.add(uPFm);
                }
                csvWriter.write(fileRecords);
            }
            logger.info("fileName::" + fileName);
            logger.info("filePath::::" + filePath);
            logger.info("link:::" + dowlonadLink.getValue());
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

    public ResponseEntity<?> viewApprovedDevice_backup(DuplicateDeviceFilterRequest duplicateRequest) {
        logger.info("View Approved Devices by id : " + duplicateRequest.getId());
        //Audit Table Save Method
        AuditTrailDB(duplicateRequest,"View");
        Optional<DuplicateDeviceDetail> result = duplicateDeviceRepo.findById(duplicateRequest.getId());
        logger.info("View result : " + result);
        if (result.isPresent()) {
            GenricResponse response = new GenricResponse(200, "", "", result.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            GenricResponse response = new GenricResponse(500, "Something wrong happend", "", result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    public ResponseEntity<?> viewApprovedDevice(DuplicateDeviceFilterRequest duplicateRequest) {
        logger.info("duplicateRequest : " +duplicateRequest);

        //Audit Table Save Method
        AuditTrailDB(duplicateRequest,"View");

        Optional<DuplicateDeviceDetail> result = duplicateDeviceRepo.findById(duplicateRequest.getId());
        logger.info("View result : "  +result);

        List<String> documentName = new ArrayList<>();
        List<String> documentPaths = new ArrayList<>();
        List<String> documentType = new ArrayList<>();

        try {
              String redmineResponse= getRedmineIssueStatus(duplicateRequest.getRedmineTktId());
              logger.info("redmineResponse " +redmineResponse);
              String redmineFileDownloadUrl = propertiesReader.redmineFileDownloadUrl;
              ObjectMapper objectMapper = new ObjectMapper();
              JsonNode rootNode = objectMapper.readTree(redmineResponse);
              JsonNode attachmentsNode = rootNode.path("issue").path("attachments");
                    if (attachmentsNode.isArray()) {
                        for (JsonNode attachmentNode : attachmentsNode) {
                            String filename = attachmentNode.path("filename").asText();
                            String contentType = attachmentNode.path("description").asText();
                            String attachmentId = attachmentNode.path("id").asText();
                            //String contentUrl = attachmentNode.path(redmineFileDownloadUrl +attachmentId).asText();

                            documentName.add(filename);
                            documentType.add(contentType);
                            documentPaths.add(redmineFileDownloadUrl+attachmentId);

                            logger.info("Filename: " + filename);
                            logger.info("Content Type: " + contentType);
                            logger.info("Content URL: " + redmineFileDownloadUrl+attachmentId);


                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }



        if (result.isPresent()) {
        	DuplicateDeviceDetail detail = result.get();
        	
        	//for status interpretation
        	//String interpretation = getInterpretationForStatus(detail.getStatus(), Long.valueOf(duplicateRequest.getFeatureId()));
        	//logger.info("interpretation in view" +interpretation);

            SystemConfigurationDb uploadedFilePath = systemConfigurationDbRepository.getByTag("upload_file_link");
            logger.info("uploadedFilePath :::" + uploadedFilePath.getValue());
            
            

            // Create a map to hold the response data
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("id", detail.getId());
            responseData.put("createdOn", detail.getCreatedOn());
            responseData.put("modifiedOn", detail.getModifiedOn());
            responseData.put("imei", detail.getImei());
            responseData.put("edrTime", detail.getEdrTime());
            responseData.put("msisdn", detail.getMsisdn());
            responseData.put("updatedBy", detail.getUpdatedBy());
            responseData.put("documentName", documentName);
            responseData.put("documentType", documentType);
            responseData.put("documentPaths", documentPaths);
            //responseData.put("interpretation", interpretation);
            responseData.put("status", detail.getStatus());
            responseData.put("uploadedFilePath", uploadedFilePath.getValue().replace("$LOCAL_IP", propertiesReader.localIp));

            GenricResponse response = new GenricResponse(200, "Success", "", responseData);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            GenricResponse response = new GenricResponse(500, "Something wrong happened", "", result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }




    @Transactional(rollbackOn = {SQLException.class})
    public ResponseEntity<?> update(DuplicateDeviceFilterRequest duplicateRequest) {

        if (Objects.nonNull(duplicateRequest.getId())) {
            logger.info("approveDuplicateDevice Request : " + duplicateRequest);

            //Audit Table Save Method
            AuditTrailDB(duplicateRequest,"Approve");

            //Changed status from duplicate to Original
            logger.info("After set status to Original[3] Status is  : " + duplicateRequest.getStatus());
            logger.info("Approve Tranaction ID  : " + duplicateRequest.getApproveTransactionId());
            logger.info("ApproveRemark  : " + duplicateRequest.getApproveRemark());
            int result = duplicateDeviceRepo.update(duplicateRequest);
            if (result == 1) {
                GenricResponse response = new GenricResponse(HttpStatus.OK.value(), "Deivce Approved Successfully", "", result);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

        }
        GenricResponse response = new GenricResponse(HttpStatus.EXPECTATION_FAILED.value(), "Something wrong happend", "", "");
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    public String getRedmineIssueStatus(String redmineTktId) {
        StringBuilder response = new StringBuilder();
        try {
            logger.info("redmineTktId Received " +redmineTktId);
            logger.info("redmine URL " +propertyReader.redmineUrl);
            String url = propertyReader.redmineUrl + redmineTktId;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("X-Client-Type", "END_USER");

            int responseCode = con.getResponseCode();
            logger.info("Response Code : " , responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            Gson gson = new Gson();
            mainResponse = gson.fromJson(response.toString(), MainResponse.class);
            logger.info("Main Response" ,mainResponse.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response.toString();
    }

    public void AuditTrailDB(DuplicateDeviceFilterRequest duplicateRequest, String subFeature){
        AuditTrail auditTrail = new AuditTrail();
        auditTrail.setFeatureName("Duplicate Device");
        auditTrail.setSubFeature(subFeature);
        auditTrail.setPublicIp(duplicateRequest.getPublicIp());
        auditTrail.setBrowser(duplicateRequest.getBrowser());
        if (Objects.nonNull(duplicateRequest.getUserId())) {
            User user = userRepository.getByid(duplicateRequest.getUserId());
            auditTrail.setUserId(duplicateRequest.getUserId());
            auditTrail.setUserName(user.getUsername());
        } else {
            auditTrail.setUserName("NA");
        }
        if (Objects.nonNull(duplicateRequest.getUserType())) {
            auditTrail.setUserType(duplicateRequest.getUserType());
            auditTrail.setRoleType(duplicateRequest.getUserType());
        } else {
            auditTrail.setUserType("NA");
            auditTrail.setRoleType("NA");
        }
        auditTrail.setTxnId("NA");
        auditTrailRepository.save(auditTrail);
        logger.info("Saving Audit Trail Data for Duplicate [{}]", auditTrail);
    }
}
