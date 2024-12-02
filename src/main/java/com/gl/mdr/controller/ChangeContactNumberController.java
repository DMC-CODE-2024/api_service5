package com.gl.mdr.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gl.mdr.model.filter.ChangeNumberFilterRequest;
import com.gl.mdr.model.generic.GenricResponse;
import com.gl.mdr.model.generic.MDRGenricResponse;
import com.gl.mdr.service.impl.ChangeContactNumberServiceImpl;

import io.swagger.annotations.ApiOperation;

@RestController
public class ChangeContactNumberController {
	private static final Logger logger = LogManager.getLogger(ChangeContactNumberController.class);
	
	@Autowired
	ChangeContactNumberServiceImpl changeContactNumberServiceImpl;
	
	@ApiOperation(value = "Customer care contact number/request id verify", response = GenricResponse.class)
	@RequestMapping(path = "/contact-number/verification", method = RequestMethod.POST)
	public ResponseEntity<?> verifyRequestNumber(@RequestBody ChangeNumberFilterRequest filterRequest ){
		logger.info("Change Contact Number Verification Request = " + filterRequest);
		
		ResponseEntity<?> genricResponse =	changeContactNumberServiceImpl.verifyRequestNumber(filterRequest);
		logger.info("Change Contact Number Verification Response = " + genricResponse);
		return genricResponse;
	}
	
	@ApiOperation(value = "Customer care update user lost/stolen contact number", response = MDRGenricResponse.class)
	@RequestMapping(path = "/contact-number/update", method = {RequestMethod.POST})
	public ResponseEntity<?> updateContactNumber(@RequestBody ChangeNumberFilterRequest filterRequest) {
		return changeContactNumberServiceImpl.updateContactNumberForDeviceInfo(filterRequest);
	}
	
	
	

	
}
