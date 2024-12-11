package com.gl.mdr.feature.stolenImeiStatusCheck.service;

import com.gl.mdr.bulk.imei.feign.NotificationFeign;
import com.gl.mdr.configuration.PropertiesReader;
import com.gl.mdr.configuration.SortDirection;
import com.gl.mdr.exceptions.ResourceServicesException;
import com.gl.mdr.feature.stolenImeiStatusCheck.model.ResponseModel;
import com.gl.mdr.feature.stolenImeiStatusCheck.model.StolenImeiStatusCheckRequest;
import com.gl.mdr.feature.stolenImeiStatusCheck.model.StolenMatchedImeiResponse;
import com.gl.mdr.feature.stolenImeiStatusCheck.model.StolenViewResponse;
import com.gl.mdr.feature.stolenImeiStatusCheck.upload.EIRSStolenCheckIMEIFileUploadPayload;
import com.gl.mdr.model.app.*;
import com.gl.mdr.model.audit.AuditTrail;
import com.gl.mdr.model.constants.Datatype;
import com.gl.mdr.model.constants.SearchOperation;
import com.gl.mdr.model.constants.Tags;
import com.gl.mdr.model.generic.GenricResponse;
import com.gl.mdr.model.generic.SearchCriteria;
import com.gl.mdr.repo.app.*;
import com.gl.mdr.repo.audit.AuditTrailRepository;
import com.gl.mdr.service.impl.AuditTrailService;
import com.gl.mdr.specificationsbuilder.GenericSpecificationBuilder;
import com.gl.mdr.util.Utility;
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

import jakarta.transaction.Transactional;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StolenImeiStatusCheckService {
    private static final Logger logger = LogManager.getLogger(StolenImeiStatusCheckService.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuditTrailRepository auditTrailRepository;

    @Autowired
    PropertiesReader propertiesReader;

    @Autowired
    SearchImeiByPoliceMgmtRepository searchImeiByPoliceMgmtRepo;

    @Autowired
    SearchImeiDetailByPoliceRepository searchImeiDetailByPoliceRepo;

    @Autowired
    StolenPoliceVerificationDevicesRepository lostDeviceManagementRepo;

    @Autowired
    Utility utility;

    @Autowired
    NotificationFeign notificationFeign;

    @Autowired
    EirsResponseParamRepository eirsResponseParamRepository;

    @Autowired
    WebActionDbRepository webActionDbRepository;

    @Autowired
    MobileDeviceRepoRepository mobileDeviceRepoRepository;

    @Autowired
    CommuneRepository communeRepository;

    @Autowired
    DistrictRepository districtRepository;

    private EIRSStolenCheckIMEIFileUploadPayload eirsStolenCheckIMEIFileUploadPayload;
    private AuditTrailService auditTrailService;
    private StolenPoliceVerificationDevicesRepository lostDeviceManagementRepository;

    public StolenImeiStatusCheckService(EIRSStolenCheckIMEIFileUploadPayload eirsStolenCheckIMEIFileUploadPayload, AuditTrailService auditTrailService, StolenPoliceVerificationDevicesRepository lostDeviceManagementRepository) {
        this.eirsStolenCheckIMEIFileUploadPayload = eirsStolenCheckIMEIFileUploadPayload;
        this.auditTrailService = auditTrailService;
        this.lostDeviceManagementRepository = lostDeviceManagementRepository;
    }

    public Page<SearchImeiByPoliceMgmt> getStolenDevicesDetails(
            StolenImeiStatusCheckRequest stolenRequest, Integer pageNo, Integer pageSize, String operation) {
        List<StatesInterpretationDb> states = null;

        try {
            logger.info("Stolen service Request{}", stolenRequest);
            String orderColumn;
            if (operation.equals("Export")) {
                orderColumn = "modifiedOn";

            } else {
                logger.info("column Name :: {}", stolenRequest.getOrderColumnName());
                orderColumn = "Creation Date".equalsIgnoreCase(stolenRequest.getOrderColumnName()) ? "createdOn" :
                        "Updated Date".equalsIgnoreCase(stolenRequest.getOrderColumnName()) ? "modifiedOn" :
                                "Transaction ID".equalsIgnoreCase(stolenRequest.getOrderColumnName()) ? "transactionId" :
                                        "Request Mode".equalsIgnoreCase(stolenRequest.getOrderColumnName()) ? "requestMode" :
                                                "IMEI".equalsIgnoreCase(stolenRequest.getOrderColumnName()) ? "imei1" :
                                                        "File Name".equalsIgnoreCase(stolenRequest.getOrderColumnName()) ? "fileName" :
                                                                "Record Count".equalsIgnoreCase(stolenRequest.getOrderColumnName()) ? "fileRecordCount" :
                                                                        "IMEI Found Count".equalsIgnoreCase(stolenRequest.getOrderColumnName()) ? "countFoundInLost" :
                                                                                "Status".equalsIgnoreCase(stolenRequest.getOrderColumnName()) ? "status" :
                                                                                        "Remark".equalsIgnoreCase(stolenRequest.getOrderColumnName()) ? "remark"
                                                                                                : "modifiedOn";
            }

            logger.info("orderColumn data:  {}", orderColumn);

            Sort.Direction direction;
            if ("modifiedOn".equalsIgnoreCase(orderColumn)) {
                direction = Sort.Direction.DESC;
            } else {
                direction = SortDirection.getSortDirection(stolenRequest.getSort());
            }


            if ("modifiedOn".equalsIgnoreCase(orderColumn) && SortDirection.getSortDirection(stolenRequest.getSort()).equals(Sort.Direction.ASC)) {
                direction = Sort.Direction.ASC;
            }
            Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, orderColumn));

            AuditTrail auditTrail = new AuditTrail();
            auditTrail.setFeatureName("Stolen IMEI status check");
            auditTrail.setSubFeature("View All");
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


            GenericSpecificationBuilder<SearchImeiByPoliceMgmt> uPSB = new GenericSpecificationBuilder<SearchImeiByPoliceMgmt>(propertiesReader.dialect);

            if (Objects.nonNull(stolenRequest.getCreatedOn()) && !stolenRequest.getCreatedOn().equals(""))
                uPSB.with(new SearchCriteria("createdOn", stolenRequest.getCreatedOn(), SearchOperation.EQUALITY, Datatype.DATE));

            if (Objects.nonNull(stolenRequest.getModifiedOn()) && !stolenRequest.getModifiedOn().equals(""))
                uPSB.with(new SearchCriteria("modifiedOn", stolenRequest.getModifiedOn(), SearchOperation.EQUALITY, Datatype.DATE));

            if (Objects.nonNull(stolenRequest.getImei1()) && !stolenRequest.getImei1().equals(""))
                uPSB.with(new SearchCriteria("imei1", stolenRequest.getImei1(), SearchOperation.LIKE, Datatype.STRING));

            if (Objects.nonNull(stolenRequest.getTransactionId()) && !stolenRequest.getTransactionId().equals(""))
                uPSB.with(new SearchCriteria("transactionId", stolenRequest.getTransactionId(), SearchOperation.LIKE, Datatype.STRING));

            if (Objects.nonNull(stolenRequest.getRequestMode()) && !stolenRequest.getRequestMode().equals(""))
                uPSB.with(new SearchCriteria("requestMode", stolenRequest.getRequestMode(), SearchOperation.LIKE, Datatype.STRING));

            if (Objects.nonNull(stolenRequest.getFileName()) && !stolenRequest.getFileName().equals(""))
                uPSB.with(new SearchCriteria("fileName", stolenRequest.getFileName(), SearchOperation.LIKE, Datatype.STRING));

            if (Objects.nonNull(stolenRequest.getStatus()) && !stolenRequest.getStatus().equals(""))
                uPSB.with(new SearchCriteria("status", stolenRequest.getStatus(), SearchOperation.LIKE, Datatype.STRING));

            if (Objects.nonNull(stolenRequest.getRemark()) && !stolenRequest.getRemark().equals(""))
                uPSB.with(new SearchCriteria("remark", stolenRequest.getRemark(), SearchOperation.LIKE, Datatype.STRING));

            if (Objects.nonNull(stolenRequest.getFileRecordCount()) && !stolenRequest.getFileRecordCount().equals(""))
                uPSB.with(new SearchCriteria("fileRecordCount", stolenRequest.getFileRecordCount(), SearchOperation.EQUALITY, Datatype.INT));

            if (Objects.nonNull(stolenRequest.getCountFoundInLost()) && !stolenRequest.getCountFoundInLost().equals(""))
                uPSB.with(new SearchCriteria("countFoundInLost", stolenRequest.getCountFoundInLost(), SearchOperation.EQUALITY, Datatype.INT));

            return searchImeiByPoliceMgmtRepo.findAll(uPSB.build(), pageable);

        } catch (Exception e) {
            logger.info("Exception found ={}", e.getMessage());
            logger.info(e.getClass().getMethods().toString());
            logger.info(e.toString());
            return null;
        }
    }

    public ResponseEntity<?> viewStolenImeiDevice(StolenImeiStatusCheckRequest stolenRequest) {
        logger.info("StolenRequest View Transaction ID : {}", stolenRequest.getTransactionId());
        List<StolenViewResponse> results = searchImeiDetailByPoliceRepo.findGroupedByRequestId(stolenRequest.getTransactionId());
        logger.info("View result : {}", results);
        GenricResponse response;
        if (!results.isEmpty()) {
            response = new GenricResponse(200, "", stolenRequest.getTransactionId(), results);
        } else {
            response = new GenricResponse(404, "No records found", stolenRequest.getTransactionId(), results);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    public List<String> getDistinctStatusService() {
        List<String> status = searchImeiByPoliceMgmtRepo.findDistinctByStatus();
        return status;
    }

    public ResponseEntity<?> getRecoveredImeiDataService(StolenImeiStatusCheckRequest stolenRequest) {
        logger.info("Recover View Request ID : {}", stolenRequest.getRequestId());
        StolenLostModel stolenLostModelResponse = null;
        List<StolenMatchedImeiResponse> tableResponse = new ArrayList<>();
        Map<String, String> map = new LinkedHashMap<>();

        List<SearchImeiDetailByPolice> results = searchImeiDetailByPoliceRepo.findByRequestId(stolenRequest.getRequestId());
        stolenLostModelResponse = lostDeviceManagementRepository.findByRequestId(stolenRequest.getRequestId());

        tableResponse = lostDeviceManagementRepository.getMatchedImei(stolenRequest.getRequestId());
        logger.info("Matched IMEI's on Request ID : {}", tableResponse);

        for (StolenMatchedImeiResponse value : tableResponse) {
            if (value.getLostDeviceImei().equals(value.getMatchedImei())) {
                map.put(value.getLostDeviceImei(), value.getLostDeviceImei());
            } else {
                map.put(value.getLostDeviceImei(), null);
            }
        }

        logger.info("map {}", map);

        // Count matched IMEIs in the map
        long matchedIMEICount = map.values().stream().filter(Objects::nonNull).count();
        logger.info("Count of matched IMEIs: {}", matchedIMEICount);


        if (stolenLostModelResponse != null) {
            if (!results.isEmpty()) {
                for (SearchImeiDetailByPolice searchImeiDetailByPolice : results) {
                    if(stolenLostModelResponse.getCommune()!=null) {
                        CommuneDb commune=communeRepository.findById(Long.parseLong(stolenLostModelResponse.getCommune()));
                        searchImeiDetailByPolice.setDeviceLostCommune(commune.getCommune());
                    }


                    if(stolenLostModelResponse.getDistrict()!=null) {
                        DistrictDb dis=districtRepository.findById(Long.parseLong(stolenLostModelResponse.getDistrict()));
                        if(dis!=null) {
                            searchImeiDetailByPolice.setDeviceLostPoliceStation(dis.getDistrict());
                        }
                    }

                    searchImeiDetailByPolice.setFileRecordCount(stolenLostModelResponse.getFileRecordCount());
                    searchImeiDetailByPolice.setMap(map);
                    searchImeiDetailByPolice.setMatchedIMEICount(matchedIMEICount);

                }
            }
        }
        logger.info("Recover View result : {}", results);
        GenricResponse response;
        if (!results.isEmpty()) {
            response = new GenricResponse(200, "", stolenRequest.getTransactionId(), results);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response = new GenricResponse(404, "No records found", stolenRequest.getTransactionId(), results);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    public boolean isBrandAndModelValid(List<String> list) {
        Map<String, String> map = new HashMap<>();
        for (String tac : list) {
            logger.info("tac {}", tac);
            mobileDeviceRepoRepository.findByDeviceId(tac).ifPresentOrElse(x -> {
                        map.put(tac, x.stream().map(y -> y.getBrandName() + "---" + y.getModelName()).collect(Collectors.joining()));
                    },() -> {map.put(tac,"NA");}
            );

        }
        logger.info(" Brand and model name {}", map);
        if (map.isEmpty()) return false;
        Set<String> uniqueKeys = map.keySet().stream().collect(Collectors.toSet());
        Set<String> uniqueValues = map.values().stream().collect(Collectors.toSet());
        return uniqueKeys.size() == 1 && uniqueValues.size() == 1;
    }

    private boolean checkTACInMDR(String tac) {
        MobileDeviceRepository device = mobileDeviceRepoRepository.getByDeviceId(tac);
        // If the device is null, it means the TAC is not found in MDR
        return device != null;
    }

    public ResponseModel save(SearchImeiByPoliceMgmt filterRequest) {
        logger.info("Upload Single/Bulk Recovery Transaction ID : {}", filterRequest.getTransactionId());
        Optional<String> optional = Optional.ofNullable(filterRequest.getTransactionId());

        if (optional.isPresent()) {
            filterRequest.setStatus("Pending");
            // Extract TACs from the IMEIs
            String[] imeis = {filterRequest.getImei1(), filterRequest.getImei2(), filterRequest.getImei3(), filterRequest.getImei4()};
            List<String> tacList = new ArrayList<>();
            for (String imei : imeis) {
                if (imei != null && imei.length() >= 8) {
                    String tac = imei.substring(0, 8);
                    tacList.add(tac);
                    logger.info("TAC from IMEI {}", tacList);
                }
            }

            //if imeis length is 1, check if TAC exists in MDR
            if (tacList.size() == 1) {
                String tac = tacList.get(0);
                logger.info("existence of single IMEI in MDR {} ",checkTACInMDR(tac));
                if (!checkTACInMDR(tac)) {
                    return new ResponseModel(HttpStatus.INTERNAL_SERVER_ERROR.value(), "TAC not found in MDR");
                }
            }

            if (isBrandAndModelValid(tacList) && filterRequest.getRequestMode().equalsIgnoreCase("single")){
                // Proceed with the upload if all checks pass
                ResponseModel response = eirsStolenCheckIMEIFileUploadPayload.upload(filterRequest);
                logger.info("isBrandAndModelValid is true and request mode is :{} : {}",filterRequest.getRequestMode(), response);
                eirsStolenCheckIMEIFileUploadPayload.saveWebActionDBEntity(filterRequest);
                auditTrailService.auditTrailOperation(filterRequest.getAuditTrailModel(), "MOI", "IMEI_SEARCH_RECOVERY");
                return response;
            } else if(filterRequest.getRequestMode().equalsIgnoreCase("bulk")){
                ResponseModel response = eirsStolenCheckIMEIFileUploadPayload.upload(filterRequest);
                logger.info("request mode is :{} : {}",filterRequest.getRequestMode(), response);
                eirsStolenCheckIMEIFileUploadPayload.saveWebActionDBEntity(filterRequest);
                auditTrailService.auditTrailOperation(filterRequest.getAuditTrailModel(), "MOI", "IMEI_SEARCH_RECOVERY");
                return response;
            } else {
                return new ResponseModel(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Invalid Modal and Brand Name");
            }

        }
        return new ResponseModel(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Pass Request type parameter in payload");
    }


    public ResponseEntity<?> initiateRecoveryService(StolenLostModel filterRequest) {
        logger.info("initiateRecovery Request ID : {}", filterRequest.getRequestId());
        StolenLostModel response = null;
        GenricResponse result = null;
        try {
            Optional<String> optional = Optional.ofNullable(filterRequest.getRequestId());
            if (optional.isPresent()) {
                response = lostDeviceManagementRepository.findByRequestId(filterRequest.getRequestId());

                logger.info("Initiate Recovery response : {}", response);
                // Check if the response is valid before saving
                if (response != null) {
                    response = mapToLostDeviceManagement(response);

                    String recoveryRequestId = "R" + utility.getTxnId();
                    logger.info("New lost_device_mgmt Request ID number={}", recoveryRequestId);

                    // Count non-null IMEI in stolen_device_mgmt
                    int nonNullCount = 0;
                    if (response.getImei1() != null) nonNullCount++;
                    if (response.getImei2() != null) nonNullCount++;
                    if (response.getImei3() != null) nonNullCount++;
                    if (response.getImei4() != null) nonNullCount++;

                    logger.info("non-null IMEI count in stolen_device_mgmt : {}", nonNullCount + " for Single request id " + recoveryRequestId);
                    // Set request mode based on count of non-null IMEI values
                    if (nonNullCount == 1) {
                        response.setRequestMode("Single");
                    } else if (nonNullCount > 1) {
                        response.setRequestMode("Bulk");
                    }

                    //set requestID in lost_id column and new recoveryRequestId in request_id column.
                    response.setLostId(filterRequest.getRequestId());
                    response.setRequestId(recoveryRequestId);

                    response.setOtp(Utility.getOtp(6));
                    logger.info("initiateRecovery lostDeviceManagement response : {}", response);

                    //save response in lost_device_mgmt table
                    eirsStolenCheckIMEIFileUploadPayload.initiateRecovery(response);
                    NotificationAPI(response.getContactNumberForOtp(), response.getDeviceOwnerNationality(), response.getOtp() + "", filterRequest.getLanguage(), recoveryRequestId,response.getOtpEmail());

                }
                result = new GenricResponse(200, "Initiate Recovery Successful", "", response);
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
        }
        result = new GenricResponse(400, "Please send valid parameters", "", response);
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> initiateBulkRecoveryService(StolenLostModel filterRequest) {
        logger.info("initiateRecovery Bulk Request ID : {}", filterRequest.getRequestId());
        StolenLostModel response = null;
        GenricResponse result = null;
        try {
            String bulkRecoveryRequestId = "R" + utility.getTxnId();
            String processedFileName = bulkRecoveryRequestId + ".csv";
            String processedFilePath = propertiesReader.recoveryFilePath + "/" + bulkRecoveryRequestId;
            logger.info("Processed file path is {}", processedFilePath);

            // Create the directory for the recovery request ID
            File directory = new File(processedFilePath);
            if (!directory.exists()) {
                boolean created = directory.mkdirs();
                if (created) {
                    logger.info("Created directory: {}", processedFilePath);
                } else {
                    logger.error("Failed to create directory: {}", processedFilePath);
                    return new ResponseEntity<>(new GenricResponse(500, "Failed to create directory.", "", response), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

            // Get imei from search_imei_detail_by_police and write in request_id.csv
            int totalCount = 0;
            List<SearchImeiDetailByPolice> results = searchImeiDetailByPoliceRepo.findByRequestId(filterRequest.getRequestId());
            File outFile = new File(directory, processedFileName);
            try (PrintWriter printWriter = new PrintWriter(new FileWriter(outFile))) {
                printWriter.println("IMEI");
                if (!results.isEmpty()) {
                    for (SearchImeiDetailByPolice detail : results) {
                        String imei = detail.getImei();
                        if (imei != null) {
                            printWriter.println(imei);
                            totalCount++;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage(), e);
            }
            logger.info("Total IMEIs written: {}", totalCount);

            Optional<String> optional = Optional.ofNullable(filterRequest.getRequestId());
            if (optional.isPresent()) {
                response = lostDeviceManagementRepository.findByRequestId(filterRequest.getRequestId());
                logger.info("Initiate Recovery Bulk response : {}", response);
                // Check if the response is valid before saving
                if (response != null) {
                    response = mapToLostDeviceManagement(response);
                    logger.info("initiateRecovery lostDeviceManagement Bulk response : {}", response);
                    response.setOtp(Utility.getOtp(6));


                    // Count non-null IMEI in stolen_device_mgmt
                    int nonNullCount = 0;
                    if (response.getImei1() != null) nonNullCount++;
                    if (response.getImei2() != null) nonNullCount++;
                    if (response.getImei3() != null) nonNullCount++;
                    if (response.getImei4() != null) nonNullCount++;

                    logger.info("non-null IMEI count in stolen_device_mgmt : {}", nonNullCount + " for Bulk request id " + bulkRecoveryRequestId);
                    // Set request mode based on count of non-null IMEI values
                    if (nonNullCount == 1) {
                        response.setRequestMode("Single");
                    } else if (nonNullCount > 1) {
                        response.setRequestMode("Bulk");
                    }

                    //set requestID in lost_id column and new recoveryRequestId in request_id column.
                    response.setLostId(filterRequest.getRequestId());
                    response.setRequestId(bulkRecoveryRequestId);
                    response.setFileName(processedFileName);
                    response.setFileRecordCount(String.valueOf(totalCount));

                    //save response in lost_device_mgmt table
                    eirsStolenCheckIMEIFileUploadPayload.initiateRecovery(response);
                    NotificationAPI(response.getContactNumberForOtp(), response.getDeviceOwnerNationality(), response.getOtp() + "", filterRequest.getLanguage(), bulkRecoveryRequestId,response.getOtpEmail());

                }
                result = new GenricResponse(200, "Initiate Recovery Successful", "", response);
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
        }
        result = new GenricResponse(400, "Please send valid parameters", "", response);
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }


    // Mapping method to convert SearchImeiDetailByPolice to LostDeviceManagement
    private StolenLostModel mapToLostDeviceManagement(StolenLostModel response) {
        StolenLostModel lostDeviceManagement = new StolenLostModel();
        try {
            // Set fields from  lost_device_mgmt response to lost_device_mgmt Table with status Recover and  Recover request ID
            lostDeviceManagement.setContactNumber(response.getContactNumber());
            lostDeviceManagement.setImei1(response.getImei1());
            lostDeviceManagement.setImei2(response.getImei2());
            lostDeviceManagement.setImei3(response.getImei3());
            lostDeviceManagement.setImei4(response.getImei4());
            lostDeviceManagement.setCreatedBy(response.getCreatedBy());


            lostDeviceManagement.setDeviceOwnerName(response.getDeviceOwnerName());
            lostDeviceManagement.setDeviceOwnerAddress(response.getDeviceOwnerAddress());
            lostDeviceManagement.setRequestMode(response.getRequestMode());
            lostDeviceManagement.setDeviceBrand(response.getDeviceBrand());
            lostDeviceManagement.setDeviceModel(response.getDeviceModel());
            lostDeviceManagement.setDevicePurchaseInvoiceUrl(response.getDevicePurchaseInvoiceUrl());
            lostDeviceManagement.setDeviceOwnerEmail(response.getDeviceOwnerEmail());
            lostDeviceManagement.setDeviceOwnerNationality(response.getDeviceOwnerNationality());
            lostDeviceManagement.setContactNumberForOtp(response.getContactNumberForOtp());
            lostDeviceManagement.setOtp(response.getOtp());
            lostDeviceManagement.setFirCopyUrl(response.getFirCopyUrl());
            lostDeviceManagement.setDeviceLostDateTime(response.getDeviceLostDateTime().toString());
            lostDeviceManagement.setStatus(response.getStatus());
            //lostDeviceManagement.setRequestMode(response.getRequestMode());
            lostDeviceManagement.setRecoveryReason(response.getRecoveryReason());
            lostDeviceManagement.setPoliceStation(response.getPoliceStation());
            lostDeviceManagement.setRequestType(Tags.lost_device_mgmt_request_type_recover);
            lostDeviceManagement.setStatus(Tags.lost_device_mgmt_status_init);


            lostDeviceManagement.setDeviceLostDateTime(response.getDeviceLostDateTime());
            lostDeviceManagement.setDeviceOwnerNationalID(response.getDeviceOwnerNationalID());
            lostDeviceManagement.setOtpEmail(response.getOtpEmail());
            lostDeviceManagement.setRemarks(response.getRemarks());
            lostDeviceManagement.setPassportNumber(response.getPassportNumber());
            lostDeviceManagement.setOwnerDOB(response.getOwnerDOB());
            lostDeviceManagement.setDeviceLostDateTime(response.getDeviceLostDateTime());
            lostDeviceManagement.setDeviceOwnerState(response.getDeviceOwnerState());
            lostDeviceManagement.setDeviceOwnerProvinceCity(response.getDeviceOwnerProvinceCity());
            lostDeviceManagement.setDeviceOwnerCommune(response.getDeviceOwnerCommune());
            lostDeviceManagement.setSerialNumber(response.getSerialNumber());
            lostDeviceManagement.setIncidentDetail(response.getIncidentDetail());
            lostDeviceManagement.setLanguage(response.getLanguage());
            //lostDeviceManagement.setOtpRetryCount(response.getOtpRetryCount());
            lostDeviceManagement.setOtherDocument(response.getOtherDocument());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
        }
        return lostDeviceManagement;
    }

    @Transactional
    public GenricResponse verifyOTP(String requestID, String otp, String lang) {

        GenricResponse genricResponse = new GenricResponse();

        logger.info("inside Otp verification block , request Id =" + requestID + " , OTP is =" + otp);

        //BulkCheckIMEIMgmt res = bulkIMEIFileUploadRepository.findByTxnId(requestID);

        StolenLostModel res = null;
        res = lostDeviceManagementRepository.findByRequestId(requestID);


        logger.info(" response from DB={}", res);
        logger.info(" response getContactNumberForOtp = {}", res.getContactNumberForOtp());
        logger.info(" response RequestId = {}", res.getRequestId());
        if (res != null) {

            Instant instant = res.getModifiedOn().atZone(ZoneId.systemDefault()).toInstant();

            Date date = Date.from(instant);
            final int diffMinutes = Utility.getNumOfMin(date, new Date());

            logger.info("diffMinutes  Request come : Request --->>>  " + diffMinutes + " < OTP_EXPIRE_TIME=" + propertiesReader.OTP_EXPIRE_TIME);

            if (diffMinutes < propertiesReader.OTP_EXPIRE_TIME) {
                if (res.getOtp().equals(otp)) {
                    logger.info("verification successfull");
                    res.setStatus("INIT");
                    lostDeviceManagementRepository.save(res);
                    genricResponse.setErrorCode(200);
                    genricResponse.setMessage("verification successfull");
                    genricResponse.setTxnId(res.getRequestId());
                   /* try {
                        eirsStolenCheckIMEIFileUploadPayload.saveInitiateWebActionDBEntity(res);
                     } catch (Exception e) {
                        // TODO: handle exception
                    }*/
                    //audiTrail(ip, browser, requestID, "Verify OTP", userAgent);

                } else {
                    logger.info("verification failed");
                    genricResponse.setErrorCode(201);
                    genricResponse.setMessage("verification failed");
                    genricResponse.setTxnId(requestID);
                }
            } else {
                logger.info("verification failed because expired");
                genricResponse.setErrorCode(410);
                genricResponse.setMessage("verification expired");
                genricResponse.setTxnId(requestID);
            }


        } else {
            logger.info("verification failed");
            genricResponse.setErrorCode(201);
            genricResponse.setMessage("verification failed");
            genricResponse.setTxnId(requestID);
        }


        return genricResponse;

    }

    public GenricResponse resendOTP(String requestID, String lang) {
        logger.info("inside resend OTP=" + requestID);
        StolenLostModel response = null;
        response = lostDeviceManagementRepository.findByRequestId(requestID);

        GenricResponse genricResponse = new GenricResponse();
        String OTPsms = Utility.getOtp(6);
        response.setOtp(OTPsms);
        logger.info("request to update  resend OTP=" + response);
        lostDeviceManagementRepository.save(response);// updating OTP in bulk_check_IMEI_mgmt table
        NotificationAPI(response.getContactNumberForOtp(), response.getDeviceOwnerNationality(), response.getOtp() + "", lang, response.getRequestId(), response.getOtpEmail());// calling notification API
        genricResponse.setErrorCode(200);
        genricResponse.setMessage("Resend OTP is succesfull");
        //audiTrail(ip, browser, requestID, "Resend OTP", browser);
        return genricResponse;
    }

    public void NotificationAPI(String msisdn, String nationality, String otp, String lang, String tid, String mail) {

        NotificationModel notificationModel = new NotificationModel();
        GenricResponse genricResponse = new GenricResponse();

        if (lang == null || lang.isEmpty() || lang.equalsIgnoreCase("")) {
            lang = "en";
        }
        String msg = "";
        try {
            EirsResponseParam param = eirsResponseParamRepository.findByTagAndLanguage("INITIATE_RECOVERY_OTP_MSG", lang.toLowerCase()); //Msg to change
            if (param != null) {
                msg = param.getValue().replaceAll("<OTP>", otp);
            }

            logger.info("NotificationAPI() :: get message Details [" + msg + "] msisdn=" + msisdn);
            logger.info("Device Owner nationality is for send OTP {}", nationality);
            notificationModel.setMessage(msg);
            notificationModel.setFeatureName("Initiate Recovery");
            notificationModel.setSubFeature("Initiate Recovery OTP Verify");
            notificationModel.setFeatureTxnId(tid);

            if (nationality.equalsIgnoreCase("0")) {
                notificationModel.setChannelType("SMS_OTP");
                notificationModel.setMsisdn(msisdn);
            }
            else {
                notificationModel.setChannelType("EMAIL_OTP");
                notificationModel.setEmail(mail);
            }
            notificationModel.setMsgLang(lang);
            logger.info("request send to notification API=" + notificationModel);
            genricResponse = notificationFeign.addNotifications(notificationModel);
            logger.info("Notification API response=" + genricResponse);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            msg = "<OTP> is your OTP to upload the bulk check IMEI file.\n Never share OTP with anyone.".replaceAll("<OTP>", otp);
            logger.info("NotificationAPI() :: Exception " + e.getMessage() + " when get message Details msisdn=" + msisdn);


        }


    }

    public ResponseEntity<?> initiateRecoveryFormService(StolenLostModel filterRequest) {
        logger.info("initiate Recovery RecoverRequest ID : {}", filterRequest.getRequestId());
        StolenLostModel response = null;
        GenricResponse result = null;
        try {
            Optional<String> optional = Optional.ofNullable(filterRequest.getRequestId());
            if (optional.isPresent()) {
                response = lostDeviceManagementRepository.findByRequestId(filterRequest.getRequestId());
                logger.info("Initiate Recovery form response : {}", response);
                // Check if the response is valid before saving
                if (response != null) {
                    response.setRequestId(filterRequest.getRequestId());
                    response.setRecoveryReason(filterRequest.getRecoveryReason());
                    response.setRemarks(filterRequest.getRemarks());
                    response.setDeviceLostDateTime(filterRequest.getDeviceLostDateTime());
                    response.setProvince(filterRequest.getProvince());
                    response.setDistrict(filterRequest.getDistrict());
                    response.setCommune(filterRequest.getCommune());
                    response.setRequestMode(filterRequest.getRequestMode());
                    response.setStatus(Tags.lost_device_mgmt_status_start);


                    //save response in lost_device_mgmt table
                    logger.info("Initiate Recovery updated form response : {}", response);
                    eirsStolenCheckIMEIFileUploadPayload.initiateRecovery(response);
                    eirsStolenCheckIMEIFileUploadPayload.saveInitiateWebActionDBEntity(response);
                    auditTrailService.auditTrailOperation(filterRequest.getAuditTrailModel(), "MOI", "MOI_INITIATE_RECOVERY");
                }
                result = new GenricResponse(200, "Initiate Recovery form Successfully updated", "", response);
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
        }
        result = new GenricResponse(400, "Please send valid parameters", "", response);
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<?> sendNotification(StolenImeiStatusCheckRequest stolenRequest) {
        NotificationModel notificationModel = new NotificationModel();
        StolenLostModel stolenDeviceMgmt = null;


        GenricResponse results = new GenricResponse();
        String requestId = stolenRequest.getRequestId();
        String TransactionId = stolenRequest.getTransactionId();
        logger.info("Send Notification Request ID : {}", requestId);
        logger.info("Send Notification Transaction ID : {}", TransactionId);
        String lang = "en";
        String msg = "";

        try {
            EirsResponseParam param = eirsResponseParamRepository.findByTagAndLanguage("INITIATE_RECOVERY_NOTIFICATION_MSG", lang.toLowerCase()); //Msg to change
            logger.info("response from eirs_response_param table {}", param);
            if (param != null) {
                msg = param.getValue().replace("<REQUEST_NUMBER>", requestId);
                msg = msg.replace("<TRANSACTION_ID>", TransactionId);
            }
            logger.info("NotificationAPI() :: get final message [{}] ", msg);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            logger.info("Notification :: Exception {}", e.getMessage());
        }
        stolenDeviceMgmt = lostDeviceManagementRepository.findByRequestId(requestId);
        notificationModel.setFeatureTxnId(TransactionId);
        notificationModel.setMessage(msg);
        notificationModel.setFeatureName("Stolen Check Status IMEI");
        notificationModel.setSubFeature("Send Notification");
        //notificationModel.setFeatureTxnId(tid);
        logger.info("Device Owner nationality for sending Notification is {}",stolenDeviceMgmt.getDeviceOwnerNationality());


        if(stolenDeviceMgmt.getDeviceOwnerNationality().equals("0")){
            notificationModel.setChannelType("SMS");
            notificationModel.setMsisdn(stolenDeviceMgmt.getContactNumberForOtp());
            notificationModel.setEmail(stolenDeviceMgmt.getOtpEmail());
            //notificationModel.setEmail(stolenDeviceMgmt.getDeviceOwnerEmail());
        }else{
            notificationModel.setChannelType("EMAIL");
            notificationModel.setEmail(stolenDeviceMgmt.getOtpEmail());
        }
        //notificationModel.setEmail(otp);

        notificationModel.setMsgLang(lang);
        logger.info("request send to notification API={}", notificationModel);
        results = notificationFeign.addNotifications(notificationModel);
        logger.info("Notification API response={}", results);
        GenricResponse response;
        if (results != null) {
            response = new GenricResponse(200, "Notification Sent Successfully", stolenRequest.getTransactionId(), results);
        } else {
            response = new GenricResponse(404, "No records found", stolenRequest.getTransactionId(), results);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}