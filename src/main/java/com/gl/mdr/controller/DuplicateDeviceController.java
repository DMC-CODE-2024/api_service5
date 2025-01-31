package com.gl.mdr.controller;

import com.gl.mdr.model.app.DuplicateDeviceDetail;
import com.gl.mdr.model.file.FileDetails;
import com.gl.mdr.model.filter.DuplicateDeviceFilterRequest;
import com.gl.mdr.service.impl.DuplicateDeviceServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DuplicateDeviceController {
	private static final Logger logger = LogManager.getLogger(DuplicateDeviceController.class);

	@Autowired
	DuplicateDeviceServiceImpl duplicateDeviceServiceImpl;

	@Tag(name = "Duplicate Device Management", description = "Duplicate Module API")
	@Operation(
			summary = "Fetch all record",
			description = "Fetches all record entities and their data from data source")
	@PostMapping("/getDuplicateDeviceDetails")
	public MappingJacksonValue getDuplicateDevicesDetails(@RequestBody DuplicateDeviceFilterRequest filterRequest,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
		MappingJacksonValue mapping = null;
		logger.info("Duplicate Device filter request:["+filterRequest.toString()+"]");
		Page<DuplicateDeviceDetail> duplicateDataResponse =  duplicateDeviceServiceImpl.getDuplicateDevicesDetails(filterRequest, pageNo, pageSize,"View");
		mapping = new MappingJacksonValue(duplicateDataResponse);
		return mapping;
	}


	@Tag(name = "Duplicate Device Management", description = "Duplicate Module API")
	@Operation(
			summary = "Export csv file",
			description = "Fetches device entities and their associated data from the data source, with the number of records limited to a configurable parameter, up to a maximum of 50,000. Subsequently, generate a .csv file containing the retrieved data.")
	@PostMapping("/exportDuplicateData")
	public MappingJacksonValue exportData(@RequestBody DuplicateDeviceFilterRequest filterRequest,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
		MappingJacksonValue mapping = null;
		logger.info("Duplicate export request:["+filterRequest.toString()+"]");
		FileDetails fileDetails = duplicateDeviceServiceImpl.exportData(filterRequest);
		mapping = new MappingJacksonValue(fileDetails);
		logger.info("Duplicate export response:["+fileDetails.toString()+"]");
		return mapping;
	}


	@Tag(name = "Duplicate Device Management", description = "Duplicate Module API")
	@Operation(
			summary = "Fetch single record based on Id",
			description = "Fetches record based on Id from data source")
	@PostMapping("/getApprovedDeviceData")
	public ResponseEntity<?> getApprovedDeviceData(@RequestBody DuplicateDeviceFilterRequest duplicateRequest ){
		return duplicateDeviceServiceImpl.viewApprovedDevice(duplicateRequest);
	}


	@Tag(name = "Duplicate Device Management", description = "Duplicate Module API")
	@Operation(
			summary = "Approve record based on Id",
			description = "Approve record based on Id from data source")
	@PostMapping("/approveDuplicateDevice")
	public ResponseEntity<?> approveDuplicateDevice(@RequestBody DuplicateDeviceFilterRequest duplicateRequest) {
		logger.info("in approveDuplicateDevice Controller with request : "+duplicateRequest);
	    return duplicateDeviceServiceImpl.update(duplicateRequest);

	}
	
	
}
