package com.gl.mdr.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gl.mdr.model.app.ListFileManagementModel;
import com.gl.mdr.model.file.FileDetails;
import com.gl.mdr.model.filter.ListFileFilterRequest;
import com.gl.mdr.service.impl.OperatorFileMagamentServiceImpl;


@RestController
public class OperatorFileManagmentController {
	private static final Logger logger = LogManager.getLogger(OperatorFileManagmentController.class);

	@Autowired
	OperatorFileMagamentServiceImpl operatorFileMagamentServiceImpl;

	//@ApiOperation(value = "get Operator List File Wise", response = ListFileManagementModel.class)
	@Tag(name = "Operator File List", description = "Operator File List")
	@Operation(
			summary = "Feature is no longer in use",
			description = "This API is no longer in use")
	@PostMapping("/getOperatorFileDetails")
	public MappingJacksonValue getPoliceVerificationDevicesDetails(@RequestBody ListFileFilterRequest filterRequest,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
		MappingJacksonValue mapping = null;
		logger.info("Operator List File Wisefilter request:["+filterRequest.toString()+"]");
		Page<ListFileManagementModel> listFileManagementModel =  operatorFileMagamentServiceImpl.getOperatorFileDetails(filterRequest, pageNo, pageSize,"View");
		mapping = new MappingJacksonValue(listFileManagementModel);
		return mapping;
	}

	
	//@ApiOperation(value = "Export get Operator List File Wise", response = ListFileManagementModel.class)
	@Tag(name = "Operator File List", description = "Operator File List")
	@Operation(
			summary = "Feature is no longer in use",
			description = "This API is no longer in use")
	@PostMapping("/exportOperatorListFileData")
	public MappingJacksonValue exportData(@RequestBody ListFileFilterRequest filterRequest,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
		MappingJacksonValue mapping = null;
		logger.info("Operator List File Wise export request:["+filterRequest.toString()+"]");
		FileDetails fileDetails = operatorFileMagamentServiceImpl.exportData(filterRequest);
		mapping = new MappingJacksonValue(fileDetails);
		logger.info("Operator List File Wise export response:["+fileDetails.toString()+"]");
		return mapping;
	}
	
}
