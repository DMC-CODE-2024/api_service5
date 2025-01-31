package com.gl.mdr.controller;

import java.util.List;

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

import com.gl.mdr.model.app.MobileDeviceRepository;
import com.gl.mdr.model.oam.MobileDeviceRepositoryHistory;
import com.gl.mdr.model.file.FileDetails;
import com.gl.mdr.model.filter.MobileDeviceRepositoryFilterRequest;
import com.gl.mdr.model.filter.RepositoryHistoryFilterRequest;
import com.gl.mdr.model.generic.DashboardData;
import com.gl.mdr.model.generic.MDRGenricResponse;
import com.gl.mdr.model.generic.UserDetails;
import com.gl.mdr.service.impl.MobileDeviceRepositoryServiceImpl;



@RestController
public class MobileDeviceRepositoryController {

	private static final Logger logger = LogManager.getLogger(MobileDeviceRepositoryController.class);

	@Autowired
	MobileDeviceRepositoryServiceImpl mdrServiceImpl;
	

	@Tag(name = "Device Management", description = "MDR Module API")
	@Operation(
			summary = "Fetch all record",
			description = "Fetches all device entities and their data from data source")
	@PostMapping("/getDevicesDetails")
	public MappingJacksonValue getDevicesDetails(@RequestBody MobileDeviceRepositoryFilterRequest filterRequest,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
		MappingJacksonValue mapping = null;
		logger.info("MDR filter request:["+filterRequest.toString()+"]");
		Page<MobileDeviceRepository>  mdrs =  mdrServiceImpl.getDevicesDetails(filterRequest, pageNo, pageSize);
		mapping = new MappingJacksonValue(mdrs);
		return mapping;
	}

	@Tag(name = "Device Management", description = "MDR Module API")
	@Operation(
			summary = "Add record to the data source",
			description = "Add the Device based on the received request")
	@RequestMapping(path = "/addDevice", method = {RequestMethod.POST})
	public MDRGenricResponse addDevice(@RequestBody MobileDeviceRepository deviceInfo) {
		MDRGenricResponse genricResponse = mdrServiceImpl.addDevice(deviceInfo);
		return genricResponse;
	}

	@Tag(name = "Device Management", description = "MDR Module API")
	@Operation(
			summary = "Update record to the data source",
			description = "Update the Device based on the received request")
	@RequestMapping(path = "/updateDevices", method = {RequestMethod.POST})
	public MDRGenricResponse updateDevices(@RequestBody List<MobileDeviceRepository> devicesInfo) {
		MDRGenricResponse genricResponse = mdrServiceImpl.updateDevices(devicesInfo);
		return genricResponse;
	}

	@Tag(name = "Device Management", description = "MDR Module API")
	@Operation(
			summary = "Delete record to the data source",
			description = "Delete the Device based on the received request")
	@RequestMapping(path = "/deleteDevice", method = {RequestMethod.POST})
	public MDRGenricResponse deleteDevice(@RequestParam("userId") Integer userId,
			@RequestParam("deviceId") String deviceId,
			@RequestParam(value = "remark", defaultValue="NA") String remark,
			@RequestParam(value = "publicIp", defaultValue="NA") String publicIp,
			@RequestParam(value = "browser", defaultValue="NA") String browser,
			@RequestParam(value = "userType", defaultValue="NA") String userType,
			@RequestParam(value = "userTypeId", defaultValue = "0") Integer userTypeId,
			@RequestParam(value = "featureId", defaultValue = "0") Integer featureId
			) {
		MDRGenricResponse genricResponse = mdrServiceImpl.deleteDevice(deviceId, remark, publicIp,
				browser, userId, userType, userTypeId, featureId);
		return genricResponse;
	}

	@Tag(name = "Device Management", description = "MDR Module API")
	@Operation(
			summary = "Fetch single record based on Id",
			description = "Fetches record based on Id from data source")
	@RequestMapping(path = "/getDeviceInfo", method = {RequestMethod.POST})
	public MappingJacksonValue getDeviceInfo(
			@RequestParam("userId") Integer userId,
			@RequestParam("deviceId") String deviceId,
			@RequestParam(value = "publicIp", defaultValue="NA") String publicIp,
			@RequestParam(value = "browser", defaultValue="NA") String browser,
			@RequestParam(value = "userType", defaultValue="NA") String userType,
			@RequestParam(value = "userTypeId", defaultValue = "0") Integer userTypeId,
			@RequestParam(value = "featureId", defaultValue = "0") Integer featureId
			) {
		MappingJacksonValue mapping = null;
		MobileDeviceRepositoryFilterRequest filterRequest = new MobileDeviceRepositoryFilterRequest();
		filterRequest.setUserId(userId);
		filterRequest.setDeviceId(deviceId);
		filterRequest.setPublicIp(publicIp);
		filterRequest.setBrowser(browser);
		filterRequest.setUserType(userType);
		filterRequest.setUserTypeId(userTypeId);
		filterRequest.setFeatureId(featureId);
		MobileDeviceRepository  mdr =  (MobileDeviceRepository) mdrServiceImpl.getDevicesDetails(filterRequest, 0, 10).getContent().get(0);
		mapping = new MappingJacksonValue(mdr);
		return mapping;
	}

	@Tag(name = "Device Management", description = "MDR Module API")
	@Operation(
			summary = "Fetch all record history",
			description = "Fetches all record history from data source")
	@RequestMapping(path = "/getDeviceHistory", method = {RequestMethod.POST})
	public MappingJacksonValue getDevieHistory(@RequestBody RepositoryHistoryFilterRequest filterRequest,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "50") Integer pageSize) {
		MappingJacksonValue mapping = null;
		Page<MobileDeviceRepositoryHistory> mdrs = mdrServiceImpl.getFilterMDRHistory(filterRequest, pageNo, pageSize);
		mapping = new MappingJacksonValue(mdrs);
		return mapping;
	}

	@Tag(name = "Device Management", description = "MDR Module API")
	@Operation(
			summary = "Fetch record history based on Id",
			description = "Fetches record history based on Id from data source")
	@RequestMapping(path = "/getDeviceHistoryInfo", method = {RequestMethod.POST})
	public MappingJacksonValue getDeviceHistoryInfo(
			@RequestParam("userId") Integer userId,
			@RequestParam("deviceId") String deviceId,
			@RequestParam("rowId") Integer id,
			@RequestParam(value = "publicIp", defaultValue="NA") String publicIp,
			@RequestParam(value = "browser", defaultValue="NA") String browser,
			@RequestParam(value = "userType", defaultValue="NA") String userType,
			@RequestParam(value = "userTypeId", defaultValue = "0") Integer userTypeId,
			@RequestParam(value =
					"featureId", defaultValue = "0") Integer featureId
			) {
		MappingJacksonValue mapping = null;
		RepositoryHistoryFilterRequest filterRequest = new RepositoryHistoryFilterRequest();
		filterRequest.setUserId(userId);
		filterRequest.setId(id);
		filterRequest.setDeviceId(deviceId);
		filterRequest.setPublicIp(publicIp);
		filterRequest.setBrowser(browser);
		filterRequest.setUserType(userType);
		filterRequest.setUserTypeId(userTypeId);
		filterRequest.setFeatureId(featureId);
		MobileDeviceRepositoryHistory  mdr =  (MobileDeviceRepositoryHistory) mdrServiceImpl.getFilterMDRHistoryRowInfo(filterRequest);
		mapping = new MappingJacksonValue(mdr);
		return mapping;
	}

	@Tag(name = "Device Management", description = "MDR Module API")
	@Operation(
			summary = "Export csv file",
			description = "Fetches device entities and their associated data from the data source, with the number of records limited to a configurable parameter, up to a maximum of 50,000. Subsequently, generate a .csv file containing the retrieved data.")
	@PostMapping("/exportData")
	public MappingJacksonValue exportData(@RequestBody MobileDeviceRepositoryFilterRequest filterRequest,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
		MappingJacksonValue mapping = null;
		logger.info("MDR export request:["+filterRequest.toString()+"]");
		FileDetails fileDetails = mdrServiceImpl.exportData(filterRequest, pageNo, pageSize);
		mapping = new MappingJacksonValue(fileDetails);
		logger.info("MDR export response:["+fileDetails.toString()+"]");
		return mapping;
	}

	@Tag(name = "MDR Dashboard", description = "MDR Module API")
	@Operation(
			summary = "Fetch all state count of record",
			description = "Fetches count of all device entities with their state from data source")
	@RequestMapping(path = "/getMDRDashboardData", method = {RequestMethod.GET})
	public MappingJacksonValue getMDRDashboardData(
			@RequestParam("userId") Integer userId,
			@RequestParam(value = "publicIp", defaultValue="NA") String publicIp,
			@RequestParam(value = "browser", defaultValue="NA") String browser,
			@RequestParam(value = "userType", defaultValue="NA") String userType,
			@RequestParam(value = "userTypeId", defaultValue = "0") Integer userTypeId,
			@RequestParam(value = "featureId", defaultValue = "0") Integer featureId
			) {
		MappingJacksonValue response = new MappingJacksonValue(mdrServiceImpl.getMDRDashboardData(publicIp, browser,
				userId, userType, userTypeId, featureId));
		return response;
	}

	@Tag(name = "Device Management", description = "MDR Module API")
	@Operation(
			summary = "Fetch distinct value from the data source",
			description = "Fetch distinct values for the user name based on the received request")
	@GetMapping("/getDistinctUserName")
	public ResponseEntity<?> getDistinctUserName() {
		List<UserDetails>  list = mdrServiceImpl.getUserData();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@Tag(name = "Device Management", description = "MDR Module API")
	@Operation(
			summary = "Fetch distinct value from the data source",
			description = "Fetch distinct values for the device type based on the received request")
	@GetMapping("/getDistinctDeviceType")
	public ResponseEntity<?> getDistinctDeviceType() {
		List<String>  list = mdrServiceImpl.getDeviceType();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@Tag(name = "Device Management", description = "MDR Module API")
	@Operation(
			summary = "Fetch distinct value from the data source",
			description = "Fetch distinct values for the manufacture country based on the received request")
	@GetMapping("/getManufacturerCountry")
	public ResponseEntity<?> getManufacturerCountry() {
		List<String>  list = mdrServiceImpl.getManufacturerCountry();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
}