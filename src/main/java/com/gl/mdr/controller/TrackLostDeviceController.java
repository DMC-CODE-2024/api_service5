package com.gl.mdr.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.gl.mdr.model.app.TrackLostDevices;
import com.gl.mdr.model.file.FileDetails;
import com.gl.mdr.model.file.TrackLostDeviceFileModel;
import com.gl.mdr.model.filter.LostDeviceRequest;
import com.gl.mdr.model.filter.TrackLostDeviceFilterRequest;
import com.gl.mdr.model.generic.GenricResponse;
import com.gl.mdr.service.impl.TrackLostDeviceServiceImpl;

import io.swagger.annotations.ApiOperation;

@RestController
public class TrackLostDeviceController {
	private static final Logger logger = LogManager.getLogger(TrackLostDeviceController.class);

	@Autowired
	TrackLostDeviceServiceImpl trackLostDeviceServiceImpl;

	@ApiOperation(value = "get list of track lost device devices", response = TrackLostDevices.class)
	@PostMapping("/getTrackLostDevicesDetails")
	public MappingJacksonValue getTrackLostDevicesDetails(@RequestBody TrackLostDeviceFilterRequest filterRequest,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
		MappingJacksonValue mapping = null;
		logger.info("track lost Device filter request:["+filterRequest.toString()+"]");
		Page<TrackLostDevices> trackLostDataResponse =  trackLostDeviceServiceImpl.getTrackLostDevicesDetails(filterRequest, pageNo, pageSize,"View");
		mapping = new MappingJacksonValue(trackLostDataResponse);
		return mapping;
	}

	
	@ApiOperation(value = "Export track lost devices", response = TrackLostDeviceFileModel.class)
	@PostMapping("/exportTrackLostData")
	public MappingJacksonValue exportData(@RequestBody TrackLostDeviceFilterRequest filterRequest,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
		MappingJacksonValue mapping = null;
		logger.info("track lost export request:["+filterRequest.toString()+"]");
		FileDetails fileDetails = trackLostDeviceServiceImpl.exportData(filterRequest);
		mapping = new MappingJacksonValue(fileDetails);
		logger.info("track lost export response:["+fileDetails.toString()+"]");
		return mapping;
	}
	
	
	@ApiOperation(value="get TrackLost Device")
	@PostMapping("/getTrackLostData")
	public ResponseEntity<?> getTrackLostData(@RequestBody TrackLostDeviceFilterRequest filterRequest ){
		return trackLostDeviceServiceImpl.getTrackLostData(filterRequest);
	}
	
	@ApiOperation(value = "get User Operator Name")
	@GetMapping("/getDistinctOperator")
	public ResponseEntity<?> getDistinctOperator() {
		List<String>  list = trackLostDeviceServiceImpl.getDistinctOperator();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@ApiOperation(value = "get User Status Name")
	@GetMapping("/getDistinctStatus")
	public ResponseEntity<?> getDistinctStatus() {
		List<String>  list = trackLostDeviceServiceImpl.getDistinctStatus();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@ApiOperation(value = "get User Track Lost Device Request Type")
	@GetMapping("/getTrackLostRequestType")
	public ResponseEntity<?> getTrackLostRequestType() {
		List<String>  list = trackLostDeviceServiceImpl.getTrackLostRequestType();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Insert Track Lost Devices Details.", response = GenricResponse.class)
	@RequestMapping(path = "tracklost/device/{operator}", method = RequestMethod.POST)
	public GenricResponse setTrackLostDevices(@RequestBody LostDeviceRequest lostDeviceRequest, @PathVariable String operator, HttpServletRequest request,HttpServletResponse response ){
		logger.info("set Track Lost Devices Details Request = " +lostDeviceRequest.toString() );
		GenricResponse genricResponse =trackLostDeviceServiceImpl.setTrackLostDevices(lostDeviceRequest,operator, request);
		return genricResponse;
	}
}
