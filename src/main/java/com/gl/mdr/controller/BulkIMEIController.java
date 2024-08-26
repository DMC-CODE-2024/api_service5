package com.gl.mdr.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gl.mdr.bulk.imei.entity.BulkIMEIRequest;
import com.gl.mdr.model.filter.LostDeviceRequest;
import com.gl.mdr.model.generic.GenricResponse;
import com.gl.mdr.service.impl.BulkIMEIFileUploadServiceImpl;

import io.swagger.annotations.ApiOperation;

@RestController
public class BulkIMEIController {
	private static final Logger logger = LogManager.getLogger(BulkIMEIController.class);
	
	@Autowired
	BulkIMEIFileUploadServiceImpl bulkIMEIFileUploadServiceImpl;
	
	@ApiOperation(value = "Upload Bulk IMEI Details.", response = GenricResponse.class)
	@RequestMapping(path = "bulkimei/fileupload", method = RequestMethod.POST)
	public GenricResponse uploadBulkIMEIFile(@RequestBody BulkIMEIRequest bulkIMEIRequest ){
		logger.info("uploadBulkIMEIFile upload Request = " + bulkIMEIRequest);
		GenricResponse genricResponse =	bulkIMEIFileUploadServiceImpl.uploadDetails(bulkIMEIRequest);
		genricResponse.setTag(bulkIMEIRequest.getContactNumber());
		logger.info("uploadBulkIMEIFile upload Response = " + genricResponse);
		return genricResponse;
	}
	
	@ApiOperation(value = "Check Today Upload Bulk IMEI File Count.", response = GenricResponse.class)
	@RequestMapping(path = "bulkimei/daycount", method = RequestMethod.POST)
	public long daycount(@RequestBody BulkIMEIRequest bulkIMEIRequest){
		
		logger.info("Check Day wise file upload Request = " + bulkIMEIRequest.toString());
		return bulkIMEIFileUploadServiceImpl.getDayWiseCount(bulkIMEIRequest);
	}
	
	@ApiOperation(value = "Verify Bulk IMEI File Upload file.", response = GenricResponse.class)
	@RequestMapping(path = "bulkimei/verify/otp", method = RequestMethod.POST)
	public GenricResponse verifyOtp(@RequestBody BulkIMEIRequest bulkIMEIRequest){
		logger.info("Check Day wise file upload Request = " + bulkIMEIRequest.toString());
		GenricResponse genricResponse =	bulkIMEIFileUploadServiceImpl.verifyOTP(bulkIMEIRequest.getTransactionId(),bulkIMEIRequest.getOtp(),bulkIMEIRequest.getLang(),bulkIMEIRequest.auditTrailModel.getPublicIp(),bulkIMEIRequest.auditTrailModel.getBrowser(),bulkIMEIRequest.auditTrailModel.getBrowser());
		return genricResponse;
	}
	
	@ApiOperation(value = "resend OTP for bulk IMEI File Upload", response = GenricResponse.class)
	@RequestMapping(path = "/resendOTP", method = RequestMethod.POST)
	public GenricResponse resendOTP(@RequestBody BulkIMEIRequest bulkIMEIRequest ) {
		
		logger.info("verify OTP Request , requestID= " + bulkIMEIRequest.getTransactionId()+" , otp ="+bulkIMEIRequest.getOtp());
		GenricResponse genricResponse = bulkIMEIFileUploadServiceImpl.resendOTP(bulkIMEIRequest.getTransactionId(),bulkIMEIRequest.getLang(),bulkIMEIRequest.auditTrailModel.getPublicIp(),bulkIMEIRequest.auditTrailModel.getBrowser(),bulkIMEIRequest.auditTrailModel.getBrowser());
		logger.info("verify OTP Response = " + genricResponse);

		return genricResponse;
	}
	
	@ApiOperation(value = "Check Upload Bulk IMEI File Status.", response = GenricResponse.class)
	@RequestMapping(path = "bulkimei/status", method = RequestMethod.POST)
	public GenricResponse getFileStatus(@RequestBody BulkIMEIRequest bulkIMEIRequest ){
		logger.info("Check Upload Bulk IMEI File Status Request = " + bulkIMEIRequest.getTransactionId(),bulkIMEIRequest.getLang());
		GenricResponse genricResponse =bulkIMEIFileUploadServiceImpl.getFileStatus(bulkIMEIRequest.getTransactionId(),bulkIMEIRequest.getLang(),bulkIMEIRequest.auditTrailModel.getPublicIp(),bulkIMEIRequest.auditTrailModel.getBrowser(), bulkIMEIRequest.auditTrailModel.getBrowser());
		return genricResponse;
	}
	
//	@ApiOperation(value = "Insert Track Lost Devices Details.", response = GenricResponse.class)
//	@RequestMapping(path = "tracklost/device/{operator}", method = RequestMethod.POST)
//	public GenricResponse setTrackLostDevices(@RequestBody LostDeviceRequest lostDeviceRequest, @PathVariable String operator, HttpServletRequest request,HttpServletResponse response ){
//		logger.info("set Track Lost Devices Details Request = " +lostDeviceRequest.toString() );
//		GenricResponse genricResponse =bulkIMEIFileUploadServiceImpl.setTrackLostDevices(lostDeviceRequest,operator, request);
//		return genricResponse;
//	}
	
}
