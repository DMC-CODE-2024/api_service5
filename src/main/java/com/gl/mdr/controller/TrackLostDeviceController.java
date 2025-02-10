package com.gl.mdr.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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



@RestController
public class TrackLostDeviceController {
	private static final Logger logger = LogManager.getLogger(TrackLostDeviceController.class);

	@Autowired
	TrackLostDeviceServiceImpl trackLostDeviceServiceImpl;

	@Tag(name = "Track lost devices", description = "Track lost device request")
	@Operation(
			summary = "Fetch all record",description = "Get list of track lost devices")
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



	@Tag(name = "Track lost devices", description = "Track lost device request")
	@Operation(
			summary = "Export  all record",description = "Download list of track lost devices in to csv file.")
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



	@Tag(name = "Track lost devices", description = "Track lost device request")
	@Operation(
			summary = "Get record by request id",description = "Get record of particular request to view the details of device.")
	@PostMapping("/getTrackLostData")
	public ResponseEntity<?> getTrackLostData(@RequestBody TrackLostDeviceFilterRequest filterRequest ){
		return trackLostDeviceServiceImpl.getTrackLostData(filterRequest);
	}


	@Tag(name = "Track lost devices", description = "Track lost device request")
	@Operation(
			summary = "Operator names list",description = "Get record of operator name to filter the particular operator in track lost device.")
	@GetMapping("/getDistinctOperator")
	public ResponseEntity<?> getDistinctOperator() {
		List<String>  list = trackLostDeviceServiceImpl.getDistinctOperator();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}


	@Tag(name = "Track lost devices", description = "Track lost device request")
	@Operation(
			summary = "Device Status list",description = "Get record of status to filter the particular operator in track lost device.")
	@GetMapping("/getDistinctStatus")
	public ResponseEntity<?> getDistinctStatus() {
		List<String>  list = trackLostDeviceServiceImpl.getDistinctStatus();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}


	@Tag(name = "Track lost devices", description = "Track lost device request")
	@Operation(
			summary = "Device request type list",description = "Get record of request type to filter the particular operator in track lost device.")
	@GetMapping("/getTrackLostRequestType")
	public ResponseEntity<?> getTrackLostRequestType() {
		List<String>  list = trackLostDeviceServiceImpl.getTrackLostRequestType();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}


	@Tag(name = "Track lost devices", description = "Track lost device request")
	@Operation(
			summary = "insert in track lost",description = "Insert Track Lost Devices Details.")
	@RequestMapping(path = "tracklost/device/{operator}", method = RequestMethod.POST)
	public GenricResponse setTrackLostDevices(@RequestBody LostDeviceRequest lostDeviceRequest, @PathVariable String operator, HttpServletRequest request,HttpServletResponse response ){
		logger.info("set Track Lost Devices Details Request = " +lostDeviceRequest.toString() );
		GenricResponse genricResponse =trackLostDeviceServiceImpl.setTrackLostDevices(lostDeviceRequest,operator, request);
		return genricResponse;
	}
}
