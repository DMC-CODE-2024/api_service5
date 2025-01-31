package com.gl.mdr.feature.appanalyticsuploader.rest_controller;



import com.gl.mdr.feature.appanalyticsuploader.service.AppAnalyticsSave;
import com.gl.mdr.feature.appanalyticsuploader.service.AppAnalyticsUploaderExportService;
import com.gl.mdr.feature.appanalyticsuploader.service.AppAnalyticsUploaderPagingService;

import com.gl.mdr.model.audit.AppAnalyticsEntity;
import com.gl.mdr.model.generic.GenricResponse;

import com.gl.mdr.repo.audit.AppAnalyticsUploaderRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/app-analytics-uploader")
public class AppAnalyticsUploaderRestController {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private final AppAnalyticsUploaderPagingService appAnalyticsUploaderPagingService;
    private final AppAnalyticsUploaderExportService appAnalyticsUploaderExportService;
    private final AppAnalyticsUploaderRepository appAnalyticsUploaderRepository;

    public AppAnalyticsUploaderRestController(AppAnalyticsUploaderPagingService appAnalyticsUploaderPagingService, AppAnalyticsUploaderExportService appAnalyticsUploaderExportService, AppAnalyticsUploaderRepository appAnalyticsUploaderRepository) {
        this.appAnalyticsUploaderPagingService = appAnalyticsUploaderPagingService;
        this.appAnalyticsUploaderExportService = appAnalyticsUploaderExportService;
        this.appAnalyticsUploaderRepository = appAnalyticsUploaderRepository;
    }

    @Autowired
    AppAnalyticsSave AppAnalyticsSave;


    @Tag(name = "App Analytics Data Uploader", description = "Customer Care Module API")
    @Operation(
            summary = "Fetch all record",
            description = "Fetches all record entities and their data from data source")
    @PostMapping("/paging")
    public MappingJacksonValue paging(@RequestBody AppAnalyticsEntity appAnalyticsEntity, @RequestParam(value = "pageNo", defaultValue = "0") int pageNo, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        logger.info("App Analytics Entity payload for paging [" + appAnalyticsEntity + "]");
        return appAnalyticsUploaderPagingService.paging(appAnalyticsEntity, pageNo, pageSize);
    }

    @Tag(name = "App Analytics Data Uploader", description = "Customer Care Module API")
    @Operation(
            summary = "Fetch single record based on Id",
            description = "Fetches record based on Id from data source")
    @PostMapping
    public MappingJacksonValue findByID(@RequestBody AppAnalyticsEntity appAnalyticsEntity) {
        return new MappingJacksonValue(appAnalyticsUploaderPagingService.find(appAnalyticsEntity));
    }

    @Tag(name = "App Analytics Data Uploader", description = "Customer Care Module API")
    @Operation(
            summary = "Export csv file",
            description = "Fetches device entities and their associated data from the data source, with the number of records limited to a configurable parameter, up to a maximum of 50,000. Subsequently, generate a .csv file containing the retrieved data.")
    @PostMapping("/export")
    public MappingJacksonValue export(@RequestBody AppAnalyticsEntity appAnalyticsEntity) {
        logger.info("App Analytics Entity payload for export [" + appAnalyticsEntity + "]");
        return appAnalyticsUploaderExportService.export(appAnalyticsEntity);
    }

    @Tag(name = "App Analytics Data Uploader", description = "Customer Care Module API")
    @Operation(
            summary = "Add csv report to the data source",
            description = "Add the csv report based on the received request")
    @RequestMapping(path = "/appAnalyticsUploaderSave", method = RequestMethod.POST)
    public GenricResponse saveReport(@RequestBody AppAnalyticsEntity appAnalyticsEntity) {
        logger.info("Processing App Analytics Entity = " + appAnalyticsEntity);
        logger.info("App Analytics Request = " + appAnalyticsEntity);
        GenricResponse genricResponse = AppAnalyticsSave.saveFile(appAnalyticsEntity);
        appAnalyticsUploaderPagingService.saveWebActionDBEntity(appAnalyticsEntity);
        logger.info("App Analytics Response = " + genricResponse);

        return genricResponse;
    }

}
