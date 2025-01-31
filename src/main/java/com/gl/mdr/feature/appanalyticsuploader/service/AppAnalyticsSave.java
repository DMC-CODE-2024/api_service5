package com.gl.mdr.feature.appanalyticsuploader.service;


import com.gl.mdr.model.audit.AppAnalyticsEntity;
import com.gl.mdr.model.generic.GenricResponse;
import com.gl.mdr.repo.audit.AppAnalyticsUploaderRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class AppAnalyticsSave {
    private static final Logger logger = LogManager.getLogger(AppAnalyticsSave.class);

    private final AppAnalyticsUploaderRepository appAnalyticsUploaderRepository;
    public AppAnalyticsSave(AppAnalyticsUploaderRepository appAnalyticsUploaderRepository) {
        this.appAnalyticsUploaderRepository = appAnalyticsUploaderRepository;
    }



    public GenricResponse saveFile(AppAnalyticsEntity appAnalyticsEntity) {
        GenricResponse genricResponse = new GenricResponse();
        logger.info("save report request in service impl = " + appAnalyticsEntity);


        /*stolenLostModel.setStatus("INIT_START");
        stolenLostModel.setUserStatus("INIT_START");*/
        //saving in stolen_mgmt table
        appAnalyticsUploaderRepository.save(appAnalyticsEntity);
        /// calling Notification API

        logger.info("-------response returned=" + genricResponse);
        return genricResponse;
    }
}
