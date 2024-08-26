package com.gl.mdr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gl.mdr.model.filter.SearchIMEIRequest;
import com.gl.mdr.service.impl.SearchIMEIServiceImpl;

import io.swagger.annotations.ApiOperation;

@RestController
public class SearchIMEIController {
	
	@Autowired
	SearchIMEIServiceImpl searchIMEIServiceImpl;
	
	//------------------------------------- get IMSI/MSISDN on basis of IMEI/MSISDN/IMSI  --------------------------------------------
	
	@ApiOperation(value="Get IMSI/MSISDN on basis of IMEI")
	@PostMapping("/get-msisdn-imsi")
	public ResponseEntity<?> getMsisdnImsibyDeviceId(@RequestBody SearchIMEIRequest searchIMEIRequest ){
		return searchIMEIServiceImpl.getImsiMsisdnService(searchIMEIRequest);
	}
	
	//------------------------------------- get device detailed information on basis of IMEI  ----------------------------------------
	
	@ApiOperation(value="Get device details on basis of TAC")
	@PostMapping("/get-device-details-by-tac")
	public ResponseEntity<?> getDevicebyDeviceId(@RequestBody SearchIMEIRequest searchIMEIRequest ){
		return searchIMEIServiceImpl.getDeviceDetails(searchIMEIRequest);
	}
	
	//------------------------------------- get device existence in multiple device tables on basis of IMEI  -------------------------

	@ApiOperation(value="Get device details in database on basis of IMEI")
	@PostMapping("/get-device-states")
	public ResponseEntity<?> getDeviceState(@RequestBody SearchIMEIRequest searchIMEIRequest ){
		return searchIMEIServiceImpl.getDeviceState(searchIMEIRequest);
	}
	
	//------------------------------------- get table record   ------------------------------------------------------------------------
	
	@ApiOperation(value="Get table record on basis of imei")
	@PostMapping("/get-table-record-by-imei")
	public ResponseEntity<?> getTableDetail(@RequestBody SearchIMEIRequest searchIMEIRequest ){
		return searchIMEIServiceImpl.getTableRecord(searchIMEIRequest);
	}
	

}
