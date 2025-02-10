package com.gl.mdr.controller;


import com.gl.mdr.configuration.PropertiesReader;
import com.gl.mdr.model.app.CountryCodeModel;
import com.gl.mdr.model.app.OTPRequest;
import com.gl.mdr.model.app.StolenLostModel;
import com.gl.mdr.model.app.SystemConfigurationDb;
import com.gl.mdr.model.generic.GenricResponse;
import com.gl.mdr.repo.app.CountryCodeRepo;
import com.gl.mdr.repo.app.LostStolenRepo;
import com.gl.mdr.repo.app.SystemConfigurationDbRepository;
import com.gl.mdr.service.impl.LostStolenServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//import io.swagger.annotations.ApiOperation;

@RestController
public class LostStolenController {

	private static final Logger logger = LogManager.getLogger(LostStolenController.class);
	
	@Autowired
	LostStolenServiceImpl LostStolenServiceImpl;

	@Autowired
	PropertiesReader propertiesReader;

	@Autowired
	LostStolenRepo lostStolenRepo;
	
	@Autowired
	SystemConfigurationDbRepository systemConfigurationDbRepository;

	@Autowired
	CountryCodeRepo countryCodeRepo;
	
	

	@Tag(name = "End user stolen", description = "End user stolen and recovery")
	@Operation(
			summary = "Save stolen request",description = "Save end user device  stolen request")
	@RequestMapping(path = "/lostStolen/save", method = RequestMethod.POST)
	public GenricResponse saveStolenDevice(@RequestBody StolenLostModel stolenLostModel) {

		logger.info("Lost stolen Request = " + stolenLostModel);
		GenricResponse genricResponse = LostStolenServiceImpl.saveLostStolenDevice(stolenLostModel);
		logger.info("Lost stolen Response = " + genricResponse);
		return genricResponse;
	}

	@Tag(name = "End user stolen", description = "End user stolen and recovery")
	@Operation(
			summary = "Update stolen request",description = "Update end user device  stolen request")
	@RequestMapping(path = "/lostStolen/update", method = RequestMethod.POST)
	public GenricResponse updateStolenDevice(@RequestBody StolenLostModel stolenLostModel) {

		logger.info("update Lost stolen Request = " + stolenLostModel);
		GenricResponse genricResponse = LostStolenServiceImpl.updateLostStolenDevice(stolenLostModel);
		logger.info("Lost stolen Response = " + genricResponse);

		return genricResponse;
	}

	@Tag(name = "End user stolen", description = "End user stolen and recovery")
	@Operation(
			summary = "Cancel stolen request",description = "Cancel end user device  stolen request")
	@RequestMapping(path = "/saveCancelRequest", method = RequestMethod.POST)
	public GenricResponse saveCancelRequest(@RequestBody StolenLostModel stolenLostModel) {
		logger.info("save cancellation request = " + stolenLostModel);
		GenricResponse genricResponse = LostStolenServiceImpl.saveCancelRequest(stolenLostModel);
		logger.info("save cancellation Response = " + genricResponse);
		return genricResponse;
	}


	@Tag(name = "End user stolen", description = "End user stolen and recovery")
	@Operation(
			summary = "OTP verification request",description = "OTP Verification request for device  stolen request")
	  @RequestMapping(path = "/verifyOTP", method = RequestMethod.POST) public
	  GenricResponse verifyOTP(@RequestBody OTPRequest otpRequest ) {
	  logger.info("verify OTP Request , requestID= " +
	  otpRequest.getRequestID()+" , otp ="+otpRequest.getOtp()); 
	  GenricResponse genricResponse = LostStolenServiceImpl.verifyOTP(otpRequest.getRequestID(),otpRequest.getOtp(),otpRequest.getRequestType(),otpRequest.getOldRequestID()); logger.info("verify OTP Response = " + genricResponse);
	  LostStolenServiceImpl.audiTrail( otpRequest.getPublicIp(), otpRequest.getBrowser(), otpRequest.getRequestID(), "OTP-Verification",otpRequest.getUserAgent());
	  return genricResponse; 
	  }


	@Tag(name = "End user stolen", description = "End user stolen and recovery")
	@Operation(
			summary = "OTP verification request",description = "OTP Verification request for update device  stolen request")
	  @RequestMapping(path = "/verifyOTPUpdateStolen", method = RequestMethod.POST) public
	  GenricResponse verifyOTPUpdateStolen(@RequestBody OTPRequest otpRequest ) {
	  logger.info("verify OTP Request  for update block form, requestID= " +
	  otpRequest.getRequestID()+" , otp ="+otpRequest.getOtp()); 
	  GenricResponse genricResponse = LostStolenServiceImpl.verifyOTPUpdateStolen(otpRequest.getRequestID(),otpRequest.getOtp(),otpRequest.getRequestType(),otpRequest.getOldRequestID()); logger.info("verify OTP Response = " + genricResponse);
	  LostStolenServiceImpl.audiTrail( otpRequest.getPublicIp(), otpRequest.getBrowser(), otpRequest.getRequestID(), "Update Stolen-OTP-Verification",otpRequest.getUserAgent());
	  return genricResponse;
	  }


	@Tag(name = "End user stolen", description = "End user stolen and recovery")
	@Operation(
			summary = "OTP verification request",description = "OTP Verification request for cancel device  stolen request")
	@RequestMapping(path = "/verifyCancelRequestOTP", method = RequestMethod.POST) public
	GenricResponse verifyCancelRequestOTP(@RequestBody OTPRequest otpRequest ) {
		logger.info("verify cancel request OTP, requestID= " +
				otpRequest.getRequestID()+" , otp ="+otpRequest.getOtp());
		GenricResponse genricResponse = LostStolenServiceImpl.verifyCancelRequestOTP(otpRequest.getRequestID(),otpRequest.getOtp(),otpRequest.getRequestType(),otpRequest.getOldRequestID()); logger.info("verify OTP Response = " + genricResponse);
		LostStolenServiceImpl.audiTrail( otpRequest.getPublicIp(), otpRequest.getBrowser(), otpRequest.getRequestID(), "OTP-Verification",otpRequest.getUserAgent());
		return genricResponse;
	}

	@Tag(name = "End user stolen", description = "End user stolen and recovery")
	@Operation(
			summary = "OTP resend request",description = "OTP resend request for stolen and recovery")
	@RequestMapping(path = "/resendOTPEndUser", method = RequestMethod.POST)
	public GenricResponse resendOTP(@RequestBody OTPRequest otpRequest ) {
		
		logger.info("resend Request , requestID= " + otpRequest.getRequestID()+" , otp ="+otpRequest.getOtp());
		GenricResponse genricResponse = LostStolenServiceImpl.resendOTP(otpRequest.getRequestID());
		logger.info("resend OTP Response = " + genricResponse);
		LostStolenServiceImpl.audiTrail( otpRequest.getPublicIp(), otpRequest.getBrowser(), otpRequest.getRequestID(), "Resend-OTP",otpRequest.getUserAgent());
		return genricResponse;
	}

	@Tag(name = "End user stolen", description = "End user stolen and recovery")
	@Operation(
			summary = "Check device details ",description = "Check stolen request is exist or not for recovery")
	@RequestMapping(path = "/recoveryFound/save", method = RequestMethod.POST)
	public GenricResponse saveRecoveryFound(@RequestBody StolenLostModel stolenLostModel) {

		logger.info("Fetch data for recovery by  Request  ---= " + stolenLostModel);
		GenricResponse genricResponse = LostStolenServiceImpl.saveRecoveryDevice1(stolenLostModel);
		logger.info("Lost stolen Response = " + genricResponse);

		return genricResponse;
	}

	@Tag(name = "End user stolen", description = "End user stolen and recovery")
	@Operation(
			summary = "Check device details ",description = "Check stolen request is exist or not")
		@RequestMapping(path = "/checkRequestStatus", method = RequestMethod.POST)
		public GenricResponse checkRequestStatus(@RequestBody StolenLostModel stolenLostModel) {
			logger.info("check Recovery or stolen save  Request = " + stolenLostModel.getRequestId());
			GenricResponse genricResponse = LostStolenServiceImpl.checkStatus1(stolenLostModel);
			logger.info("Lost stolen Response = " + genricResponse);
			return genricResponse;
		}

	@Tag(name = "End user stolen", description = "End user stolen and recovery")
	@Operation(
			summary = "Get Recovery OTP ",description = "Get OTP for recovering any blocked device")
		@RequestMapping(path = "/getOTP", method = RequestMethod.POST)
		public GenricResponse getOTP(@RequestBody StolenLostModel stolenLostModel) {
			logger.info("get otp Request = " + stolenLostModel.getRequestId());
			GenricResponse genricResponse = LostStolenServiceImpl.getOtp(stolenLostModel);
			logger.info("Lost stolen Response = " + genricResponse);
			return genricResponse;
		}

	@Tag(name = "End user stolen", description = "End user stolen and recovery")
	@Operation(
			summary = "Get Stolen Update OTP ",description = "Get OTP for update any blocked request")
		@RequestMapping(path = "/getOTPForCheckRequest", method = RequestMethod.POST)
		public GenricResponse getOTPForCheckRequest(@RequestBody StolenLostModel stolenLostModel) {
			logger.info("get otp Request for check status = " + stolenLostModel.getRequestId());
			GenricResponse genricResponse = LostStolenServiceImpl.getOtpForCheckStatus(stolenLostModel);
			logger.info("Lost stolen Response fr check status = " + genricResponse);
			return genricResponse;
		}


	@Tag(name = "End user stolen", description = "End user stolen and recovery")
	@Operation(
			summary = "Get Cancel  OTP ",description = "Get OTP for cancel any blocked request")
	@RequestMapping(path = "/getOTPForCancelRequest", method = RequestMethod.POST)
	public GenricResponse getOTPForCancelRequest(@RequestBody StolenLostModel stolenLostModel) {
		logger.info("get otp Request for cancel request = " + stolenLostModel.getRequestId());
		GenricResponse genricResponse = LostStolenServiceImpl.getOtpForCancelRequest(stolenLostModel);
		logger.info("Otp Response for cancel request = " + genricResponse);
		return genricResponse;
	}

	@Tag(name = "End user stolen", description = "End user stolen and recovery")
	@Operation(
			summary = "Get Stolen Device Details ",description = "Get particular Stolen device details to view details ")
		@RequestMapping(path = "/getByRequestID", method = RequestMethod.POST)
		public StolenLostModel getByRequestID(@RequestBody StolenLostModel stolenLostModel) {
			logger.info("get by Request id = " + stolenLostModel.getRequestId());
			StolenLostModel res = lostStolenRepo.findByRequestId2(stolenLostModel.getRequestId());
			SystemConfigurationDb ss=new SystemConfigurationDb ();
			ss=systemConfigurationDbRepository.getByTag("upload_file_link");
			
			res.setFirCopyUrl(ss.getValue().replace("$LOCAL_IP",propertiesReader.localIp));
			
			logger.info("Lost stolen Response  by id  = " + res);
			return res;
		}

	@Tag(name = "End user stolen", description = "End user stolen and recovery")
	@Operation(
			summary = "Get Stolen Device Details ",description = "Get particular Stolen device details to view details ")
		@RequestMapping(path = "/getLostDeviceByRequestID", method = RequestMethod.POST)
		public StolenLostModel getByRequestIDForViews(@RequestBody StolenLostModel stolenLostModel) {
			logger.info("get lost device details by request id = " + stolenLostModel.getRequestId());
			StolenLostModel res = lostStolenRepo.findByRequestId3(stolenLostModel.getRequestId());
			SystemConfigurationDb ss=new SystemConfigurationDb ();
			ss=systemConfigurationDbRepository.getByTag("upload_file_link");

			if(res!=null) {
				res.setDevicePurchaseInvoiceUrl(res.getFirCopyUrl());
				res.setFirCopyUrl(ss.getValue().replace("$LOCAL_IP",propertiesReader.localIp));
				try {
					String dist = lostStolenRepo.getDistrict(res.getDistrict());
					String commune = lostStolenRepo.getCommune(res.getCommune());
					String province = lostStolenRepo.getProvince(res.getProvince());
					String police = lostStolenRepo.getPolice(res.getPoliceStation());
					String nationality = lostStolenRepo.getNationality(res.getDeviceOwnerNationality());
					//String categ=lostStolenRepo.getDistrict(stolenLostModel.getCategory());
					res.setDistrict(dist);
					res.setCommune(commune);
					res.setPoliceStation(police);
					res.setProvince(province);
					res.setDeviceOwnerNationality(nationality);

				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			
			logger.info("Lost stolen Response  by id  = " + res);
			return res;
		}


	@Tag(name = "End user stolen", description = "End user stolen and recovery")
	@Operation(
			summary = "Get Bulk Stolen Device Details ",description = "Get particular bulk stolen device details to view details ")
	@RequestMapping(path = "/getBulkDeviceByRequestID", method = RequestMethod.POST)
	public StolenLostModel getBulkDeviceByRequestID(@RequestBody StolenLostModel stolenLostModel) {
		logger.info("get bulk lost device details by request id = " + stolenLostModel.getRequestId());
		StolenLostModel res = lostStolenRepo.findByRequestId3(stolenLostModel.getRequestId());
		SystemConfigurationDb systemConfigurationDb=new SystemConfigurationDb ();
		systemConfigurationDb=systemConfigurationDbRepository.getByTag("upload_file_link");

		if(res!=null) {
			res.setDevicePurchaseInvoiceUrl(res.getFirCopyUrl());
			res.setFirCopyUrl(systemConfigurationDb.getValue().replace("$LOCAL_IP",propertiesReader.localIp));

		}

		logger.info("Bulk lost stolen Response  by id  = " + res);
		return res;
	}




	@Tag(name = "End user stolen", description = "End user stolen and recovery")
	@Operation(
			summary = "Country code list",description = "Country code list for stolen form ")
	@PostMapping("/countryCodeList")
	public List<CountryCodeModel> countryCodeList() {
		List<CountryCodeModel> countryCodeModel=countryCodeRepo.fetchCountryCode();
		logger.info("Response to send for"+countryCodeModel);
		return countryCodeModel;
	}




}
