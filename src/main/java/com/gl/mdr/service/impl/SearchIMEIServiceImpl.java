package com.gl.mdr.service.impl;

import com.gl.mdr.configuration.PropertiesReader;
import com.gl.mdr.exceptions.ResourceServicesException;
import com.gl.mdr.model.app.*;
import com.gl.mdr.model.audit.ImeiSearchAudit;
import com.gl.mdr.model.constants.Tags;
import com.gl.mdr.model.filter.SearchIMEIRequest;
import com.gl.mdr.model.generic.DeviceStateResponse;
import com.gl.mdr.model.generic.GenricResponse;
import com.gl.mdr.model.generic.SearchIMEIResponse;
import com.gl.mdr.model.generic.SearchIMEITableResponse;
import com.gl.mdr.repo.app.*;
import com.gl.mdr.repo.audit.IMEISearchAuditRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class SearchIMEIServiceImpl {
    private static final Logger logger = LogManager.getLogger(SearchIMEIServiceImpl.class);

    @Autowired
    PropertiesReader propertiesReader;

    @Autowired
    MobileDeviceRepoRepository mdrRepository;

    @Autowired
    BlackListRepo blackListRepo;

    @Autowired
    GreyListRepo greyListRepo;

    @Autowired
    ExceptionListReporsitory exceptionListRepo;

    @Autowired
    GdceDataRepository gdceDataRepo;

    @Autowired
    TrcLocalManufacturedDeviceDataRepo trcLocalManufacturedDeviceDataRepo;

    @Autowired
    NationalWhiteListRepository nationalWhiteListRepo;

    @Autowired
    ActiveUniqueImeiRepo activeUniqueImeiRepo;

    @Autowired
    ActiveImeiWithDifferentMsisdnRepo activeImeiWithDifferentMsisdnRepo;

    @Autowired
    ActiveIMEIWithDifferentImsiRepo activeIMEIWithDifferentImsiRepo;

    @Autowired
    ImeiPairDetailRepo imeiPairDetailRepo;

    @Autowired
    ActiveMsisdnListRepository activeMsisdnListRepository;

    @Autowired
    GreyListHistoryRepo greyListHistoryRepo;

    @Autowired
    BlackListHistoryRepo blackListHistoryRepo;

    @Autowired
    ImeiPairDetailHistoryRepo imeiPairDetailHistoryRepo;

    @Autowired
    DuplicateDeviceRepository duplicateDeviceRepository;

    @Autowired
    EirsInvalidImeiRepo invalidImeiRepo;

    @Autowired
    LostDeviceDetailRepository lostDeviceDetailRepo;

    @Autowired
    LostDeviceDetailHistoryRepo lostDeviceDetailHistoryRepo;

    @Autowired
    IMEISearchAuditRepo imeiSearchAuditRepo;

    @Autowired
    DuplicateDeviceDetailHisRepository duplicateDeviceDetailHisRepository;

    @Autowired
    ExceptionListHistoryRepo exceptionListHistoryRepo;

    @Autowired
    SystemConfigurationDbRepository systemConfigurationDbRepository;

    @Autowired
    SystemConfigListRepository systemConfigListRepository;

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public ResponseEntity<?> getImsiMsisdnService(SearchIMEIRequest searchIMEIRequest) {
        ActiveUniqueImei result;
        String imei = searchIMEIRequest.getImei();
        String msisdn = searchIMEIRequest.getMsisdn();
        String requestId = searchIMEIRequest.getRequestId();

        // substring of the IMEI if it's longer than 14 digits;
        imei = (imei != null && imei.length() > 14) ? imei.substring(0, 14) : imei;
        logger.info("Get Detail of IMEI " + imei + " with requestId " + requestId);
        if (msisdn == null || msisdn.isBlank()) {
            result = activeUniqueImeiRepo.findByImei(imei);
        } else {
            result = activeUniqueImeiRepo.findByImeiAndMsisdn(imei, msisdn);
        }

        //Entry in search IMEI history table
        ImeiSearchAudit imeiSearchAudit = new ImeiSearchAudit();
        imeiSearchAudit.setTicketId(requestId);
        imeiSearchAudit.setImei(searchIMEIRequest.getImei());
        imeiSearchAudit.setUsername(searchIMEIRequest.getUserName());
        imeiSearchAuditRepo.save(imeiSearchAudit);

        logger.info("IMEI [" + imei + "] Details From active_unique_imei table " + result);

        if (result != null) {
            GenricResponse response = new GenricResponse(200, "Success", "", result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            logger.info("IMEI [" + imei + "] not Found");
            GenricResponse response = new GenricResponse(500, "IMEI not Found", "", result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

    }

    public ResponseEntity<?> getDeviceDetails(SearchIMEIRequest searchIMEIRequest) {
        MobileDeviceRepository result = new MobileDeviceRepository();
        String imei = searchIMEIRequest.getImei();
        Integer validityFlag = null;
        String deviceId;
        NationalWhiteList nwlResponse = new NationalWhiteList();
        // substring of the IMEI if it's longer than 14 digits;
        imei = (imei != null && imei.length() > 14) ? imei.substring(0, 14) : imei;


        // Check if IMEI length is between 14 and 16 characters and contains only digit
        // if IMEI exists in NWL and validityFlag is 0 then don't display MDR data.

        // Check if the IMEI is longer than 14 digits


        //if((imei.length() >= 14 && imei.length() <= 16) && imei.matches("\\d+")) {
        if (nationalWhiteListRepo.existsByImei(imei)) {
            logger.info("IMEI " + imei + " Exist in NWL");
            nwlResponse = nationalWhiteListRepo.findByImeiIgnoreCase(imei);
            validityFlag = nwlResponse.getValidityFlag();
            logger.info("IMEI " + imei + " Exist in NWL with validityFlag " + validityFlag);
            if (validityFlag != 0) {
                logger.info("IMEI " + imei + " received with valid length " + imei.length());
                deviceId = searchIMEIRequest.getImei().substring(0, 8);
                result = mdrRepository.getByDeviceId(deviceId);
            }
        }
        //}
        if (result.getId() != null) {
            logger.info("Response From MDR table " + result);
            GenricResponse response = new GenricResponse(200, "Success", "", result);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } else {
            logger.info("IMEI [" + imei + "] not Found in MDR table");
            GenricResponse response = new GenricResponse(500, "IMEI not Found", "", result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }


    }


    public ResponseEntity<?> getDeviceState(SearchIMEIRequest searchIMEIRequest) {
        String imei = searchIMEIRequest.getImei();
        String msisdn = searchIMEIRequest.getMsisdn();
        // substring of the IMEI if it's longer than 14 digits;
        imei = (imei != null && imei.length() > 14) ? imei.substring(0, 14) : imei;
        logger.info("Recieved IMEI [" + imei + "] and MSISDN in Device State Impl [" + msisdn + "]");
        List<List<SearchIMEIResponse>> list = new ArrayList<>();
        try {
            //if not found in main table then check in history table of respective table
            List<SearchIMEIResponse> searchIMEIResponse;
            searchIMEIResponse = getBlackListData(imei, msisdn);
            if (Objects.isNull(searchIMEIResponse.get(0).getImei())) {
                searchIMEIResponse = getBlackListHistorytData(imei, msisdn);
                if (Objects.isNull(searchIMEIResponse.get(0).getImei())) {
                    searchIMEIResponse.get(0).setTableName(Tags.black_list);
                    logger.info("if IMEI pair not exists in table then data from " + Tags.black_list_his + " table  " + searchIMEIResponse);
                }
            }
            list.add(searchIMEIResponse);

            searchIMEIResponse = getGreyListData(imei, msisdn);
            if (Objects.isNull(searchIMEIResponse.get(0).getImei())) {
                searchIMEIResponse = getGreyListHistorytData(imei, msisdn);
                if (Objects.isNull(searchIMEIResponse.get(0).getImei())) {
                    searchIMEIResponse.get(0).setTableName(Tags.grey_list);
                    logger.info("if IMEI pair not exists in table then data from " + Tags.grey_list_his + " table  " + searchIMEIResponse);
                }
            }
            list.add(searchIMEIResponse);

            SystemConfigurationDb systemConfigurationDb = systemConfigurationDbRepository.getByTag("is_edr_enabled");
            String isEdrEnabled = systemConfigurationDb.getValue();
            logger.info("is_edr_enabled: {}", isEdrEnabled);
            if (isEdrEnabled.equalsIgnoreCase("True")) {
                searchIMEIResponse = getActiveImeiDifferentImsiData(imei, msisdn);
            } else {
                searchIMEIResponse = getActiveImeiDifferentMsisdnData(imei, msisdn);
            }
            list.add(searchIMEIResponse);


            searchIMEIResponse = getExceptionListData(imei, msisdn);
            if (Objects.isNull(searchIMEIResponse.get(0).getImei())) {
                searchIMEIResponse = getExceptionListHistorytData(imei, msisdn);
                if (Objects.isNull(searchIMEIResponse.get(0).getImei())) {
                    searchIMEIResponse.get(0).setTableName(Tags.exception_list);
                    logger.info("if IMEI pair not exists in table then data from " + Tags.exception_list_his + " table  " + searchIMEIResponse);
                }
            }
            list.add(searchIMEIResponse);

            searchIMEIResponse = getGdceData(imei);
            list.add(searchIMEIResponse);

            searchIMEIResponse = getLocalManufacturedData(imei);
            list.add(searchIMEIResponse);

            searchIMEIResponse = getPairedData(imei, msisdn);
            if (Objects.isNull(searchIMEIResponse.get(0).getImei())) {
                searchIMEIResponse = getPairDetailHistorytData(imei, msisdn);
                if (Objects.isNull(searchIMEIResponse.get(0).getImei())) {
                    searchIMEIResponse.get(0).setTableName(Tags.imei_pair_detail);
                    logger.info("if IMEI pair not exists in table then data from " + Tags.imei_pair_detail_his + " table  " + searchIMEIResponse);
                }
            }
            list.add(searchIMEIResponse);

            searchIMEIResponse = getNationalWhiteListData(imei, msisdn);
            list.add(searchIMEIResponse);

            searchIMEIResponse = getInvalidImeiData(imei);
            list.add(searchIMEIResponse);

            searchIMEIResponse = getDuplicateDeviceData(imei, msisdn);
            if (Objects.isNull(searchIMEIResponse.get(0).getImei())) {
                searchIMEIResponse = getDuplicateHistorytData(imei, msisdn);
                if (Objects.isNull(searchIMEIResponse.get(0).getImei())) {
                    searchIMEIResponse.get(0).setTableName(Tags.duplicate_device_detail);
                    logger.info("if IMEI pair not exists in table then data from " + Tags.duplicate_device_detail_his + " table " + searchIMEIResponse);
                }
            }
            list.add(searchIMEIResponse);

            searchIMEIResponse = getLostDeviceData(imei);
            if (Objects.isNull(searchIMEIResponse.get(0).getImei())) {
                searchIMEIResponse = getLostDeviceHistoryData(imei);
                if (Objects.isNull(searchIMEIResponse.get(0).getImei())) {
                    searchIMEIResponse.get(0).setTableName(Tags.lost_device_detail);
                    logger.info("if IMEI pair not exists in table then data from " + Tags.lost_device_detail_his + " table " + searchIMEIResponse);
                }
            }
            list.add(searchIMEIResponse);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
        }
        DeviceStateResponse deviceStateResponse = new DeviceStateResponse(list);
        return new ResponseEntity<>(deviceStateResponse, HttpStatus.OK);
    }


    public List<SearchIMEIResponse> getBlackListData(String imei, String msisdn) {
        List<SearchIMEIResponse> searchIMEIResponseList = new ArrayList<>();
        SearchIMEIResponse searchIMEIResponse = new SearchIMEIResponse();
        List<BlackList> response = new ArrayList<>();
        searchIMEIResponse.setTableName(Tags.black_list);
        ActiveMsisdnList activeMsisdnList;
        String imsi = null;
        if (msisdn == null || msisdn.isBlank()) {
            response = blackListRepo.findTop1ByImei(imei);
            logger.info("Response of IMEI [" + imei + "] recieved in black_list" + response);
        } else {
            activeMsisdnList = activeMsisdnListRepository.findByMsisdn(msisdn);
            if (activeMsisdnList != null) {
                imsi = activeMsisdnList.getImsi();
            }
            response = blackListRepo.findByImeiAndImsi(imei, imsi);
            logger.info("Response of IMEI [" + imei + "]  and IMSI [" + imsi + "] recieved in black_list" + response);
        }
        if (!response.isEmpty()) {
            for (BlackList responses : response) {
                SearchIMEIResponse imeiResponse = new SearchIMEIResponse();
                imeiResponse.setTableName(Tags.black_list);
                imeiResponse.setCreatedOn(responses.getCreatedOn().format(dtf));
                imeiResponse.setImei(responses.getImei());
                imeiResponse.setId(responses.getId());
                searchIMEIResponseList.add(imeiResponse);
            }

            logger.info("Multiple SearchIMEIResponses found for IMEI [" + imei + "] or MSISDN [" + msisdn + "] in black_list");
            return searchIMEIResponseList;
        }
        searchIMEIResponseList.add(searchIMEIResponse);
        return searchIMEIResponseList;

    }


    public List<SearchIMEIResponse> getGreyListData(String imei, String msisdn) {
        List<SearchIMEIResponse> searchIMEIResponseList = new ArrayList<>();
        SearchIMEIResponse searchIMEIResponse = new SearchIMEIResponse();
        List<GreyList> response = new ArrayList<>();
        ActiveMsisdnList activeMsisdnList;
        String imsi = null;
        searchIMEIResponse.setTableName(Tags.grey_list);
        if (msisdn == null || msisdn.isBlank()) {
            response = greyListRepo.findTop1ByImei(imei);
            logger.info("Response of IMEI [" + imei + "] revcieved in grey_list" + response);
        } else {
            activeMsisdnList = activeMsisdnListRepository.findByMsisdn(msisdn);
            if (activeMsisdnList != null) {
                imsi = activeMsisdnList.getImsi();
            }
            response = greyListRepo.findByImeiAndImsi(imei, imsi);
            logger.info("Response of IMEI [" + imei + "]  and IMSI [" + imsi + "] revcieved in grey_list" + response);
        }
        if (!response.isEmpty()) {
            for (GreyList greyListresponse : response) {
                SearchIMEIResponse imeiResponse = new SearchIMEIResponse();
                imeiResponse.setTableName(Tags.grey_list);
                imeiResponse.setCreatedOn(greyListresponse.getCreatedOn().format(dtf));
                imeiResponse.setImei(greyListresponse.getImei());
                imeiResponse.setId(greyListresponse.getId());
                searchIMEIResponseList.add(imeiResponse);
            }

            logger.info("Multiple SearchIMEIResponses found for IMEI [" + imei + "] or MSISDN [" + msisdn + "] in grey_list");
            return searchIMEIResponseList;
        }
        searchIMEIResponseList.add(searchIMEIResponse);
        return searchIMEIResponseList;

    }

    //active_imei_with_different_msisdn can have multiple entries
    public List<SearchIMEIResponse> getActiveImeiDifferentMsisdnData(String imei, String msisdn) {
        List<SearchIMEIResponse> searchIMEIResponseList = new ArrayList<>();
        SearchIMEIResponse searchIMEIResponse = new SearchIMEIResponse();
        SystemConfigurationDb systemConfigurationDb = systemConfigurationDbRepository.getByTag("is_edr_enabled");
        String is_edr_enabled = systemConfigurationDb.getValue();
        if (is_edr_enabled != "True") {
            List<ActiveImeiWithDifferentMsisdn> responses = new ArrayList<>(); // List to hold multiple responses
            String imsi = null;
            searchIMEIResponse.setTableName(Tags.active_imei_with_different_msisdn);

            if (msisdn == null || msisdn.isBlank()) {
                responses = activeImeiWithDifferentMsisdnRepo.findTop1ByImei(imei);
                logger.info("Responses for IMEI [" + imei + "] received in active_imei_with_different_msisdn: " + responses);
            } else {
                ActiveMsisdnList activeMsisdnList = activeMsisdnListRepository.findByMsisdn(msisdn);
                if (activeMsisdnList != null) {
                    imsi = activeMsisdnList.getImsi();
                }
                responses = activeImeiWithDifferentMsisdnRepo.findByImeiAndImsi(imei, imsi);
                logger.info("Responses for IMEI [" + imei + "] and IMSI [" + imsi + "] received in active_imei_with_different_msisdn: " + responses);
            }
            if (!responses.isEmpty()) {
                // Multiple responses case
                for (ActiveImeiWithDifferentMsisdn response : responses) {
                    SearchIMEIResponse imeiResponse = new SearchIMEIResponse();
                    imeiResponse.setTableName(Tags.active_imei_with_different_msisdn);
                    imeiResponse.setCreatedOn(response.getCreatedOn().format(dtf));
                    imeiResponse.setImei(response.getImei());
                    imeiResponse.setId(response.getId());
                    searchIMEIResponseList.add(imeiResponse);
                }

                logger.info("Multiple SearchIMEIResponses found for IMEI [" + imei + "] or MSISDN [" + msisdn + "] in active_imei_with_different_msisdn" + searchIMEIResponseList);
                return searchIMEIResponseList;
            }

        }

        searchIMEIResponseList.add(searchIMEIResponse);
        return searchIMEIResponseList;
    }

    public List<SearchIMEIResponse> getActiveImeiDifferentImsiData(String imei, String msisdn) {
        List<SearchIMEIResponse> searchIMEIResponseList = new ArrayList<>();
        SearchIMEIResponse searchIMEIResponse = new SearchIMEIResponse();
        List<ActiveIMEIWithDifferentImsi> responses = new ArrayList<>(); // List to hold multiple responses
        String imsi = null;
        searchIMEIResponse.setTableName(Tags.active_imei_with_different_imsi);

        if (msisdn == null || msisdn.isBlank()) {
            responses = activeIMEIWithDifferentImsiRepo.findTop1ByImei(imei);
            logger.info("Responses for IMEI [" + imei + "] received in active_imei_with_different_imsi: " + responses);
        }
        //No IMSI field
        if (!responses.isEmpty()) {
            // Multiple responses case
            for (ActiveIMEIWithDifferentImsi response : responses) {
                SearchIMEIResponse imeiResponse = new SearchIMEIResponse();
                imeiResponse.setTableName(Tags.active_imei_with_different_imsi);
                imeiResponse.setCreatedOn(response.getCreatedOn().format(dtf));
                imeiResponse.setImei(response.getImei());
                imeiResponse.setId(response.getId());
                searchIMEIResponseList.add(imeiResponse);
            }

            logger.info("Multiple SearchIMEIResponses found for IMEI [" + imei + "] or MSISDN [" + msisdn + "] in active_imei_with_different_imsi" + searchIMEIResponseList);
            return searchIMEIResponseList;
        }
        searchIMEIResponseList.add(searchIMEIResponse);
        return searchIMEIResponseList;
    }

    public List<SearchIMEIResponse> getExceptionListData(String imei, String msisdn) {
        List<SearchIMEIResponse> searchIMEIResponseList = new ArrayList<>();
        SearchIMEIResponse searchIMEIResponse = new SearchIMEIResponse();
        List<ExceptionList> response = new ArrayList<>();
        ActiveMsisdnList activeMsisdnList;
        String imsi = null;
        searchIMEIResponse.setTableName(Tags.exception_list);
        if (msisdn == null || msisdn.isBlank()) {
            response = exceptionListRepo.findTop1ByImei(imei);
            logger.info("Response of IMEI [" + imei + "] revcieved in exception_list" + response);
        } else {
            activeMsisdnList = activeMsisdnListRepository.findByMsisdn(msisdn);
            if (activeMsisdnList != null) {
                imsi = activeMsisdnList.getImsi();
            }
            response = exceptionListRepo.findByImeiAndImsi(imei, imsi);
            logger.info("Response of IMEI [" + imei + "]  and IMSI [" + imsi + "] revcieved in exception_list " + response);
        }
        if (!response.isEmpty()) {
            for (ExceptionList exceptionListResponse : response) {
                SearchIMEIResponse imeiResponse = new SearchIMEIResponse();
                imeiResponse.setTableName(Tags.exception_list);
                imeiResponse.setCreatedOn(exceptionListResponse.getCreatedOn().format(dtf));
                imeiResponse.setImei(exceptionListResponse.getImei());
                imeiResponse.setId(exceptionListResponse.getId());
                searchIMEIResponseList.add(imeiResponse);
            }

            logger.info("Multiple SearchIMEIResponses found for IMEI [" + imei + "] or MSISDN [" + msisdn + "] in exception_list");
            return searchIMEIResponseList;
        }
        searchIMEIResponseList.add(searchIMEIResponse);
        return searchIMEIResponseList;
    }

    public List<SearchIMEIResponse> getGdceData(String imei) {
        List<SearchIMEIResponse> searchIMEIResponseList = new ArrayList<>();
        SearchIMEIResponse searchIMEIResponse = new SearchIMEIResponse();
        List<GdceData> response = new ArrayList<>();
        searchIMEIResponse.setTableName(Tags.gdce_data);
        response = gdceDataRepo.findTop1ByImei(imei); //Only IMEI is available in this table. no IMSI/MSISDN
        logger.info("Response of IMEI [" + imei + "] in gdce_data " + response);
        if (!response.isEmpty()) {
            for (GdceData gdceDataResponse : response) {
                SearchIMEIResponse imeiResponse = new SearchIMEIResponse();
                imeiResponse.setTableName(Tags.gdce_data);
                imeiResponse.setCreatedOn(gdceDataResponse.getCreatedOn().format(dtf));
                imeiResponse.setImei(gdceDataResponse.getImei());
                imeiResponse.setId(gdceDataResponse.getId());
                searchIMEIResponseList.add(imeiResponse);
            }
            logger.info("Multiple SearchIMEIResponses found for IMEI [" + imei + "]  in gdce_data");
            return searchIMEIResponseList;
        }
        searchIMEIResponseList.add(searchIMEIResponse);
        return searchIMEIResponseList;
    }

    public List<SearchIMEIResponse> getLocalManufacturedData(String imei) {
        List<SearchIMEIResponse> searchIMEIResponseList = new ArrayList<>();
        SearchIMEIResponse searchIMEIResponse = new SearchIMEIResponse();
        List<TrcLocalManufacturedDeviceData> response = new ArrayList<>();
        searchIMEIResponse.setTableName(Tags.trc_local_manufactured_device_data);
        response = trcLocalManufacturedDeviceDataRepo.findTop1ByImei(imei); //Only IMEI is available in this table. no IMSI/MSISDN
        logger.info("Response of IMEI [" + imei + "] revcieved in trc_local_manufactured_device_data " + response);
        if (!response.isEmpty()) {
            for (TrcLocalManufacturedDeviceData localManufacturedDataResponse : response) {
                SearchIMEIResponse imeiResponse = new SearchIMEIResponse();
                imeiResponse.setTableName(Tags.trc_local_manufactured_device_data);
                imeiResponse.setCreatedOn(localManufacturedDataResponse.getCreatedOn().format(dtf));
                imeiResponse.setImei(localManufacturedDataResponse.getImei());
                imeiResponse.setId(localManufacturedDataResponse.getId());
                searchIMEIResponseList.add(imeiResponse);
            }
            logger.info("Multiple SearchIMEIResponses found for IMEI [" + imei + "]  in trc_local_manufactured_device_data");
            return searchIMEIResponseList;
        }
        searchIMEIResponseList.add(searchIMEIResponse);
        return searchIMEIResponseList;
    }

    //imei_pair_detail can have multiple entries. IMEI/IMSI
    public List<SearchIMEIResponse> getPairedData(String imei, String msisdn) {
        List<SearchIMEIResponse> searchIMEIResponseList = new ArrayList<>();
        SearchIMEIResponse searchIMEIResponse = new SearchIMEIResponse();
        List<ImeiPairDetail> response = new ArrayList<>();
        ActiveMsisdnList activeMsisdnList;
        String imsi = null;
        searchIMEIResponse.setTableName(Tags.imei_pair_detail);
        if (msisdn == null || msisdn.isBlank()) {
            response = imeiPairDetailRepo.findTop1ByImei(imei); //Only IMEI/IMSI is available in this table
            logger.info("Response of IMEI [" + imei + "] revcieved  in imei_pair_detail" + response);
        } else {
            activeMsisdnList = activeMsisdnListRepository.findByMsisdn(msisdn);
            if (activeMsisdnList != null) {
                imsi = activeMsisdnList.getImsi();
            }
            response = imeiPairDetailRepo.findByImeiAndImsi(imei, imsi);
            logger.info("Response of IMEI [" + imei + "]  and IMSI [" + imsi + "] revcieved in imei_pair_detail " + response);
        }
        if (!response.isEmpty()) {
            for (ImeiPairDetail imeiPairDetailDataResponse : response) {

                SearchIMEIResponse imeiResponse = new SearchIMEIResponse();
                imeiResponse.setTableName(Tags.imei_pair_detail);
                imeiResponse.setCreatedOn(imeiPairDetailDataResponse.getCreatedOn().format(dtf));
                imeiResponse.setImei(imeiPairDetailDataResponse.getImei());
                imeiResponse.setId(imeiPairDetailDataResponse.getId());
                searchIMEIResponseList.add(imeiResponse);
            }
            logger.info("Multiple SearchIMEIResponses found for IMEI [" + imei + "]  in imei_pair_detail");
            return searchIMEIResponseList;
        }
        searchIMEIResponseList.add(searchIMEIResponse);
        return searchIMEIResponseList;
    }

    public List<SearchIMEIResponse> getNationalWhiteListData(String imei, String msisdn) {
        List<SearchIMEIResponse> searchIMEIResponseList = new ArrayList<>();
        SearchIMEIResponse searchIMEIResponse = new SearchIMEIResponse();
        List<NationalWhiteList> response = new ArrayList<>();

        ActiveMsisdnList activeMsisdnList;
        String imsi = null;
        searchIMEIResponse.setTableName(Tags.national_whitelist);
        if (msisdn == null || msisdn.isBlank()) {
            response = nationalWhiteListRepo.findTop1ByImei(imei);
            logger.info("Response of IMEI [" + imei + "] revcieved  in national_whitelist" + response);
        } else {
            activeMsisdnList = activeMsisdnListRepository.findByMsisdn(msisdn);
            if (activeMsisdnList != null) {
                imsi = activeMsisdnList.getImsi();
            }
            response = nationalWhiteListRepo.findByImeiAndImsi(imei, imsi);
            logger.info("Response of IMEI [" + imei + "]  and IMSI [" + imsi + "] revcieved in national_whitelist" + response);
        }
        if (!response.isEmpty()) {
            for (NationalWhiteList nationalWhiteListResponse : response) {
                SearchIMEIResponse imeiResponse = new SearchIMEIResponse();
                imeiResponse.setTableName(Tags.national_whitelist);
                imeiResponse.setCreatedOn(nationalWhiteListResponse.getCreatedOn().format(dtf));
                imeiResponse.setImei(nationalWhiteListResponse.getImei());
                imeiResponse.setId(nationalWhiteListResponse.getNationalWhitelistId());
                searchIMEIResponseList.add(imeiResponse);
            }
            logger.info("Multiple SearchIMEIResponses found for IMEI [" + imei + "]  in national_whitelist");
            return searchIMEIResponseList;
        }
        searchIMEIResponseList.add(searchIMEIResponse);
        return searchIMEIResponseList;
    }

    public List<SearchIMEIResponse> getInvalidImeiData(String imei) {
        List<SearchIMEIResponse> searchIMEIResponseList = new ArrayList<>();
        SearchIMEIResponse searchIMEIResponse = new SearchIMEIResponse();
        List<EirsInvalidImei> response = new ArrayList<>();
        searchIMEIResponse.setTableName(Tags.eirs_invalid_imei);
        response = invalidImeiRepo.findTop1ByImei(imei); //Only IMEI is available in this table. no IMSI/MSISDN
        logger.info("Response of IMEI [" + imei + "] in eirs_invalid_imei " + response);
        if (!response.isEmpty()) {
            for (EirsInvalidImei InvalidImeiResponse : response) {
                SearchIMEIResponse imeiResponse = new SearchIMEIResponse();
                imeiResponse.setTableName(Tags.eirs_invalid_imei);
                imeiResponse.setCreatedOn(InvalidImeiResponse.getCreatedOn().format(dtf));
                imeiResponse.setImei(InvalidImeiResponse.getImei());
                imeiResponse.setId(InvalidImeiResponse.getId());
                searchIMEIResponseList.add(imeiResponse);
            }
            logger.info("Multiple SearchIMEIResponses found for IMEI [" + imei + "]  in eirs_invalid_imei");
            return searchIMEIResponseList;
        }
        searchIMEIResponseList.add(searchIMEIResponse);
        return searchIMEIResponseList;
    }

    public List<SearchIMEIResponse> getDuplicateDeviceData(String imei, String msisdn) {
        List<SearchIMEIResponse> searchIMEIResponseList = new ArrayList<>();
        SearchIMEIResponse searchIMEIResponse = new SearchIMEIResponse();
        List<DuplicateDeviceDetail> response = new ArrayList<>();
        ActiveMsisdnList activeMsisdnList;
        String imsi = null;
        searchIMEIResponse.setTableName(Tags.duplicate_device_detail);
        if (msisdn == null || msisdn.isBlank()) {
            response = duplicateDeviceRepository.findTop1ByImei(imei);
            logger.info("Response of IMEI [" + imei + "] revcieved in duplicate_device_detail" + response);
        } else {
            activeMsisdnList = activeMsisdnListRepository.findByMsisdn(msisdn);
            if (activeMsisdnList != null) {
                imsi = activeMsisdnList.getImsi();
            }
            response = duplicateDeviceRepository.findByImeiAndImsi(imei, imsi);
            logger.info("Response of IMEI [" + imei + "]  and IMSI [" + imsi + "] revcieved in duplicate_device_detail " + response);
        }
        if (!response.isEmpty()) {
            for (DuplicateDeviceDetail duplicateDeviceDetailResponse : response) {
                SearchIMEIResponse imeiResponse = new SearchIMEIResponse();
                imeiResponse.setTableName(Tags.duplicate_device_detail);
                imeiResponse.setCreatedOn(duplicateDeviceDetailResponse.getCreatedOn().format(dtf));
                imeiResponse.setImei(duplicateDeviceDetailResponse.getImei());
                imeiResponse.setId(duplicateDeviceDetailResponse.getId());
                searchIMEIResponseList.add(imeiResponse);
            }

            logger.info("Multiple SearchIMEIResponses found for IMEI [" + imei + "] or MSISDN [" + msisdn + "] in duplicate_device_detail");
            return searchIMEIResponseList;
        }
        searchIMEIResponseList.add(searchIMEIResponse);
        return searchIMEIResponseList;
    }

    public List<SearchIMEIResponse> getLostDeviceData(String imei) {
        List<SearchIMEIResponse> searchIMEIResponseList = new ArrayList<>();
        SearchIMEIResponse searchIMEIResponse = new SearchIMEIResponse();
        List<LostDeviceDetail> response = new ArrayList<>();
        searchIMEIResponse.setTableName(Tags.lost_device_detail);
        response = lostDeviceDetailRepo.findTop1ByImei(imei); //Only IMEI is available in this table. no IMSI/MSISDN
        logger.info("Response of IMEI [" + imei + "] in lost_device_detail " + response);
        if (!response.isEmpty()) {
            for (LostDeviceDetail lostDeviceDetailDataResponse : response) {
                SearchIMEIResponse imeiResponse = new SearchIMEIResponse();
                imeiResponse.setTableName(Tags.lost_device_detail);
                imeiResponse.setCreatedOn(lostDeviceDetailDataResponse.getCreatedOn().format(dtf));
                imeiResponse.setImei(lostDeviceDetailDataResponse.getImei());
                imeiResponse.setId(lostDeviceDetailDataResponse.getId());
                searchIMEIResponseList.add(imeiResponse);
            }
            logger.info("Multiple SearchIMEIResponses found for IMEI [" + imei + "]  in lost_device_detail");
            return searchIMEIResponseList;
        }
        searchIMEIResponseList.add(searchIMEIResponse);
        return searchIMEIResponseList;
    }

    public ResponseEntity<?> getTableRecord(SearchIMEIRequest searchIMEIRequest) {
        String tableName = searchIMEIRequest.getTableName();
        String imei = searchIMEIRequest.getImei();
        // substring of the IMEI if it's longer than 14 digits;
        imei = (imei != null && imei.length() > 14) ? imei.substring(0, 14) : imei;
        List<?> response = new ArrayList<>();
        List<?> historyResponse = new ArrayList<>();
        logger.info("get Table [" + tableName + "] details with imei [" + imei + "] ");

        if (imei != null && !imei.isEmpty() && tableName != null) {
            switch (tableName) {
                case Tags.black_list:
                    response = getBlackListRecord(imei);
                    historyResponse = getBlackListHistoryRecord(imei);

                    break;
                case Tags.black_list_his:
                    historyResponse = getBlackListHistoryRecord(imei);
                    break;
                case Tags.grey_list:
                    response = getGreyListRecord(imei);
                    historyResponse = getGreyListHistoryRecord(imei);
                    break;
                case Tags.grey_list_his:
                    historyResponse = getGreyListHistoryRecord(imei);
                    break;
                case Tags.active_imei_with_different_msisdn:
                    response = getActiveImeiDifferentMsisdnRecord(imei);
                    historyResponse = Collections.EMPTY_LIST;
                    break;
                case Tags.active_imei_with_different_imsi:
                    response = getActiveImeiDifferentImsiRecord(imei);
                    historyResponse = Collections.EMPTY_LIST;
                    break;
                case Tags.exception_list:
                    response = getExceptionListDataRecord(imei);
                    historyResponse = exceptionListHistoryRecord(imei);
                    break;
                case Tags.exception_list_his:
                    historyResponse = exceptionListHistoryRecord(imei);
                    break;
                case Tags.gdce_data:
                    response = getGdceDataRecord(imei);
                    historyResponse = Collections.EMPTY_LIST;
                    break;
                case Tags.trc_local_manufactured_device_data:
                    response = getLocalManufacturedRecord(imei);
                    historyResponse = Collections.EMPTY_LIST;
                    break;
                case Tags.imei_pair_detail:
                    response = getPairedTableDeviceData(imei);
                    historyResponse = getImeiPairDetailHistoryRecord(imei);
                    break;
                case Tags.imei_pair_detail_his:
                    historyResponse = getImeiPairDetailHistoryRecord(imei);
                    break;
                case Tags.national_whitelist:
                    response = getNationalWhiteListTableRecord(imei);
                    historyResponse = Collections.EMPTY_LIST;
                    break;
                case Tags.eirs_invalid_imei:
                    response = getInvalidImeiTableRecord(imei);
                    historyResponse = Collections.EMPTY_LIST;
                    break;
                case Tags.duplicate_device_detail:
                    response = getDuplicateDeviceTableRecord(imei);
                    historyResponse = getDuplicateDeviceDetailHistoryRecord(imei);
                    break;
                case Tags.duplicate_device_detail_his:
                    historyResponse = getDuplicateDeviceDetailHistoryRecord(imei);
                    break;
                case Tags.lost_device_detail:
                    response = getLostDeviceDataRecord(imei);
                    historyResponse = getLostDeviceDetailHistoryRecord(imei);
                    break;
                case Tags.lost_device_detail_his:
                    historyResponse = getLostDeviceDetailHistoryRecord(imei);
                    break;
            }
        }
        SearchIMEITableResponse searchIMEITableResponse = new SearchIMEITableResponse();
        searchIMEITableResponse.setTableResponse(response);
        searchIMEITableResponse.setHistoryTableResponse(historyResponse);
        return ResponseEntity.ok(searchIMEITableResponse);
    }

    public List<?> getBlackListRecord(String imei) {
        List<BlackList> response = new ArrayList<>();
        response = blackListRepo.findByImei(imei);
        if (!response.isEmpty()) {
            logger.info("[" + Tags.black_list + "]Table response with IMEI [" + imei + "] " + response);
            return response;
        }
        return Collections.EMPTY_LIST;
    }

    public List<?> getGreyListRecord(String imei) {
        List<GreyList> response = new ArrayList<>();
        response = greyListRepo.findByImei(imei);
        if (!response.isEmpty()) {
            logger.info("[" + Tags.grey_list + "] Table response with IMEI [" + imei + "] " + response);
            return response;
        }
        return Collections.EMPTY_LIST;
    }


    public List<?> getActiveImeiDifferentMsisdnRecord(String imei) {
        List<ActiveImeiWithDifferentMsisdn> response = new ArrayList<>();
        response = activeImeiWithDifferentMsisdnRepo.findByImei(imei);
        if (!response.isEmpty()) {
            logger.info("[" + Tags.active_imei_with_different_msisdn + "] Table response with IMEI [" + imei + "] " + response);
            return response;
        }
        return Collections.EMPTY_LIST;
    }

    public List<?> getActiveImeiDifferentImsiRecord(String imei) {
        List<ActiveIMEIWithDifferentImsi> response = new ArrayList<>();
        response = activeIMEIWithDifferentImsiRepo.findByImei(imei);
        if (!response.isEmpty()) {
            logger.info("[" + Tags.active_imei_with_different_imsi + "] Table response with IMEI [" + imei + "] " + response);
            return response;
        }
        return Collections.EMPTY_LIST;
    }

    public List<?> getExceptionListDataRecord(String imei) {
        List<ExceptionList> response = new ArrayList<>();
        response = exceptionListRepo.findByImei(imei);
        if (!response.isEmpty()) {
            logger.info("[" + Tags.exception_list + "] Table response with IMEI [" + imei + "] " + response);
            return response;
        }
        return Collections.EMPTY_LIST;
    }

    public List<?> getGdceDataRecord(String imei) {
        List<GdceData> response = new ArrayList<>();
        response = gdceDataRepo.findByImei(imei);
        if (!response.isEmpty()) {
            logger.info("[" + Tags.gdce_data + "] Table response with IMEI [" + imei + "] " + response);
            return response;
        }
        return Collections.EMPTY_LIST;
    }

    public List<?> getLocalManufacturedRecord(String imei) {
        List<TrcLocalManufacturedDeviceData> response = new ArrayList<>();
        response = trcLocalManufacturedDeviceDataRepo.findByImei(imei);
        if (!response.isEmpty()) {
            logger.info("[" + Tags.trc_local_manufactured_device_data + "] Table response with IMEI [" + imei + "] " + response);
            return response;
        }
        return Collections.EMPTY_LIST;
    }

    public List<?> getPairedTableDeviceData(String imei) {
        List<ImeiPairDetail> response = new ArrayList<>();
        response = imeiPairDetailRepo.findByImei(imei);
        if (!response.isEmpty()) {

            logger.info("[" + Tags.imei_pair_detail + "] Table response with IMEI [" + imei + "] " + response);
            return response;
        }
        return Collections.EMPTY_LIST;
    }

    public List<?> getNationalWhiteListTableRecord(String imei) {
        List<NationalWhiteList> response = new ArrayList<>();
       // List<SystemConfigListDb> interp = null;
        List<SystemConfigListDb> gdceImeiInterp = new ArrayList<>();
        List<SystemConfigListDb> trcImeiInterp = new ArrayList<>();
        List<SystemConfigListDb> validityFlagInterp = new ArrayList<>();

        response = nationalWhiteListRepo.findByImei(imei);
        if (!response.isEmpty()) {
            logger.info("[" + Tags.national_whitelist + "] Table response on basis of IMEI [" + imei + "] " + response);
            gdceImeiInterp = systemConfigListRepository.findByTag("NWL_GDCE_INTERP");
            trcImeiInterp = systemConfigListRepository.findByTag("NWL_TRC_STATUS_INTERP");
            validityFlagInterp = systemConfigListRepository.findByTag("NWL_VALIDITY_FLAG_INTERP");
            logger.info("GDCE Status Interp {} ", gdceImeiInterp);
            logger.info("TRC IMEI Status Interp {} ", trcImeiInterp);
            logger.info("Validity Flag Status Interp {} ", validityFlagInterp);
            //get Interpretation correspondence to status
            for (NationalWhiteList nationalWhiteList : response) {
                Integer gdceImeiStatus = nationalWhiteList.getGdceImeiStatus();
                Integer trcImeiStatus = nationalWhiteList.getTrcImeiStatus();
                Integer validityFlag = nationalWhiteList.getValidityFlag();
                // Set GdceStatusInterp
                for (SystemConfigListDb systemConfigListDb : gdceImeiInterp) {
                    if (gdceImeiStatus.equals(systemConfigListDb.getValue())) {
                        nationalWhiteList.setGdceStatusInterp(systemConfigListDb.getInterpretation());
                        break;
                    }
                }

                // Set TrcImeiStatusInterp
                for (SystemConfigListDb systemConfigListDb : trcImeiInterp) {
                    if (trcImeiStatus.equals(systemConfigListDb.getValue())) {
                        nationalWhiteList.setTrcImeiStatusInterp(systemConfigListDb.getInterpretation());
                        break;
                    }
                }

                // Set validityFlagInterp
                for (SystemConfigListDb systemConfigListDb : validityFlagInterp) {
                    if (validityFlag.equals(systemConfigListDb.getValue())) {
                        nationalWhiteList.setValidityFlagInterp(systemConfigListDb.getInterpretation());
                        break;
                    }
                }
            }
            return response;
        }
        return Collections.EMPTY_LIST;

    }

    public List<?> getInvalidImeiTableRecord(String imei) {
        List<EirsInvalidImei> response = new ArrayList<>();
        response = invalidImeiRepo.findByImei(imei);
        if (!response.isEmpty()) {
            logger.info("[" + Tags.eirs_invalid_imei + "] Table response with IMEI [" + imei + "] " + response);
            return response;
        }
        return Collections.EMPTY_LIST;

    }

    public List<?> getDuplicateDeviceTableRecord(String imei) {
        List<DuplicateDeviceDetail> response = new ArrayList<>();
        response = duplicateDeviceRepository.findByImei(imei);
        if (!response.isEmpty()) {
            logger.info("[" + Tags.duplicate_device_detail + "] Table response with IMEI [" + imei + "] " + response);
            return response;
        }
        return Collections.EMPTY_LIST;

    }

    public List<?> getDuplicateDeviceDetailHistoryRecord(String imei) {
        List<DuplicateDeviceDetailHis> response = new ArrayList<>();
        response = duplicateDeviceDetailHisRepository.findByImei(imei);
        if (!response.isEmpty()) {
            logger.info("[" + Tags.duplicate_device_detail_his + "] Table response with IMEI [" + imei + "] " + response);
            return response;
        }
        return Collections.EMPTY_LIST;
    }

    public List<?> exceptionListHistoryRecord(String imei) {
        List<ExceptionListHis> response = new ArrayList<>();
        List<SystemConfigListDb> operationInterp = new ArrayList<>();
        response = exceptionListHistoryRepo.findByImei(imei);
        if (!response.isEmpty()) {
            logger.info("[" + Tags.exception_list_his + "] Table response with IMEI [" + imei + "] " + response);
            operationInterp = systemConfigListRepository.findByTag("HISTORY_OPERATION");
            logger.info("Operation Interp {} ", operationInterp);
            //get Interpretation correspondence to status
            for (ExceptionListHis exceptionListHis : response) {
                Integer operation = exceptionListHis.getOperation();
                // Set Operation Interp
                for (SystemConfigListDb systemConfigListDb : operationInterp) {
                    if (operation.equals(systemConfigListDb.getValue())) {
                        exceptionListHis.setOperationInterp(systemConfigListDb.getInterpretation());
                        break;
                    }
                }
            }
            return response;
        }
        return Collections.EMPTY_LIST;
    }

    public List<?> getLostDeviceDataRecord(String imei) {
        List<LostDeviceDetail> response = new ArrayList<>();
        response = lostDeviceDetailRepo.findByImeiIgnoreCase(imei);
        if (!response.isEmpty()) {
            logger.info("[" + Tags.lost_device_detail + "] Table response with IMEI [" + imei + "] " + response);
            return response;
        }
        return Collections.EMPTY_LIST;
    }

    public List<?> getGreyListHistoryRecord(String imei) {
        List<GreyListDeviceHistory> response = new ArrayList<>();
        List<SystemConfigListDb> operationInterp = new ArrayList<>();
        response = greyListHistoryRepo.findByImei(imei);
        if (!response.isEmpty()) {
            logger.info("[" + Tags.grey_list_his + "] Table response with IMEI [" + imei + "] " + response);
            operationInterp = systemConfigListRepository.findByTag("HISTORY_OPERATION");
            logger.info("Operation Interp {} ", operationInterp);
            //get Interpretation correspondence to status
            for (GreyListDeviceHistory greyListDeviceHistory : response) {
                Integer operation = greyListDeviceHistory.getOperation();
                // Set Operation Interp
                for (SystemConfigListDb systemConfigListDb : operationInterp) {
                    if (operation.equals(systemConfigListDb.getValue())) {
                        greyListDeviceHistory.setOperationInterp(systemConfigListDb.getInterpretation());
                        break;
                    }
                }
            }
            return response;
        }
        return Collections.EMPTY_LIST;
    }


    public List<?> getBlackListHistoryRecord(String imei) {
        List<BlackListHistory> response = new ArrayList<>();
        List<SystemConfigListDb> operationInterp = new ArrayList<>();
        response = blackListHistoryRepo.findByImei(imei);
        if (!response.isEmpty()) {
            logger.info("[" + Tags.black_list_his + "] Table response with IMEI [" + imei + "] " + response);
            operationInterp = systemConfigListRepository.findByTag("HISTORY_OPERATION");
            logger.info("Operation Interp {} ", operationInterp);
            //get Interpretation correspondence to status
            for (BlackListHistory blackListHistory : response) {
                Integer operation = blackListHistory.getOperation();
                // Set Operation Interp
                for (SystemConfigListDb systemConfigListDb : operationInterp) {
                    if (operation.equals(systemConfigListDb.getValue())) {
                        blackListHistory.setOperationInterp(systemConfigListDb.getInterpretation());
                        break;
                    }
                }
            }
            return response;
        }
        return Collections.EMPTY_LIST;
    }


    public List<?> getImeiPairDetailHistoryRecord(String imei) {
        List<ImeiPairDetailHistory> response = new ArrayList<>();
        response = imeiPairDetailHistoryRepo.findByImei(imei);
        if (!response.isEmpty()) {
            logger.info("[" + Tags.imei_pair_detail_his + "] Table response with IMEI [" + imei + "] " + response);
            return response;
        }
        return Collections.EMPTY_LIST;
    }

    public List<?> getLostDeviceDetailHistoryRecord(String imei) {
        List<LostDeviceDetailHistory> response = new ArrayList<>();
        response = lostDeviceDetailHistoryRepo.findByImei(imei);
        if (!response.isEmpty()) {
            logger.info("[" + Tags.lost_device_detail_his + "] Table response with IMEI [" + imei + "] " + response);
            return response;
        }
        return Collections.EMPTY_LIST;
    }


    //lostDeviceDetailHistoryRepo

    public List<SearchIMEIResponse> getBlackListHistorytData(String imei, String msisdn) {
        List<SearchIMEIResponse> searchIMEIResponseList = new ArrayList<>();
        SearchIMEIResponse searchIMEIResponse = new SearchIMEIResponse();
        List<BlackListHistory> response = new ArrayList<>();
        searchIMEIResponse.setTableName(Tags.black_list_his);
        ActiveMsisdnList activeMsisdnList;
        String imsi = null;
        if (msisdn == null || msisdn.isBlank()) {
            response = blackListHistoryRepo.findTop1ByImei(imei);
            logger.info("Response of IMEI [" + imei + "] recieved in black_list_device_his" + response);
        } else {
            activeMsisdnList = activeMsisdnListRepository.findByMsisdn(msisdn);
            if (activeMsisdnList != null) {
                imsi = activeMsisdnList.getImsi();
            }
            response = blackListHistoryRepo.findByImeiAndImsi(imei, imsi);
            logger.info("Response of IMEI [" + imei + "]  and IMSI [" + imsi + "] recieved in black_list_device_his" + response);
        }
        if (!response.isEmpty()) {
            for (BlackListHistory blackListHistoryResponses : response) {
                SearchIMEIResponse imeiResponse = new SearchIMEIResponse();
                imeiResponse.setTableName(Tags.black_list_his);
                imeiResponse.setCreatedOn(blackListHistoryResponses.getCreatedOn().format(dtf));
                imeiResponse.setImei(blackListHistoryResponses.getImei());
                imeiResponse.setId(blackListHistoryResponses.getId());
                searchIMEIResponseList.add(imeiResponse);
            }

            logger.info("Multiple SearchIMEIResponses found for IMEI [" + imei + "] or MSISDN [" + msisdn + "] in black_list_device_his");
            return searchIMEIResponseList;
        }
        searchIMEIResponseList.add(searchIMEIResponse);
        return searchIMEIResponseList;

    }

    public List<SearchIMEIResponse> getGreyListHistorytData(String imei, String msisdn) {
        List<SearchIMEIResponse> searchIMEIResponseList = new ArrayList<>();
        SearchIMEIResponse searchIMEIResponse = new SearchIMEIResponse();
        List<GreyListDeviceHistory> response = new ArrayList<>();
        searchIMEIResponse.setTableName(Tags.grey_list_his);
        ActiveMsisdnList activeMsisdnList;
        String imsi = null;
        if (msisdn == null || msisdn.isBlank()) {
            response = greyListHistoryRepo.findTop1ByImei(imei);
            logger.info("Response of IMEI [" + imei + "] recieved in grey_list_device_his" + response);
        } else {
            activeMsisdnList = activeMsisdnListRepository.findByMsisdn(msisdn);
            if (activeMsisdnList != null) {
                imsi = activeMsisdnList.getImsi();
            }
            response = greyListHistoryRepo.findByImeiAndImsi(imei, imsi);
            logger.info("Response of IMEI [" + imei + "]  and IMSI [" + imsi + "] recieved in grey_list_device_his" + response);
        }
        if (!response.isEmpty()) {
            for (GreyListDeviceHistory greyListHistoryResponses : response) {
                SearchIMEIResponse imeiResponse = new SearchIMEIResponse();
                imeiResponse.setTableName(Tags.grey_list_his);
                imeiResponse.setCreatedOn(greyListHistoryResponses.getCreatedOn().format(dtf));
                imeiResponse.setImei(greyListHistoryResponses.getImei());
                imeiResponse.setId(greyListHistoryResponses.getId());
                searchIMEIResponseList.add(imeiResponse);
            }

            logger.info("Multiple SearchIMEIResponses found for IMEI [" + imei + "] or MSISDN [" + msisdn + "] in grey_list_device_his");
            return searchIMEIResponseList;
        }
        searchIMEIResponseList.add(searchIMEIResponse);
        return searchIMEIResponseList;

    }

    public List<SearchIMEIResponse> getPairDetailHistorytData(String imei, String msisdn) {
        List<SearchIMEIResponse> searchIMEIResponseList = new ArrayList<>();
        SearchIMEIResponse searchIMEIResponse = new SearchIMEIResponse();
        List<ImeiPairDetailHistory> response = new ArrayList<>();
        searchIMEIResponse.setTableName(Tags.imei_pair_detail_his);
        ActiveMsisdnList activeMsisdnList;
        String imsi = null;
        if (msisdn == null || msisdn.isBlank()) {
            response = imeiPairDetailHistoryRepo.findTop1ByImei(imei);
            logger.info("Response of IMEI [" + imei + "] recieved in imei_pair_detail_his" + response);
        } else {
            activeMsisdnList = activeMsisdnListRepository.findByMsisdn(msisdn);
            if (activeMsisdnList != null) {
                imsi = activeMsisdnList.getImsi();
            }
            response = imeiPairDetailHistoryRepo.findByImeiAndImsi(imei, imsi);
            logger.info("Response of IMEI [" + imei + "]  and IMSI [" + imsi + "] recieved in imei_pair_detail_his" + response);
        }
        if (!response.isEmpty()) {
            for (ImeiPairDetailHistory pairListHistoryResponses : response) {
                SearchIMEIResponse imeiResponse = new SearchIMEIResponse();
                imeiResponse.setTableName(Tags.imei_pair_detail_his);
                imeiResponse.setCreatedOn(pairListHistoryResponses.getCreatedOn().format(dtf));
                imeiResponse.setImei(pairListHistoryResponses.getImei());
                searchIMEIResponseList.add(imeiResponse);
            }

            logger.info("Multiple SearchIMEIResponses found for IMEI [" + imei + "] or MSISDN [" + msisdn + "] in imei_pair_detail_his");
            return searchIMEIResponseList;
        }
        searchIMEIResponseList.add(searchIMEIResponse);
        return searchIMEIResponseList;

    }

    public List<SearchIMEIResponse> getLostDeviceHistoryData(String imei) {
        List<SearchIMEIResponse> searchIMEIResponseList = new ArrayList<>();
        SearchIMEIResponse searchIMEIResponse = new SearchIMEIResponse();
        List<LostDeviceDetailHistory> response = new ArrayList<>();
        searchIMEIResponse.setTableName(Tags.lost_device_detail_his);
        response = lostDeviceDetailHistoryRepo.findTop1ByImei(imei); //Only IMEI is available in this table. no IMSI/MSISDN
        logger.info("Response of IMEI [" + imei + "] in lost_device_detail_his " + response);
        if (!response.isEmpty()) {
            for (LostDeviceDetailHistory lostDeviceDetailHistoryResponse : response) {
                SearchIMEIResponse imeiResponse = new SearchIMEIResponse();
                imeiResponse.setTableName(Tags.lost_device_detail_his);
                imeiResponse.setCreatedOn(lostDeviceDetailHistoryResponse.getCreatedOn().format(dtf));
                imeiResponse.setImei(lostDeviceDetailHistoryResponse.getImei());
                searchIMEIResponseList.add(imeiResponse);
            }
            logger.info("Multiple SearchIMEIResponses found for IMEI [" + imei + "]  in lost_device_detail_his");
            return searchIMEIResponseList;
        }
        searchIMEIResponseList.add(searchIMEIResponse);
        return searchIMEIResponseList;
    }

    public List<SearchIMEIResponse> getDuplicateHistorytData(String imei, String msisdn) {
        List<SearchIMEIResponse> searchIMEIResponseList = new ArrayList<>();
        SearchIMEIResponse searchIMEIResponse = new SearchIMEIResponse();
        List<DuplicateDeviceDetailHis> response = new ArrayList<>();
        searchIMEIResponse.setTableName(Tags.duplicate_device_detail_his);
        ActiveMsisdnList activeMsisdnList;
        if (msisdn == null || msisdn.isBlank()) {
            response = duplicateDeviceDetailHisRepository.findTop1ByImei(imei);
            logger.info("Response of IMEI [" + imei + "] recieved in Duplicate History" + response);
        }
        if (!response.isEmpty()) {
            for (DuplicateDeviceDetailHis duplicateListHistoryResponses : response) {
                SearchIMEIResponse imeiResponse = new SearchIMEIResponse();
                imeiResponse.setTableName(Tags.duplicate_device_detail_his);
                imeiResponse.setCreatedOn(duplicateListHistoryResponses.getCreatedOn().format(dtf));
                imeiResponse.setImei(duplicateListHistoryResponses.getImei());
                searchIMEIResponseList.add(imeiResponse);
            }
            logger.info("Multiple SearchIMEIResponses found for IMEI [" + imei + "] in duplicate_device_detail_his");
            return searchIMEIResponseList;
        }
        searchIMEIResponseList.add(searchIMEIResponse);
        return searchIMEIResponseList;

    }

    public List<SearchIMEIResponse> getExceptionListHistorytData(String imei, String msisdn) {
        List<SearchIMEIResponse> searchIMEIResponseList = new ArrayList<>();
        SearchIMEIResponse searchIMEIResponse = new SearchIMEIResponse();
        List<ExceptionListHis> response = new ArrayList<>();
        searchIMEIResponse.setTableName(Tags.exception_list_his);
        ActiveMsisdnList activeMsisdnList;
        if (msisdn == null || msisdn.isBlank()) {
            response = exceptionListHistoryRepo.findTop1ByImei(imei);
            logger.info("Response of IMEI [" + imei + "] recieved in Exception List History" + response);
        }
        if (!response.isEmpty()) {
            for (ExceptionListHis exceptionListHistoryResponses : response) {
                SearchIMEIResponse imeiResponse = new SearchIMEIResponse();
                imeiResponse.setTableName(Tags.exception_list_his);
                imeiResponse.setCreatedOn(exceptionListHistoryResponses.getCreatedOn().format(dtf));
                imeiResponse.setImei(exceptionListHistoryResponses.getImei());
                imeiResponse.setId(exceptionListHistoryResponses.getId());
                searchIMEIResponseList.add(imeiResponse);
            }
            logger.info("Multiple SearchIMEIResponses found for IMEI [" + imei + "] in Exception List");
            return searchIMEIResponseList;
        }
        searchIMEIResponseList.add(searchIMEIResponse);
        return searchIMEIResponseList;

    }
}
