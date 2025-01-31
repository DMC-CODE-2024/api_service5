package com.gl.mdr.feature.stolenImeiStatusCheck.rest_Controller;

import com.gl.mdr.feature.stolenImeiStatusCheck.export.StolenImeiStatusCheckExport;
import com.gl.mdr.feature.stolenImeiStatusCheck.model.ResponseModel;
import com.gl.mdr.feature.stolenImeiStatusCheck.model.StolenImeiStatusCheckRequest;
import com.gl.mdr.feature.stolenImeiStatusCheck.service.StolenImeiStatusCheckService;
import com.gl.mdr.model.app.SearchImeiByPoliceMgmt;
import com.gl.mdr.model.app.StolenLostModel;
import com.gl.mdr.model.file.FileDetails;
import com.gl.mdr.model.generic.GenricResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StolenImeiStatusCheckController {
    private static final Logger logger = LogManager.getLogger(StolenImeiStatusCheckController.class);

    @Autowired
    StolenImeiStatusCheckService stolenImeiStatusCheckService;

    @Autowired
    StolenImeiStatusCheckExport stolenImeiStatusCheckExport;

    @Tag(name = "Stolen Status Check IMEI", description = "MOI Module API")
    @Operation(
            summary = "Fetch all record",
            description = "Fetches all record entities and their data from data source")
    @PostMapping("/getStolenDeviceDetails")
    public MappingJacksonValue getDuplicateDevicesDetails(@RequestBody StolenImeiStatusCheckRequest filterRequest,
                                                          @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
                                                          @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        MappingJacksonValue mapping = null;
        logger.info("Stolen device filter request:[{}]", filterRequest.toString());
        Page<SearchImeiByPoliceMgmt> stolenDataResponse =  stolenImeiStatusCheckService.getStolenDevicesDetails(filterRequest, pageNo, pageSize,"View");
        mapping = new MappingJacksonValue(stolenDataResponse);
        return mapping;
    }

    @Tag(name = "Stolen Status Check IMEI", description = "MOI Module API")
    @Operation(
            summary = "Export csv file",
            description = "Fetches device entities and their associated data from the data source, with the number of records limited to a configurable parameter, up to a maximum of 50,000. Subsequently, generate a .csv file containing the retrieved data.")
    @PostMapping("/exportStolenData")
    public MappingJacksonValue exportData(@RequestBody StolenImeiStatusCheckRequest filterRequest) {
        MappingJacksonValue mapping = null;
        logger.info("Stolen export request:[{}]", filterRequest.toString());
        FileDetails fileDetails = stolenImeiStatusCheckExport.exportData(filterRequest);
        mapping = new MappingJacksonValue(fileDetails);
        logger.info("Stolen export response:[{}]", fileDetails.toString());
        return mapping;
    }

    //@ApiOperation(value="View Detailed info of stolen device")
    @Tag(name = "Stolen Status Check IMEI", description = "MOI Module API")
    @Operation(
            summary = "Fetch record based on Transaction Id",
            description = "Fetches record based on Transaction Id from data source")
    @PostMapping("/viewStolenImeiData")
    public ResponseEntity<?> getStolenImeiData(@RequestBody StolenImeiStatusCheckRequest filterRequest){
        return stolenImeiStatusCheckService.viewStolenImeiDevice(filterRequest);
    }

    //@ApiOperation(value="get Distinct Status from search_imei_by_police_mgmt")
    @Tag(name = "Stolen Status Check IMEI", description = "MOI Module API")
    @Operation(
            summary = "Fetch distinct Status from the data source",
            description = "Fetch distinct values for the status based on the received request")
    @GetMapping("/initiateRecoveryDistinctStatus")
    public ResponseEntity<?> getDistinctStatus(){
        List<String> list = stolenImeiStatusCheckService.getDistinctStatusService();
        return new ResponseEntity<>(list, HttpStatus.OK);

    }

    @Tag(name = "Stolen Status Check IMEI", description = "MOI Module API")
    @Operation(
            summary = "Upload recovery request single/bulk",
            description = "Upload a recovery request when the device is found")
    @PostMapping("/upload")
    public ResponseModel fileUpload(@RequestBody SearchImeiByPoliceMgmt filterRequest) {
        logger.info("Stolen Check IMEI payload for file upload [{}]", filterRequest);
        return stolenImeiStatusCheckService.save(filterRequest);
    }

    @Tag(name = "Stolen Status Check IMEI", description = "MOI Module API")
    @Operation(
            summary = "Initiate recovery single",
            description = "Initiate a recovery request when the single device is found")
    @PostMapping("/initiateRecovery-single")
    public ResponseEntity<?> initiateSingleRecovery(@RequestBody StolenLostModel filterRequest) {
        logger.info("initiate-Single-Recovery payload [{}]", filterRequest);
        return stolenImeiStatusCheckService.initiateRecoveryService(filterRequest);
    }


    @Tag(name = "Stolen Status Check IMEI", description = "MOI Module API")
    @Operation(
            summary = "Initiate recovery bulk",
            description = "Initiate a recovery request when multiple devices are found.")
    @PostMapping("/initiateRecovery-bulk")
    public ResponseEntity<?> initiateBulkRecovery(@RequestBody StolenLostModel filterRequest) {
        logger.info("initiate-Bulk-Recovery payload [{}]", filterRequest);
        return stolenImeiStatusCheckService.initiateBulkRecoveryService(filterRequest);
    }


    @Tag(name = "Stolen Status Check IMEI", description = "MOI Module API")
    @Operation(
            summary = "Verify OTP request",
            description = "Verify the OTP when initiating a single or bulk recovery request")
    @RequestMapping(path = "/initiate-recovery/verify/otp", method = RequestMethod.POST)
    public GenricResponse verifyOtp(@RequestBody StolenLostModel filterRequest){
        logger.info("OTP Request = " + filterRequest.toString());
        GenricResponse genricResponse =	stolenImeiStatusCheckService.verifyOTP(filterRequest.getRequestId(),filterRequest.getOtp(),filterRequest.getLanguage());
        return genricResponse;
    }

    @Tag(name = "Stolen Status Check IMEI", description = "MOI Module API")
    @Operation(
            summary = "Submit recovery information",
            description = "Submit the recovery information for a single or bulk request once the device has been successfully recovered")
    @PostMapping("/initiateRecovery")
    public ResponseEntity<?> initiateRecoveryForm(@RequestBody StolenLostModel filterRequest) {
        logger.info("initiate-Recovery payload [{}]", filterRequest);
        return stolenImeiStatusCheckService.initiateRecoveryFormService(filterRequest);
    }

    @Tag(name = "Stolen Status Check IMEI", description = "MOI Module API")
    @Operation(
            summary = "View recovery information",
            description = "Retrieve the recovery information from the data source based on the request ID")
    @PostMapping("/viewRecoveredImeiData")
    public ResponseEntity<?> getRecoveredImeiData(@RequestBody StolenImeiStatusCheckRequest filterRequest){
        return stolenImeiStatusCheckService.getRecoveredImeiDataService(filterRequest);
    }

    @Tag(name = "Stolen Status Check IMEI", description = "MOI Module API")
    @Operation(
            summary = "Resend OTP",
            description = "Resend the OTP when initiating a recovery request for a single or bulk device")
    @RequestMapping(path = "/initiate-recovery/resendOTP", method = RequestMethod.POST)
    public GenricResponse resendOTP(@RequestBody StolenLostModel filterRequest ) {

        logger.info("verify OTP Request , requestID= " + filterRequest.getRequestId()+" , otp ="+filterRequest.getOtp());
        GenricResponse genricResponse = stolenImeiStatusCheckService.resendOTP(filterRequest.getRequestId(),filterRequest.getLanguage());
        logger.info("verify OTP Response = " + genricResponse);

        return genricResponse;
    }

    @Tag(name = "Stolen Status Check IMEI", description = "MOI Module API")
    @Operation(
            summary = "Send Notification",
            description = "Send a notification to the user associated with the recovered device")
    @PostMapping("/initiateRecovery-sendNotification")
    public ResponseEntity<?> sendNotification(@RequestBody StolenImeiStatusCheckRequest filterRequest){
        return stolenImeiStatusCheckService.sendNotification(filterRequest);
    }
}
