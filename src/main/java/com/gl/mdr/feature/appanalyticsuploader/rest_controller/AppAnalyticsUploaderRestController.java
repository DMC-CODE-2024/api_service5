package com.gl.mdr.feature.appanalyticsuploader.rest_controller;



import com.gl.mdr.feature.appanalyticsuploader.service.AppAnalyticsSave;
import com.gl.mdr.feature.appanalyticsuploader.service.AppAnalyticsUploaderExportService;
import com.gl.mdr.feature.appanalyticsuploader.service.AppAnalyticsUploaderPagingService;

import com.gl.mdr.model.audit.AppAnalyticsEntity;
import com.gl.mdr.model.generic.GenricResponse;

import com.gl.mdr.repo.audit.AppAnalyticsUploaderRepository;
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
    //private final CsvUploadService csvUploadService;
    /*private final AppAnalyticsSave appAnalyticsSave;*/
    //private final FileProcessorService fileProcessorService;
    private final AppAnalyticsUploaderRepository appAnalyticsUploaderRepository;

    public AppAnalyticsUploaderRestController(AppAnalyticsUploaderPagingService appAnalyticsUploaderPagingService, AppAnalyticsUploaderExportService appAnalyticsUploaderExportService, AppAnalyticsUploaderRepository appAnalyticsUploaderRepository) {
        this.appAnalyticsUploaderPagingService = appAnalyticsUploaderPagingService;
        this.appAnalyticsUploaderExportService = appAnalyticsUploaderExportService;
        //this.csvUploadService = csvUploadService;
        /*this.appAnalyticsSave = appAnalyticsSave;*/
        //this.fileProcessorService = fileProcessorService;
        this.appAnalyticsUploaderRepository = appAnalyticsUploaderRepository;
    }

    @Autowired
    AppAnalyticsSave AppAnalyticsSave;



    @PostMapping("/paging")
    public MappingJacksonValue paging(@RequestBody AppAnalyticsEntity appAnalyticsEntity, @RequestParam(value = "pageNo", defaultValue = "0") int pageNo, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        logger.info("App Analytics Entity payload for paging [" + appAnalyticsEntity + "]");
        return appAnalyticsUploaderPagingService.paging(appAnalyticsEntity, pageNo, pageSize);
    }

    @PostMapping
    public MappingJacksonValue findByID(@RequestBody AppAnalyticsEntity appAnalyticsEntity) {
        return new MappingJacksonValue(appAnalyticsUploaderPagingService.find(appAnalyticsEntity));
    }

    @PostMapping("/export")
    public MappingJacksonValue export(@RequestBody AppAnalyticsEntity appAnalyticsEntity) {
        logger.info("App Analytics Entity payload for export [" + appAnalyticsEntity + "]");
        return appAnalyticsUploaderExportService.export(appAnalyticsEntity);
    }

    /*@RequestMapping(path = "/appAnalyticsUploaderSave", method = RequestMethod.POST)
    public GenricResponse saveStolenDevice(@RequestBody AppAnalyticsEntity appAnalyticsEntity) {


        String fileUrl = appAnalyticsEntity.getUrl();
        String reportType = appAnalyticsEntity.getReportType();

        logger.info("App Analytics Request = " + appAnalyticsEntity);
        GenricResponse genricResponse = AppAnalyticsSave.saveFile(appAnalyticsEntity);
        logger.info("App Analytics Response = " + genricResponse);
        return genricResponse;
    }*/
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
