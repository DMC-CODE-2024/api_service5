package com.gl.mdr.controller;

import java.util.List;

import com.gl.mdr.dto.StolenLostModelDtoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gl.mdr.model.app.CommuneDb;
import com.gl.mdr.model.app.DistrictDb;
import com.gl.mdr.model.app.PoliceStationDb;
import com.gl.mdr.model.app.ProvinceDb;
import com.gl.mdr.model.app.StolenLostModel;
import com.gl.mdr.model.app.TrackLostDevices;
import com.gl.mdr.model.file.FileDetails;
import com.gl.mdr.model.file.TrackLostDeviceFileModel;
import com.gl.mdr.model.filter.MOIStatus;
import com.gl.mdr.model.filter.MOIVerificationDeviceFilterRequest;
import com.gl.mdr.model.generic.MDRGenricResponse;
import com.gl.mdr.service.impl.StolenVerificationDeviceServiceImpl;



@RestController
public class MOIAdminVerificationDeviceController {
	private static final Logger logger = LogManager.getLogger(MOIAdminVerificationDeviceController.class);

	@Autowired
	StolenVerificationDeviceServiceImpl stolenVerificationDeviceServiceImpl;
	
	


	@Tag(name = "Request Management/Police verification Data", description = "Request Management/Police verification  details")
	@Operation(
			summary = "Fetch all record",description = "Filter all record od stolen marked for police and MOI user ")
	@PostMapping("/getVerificationDevicesDetails")
	public StolenLostModelDtoResponse getPoliceVerificationDevicesDetails(@RequestBody MOIVerificationDeviceFilterRequest filterRequest,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
		MappingJacksonValue mapping = null;
		logger.info("Verification Device filter request date field["+filterRequest.toString()+"]");
		StolenLostModelDtoResponse trackLostDataResponse =  stolenVerificationDeviceServiceImpl.getVerificationDevicesDetails(filterRequest, pageNo, pageSize,"View");
		logger.info("controller CC "+trackLostDataResponse.getContent().size());
		return trackLostDataResponse;
	}


	@Tag(name = "Request Management/Police verification Data", description = "Request Management/Police verification  details")
	@Operation(
			summary = "Export all record",description = "Download all record od stolen marked for police and MOI user in csv file ")
	@PostMapping("/exportVerificationData")
	public MappingJacksonValue exportData(@RequestBody MOIVerificationDeviceFilterRequest filterRequest,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
		MappingJacksonValue mapping = null;
		logger.info("police verification export request:["+filterRequest.toString()+"]");
		FileDetails fileDetails = stolenVerificationDeviceServiceImpl.exportData(filterRequest);
		mapping = new MappingJacksonValue(fileDetails);
		logger.info("police verification export response:["+fileDetails.toString()+"]");
		return mapping;
	}

	@Tag(name = "Request Management/Police verification Data", description = "Request Management/Police verification  details")
	@Operation(
			summary = "Get Record for Particular request",description = "Get particular stolen request for view the device or user details to verify or reject the request")
	@PostMapping("/getPoliceVerificationData")
	public ResponseEntity<?> getPoliceVerificationData(@RequestBody MOIVerificationDeviceFilterRequest filterRequest ){
		return stolenVerificationDeviceServiceImpl.getPoliceVerificationData(filterRequest);
	}

	@Tag(name = "Request Management/Police verification Data", description = "Request Management/Police verification  details")
	@Operation(
			summary = "Get Stolen request status",description = "Get Stolen request status list to filter on status column")
	@GetMapping("/getDistinctStolenStatus")
	public ResponseEntity<?> getDistinctStatus() {
		List<String>  list = stolenVerificationDeviceServiceImpl.getDistinctStolenStatus();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@Tag(name = "Request Management/Police verification Data", description = "Request Management/Police verification  details")
	@Operation(
			summary = "Get Stolen request mode",description = "Get Stolen request mode list to filter on mode column")
	@GetMapping("/getDistinctRequestMode")
	public ResponseEntity<?> getDistinctRequestMode() {
		List<String>  list = stolenVerificationDeviceServiceImpl.getDistinctRequestMode();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@Tag(name = "Request Management/Police verification Data", description = "Request Management/Police verification  details")
	@Operation(
			summary = "Get Stolen request type",description = "Get Stolen request type list to filter on type column")
	@GetMapping("/getDistinctRequestType")
	public ResponseEntity<?> getDistinctRequestType() {
		List<String>  list = stolenVerificationDeviceServiceImpl.getDistinctRequestType();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@Tag(name = "Request Management/Police verification Data", description = "Request Management/Police verification  details")
	@Operation(
			summary = "Get Stolen request created by",description = "Get Stolen request created by list to filter")
	@GetMapping("/getDistinctCreatedBy")
	public ResponseEntity<?> createdBy() {
		List<String>  list = stolenVerificationDeviceServiceImpl.getDistinctCreatedBy();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}


	@Tag(name = "Request Management/Police verification Data", description = "Request Management/Police verification  details")
	@Operation(
			summary = "Update stolen device status",description = "verify status of stolen device request  by police")
	@RequestMapping(path = "/policeVerificationStatus", method = {RequestMethod.POST})
	public ResponseEntity<?> updateDevices(@RequestBody MOIVerificationDeviceFilterRequest filterRequest) {
		return stolenVerificationDeviceServiceImpl.updateDevicesStatus(filterRequest);
	}

	@Tag(name = "Request Management/Police verification Data", description = "Request Management/Police verification  details")
	@Operation(
			summary = "Update stolen device status",description = "Approve or reject the status of stolen device request  by MOI")
    @RequestMapping(path = "/MOIAdminVerifyStatus", method = {RequestMethod.POST})
	public ResponseEntity<?> updateLostDeviceStatus(@RequestBody MOIVerificationDeviceFilterRequest filterRequest) {
		return stolenVerificationDeviceServiceImpl.updateLostDeviceStatus(filterRequest);
	}

	@Tag(name = "Request Management/Police verification Data", description = "Request Management/Police verification  details")
	@Operation(
			summary = "Get province details",description = "Get province details list to filter on type column")
	@GetMapping("/getProvince")
	public ResponseEntity<?> getProvince() {
		List<ProvinceDb>  list = stolenVerificationDeviceServiceImpl.getProvince();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@Tag(name = "Request Management/Police verification Data", description = "Request Management/Police verification  details")
	@Operation(
			summary = "Get commune details",description = "Get commune details list to filter on type column")
	@GetMapping("/getCommune")
	public ResponseEntity<?> getCommune() {
		List<CommuneDb>  list = stolenVerificationDeviceServiceImpl.getCommune();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@Tag(name = "Request Management/Police verification Data", description = "Request Management/Police verification  details")
	@Operation(
			summary = "Get district details",description = "Get district details list to filter on type column")
	@GetMapping("/getDistrict")
	public ResponseEntity<?> getDistrict() {
		List<DistrictDb>  list = stolenVerificationDeviceServiceImpl.getDistrict();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@Tag(name = "Request Management/Police verification Data", description = "Request Management/Police verification  details")
	@Operation(
			summary = "Get police station details",description = "Get police station details list to filter on type column")
	@GetMapping("/getPoliceStation")
	public ResponseEntity<?> getPoliceStation() {
		List<PoliceStationDb>  list = stolenVerificationDeviceServiceImpl.getPoliceStation();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	

	@Tag(name = "Request Management/Police verification Data", description = "Request Management/Police verification  details")
	@Operation(
			summary = "Get status details",description = "Get device status  details list for police user to filter on type column")
	@GetMapping("/getPolistStatus")
	public ResponseEntity<?> getPolistStatus() {
		List<MOIStatus>  list = stolenVerificationDeviceServiceImpl.getPolistStatus();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@Tag(name = "Request Management/Police verification Data", description = "Request Management/Police verification  details")
	@Operation(
			summary = "Get status details",description = "Get device status  details list for MOI user to filter on type column")
	@GetMapping("/getMOIAdminStatus")
	public ResponseEntity<?> getMOIAdminStatus() {
		List<MOIStatus>  list = stolenVerificationDeviceServiceImpl.getMOIAdminStatus();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	

	@Tag(name = "Request Management/Police verification Data", description = "Request Management/Police verification  details")
	@Operation(
			summary = "Get device type",description = "Get device type  details list for  filter on type column")
	@GetMapping("/getDistinctMOIDeviceType")
	public ResponseEntity<?> getDistinctMOIDeviceType() {
		List<String>  list = stolenVerificationDeviceServiceImpl.getDistinctMOIDeviceType();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	
}
