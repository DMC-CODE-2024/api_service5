package com.gl.mdr.controller;

import java.util.List;

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
	
	

	//@ApiOperation(value = "get list of verification devices", response = TrackLostDevices.class)
	@PostMapping("/getVerificationDevicesDetails")
	public MappingJacksonValue getPoliceVerificationDevicesDetails(@RequestBody MOIVerificationDeviceFilterRequest filterRequest,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
		MappingJacksonValue mapping = null;
		logger.info("Verification Device filter request:["+filterRequest.toString()+"]");
		Page<StolenLostModel> trackLostDataResponse =  stolenVerificationDeviceServiceImpl.getVerificationDevicesDetails(filterRequest, pageNo, pageSize,"View");
		mapping = new MappingJacksonValue(trackLostDataResponse);
		return mapping;
	}

	
	//@ApiOperation(value = "Export Verification devices", response = TrackLostDeviceFileModel.class)
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
	
	
	//@ApiOperation(value="get Police Verification Device")
	@PostMapping("/getPoliceVerificationData")
	public ResponseEntity<?> getPoliceVerificationData(@RequestBody MOIVerificationDeviceFilterRequest filterRequest ){
		return stolenVerificationDeviceServiceImpl.getPoliceVerificationData(filterRequest);
	}
	
	//@ApiOperation(value = "get User Lost/Stolen Device Status Name")
	@GetMapping("/getDistinctStolenStatus")
	public ResponseEntity<?> getDistinctStatus() {
		List<String>  list = stolenVerificationDeviceServiceImpl.getDistinctStolenStatus();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	//@ApiOperation(value = "get User Lost/Stolen Device Request Mode")
	@GetMapping("/getDistinctRequestMode")
	public ResponseEntity<?> getDistinctRequestMode() {
		List<String>  list = stolenVerificationDeviceServiceImpl.getDistinctRequestMode();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	//@ApiOperation(value = "get User Lost/Stolen Device Request Type")
	@GetMapping("/getDistinctRequestType")
	public ResponseEntity<?> getDistinctRequestType() {
		List<String>  list = stolenVerificationDeviceServiceImpl.getDistinctRequestType();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	//@ApiOperation(value = "get User Lost/Stolen Device Created By")
	@GetMapping("/getDistinctCreatedBy")
	public ResponseEntity<?> createdBy() {
		List<String>  list = stolenVerificationDeviceServiceImpl.getDistinctCreatedBy();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	
	//@ApiOperation(value = "Police Verification User Lost/Stolen Device Status", response = MDRGenricResponse.class)
	@RequestMapping(path = "/policeVerificationStatus", method = {RequestMethod.POST})
	public ResponseEntity<?> updateDevices(@RequestBody MOIVerificationDeviceFilterRequest filterRequest) {
		return stolenVerificationDeviceServiceImpl.updateDevicesStatus(filterRequest);
	}
	
	//@ApiOperation(value = "MOI Admin Update User Lost/Stolen Device Status", response = MDRGenricResponse.class)
	@RequestMapping(path = "/MOIAdminVerifyStatus", method = {RequestMethod.POST})
	public ResponseEntity<?> updateLostDeviceStatus(@RequestBody MOIVerificationDeviceFilterRequest filterRequest) {
		return stolenVerificationDeviceServiceImpl.updateLostDeviceStatus(filterRequest);
	}
	
	//@ApiOperation(value = "get All Province Details")
	@GetMapping("/getProvince")
	public ResponseEntity<?> getProvince() {
		List<ProvinceDb>  list = stolenVerificationDeviceServiceImpl.getProvince();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	//@ApiOperation(value = "get All Commune Details")
	@GetMapping("/getCommune")
	public ResponseEntity<?> getCommune() {
		List<CommuneDb>  list = stolenVerificationDeviceServiceImpl.getCommune();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	//@ApiOperation(value = "get All District Details")
	@GetMapping("/getDistrict")
	public ResponseEntity<?> getDistrict() {
		List<DistrictDb>  list = stolenVerificationDeviceServiceImpl.getDistrict();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	//@ApiOperation(value = "get All Police Station Details")
	@GetMapping("/getPoliceStation")
	public ResponseEntity<?> getPoliceStation() {
		List<PoliceStationDb>  list = stolenVerificationDeviceServiceImpl.getPoliceStation();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	
	//@ApiOperation(value = "get All Police Station Details Status")
	@GetMapping("/getPolistStatus")
	public ResponseEntity<?> getPolistStatus() {
		List<MOIStatus>  list = stolenVerificationDeviceServiceImpl.getPolistStatus();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	//@ApiOperation(value = "get All MOI Admin Details Status")
	@GetMapping("/getMOIAdminStatus")
	public ResponseEntity<?> getMOIAdminStatus() {
		List<MOIStatus>  list = stolenVerificationDeviceServiceImpl.getMOIAdminStatus();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	//@ApiOperation(value = "get all device type")
	@GetMapping("/getDistinctMOIDeviceType")
	public ResponseEntity<?> getDistinctMOIDeviceType() {
		List<String>  list = stolenVerificationDeviceServiceImpl.getDistinctMOIDeviceType();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	
}
