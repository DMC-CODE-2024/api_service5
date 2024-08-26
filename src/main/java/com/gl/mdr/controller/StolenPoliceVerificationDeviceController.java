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
import com.gl.mdr.model.app.StolenLostModel;
import com.gl.mdr.model.app.TrackLostDevices;
import com.gl.mdr.model.file.FileDetails;
import com.gl.mdr.model.file.TrackLostDeviceFileModel;
import com.gl.mdr.model.filter.TrackLostDeviceFilterRequest;
import com.gl.mdr.model.generic.MDRGenricResponse;
import com.gl.mdr.service.impl.StolenPoliceVerificationDeviceServiceImpl;
import io.swagger.annotations.ApiOperation;

@RestController
public class StolenPoliceVerificationDeviceController {
	private static final Logger logger = LogManager.getLogger(StolenPoliceVerificationDeviceController.class);

	@Autowired
	StolenPoliceVerificationDeviceServiceImpl policeVerificationDeviceServiceImpl;

	@ApiOperation(value = "get list of police verification devices", response = TrackLostDevices.class)
	@PostMapping("/getPoliceVerificationDevicesDetails")
	public MappingJacksonValue getPoliceVerificationDevicesDetails(@RequestBody TrackLostDeviceFilterRequest filterRequest,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
		MappingJacksonValue mapping = null;
		logger.info("Police Verification Device filter request:["+filterRequest.toString()+"]");
		Page<StolenLostModel> trackLostDataResponse =  policeVerificationDeviceServiceImpl.getPoliceVerificationDevicesDetails(filterRequest, pageNo, pageSize,"View");
		mapping = new MappingJacksonValue(trackLostDataResponse);
		return mapping;
	}

	
	@ApiOperation(value = "Export Police Verification devices", response = TrackLostDeviceFileModel.class)
	@PostMapping("/exportPoliceVerificationData")
	public MappingJacksonValue exportData(@RequestBody TrackLostDeviceFilterRequest filterRequest,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
		MappingJacksonValue mapping = null;
		logger.info("police verification export request:["+filterRequest.toString()+"]");
		FileDetails fileDetails = policeVerificationDeviceServiceImpl.exportData(filterRequest);
		mapping = new MappingJacksonValue(fileDetails);
		logger.info("police verification export response:["+fileDetails.toString()+"]");
		return mapping;
	}
	
	
	@ApiOperation(value="get Police Verification Device")
	@PostMapping("/getPoliceVerificationData")
	public ResponseEntity<?> getPoliceVerificationData(@RequestBody TrackLostDeviceFilterRequest filterRequest ){
		return policeVerificationDeviceServiceImpl.getPoliceVerificationData(filterRequest);
	}
	
	@ApiOperation(value = "get User Lost/Stolen Device Status Name")
	@GetMapping("/getDistinctStolenStatus")
	public ResponseEntity<?> getDistinctStatus() {
		List<String>  list = policeVerificationDeviceServiceImpl.getDistinctStolenStatus();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Police Verification User Lost/Stolen Device Status", response = MDRGenricResponse.class)
	@RequestMapping(path = "/policeVerificationStatus", method = {RequestMethod.POST})
	public ResponseEntity<?> updateDevices(@RequestBody TrackLostDeviceFilterRequest filterRequest) {
		return policeVerificationDeviceServiceImpl.updateDevicesStatus(filterRequest);
	}
	
	@ApiOperation(value = "MOI Admin Update User Lost/Stolen Device Status", response = MDRGenricResponse.class)
	@RequestMapping(path = "/MOIAdminVerifyStatus", method = {RequestMethod.POST})
	public ResponseEntity<?> updateLostDeviceStatus(@RequestBody TrackLostDeviceFilterRequest filterRequest) {
		return policeVerificationDeviceServiceImpl.updateLostDeviceStatus(filterRequest);
	}
}
