package com.gl.mdr.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gl.mdr.model.filter.SearchIMEIRequest;
import com.gl.mdr.service.impl.SearchIMEIServiceImpl;



@RestController
public class SearchIMEIController {
	
	@Autowired
	SearchIMEIServiceImpl searchIMEIServiceImpl;
	
	//------------------------------------- get IMSI/MSISDN on basis of IMEI/MSISDN/IMSI  --------------------------------------------
	
	@Tag(name = "Search IMEI", description = "Search IMEI API")
	@Operation(
			summary = "Fetch record based on IMEI",
			description = "Fetches record based on IMEI from data source")
	@PostMapping("/get-msisdn-imsi")
	public ResponseEntity<?> getMsisdnImsibyDeviceId(@RequestBody SearchIMEIRequest searchIMEIRequest ){
		return searchIMEIServiceImpl.getImsiMsisdnService(searchIMEIRequest);
	}
	
	//------------------------------------- get device detailed information on basis of IMEI  ----------------------------------------
	
	@Tag(name = "Search IMEI", description = "Search IMEI API")
	@Operation(
			summary = "Fetch record based on TAC",
			description = "Fetches record based on TAC from data source")
	@PostMapping("/get-device-details-by-tac")
	public ResponseEntity<?> getDevicebyDeviceId(@RequestBody SearchIMEIRequest searchIMEIRequest ){
		return searchIMEIServiceImpl.getDeviceDetails(searchIMEIRequest);
	}
	
	//------------------------------------- get device existence in multiple device tables on basis of IMEI  -------------------------


	@Tag(name = "Search IMEI", description = "Search IMEI API")
	@Operation(
			summary = "Fetch record based on IMEI across multiple device table",
			description = "Retrieve the existence of a device across multiple device tables based on the IMEI")
	@PostMapping("/get-device-states")
	public ResponseEntity<?> getDeviceState(@RequestBody SearchIMEIRequest searchIMEIRequest ){
		return searchIMEIServiceImpl.getDeviceState(searchIMEIRequest);
	}
	
	//------------------------------------- get table record   ------------------------------------------------------------------------
	
	@Tag(name = "Search IMEI", description = "Search IMEI API")
	@Operation(
			summary = "Fetch record based on IMEI from selected table",
			description = "Retrieve the record from the table based on the IMEI")
	@PostMapping("/get-table-record-by-imei")	
	public ResponseEntity<?> getTableDetail(@RequestBody SearchIMEIRequest searchIMEIRequest ){
		return searchIMEIServiceImpl.getTableRecord(searchIMEIRequest);
	}
	

}
