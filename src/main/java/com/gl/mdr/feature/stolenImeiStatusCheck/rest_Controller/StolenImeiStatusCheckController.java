package com.gl.mdr.feature.stolenImeiStatusCheck.rest_Controller;

import com.gl.mdr.feature.stolenImeiStatusCheck.export.StolenImeiStatusCheckExport;
import com.gl.mdr.feature.stolenImeiStatusCheck.model.ResponseModel;
import com.gl.mdr.feature.stolenImeiStatusCheck.model.StolenImeiStatusCheckRequest;
import com.gl.mdr.feature.stolenImeiStatusCheck.service.StolenImeiStatusCheckService;
import com.gl.mdr.model.app.SearchImeiByPoliceMgmt;
import com.gl.mdr.model.app.StolenLostModel;
import com.gl.mdr.model.file.FileDetails;
import com.gl.mdr.model.generic.GenricResponse;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "get list of stolen reported devices", response = SearchImeiByPoliceMgmt.class)
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

    @ApiOperation(value = "Export stolen devices", response = SearchImeiByPoliceMgmt.class)
    @PostMapping("/exportStolenData")
    public MappingJacksonValue exportData(@RequestBody StolenImeiStatusCheckRequest filterRequest) {
        MappingJacksonValue mapping = null;
        logger.info("Stolen export request:[{}]", filterRequest.toString());
        FileDetails fileDetails = stolenImeiStatusCheckExport.exportData(filterRequest);
        mapping = new MappingJacksonValue(fileDetails);
        logger.info("Stolen export response:[{}]", fileDetails.toString());
        return mapping;
    }

    @ApiOperation(value="View Detailed info of stolen device")
    @PostMapping("/viewStolenImeiData")
    public ResponseEntity<?> getStolenImeiData(@RequestBody StolenImeiStatusCheckRequest filterRequest){
        return stolenImeiStatusCheckService.viewStolenImeiDevice(filterRequest);
    }

    @ApiOperation(value="get Distinct Status from search_imei_by_police_mgmt")
    @GetMapping("/initiateRecoveryDistinctStatus")
    public ResponseEntity<?> getDistinctStatus(){
        List<String> list = stolenImeiStatusCheckService.getDistinctStatusService();
        return new ResponseEntity<>(list, HttpStatus.OK);

    }

    @ApiOperation(value="stolen IMEI search recovery")
    @PostMapping("/upload")
    public ResponseModel fileUpload(@RequestBody SearchImeiByPoliceMgmt filterRequest) {
        logger.info("Stolen Check IMEI payload for file upload [{}]", filterRequest);
        return stolenImeiStatusCheckService.save(filterRequest);
    }

    @ApiOperation(value="Initiate single recovery")
    @PostMapping("/initiateRecovery-single")
    public ResponseEntity<?> initiateSingleRecovery(@RequestBody StolenLostModel filterRequest) {
        logger.info("initiate-Single-Recovery payload [{}]", filterRequest);
        return stolenImeiStatusCheckService.initiateRecoveryService(filterRequest);
    }

    @ApiOperation(value="Initiate Bulk recovery")
    @PostMapping("/initiateRecovery-bulk")
    public ResponseEntity<?> initiateBulkRecovery(@RequestBody StolenLostModel filterRequest) {
        logger.info("initiate-Bulk-Recovery payload [{}]", filterRequest);
        return stolenImeiStatusCheckService.initiateBulkRecoveryService(filterRequest);
    }

    @ApiOperation(value = "Initiate Recovery verify OTP", response = GenricResponse.class)
    @RequestMapping(path = "/initiate-recovery/verify/otp", method = RequestMethod.POST)
    public GenricResponse verifyOtp(@RequestBody StolenLostModel filterRequest){
        logger.info("OTP Request = " + filterRequest.toString());
        GenricResponse genricResponse =	stolenImeiStatusCheckService.verifyOTP(filterRequest.getRequestId(),filterRequest.getOtp(),filterRequest.getLanguage());
        return genricResponse;
    }

    @ApiOperation(value = "Initiate Single Recovery form", response = GenricResponse.class)
    @PostMapping("/initiateRecovery")
    public ResponseEntity<?> initiateRecoveryForm(@RequestBody StolenLostModel filterRequest) {
        logger.info("initiate-Recovery payload [{}]", filterRequest);
        return stolenImeiStatusCheckService.initiateRecoveryFormService(filterRequest);
    }



    @ApiOperation(value="View Recovered data")
    @PostMapping("/viewRecoveredImeiData")
    public ResponseEntity<?> getRecoveredImeiData(@RequestBody StolenImeiStatusCheckRequest filterRequest){
        return stolenImeiStatusCheckService.getRecoveredImeiDataService(filterRequest);
    }

    @ApiOperation(value = "Initiate Recovery resend OTP", response = GenricResponse.class)
    @RequestMapping(path = "/initiate-recovery/resendOTP", method = RequestMethod.POST)
    public GenricResponse resendOTP(@RequestBody StolenLostModel filterRequest ) {

        logger.info("verify OTP Request , requestID= " + filterRequest.getRequestId()+" , otp ="+filterRequest.getOtp());
        GenricResponse genricResponse = stolenImeiStatusCheckService.resendOTP(filterRequest.getRequestId(),filterRequest.getLanguage());
        logger.info("verify OTP Response = " + genricResponse);

        return genricResponse;
    }



    @ApiOperation(value="Send Notification to contact number")
    @PostMapping("/initiateRecovery-sendNotification")
    public ResponseEntity<?> sendNotification(@RequestBody StolenImeiStatusCheckRequest filterRequest){
        return stolenImeiStatusCheckService.sendNotification(filterRequest);
    }
}
