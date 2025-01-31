package com.gl.mdr.feature.appanalyticsuploader.export;


import com.gl.mdr.configuration.PropertiesReader;
import com.gl.mdr.exceptions.ResourceServicesException;
import com.gl.mdr.feature.appanalyticsuploader.csv_file_model.AppAnalyticsUploaderFileModel;
import com.gl.mdr.feature.appanalyticsuploader.extras.sysparam.ConfigTags;
import com.gl.mdr.feature.appanalyticsuploader.extras.sysparam.ConfigurationManagementServiceImpl;
import com.gl.mdr.feature.appanalyticsuploader.paging.AppAnalyticsUploaderPaging;
import com.gl.mdr.model.app.SystemConfigurationDb;
import com.gl.mdr.model.audit.AppAnalyticsEntity;
import com.gl.mdr.model.file.FileDetails;
import com.gl.mdr.util.CustomMappingStrategy;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class AppAnalyticsUploaderExport { private final Logger logger = LogManager.getLogger(this.getClass());
    private ConfigurationManagementServiceImpl configurationManagementServiceImpl;
    private AppAnalyticsUploaderPaging appAnalyticsUploaderPaging;

    public AppAnalyticsUploaderExport(ConfigurationManagementServiceImpl configurationManagementServiceImpl, AppAnalyticsUploaderPaging appAnalyticsUploaderPaging) {
        this.configurationManagementServiceImpl = configurationManagementServiceImpl;
        this.appAnalyticsUploaderPaging = appAnalyticsUploaderPaging;
    }

    @Autowired
    private PropertiesReader propertiesReader;

    public FileDetails export(AppAnalyticsEntity appAnalyticsEntity, String subFeature) {
        String fileName = null;
        Writer writer = null;
        Integer pageNo = 0;
        Integer pageSize = Integer.valueOf(configurationManagementServiceImpl.findByTag("file.max-file-record").getValue());

        AppAnalyticsUploaderFileModel fileModel = null;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

        SystemConfigurationDb filepath = configurationManagementServiceImpl.findByTag(ConfigTags.file_download_dir);
        SystemConfigurationDb link = configurationManagementServiceImpl.findByTag(ConfigTags.file_download_link);
        logger.info("File Path :  [" + filepath + "] & Link : [" + link + "]");

        StatefulBeanToCsvBuilder<AppAnalyticsUploaderFileModel> builder = null;
        StatefulBeanToCsv<AppAnalyticsUploaderFileModel> csvWriter = null;
        List<AppAnalyticsUploaderFileModel> fileRecords = null;
        CustomMappingStrategy<AppAnalyticsUploaderFileModel> mappingStrategy = new CustomMappingStrategy<>();

        try {
            List<AppAnalyticsEntity> list = appAnalyticsUploaderPaging.findAll(appAnalyticsEntity, pageNo, pageSize).getContent();
            fileName = LocalDateTime.now().format(dtf2).replace(" ", "_") + "_" + subFeature + ".csv";
            writer = Files.newBufferedWriter(Paths.get(filepath.getValue() + fileName));
            mappingStrategy.setType(AppAnalyticsUploaderFileModel.class);

            builder = new StatefulBeanToCsvBuilder<>(writer);
            csvWriter = builder.withMappingStrategy(mappingStrategy).withSeparator(',').withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER).build();
            if (list.size() > 0) {
                fileRecords = new ArrayList<AppAnalyticsUploaderFileModel>();
                for (AppAnalyticsEntity data : list) {
                    fileModel = new AppAnalyticsUploaderFileModel()
                            .setId(data.getId())
                            .setUploadedBy(data.getUploadedBy())
                            .setUploadedOn(data.getUploadedOn())
                            .setOsType(data.getOsType())
                            .setReportType(data.getReportType())
                            .setInsertCount(data.getInsertCount())
                            .setStatus(data.getStatus())
                            .setTransactionId(data.getTransactionId())
                            .setReason(data.getReason())
                            .setSourceFileName(data.getSourceFileName());
                    fileRecords.add(fileModel);
                }
                logger.info("Exported data : [" + fileRecords + "]");
                csvWriter.write(fileRecords);
            } else {
                csvWriter.write(new AppAnalyticsUploaderFileModel());
            }
            logger.info("fileName [" + fileName + "] filePath [" + filepath + "] download link [" + link.getValue() + "]");

            FileDetails fileDetails = new FileDetails(fileName, filepath.getValue(), link.getValue().replace("$LOCAL_IP", propertiesReader.localIp) + fileName);
            logger.info("export file Details [" + fileDetails + "]");
            return fileDetails;

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
        } finally {
            try {

                if (writer != null) writer.close();
            } catch (IOException e) {
            }
        }
    }
}
