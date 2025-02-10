package com.gl.mdr.controller;


import com.gl.mdr.model.app.MDRModel;
import com.gl.mdr.model.app.OTPRequest;
import com.gl.mdr.model.app.StolenLostModel;
import com.gl.mdr.model.generic.GenricResponse;
import com.gl.mdr.service.impl.PoliceStolenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/police/")
public class PoliceStolenRequestController {

    private static final Logger logger = LogManager.getLogger(PoliceStolenRequestController.class);

    @Autowired
    com.gl.mdr.service.impl.LostStolenServiceImpl LostStolenServiceImpl;

    @Autowired
    PoliceStolenService lostStolenRepo;


    @Tag(name = "End user stolen", description = "End user stolen and recovery")
    @Operation(
            summary = "Save stolen request By police",description = "Save end user device  stolen request raise  by police user")
    @RequestMapping(path = "/lostStolen/create", method = RequestMethod.POST)
    public GenricResponse saveStolenDevice(@RequestBody StolenLostModel stolenLostModel) {

        logger.info("Lost stolen Request = " + stolenLostModel);
        GenricResponse genricResponse = lostStolenRepo.saveLostStolenDevice(stolenLostModel);
        logger.info("Lost stolen Response = " + genricResponse);
        return genricResponse;
    }

    @Tag(name = "End user stolen", description = "End user stolen and recovery")
    @Operation(
            summary = "OTP verification request",description = "OTP Verification request for device  stolen request")
    @RequestMapping(path = "/verifyOTP", method = RequestMethod.POST) public
    GenricResponse verifyOTP(@RequestBody OTPRequest otpRequest ) {
        logger.info("verify OTP Request , requestID= " +
                otpRequest.getRequestID()+" , otp ="+otpRequest.getOtp());
        GenricResponse genricResponse = lostStolenRepo.verifyOTP(otpRequest.getRequestID(),otpRequest.getOtp(),otpRequest.getRequestType(),otpRequest.getOldRequestID()); logger.info("verify OTP Response = " + genricResponse);
        LostStolenServiceImpl.audiTrail( otpRequest.getPublicIp(), otpRequest.getBrowser(), otpRequest.getRequestID(), "OTP-Verification",otpRequest.getUserAgent());
        return genricResponse;
    }

    @Tag(name = "End user stolen", description = "End user stolen and recovery")
    @Operation(
            summary = "OTP resend request",description = "OTP resend request for stolen and recovery")
    @RequestMapping(path = "/resendOTP", method = RequestMethod.POST)
    public GenricResponse resendOTP(@RequestBody OTPRequest otpRequest ) {

        logger.info("resend Request , requestID= " + otpRequest.getRequestID()+" , otp ="+otpRequest.getOtp());
        GenricResponse genricResponse = lostStolenRepo.resendOTP(otpRequest.getRequestID());
        logger.info("resend OTP Response = " + genricResponse);
        LostStolenServiceImpl.audiTrail( otpRequest.getPublicIp(), otpRequest.getBrowser(), otpRequest.getRequestID(), "Resend-OTP",otpRequest.getUserAgent());
        return genricResponse;
    }


    @Tag(name = "End user stolen", description = "End user stolen and recovery")
    @Operation(
            summary = "Save bulk stolen request By police",description = "Save end user bulk device  stolen request raise  by police user")
    @RequestMapping(path = "/lostStolen/bulkSave", method = RequestMethod.POST)
    public GenricResponse bulkSave(@RequestBody StolenLostModel stolenLostModel) {

        logger.info("Bulk Lost stolen Request = " + stolenLostModel);
        GenricResponse genricResponse = lostStolenRepo.saveBulkStolenDevice(stolenLostModel);
        logger.info("Bulk Lost stolen Response = " + genricResponse);
        return genricResponse;
    }

    @Tag(name = "End user stolen", description = "End user stolen and recovery")
    @Operation(
            summary = "Verify stolen request By police",description = "Verify device brand and model details")
    @PostMapping("/verifyDevice")
    public GenricResponse verifyDevice(@RequestBody StolenLostModel stolenLostModel) {
        String tac=	stolenLostModel.getImei1().substring(0, 8);
        MDRModel mdrModel=lostStolenRepo.tacValdation(tac);
        GenricResponse genricResponse=new GenricResponse();
        genricResponse.setData(mdrModel);
        genricResponse.setTag(tac);
        logger.info("Response = " + genricResponse);
        return genricResponse;
    }

    @Tag(name = "End user stolen", description = "End user stolen and recovery")
    @Operation(
            summary = "Verify stolen request By police",description = "Verify end user device  stolen request raise  by police user")
    @RequestMapping(path = "/approvePoliceRequest", method = RequestMethod.POST)
    public GenricResponse approvePoliceRequest(@RequestBody StolenLostModel stolenLostModel) {
        logger.info("approve request for request id = " + stolenLostModel.getRequestId());
        GenricResponse genricResponse=LostStolenServiceImpl.approvePoliceRequest(stolenLostModel);
        return genricResponse;
    }
}
