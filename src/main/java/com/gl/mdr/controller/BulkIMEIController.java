package com.gl.mdr.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gl.mdr.bulk.imei.entity.BulkIMEIRequest;
import com.gl.mdr.model.generic.GenricResponse;
import com.gl.mdr.service.impl.BulkIMEIFileUploadServiceImpl;



@RestController
public class BulkIMEIController {
	private static final Logger logger = LogManager.getLogger(BulkIMEIController.class);
	
	@Autowired
	BulkIMEIFileUploadServiceImpl bulkIMEIFileUploadServiceImpl;

	@Tag(name = "Bulk check IMEI", description = "Check multiple IMEI")
	@Operation(
			summary = "Check multiple IMEI",
			description = "Save all the IMEI file name and other form details in database")
	@RequestMapping(path = "bulkimei/fileupload", method = RequestMethod.POST)
	public GenricResponse uploadBulkIMEIFile(@RequestBody BulkIMEIRequest bulkIMEIRequest ){
		logger.info("uploadBulkIMEIFile upload Request = " + bulkIMEIRequest);
		GenricResponse genricResponse =	bulkIMEIFileUploadServiceImpl.uploadDetails(bulkIMEIRequest);
		genricResponse.setTag(bulkIMEIRequest.getContactNumber());
		logger.info("uploadBulkIMEIFile upload Response = " + genricResponse);
		return genricResponse;
	}

	@Tag(name = "Bulk check IMEI", description = "Check multiple IMEI")
	@Operation(
			summary = "Bulk IMEI File Count",
			description = "Check Today Upload Bulk IMEI File Count.")
	@RequestMapping(path = "bulkimei/daycount", method = RequestMethod.POST)
	public long daycount(@RequestBody BulkIMEIRequest bulkIMEIRequest){

		logger.info("Check Day wise file upload Request = " + bulkIMEIRequest.toString());
		return bulkIMEIFileUploadServiceImpl.getDayWiseCount(bulkIMEIRequest);
	}

	@Tag(name = "Bulk check IMEI", description = "Check multiple IMEI")
	@Operation(
			summary = "Bulk IMEI otp verification",description = "Verify otp of Bulk IMEI File Upload file.")
	@RequestMapping(path = "bulkimei/verify/otp", method = RequestMethod.POST)
	public GenricResponse verifyOtp(@RequestBody BulkIMEIRequest bulkIMEIRequest){
		logger.info("Check Day wise file upload Request = " + bulkIMEIRequest.toString());
		GenricResponse genricResponse =	bulkIMEIFileUploadServiceImpl.verifyOTP(bulkIMEIRequest.getTransactionId(),bulkIMEIRequest.getOtp(),bulkIMEIRequest.getLang(),bulkIMEIRequest.auditTrailModel.getPublicIp(),bulkIMEIRequest.auditTrailModel.getBrowser(),bulkIMEIRequest.auditTrailModel.getBrowser());
		return genricResponse;
	}


	@Tag(name = "Bulk check IMEI", description = "Check multiple IMEI")
	@Operation(
			summary = "Resend otp if enter otp time out is exceed",description = "Resend otp for bulk IMEI File Upload.")
	@RequestMapping(path = "/resendOTP", method = RequestMethod.POST)
	public GenricResponse resendOTP(@RequestBody BulkIMEIRequest bulkIMEIRequest ) {
		
		logger.info("verify OTP Request , requestID= " + bulkIMEIRequest.getTransactionId()+" , otp ="+bulkIMEIRequest.getOtp());
		GenricResponse genricResponse = bulkIMEIFileUploadServiceImpl.resendOTP(bulkIMEIRequest.getTransactionId(),bulkIMEIRequest.getLang(),bulkIMEIRequest.auditTrailModel.getPublicIp(),bulkIMEIRequest.auditTrailModel.getBrowser(),bulkIMEIRequest.auditTrailModel.getBrowser());
		logger.info("verify OTP Response = " + genricResponse);

		return genricResponse;
	}

	@Tag(name = "Bulk check IMEI", description = "Check multiple IMEI")
	@Operation(
			summary = "Check file status of uploaded file",description = "Check Upload Bulk IMEI File Status.")
	@RequestMapping(path = "bulkimei/status", method = RequestMethod.POST)
	public GenricResponse getFileStatus(@RequestBody BulkIMEIRequest bulkIMEIRequest ){
		logger.info("Check Upload Bulk IMEI File Status Request = " + bulkIMEIRequest.getTransactionId(),bulkIMEIRequest.getLang());
		GenricResponse genricResponse =bulkIMEIFileUploadServiceImpl.getFileStatus(bulkIMEIRequest.getTransactionId(),bulkIMEIRequest.getLang(),bulkIMEIRequest.auditTrailModel.getPublicIp(),bulkIMEIRequest.auditTrailModel.getBrowser(), bulkIMEIRequest.auditTrailModel.getBrowser());
		return genricResponse;
	}
	

	
}
