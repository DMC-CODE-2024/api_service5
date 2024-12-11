package com.gl.mdr.feature.stolenImeiStatusCheck.upload;

import com.gl.mdr.feature.stolenImeiStatusCheck.model.ResponseModel;
import com.gl.mdr.model.app.SearchImeiByPoliceMgmt;
import com.gl.mdr.model.app.StolenLostModel;
import com.gl.mdr.model.app.WebActionDb;
import com.gl.mdr.repo.app.SearchImeiByPoliceMgmtRepository;
import com.gl.mdr.repo.app.StolenPoliceVerificationDevicesRepository;
import com.gl.mdr.repo.app.WebActionDbRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EIRSStolenCheckIMEIFileUploadPayload<T> {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private SearchImeiByPoliceMgmtRepository searchImeiByPoliceMgmtRepository;
    private WebActionDbRepository webActionDbRepository;
    private StolenPoliceVerificationDevicesRepository lostDeviceManagementRepository;


    public EIRSStolenCheckIMEIFileUploadPayload(SearchImeiByPoliceMgmtRepository searchImeiByPoliceMgmtRepository, WebActionDbRepository webActionDbRepository,StolenPoliceVerificationDevicesRepository lostDeviceManagementRepository) {
        this.searchImeiByPoliceMgmtRepository = searchImeiByPoliceMgmtRepository;
        this.webActionDbRepository = webActionDbRepository;
        this.lostDeviceManagementRepository=lostDeviceManagementRepository;
    }

    @Transactional(rollbackOn = {SQLException.class})
    public <T extends SearchImeiByPoliceMgmt> ResponseModel upload(T t) {
        ResponseModel responseModel = new ResponseModel();
        if (Objects.nonNull(t)) {
            logger.info("EIRSStolenCheckIMEI payload[{}]", t);
            searchImeiByPoliceMgmtRepository.save(t);
            responseModel.setData("Successfully Saved!").setStatusCode(HttpStatus.OK.value());
        }
        return responseModel;
    }

    public void saveWebActionDBEntity(SearchImeiByPoliceMgmt filterRequest) {
        Optional<String> optional = Optional.ofNullable(filterRequest.getTransactionId());
        if (optional.isPresent()) {
            String txnID = optional.get();
            logger.info("txnID [{}]", txnID);
            WebActionDb webActionDBEntity = new WebActionDb();
            webActionDBEntity.setState(1);
            webActionDBEntity.setFeature("MOI");
            webActionDBEntity.setSub_feature("IMEI_SEARCH_RECOVERY");
            webActionDBEntity.setTxnId(txnID);
            logger.info("WebActionDb payload[{}]", webActionDBEntity);
            webActionDbRepository.save(webActionDBEntity);
        }
    }



    public void saveInitiateWebActionDBEntity(StolenLostModel filterRequest) {
        Optional<String> optional = Optional.ofNullable(filterRequest.getRequestId());
        if (optional.isPresent()) {
            String txnID = optional.get();
            logger.info("txnID [{}]", txnID);
            WebActionDb webActionDBEntity = new WebActionDb();
            webActionDBEntity.setState(1);
            webActionDBEntity.setFeature("MOI");
            webActionDBEntity.setSub_feature("RECOVER");
            webActionDBEntity.setTxnId(filterRequest.getRequestId());
            logger.info("WebActionDb payload[{}]", webActionDBEntity);
            webActionDbRepository.save(webActionDBEntity);
        }
    }

    @Transactional(rollbackOn = {SQLException.class})
    public <T extends StolenLostModel> ResponseModel initiateRecovery(T lostDeviceManagement) {
        ResponseModel responseModel = new ResponseModel();
        if (lostDeviceManagement != null) {
            logger.info("Stolen Initiate Recovery payload: {}", lostDeviceManagement);
            lostDeviceManagementRepository.save(lostDeviceManagement);
            responseModel.setData("Successfully Saved in lost_device_mgmt!").setStatusCode(HttpStatus.OK.value());
        } else {
            responseModel.setData("Failed to save: payload is null").setStatusCode(HttpStatus.BAD_REQUEST.value());
        }
        return responseModel;
    }

   /* public List<String> getImeisFromUploadedFiles(String uploadedBulkFilePath) {
        List<String> imeis = new ArrayList<>();
        // Logic to read IMEI files from the directory
        File folder = new File(uploadedBulkFilePath);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".csv")) { // Assuming IMEIs are in .csv files
                    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            imeis.add(line.trim()); // Add each IMEI to the list
                        }
                    } catch (IOException e) {
                        logger.error("Error reading file {}: {}", file.getName(), e.getMessage());
                    }
                }
            }
        }

        return imeis;
    }*/
}
