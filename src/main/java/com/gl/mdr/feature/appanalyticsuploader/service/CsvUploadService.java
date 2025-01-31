package com.gl.mdr.feature.appanalyticsuploader.service;/*
package com.glocks.application.features.appanalyticsuploader.service;

import com.glocks.application.entity.aud.AppAnalyticsEntity;
import com.glocks.application.repository.aud.AppAnalyticsUploaderRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CsvUploadService {

    private final Logger logger = LogManager.getLogger(CsvUploadService.class);

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AppAnalyticsUploaderRepository analyticsRepository;

    // Map of report types to their respective table names
    private static final Map<String, String> REPORT_TABLE_MAP = Map.ofEntries(
            Map.entry("Android - Daily Active Devices", "android_dailyactivedevices"),
            Map.entry("Android - Device Store Listing Impressions", "android_devicestorelistingimpressions"),
            Map.entry("Android - Uninstall Events", "android_uninstallevents"),
            Map.entry("Android - Total Installations by App Version", "android_totalinstallationsbyappversion"),
            Map.entry("iOS - Active Devices by App Version", "ios_activedevicesbyappversion"),
            Map.entry("iOS - Active Devices by Device", "ios_activedevicesbydevice"),
            Map.entry("iOS - Deletions by App Version", "ios_deletionsbyappversion"),
            Map.entry("iOS - Deletions by Device", "ios_deletionsbydevice"),
            Map.entry("iOS - Device Store Listing Impressions by Device", "ios_devicestorelistingimpressionsbydevice"),
            Map.entry("iOS - Total Downloads by Device", "ios_totaldownloadsbydevice"),
            Map.entry("iOS - Total Installations by App Version", "ios_totalinstallationsbyappversion"),
            Map.entry("iOS - Total Installations by Device", "ios_totalinstallationsbydevice")
    );


    public void processCsv(MultipartFile file, String reportOs, String reportType) throws Exception {
        List<Map<String, Object>> csvData;

        if (requiresHeaderSkipping(reportType)) {
            csvData = parseCsvSkippingHeader(file);
        } else if (requiresRowSkipping(reportType)) {
            csvData = parseCsvSkippingRows(file, 4); // Skip first 4 rows
        } else {
            csvData = parseCsv(file);
        }

        List<Map<String, Object>> transformedData = transformData(reportType, csvData);

        int insertCount = insertData(reportType, transformedData);

        saveMetadata(file, reportOs, reportType, insertCount);
    }

    private boolean requiresHeaderSkipping(String reportType) {
        return List.of(
                "Android - Daily Active Devices",
                "Android - Device Store Listing Impressions",
                "Android - Uninstall Events"
        ).contains(reportType);
    }

    private boolean requiresRowSkipping(String reportType) {
        return List.of(
                "iOS - Active Devices by App Version",
                "iOS - Active Devices by Device",
                "iOS - Deletions by App Version",
                "iOS - Deletions by Device",
                "iOS - Device Store Listing Impressions by Device",
                "iOS - Total Downloads by Device",
                "iOS - Total Installations by App Version",
                "iOS - Total Installations by Device"
        ).contains(reportType);
    }

    private List<Map<String, Object>> parseCsvSkippingHeader(MultipartFile file) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            br.readLine(); // Skip the header
            return parseCsv(br);
        }
    }

    private List<Map<String, Object>> parseCsvSkippingRows(MultipartFile file, int rowsToSkip) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            for (int i = 0; i < rowsToSkip; i++) {
                br.readLine(); // Skip rows
            }
            return parseCsv(br);
        }
    }

    private List<Map<String, Object>> parseCsv(MultipartFile file) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            return parseCsv(br);
        }
    }

    private List<Map<String, Object>> parseCsv(BufferedReader br) throws Exception {
        List<Map<String, Object>> records = new ArrayList<>();
        String[] headers = br.readLine().split(",");
        String line;

        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            Map<String, Object> record = new HashMap<>();
            for (int i = 0; i < headers.length; i++) {
                record.put(headers[i].trim(), values[i].trim());
            }
            records.add(record);
        }
        return records;
    }

    private List<Map<String, Object>> transformData(String reportType, List<Map<String, Object>> data) {
        if (reportType.equals("Android - Total Installations by App Version") ||
                reportType.startsWith("iOS - Total Installations")) {
            return transformVersionData(data);
        }
        if (reportType.startsWith("iOS - Active Devices") || reportType.startsWith("iOS - Deletions")) {
            return transformDeviceData(data);
        }
        return data;
    }

    private List<Map<String, Object>> transformVersionData(List<Map<String, Object>> data) {
        List<Map<String, Object>> transformed = new ArrayList<>();
        for (Map<String, Object> record : data) {
            String date = (String) record.get("Date");
            for (Map.Entry<String, Object> entry : record.entrySet()) {
                if (!entry.getKey().equals("Date") && !entry.getKey().equals("Notes")) {
                    Map<String, Object> transformedRecord = new HashMap<>();
                    transformedRecord.put("install_version", entry.getKey());
                    transformedRecord.put("install_date", LocalDate.parse(date));
                    transformedRecord.put("install_count", Integer.parseInt(entry.getValue().toString()));
                    transformed.add(transformedRecord);
                }
            }
        }
        return transformed;
    }

    private List<Map<String, Object>> transformDeviceData(List<Map<String, Object>> data) {
        List<Map<String, Object>> transformed = new ArrayList<>();
        for (Map<String, Object> record : data) {
            String date = (String) record.get("Date");
            for (Map.Entry<String, Object> entry : record.entrySet()) {
                if (!entry.getKey().equals("Date") && !entry.getKey().equals("Notes")) {
                    Map<String, Object> transformedRecord = new HashMap<>();
                    transformedRecord.put("device_name", entry.getKey());
                    transformedRecord.put("event_date", LocalDate.parse(date));
                    transformedRecord.put("event_total", Integer.parseInt(entry.getValue().toString()));
                    transformed.add(transformedRecord);
                }
            }
        }
        return transformed;
    }

    private int insertData(String reportType, List<Map<String, Object>> data) {
        String tableName = REPORT_TABLE_MAP.get(reportType);
        if (tableName == null || data.isEmpty()) {
            logger.warn("No data or invalid report type: {}", reportType);
            return 0;
        }

        String sql = buildInsertQuery(tableName, data.get(0).keySet());
        logger.info("Executing SQL: {}", sql);

        int count = 0;
        for (Map<String, Object> record : data) {
            Query query = entityManager.createNativeQuery(sql);
            record.forEach(query::setParameter);
            query.executeUpdate();
            count++;
        }
        return count;
    }

    private String buildInsertQuery(String tableName, Set<String> columnNames) {
        String columns = String.join(", ", columnNames);
        String placeholders = columnNames.stream().map(name -> ":" + name).collect(Collectors.joining(", "));
        return String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, columns, placeholders);
    }

    private void saveMetadata(MultipartFile file, String reportOs, String reportType, int insertCount) {
        AppAnalyticsEntity analytics = new AppAnalyticsEntity();
        analytics.setUploadedBy("System User");
        analytics.setUploadedOn(new Timestamp(System.currentTimeMillis()).toLocalDateTime());
        analytics.setReportOs(reportOs);
        analytics.setReportType(reportType);
        analytics.setInsertCount(insertCount);
        analytics.setSourceFileName(file.getOriginalFilename());
        analyticsRepository.save(analytics);
        logger.info("Metadata saved for report type: {}", reportType);
    }
}















































*/
/*
package com.glocks.application.features.appanalyticsuploader.service;

import com.glocks.application.entity.aud.AppAnalyticsEntity;
import com.glocks.application.repository.aud.AppAnalyticsUploaderRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CsvUploadService {

    private final Logger logger = LogManager.getLogger(CsvUploadService.class);

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AppAnalyticsUploaderRepository analyticsRepository;

    public void processCsv(MultipartFile file, String reportOs, String reportType) throws Exception {
        List<Map<String, Object>> csvData = parseCsv(file);
        int insertCount = insertDataIntoTable(reportType, csvData);

        AppAnalyticsEntity analytics = new AppAnalyticsEntity();
        analytics.setUploadedBy("System User");
        analytics.setUploadedOn(new Timestamp(System.currentTimeMillis()));
        analytics.setReportOs(reportOs);
        analytics.setReportType(reportType);
        analytics.setInsertCount(insertCount);
        analytics.setSourceFileName(file.getOriginalFilename());

        analyticsRepository.save(analytics);
        logger.info("Metadata saved for report type: {}", reportType);
    }

    private List<Map<String, Object>> parseCsv(MultipartFile file) throws Exception {
        List<Map<String, Object>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String[] headers = br.readLine().split(",");
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Map<String, Object> record = new HashMap<>();
                for (int i = 0; i < headers.length; i++) {
                    record.put(headers[i], values[i]);
                }
                records.add(record);
            }
        }
        logger.info("Parsed {} records from CSV file.", records.size());
        return records;
    }

    private int insertDataIntoTable(String tableName, List<Map<String, Object>> data) {
        if (data.isEmpty()) {
            logger.warn("No data to insert for table: {}", tableName);
            return 0;
        }

        String columns = String.join(", ", data.get(0).keySet());
        String placeholders = data.get(0).keySet().stream()
                .map(key -> ":" + key)
                .collect(Collectors.joining(", "));

        String sql = String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, columns, placeholders);
        logger.info("Generated SQL: {}", sql);

        int count = 0;
        for (Map<String, Object> record : data) {
            Query query = entityManager.createNativeQuery(sql);
            record.forEach(query::setParameter);
            query.executeUpdate();
            count++;
        }
        logger.info("Inserted {} rows into table: {}", count, tableName);
        return count;
    }
}
*//*


*/
