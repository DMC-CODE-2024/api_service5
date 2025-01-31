package com.gl.mdr.feature.appanalyticsuploader.service;/*
package com.glocks.application.features.appanalyticsuploader.service;

import com.glocks.application.entity.rep.*;
import com.glocks.application.repository.rep.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileProcessorService {
    @Autowired
    private AndroidDailyActiveDevicesRepository androidDailyActiveDevicesRepository;
    @Autowired
    private AndroidUninstallEventsRepository androidUninstallEventsRepository;
    @Autowired
    private AndroidDeviceStoreListingImpressionsRepository androidDeviceStoreListingImpressionsRepository;
    @Autowired
    private AndroidTotalInstallationsByAppVersionRepository androidTotalInstallationsByAppVersionRepository;
    @Autowired
    private IosActiveDevicesByAppVersionRepository iosActiveDevicesByAppVersionRepository;
    @Autowired
    private IosActiveDevicesByDeviceRepository iosActiveDevicesByDeviceRepository;
    @Autowired
    private IosDeletionsByAppVersionRepository iosDeletionsByAppVersionRepository;
    @Autowired
    private IosDeletionsByDeviceRepository iosDeletionsByDeviceRepository;
    @Autowired
    private IosTotalDownloadsByDeviceRepository iosTotalDownloadsByDeviceRepository;
    @Autowired
    private IosTotalInstallationsByDeviceRepository iosTotalInstallationsByDeviceRepository;
    @Autowired
    private IosTotalInstallationsByAppVersionRepository iosTotalInstallationsByAppVersionRepository;
    @Autowired
    private IosDeviceStoreListingImpressionsByDeviceRepository iosDeviceStoreListingImpressionsByDeviceRepository;


    @Transactional
    public int processFile(String fileUrl, String reportType) throws Exception {
        String filePath = downloadFile(fileUrl);
        String normalizedReportType = reportType.trim().toLowerCase();
        System.out.println("Normalized Report Type: " + normalizedReportType);
        int insertCount = 0;
        switch (normalizedReportType) {
            case "android - daily active devices":
                processAndroidDailyActiveDevices(filePath);
                break;
            case "android - uninstall events":
                processAndroidUninstallEvents(filePath);
                break;
            case "android - device store listing impressions":
                processAndroidDeviceStoreListingImpressions(filePath);
                break;
            case "android - total installations by app version":
                processAndroidTotalInstallationsByAppVersion(filePath);
                break;
            case "ios - active devices by app version":
                processIosActiveDevicesByAppVersion(filePath);
                break;
            case "ios - active devices by device":
                processIosActiveDevicesByDevice(filePath);
                break;
            case "ios - deletions by app version":
                processIosDeletionsByAppVersion(filePath);
                break;
            case "ios - deletions by device":
                processIosDeletionsByDevice(filePath);
                break;
            case "ios - total downloads by device":
                processIosTotalDownloadsByDevice(filePath);
                break;
            case "ios - total installations by device":
                processIosTotalInstallationsByDevice(filePath);
                break;
            case "ios - total installations by app version":
                processIosTotalInstallationsByAppVersion(filePath);
                break;
            case "ios - device store listing impressions by device":
                processIosImpressionsByDevice(filePath);
                break;
            default:
                throw new IllegalArgumentException("Unsupported report type: " + reportType);
        }

        Files.delete(Paths.get(filePath));
        return insertCount;
    }

    private String downloadFile(String serverFilePath) throws Exception {
        // Define a temporary file where the content will be copied
        String tempFilePath = "tempFile.csv";

        // Copy the file from the given server path to the temporary file
        Path sourcePath = Paths.get(serverFilePath);
        Path destinationPath = Paths.get(tempFilePath);

        if (!Files.exists(sourcePath)) {
            throw new FileNotFoundException("The file at path " + serverFilePath + " does not exist.");
        }

        Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);

        return tempFilePath;
    }


    private int processAndroidDailyActiveDevices(String filePath) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        lines.remove(0); // Remove header row
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        int insertCount=0;

        for (String line : lines) {
            String[] values = line.split(",");
            String dateString = values[0].trim();
            String activeDevices = values[1].trim();
            String notes = values.length > 2 ? values[2].trim() : "";

            LocalDate activeDate = null;
            try {
                if (dateString.contains("-")) {
                    activeDate = LocalDate.parse(dateString, formatter1);
                } else if (dateString.contains(",")) {
                    activeDate = LocalDate.parse(dateString, formatter2);
                } else {
                    throw new DateTimeParseException("Unsupported date format", dateString, 0);
                }

                AndroidDailyActiveDevices record = new AndroidDailyActiveDevices();
                record.setActiveDate(activeDate);

                if (activeDevices.equals("-")) {
                    record.setActiveDevicesCount(0);
                } else {
                    record.setActiveDevicesCount(Integer.parseInt(activeDevices));
                }

                record.setNotes(notes);
                androidDailyActiveDevicesRepository.save(record);
                insertCount++;
            } catch (DateTimeParseException | NumberFormatException e) {
                System.err.println("Skipping invalid row: " + line + " | Error: " + e.getMessage());
            }
        }
        return insertCount;
    }


    private void processAndroidUninstallEvents(String filePath) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        lines.remove(0); // Remove header row

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MMM dd, yyyy");

        for (String line : lines) {
            String[] values = line.split(",");
            String dateString = values[0].trim();
            String uninstallEventsString = values[1].trim();
            String notes = values.length > 2 ? values[2].trim() : "";

            LocalDate uninstallDate = null;
            try {
                // Parse date based on its format
                if (dateString.contains("-")) {
                    uninstallDate = LocalDate.parse(dateString, formatter1);
                } else if (dateString.contains(",")) {
                    uninstallDate = LocalDate.parse(dateString, formatter2);
                } else {
                    throw new DateTimeParseException("Unsupported date format", dateString, 0);
                }

                AndroidUninstallEvents record = new AndroidUninstallEvents();
                record.setUninstallDate(uninstallDate);

                // Handle "uninstall events" and replace "-" with default value 0
                if (uninstallEventsString.equals("-")) {
                    record.setUninstallEvents(0);
                } else {
                    record.setUninstallEvents(Integer.parseInt(uninstallEventsString));
                }

                record.setNotes(notes);
                androidUninstallEventsRepository.save(record);
            } catch (DateTimeParseException | NumberFormatException e) {
                System.err.println("Skipping invalid row: " + line + " | Error: " + e.getMessage());
            }
        }
    }




*/
/*
    private void processAndroidDeviceStoreListingImpressions(String filePath) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        lines.remove(0);
        for (String line : lines) {
            String[] values = line.split(",");
            AndroidDeviceStoreListingImpressions record = new AndroidDeviceStoreListingImpressions();
            record.setListingDate(LocalDate.parse(values[0].trim(), DateTimeFormatter.ofPattern("MMM dd, yyyy")));
            record.setDevicesListingCount(Integer.parseInt(values[1].trim()));
            record.setNotes(values.length > 2 ? values[2].trim() : "");
            androidDeviceStoreListingImpressionsRepository.save(record);
        }
    }
*//*

private void processAndroidDeviceStoreListingImpressions(String filePath) throws Exception {
    List<String> lines = Files.readAllLines(Paths.get(filePath));
    lines.remove(0); // Remove header row

    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MMM dd, yyyy");

    for (String line : lines) {
        String[] values = line.split(",");
        String dateString = values[0].trim();
        String devicesListingCountString = values[1].trim();
        String notes = values.length > 2 ? values[2].trim() : "";

        LocalDate listingDate = null;
        try {
            // Parse date based on its format
            if (dateString.contains("-")) {
                listingDate = LocalDate.parse(dateString, formatter1);
            } else if (dateString.contains(",")) {
                listingDate = LocalDate.parse(dateString, formatter2);
            } else {
                throw new DateTimeParseException("Unsupported date format", dateString, 0);
            }

            AndroidDeviceStoreListingImpressions record = new AndroidDeviceStoreListingImpressions();
            record.setListingDate(listingDate);

            // Handle "devices listing count" and replace "-" with default value 0
            if (devicesListingCountString.equals("-")) {
                record.setDevicesListingCount(0);
            } else {
                record.setDevicesListingCount(Integer.parseInt(devicesListingCountString));
            }

            record.setNotes(notes);
            androidDeviceStoreListingImpressionsRepository.save(record);
        } catch (DateTimeParseException | NumberFormatException e) {
            System.err.println("Skipping invalid row: " + line + " | Error: " + e.getMessage());
        }
    }
}


private void processAndroidTotalInstallationsByAppVersion(String filePath) throws Exception {
    List<String> lines = Files.readAllLines(Paths.get(filePath));

    lines.remove(0);

    for (String line : lines) {
        String[] values = line.split(",");

        String dateString = values[0].trim();
        LocalDate installDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        for (int i = 1; i < values.length - 1; i++) {
            String versionName;
            int installCount;

            if (i == 1) {
                versionName = "All";
            } else {
                versionName = "1.0." + (i - 2);
            }

            installCount = Integer.parseInt(values[i].trim());

            AndroidTotalInstallationsByAppVersion record = new AndroidTotalInstallationsByAppVersion();
            record.setInstallDate(installDate);
            record.setInstallVersion(versionName);
            record.setInstallCount(installCount);

            record.setNotes(values[values.length - 1].trim());

            androidTotalInstallationsByAppVersionRepository.save(record);
        }
    }
}


    private void processIosActiveDevicesByAppVersion(String filePath) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        if (lines.size() <= 4) {
            throw new IllegalArgumentException("Input file does not contain sufficient data rows.");
        }

        String headerLine = lines.get(4);
        String[] headers = headerLine.split(",");

        List<String> versions = Arrays.stream(headers)
                .skip(1)
                .map(header -> header.split("\\s")[0].trim())
                .collect(Collectors.toList());

        lines.subList(0, 5).clear();

        // Define possible date formats
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("M/d/yy");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MM-dd-yyyy");

        for (String line : lines) {
            try {
                String[] values = line.split(",");
                String date = values[0].trim();

                LocalDate eventDate;
                if (date.contains("/")) {
                    eventDate = LocalDate.parse(date, formatter1);
                } else if (date.contains("-")) {
                    eventDate = LocalDate.parse(date, formatter2);
                } else {
                    throw new DateTimeParseException("Unknown date format", date, 0);
                }

                for (int i = 1; i < values.length; i++) {
                    // Create and populate the record
                    IosActiveDevicesByAppVersion record = new IosActiveDevicesByAppVersion();
                    record.setEventDate(eventDate);
                    record.setVersion(versions.get(i - 1));
                    double parsedValue = Double.parseDouble(values[i].trim());
                    record.setEventTotal((int) parsedValue);

                    // Save the record
                    iosActiveDevicesByAppVersionRepository.save(record);
                }
            } catch (DateTimeParseException e) {
                System.err.println("Skipping line due to invalid date format: " + line);
            } catch (NumberFormatException e) {
                System.err.println("Skipping line due to invalid number format: " + line);
            } catch (Exception e) {
                System.err.println("Skipping line due to an unexpected error: " + line);
            }
        }
    }



    private void processIosActiveDevicesByDevice(String filePath) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        String headerLine = lines.get(4);
        String[] headerValues = headerLine.split(",");
        List<String> deviceNames = new ArrayList<>();

        for (int i = 1; i < headerValues.length; i++) {
            deviceNames.add(headerValues[i].trim().replace(" Active Devices", ""));
        }

        lines.subList(0, 5).clear();

        // Process each data row
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M/d/yy");
        for (String line : lines) {
            String[] values = line.split(",");
            String date = values[0].trim();

            try {
                LocalDate eventDate = LocalDate.parse(date, dateFormatter);

                for (int i = 1; i < values.length; i++) {
                    String value = values[i].trim();

                    // Treat "-" as 0
                    int eventTotal;
                    if (value.equals("-") || value.isEmpty()) {
                        eventTotal = 0;
                    } else {
                        try {
                            eventTotal = (int) Double.parseDouble(value);
                        } catch (NumberFormatException e) {
                            System.err.println("Skipping invalid number format: " + value);
                            continue;
                        }
                    }

                    // Create and save the record
                    IosActiveDevicesByDevice record = new IosActiveDevicesByDevice();
                    record.setEventDate(eventDate);
                    record.setDeviceName(deviceNames.get(i - 1));
                    record.setEventTotal(eventTotal);

                    iosActiveDevicesByDeviceRepository.save(record);
                }
            } catch (DateTimeParseException e) {
                System.err.println("Skipping invalid date format: " + date);
            }
        }
    }


    private void processIosDeletionsByAppVersion(String filePath) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        String headerLine = lines.get(4);
        String[] headerValues = headerLine.split(",");
        List<String> versions = new ArrayList<>();

        for (int i = 1; i < headerValues.length; i++) {
            versions.add(headerValues[i].trim().replace(" (iOS) Deletions", ""));
        }

        lines.subList(0, 5).clear();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M/d/yy");
        for (String line : lines) {
            String[] values = line.split(",");
            String date = values[0].trim();

            try {
                LocalDate eventDate = LocalDate.parse(date, dateFormatter);

                for (int i = 1; i < values.length; i++) {
                    String value = values[i].trim();

                    // Treat empty or "-" as 0
                    int eventTotal;
                    if (value.equals("-") || value.isEmpty()) {
                        eventTotal = 0;
                    } else {
                        try {
                            eventTotal = (int) Double.parseDouble(value);
                        } catch (NumberFormatException e) {
                            System.err.println("Skipping invalid number format: " + value);
                            continue;
                        }
                    }

                    IosDeletionsByAppVersion record = new IosDeletionsByAppVersion();
                    record.setEventDate(eventDate);
                    record.setVersion(versions.get(i - 1));
                    record.setEventTotal(eventTotal);

                    iosDeletionsByAppVersionRepository.save(record);
                }
            } catch (DateTimeParseException e) {
                System.err.println("Skipping invalid date format: " + date);
            }
        }
    }


    private void processIosDeletionsByDevice(String filePath) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        String headerLine = lines.get(4);
        String[] headerValues = headerLine.split(",");
        List<String> devices = new ArrayList<>();

        for (int i = 1; i < headerValues.length; i++) {
            devices.add(headerValues[i].trim().replace(" Deletions", ""));
        }

        lines.subList(0, 5).clear();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M/d/yy");
        for (String line : lines) {
            String[] values = line.split(",");
            String date = values[0].trim();

            try {
                LocalDate eventDate = LocalDate.parse(date, dateFormatter);

                for (int i = 1; i < values.length; i++) {
                    String value = values[i].trim();

                    // Treat empty or "-" as 0
                    int eventTotal;
                    if (value.equals("-") || value.isEmpty()) {
                        eventTotal = 0;
                    } else {
                        try {
                            eventTotal = (int) Double.parseDouble(value);
                        } catch (NumberFormatException e) {
                            System.err.println("Skipping invalid number format: " + value);
                            continue;
                        }
                    }

                    IosDeletionsByDevice record = new IosDeletionsByDevice();
                    record.setEventDate(eventDate);
                    record.setDeviceName(devices.get(i - 1));
                    record.setEventTotal(eventTotal);

                    iosDeletionsByDeviceRepository.save(record);
                }
            } catch (DateTimeParseException e) {
                System.err.println("Skipping invalid date format: " + date);
            }
        }
    }


    private void processIosTotalDownloadsByDevice(String filePath) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        String headerLine = lines.get(4);
        String[] headerValues = headerLine.split(",");
        List<String> devices = new ArrayList<>();

        for (int i = 1; i < headerValues.length; i++) {
            devices.add(headerValues[i].trim().replace(" Total Downloads", ""));
        }


        lines.subList(0, 5).clear();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M/d/yy");
        for (String line : lines) {
            String[] values = line.split(",");
            String date = values[0].trim();

            try {
                LocalDate eventDate = LocalDate.parse(date, dateFormatter);

                for (int i = 1; i < values.length; i++) {
                    String value = values[i].trim();

                    // Treat empty or "-" as 0
                    int eventTotal;
                    if (value.equals("-") || value.isEmpty()) {
                        eventTotal = 0;
                    } else {
                        try {
                            eventTotal = (int) Double.parseDouble(value);
                        } catch (NumberFormatException e) {
                            System.err.println("Skipping invalid number format: " + value);
                            continue;
                        }
                    }

                    IosTotalDownloadsByDevice record = new IosTotalDownloadsByDevice();
                    record.setEventDate(eventDate);
                    record.setDeviceName(devices.get(i - 1));
                    record.setEventTotal(eventTotal);

                    iosTotalDownloadsByDeviceRepository.save(record);
                }
            } catch (DateTimeParseException e) {
                System.err.println("Skipping invalid date format: " + date);
            }
        }
    }

    private void processIosTotalInstallationsByDevice(String filePath) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        String headerLine = lines.get(4);
        String[] headerValues = headerLine.split(",");
        List<String> devices = new ArrayList<>();

        for (int i = 1; i < headerValues.length; i++) {
            devices.add(headerValues[i].trim().replace(" Installations", ""));
        }

        lines.subList(0, 5).clear();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M/d/yy");
        for (String line : lines) {
            String[] values = line.split(",");
            String date = values[0].trim();

            try {
                LocalDate eventDate = LocalDate.parse(date, dateFormatter);

                for (int i = 1; i < values.length; i++) {
                    String value = values[i].trim();

                    // Treat empty or "-" as 0
                    int eventTotal;
                    if (value.equals("-") || value.isEmpty()) {
                        eventTotal = 0;
                    } else {
                        try {
                            eventTotal = (int) Double.parseDouble(value);
                        } catch (NumberFormatException e) {
                            System.err.println("Skipping invalid number format: " + value);
                            continue;
                        }
                    }

                    IosTotalInstallationsByDevice record = new IosTotalInstallationsByDevice();
                    record.setEventDate(eventDate);
                    record.setDeviceName(devices.get(i - 1));
                    record.setEventTotal(eventTotal);

                    iosTotalInstallationsByDeviceRepository.save(record);
                }
            } catch (DateTimeParseException e) {
                System.err.println("Skipping invalid date format: " + date);
            }
        }
    }


    private void processIosTotalInstallationsByAppVersion(String filePath) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        String headerLine = lines.get(4);
        String[] headerValues = headerLine.split(",");
        List<String> versions = new ArrayList<>();

        for (int i = 1; i < headerValues.length; i++) {
            String version = headerValues[i].trim();

            version = version.replaceAll(" \\(.*\\)", "").replaceAll(" Installations", "").trim();

            versions.add(version);
        }

        lines.subList(0, 5).clear();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M/d/yy");
        for (String line : lines) {
            String[] values = line.split(",");
            String date = values[0].trim();

            try {
                LocalDate eventDate = LocalDate.parse(date, dateFormatter);

                for (int i = 1; i < values.length; i++) {
                    String value = values[i].trim();

                    // Treat empty or "-" as 0
                    int eventTotal;
                    if (value.equals("-") || value.isEmpty()) {
                        eventTotal = 0;
                    } else {
                        try {
                            eventTotal = (int) Double.parseDouble(value);
                        } catch (NumberFormatException e) {
                            System.err.println("Skipping invalid number format: " + value);
                            continue;
                        }
                    }

                    IosTotalInstallationsByAppVersion record = new IosTotalInstallationsByAppVersion();
                    record.setEventDate(eventDate);
                    record.setVersion(versions.get(i - 1));
                    record.setEventTotal(eventTotal);
                    iosTotalInstallationsByAppVersionRepository.save(record);
                }
            } catch (Exception e) {
                System.err.println("Error processing line: " + line);
                e.printStackTrace();
            }
        }
    }
    private void processIosImpressionsByDevice(String filePath) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        String headerLine = lines.get(4);
        String[] headerValues = headerLine.split(",");
        List<String> devices = new ArrayList<>();

        for (int i = 1; i < headerValues.length; i++) {
            String device = headerValues[i].trim();

            device = device.replaceAll(" \\(Unique Devices\\)", "").trim();

            devices.add(device);
        }

        lines.subList(0, 5).clear();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M/d/yy");
        for (String line : lines) {
            String[] values = line.split(",");
            String date = values[0].trim();

            try {
                LocalDate eventDate = LocalDate.parse(date, dateFormatter);

                for (int i = 1; i < values.length; i++) {
                    String value = values[i].trim();

                    // Treat empty or "-" as 0
                    int eventTotal;
                    if (value.equals("-") || value.isEmpty()) {
                        eventTotal = 0;
                    } else {
                        try {
                            eventTotal = (int) Double.parseDouble(value);
                        } catch (NumberFormatException e) {
                            System.err.println("Skipping invalid number format: " + value);
                            continue;
                        }
                    }

                    IosDeviceStoreListingImpressionsByDevice record = new IosDeviceStoreListingImpressionsByDevice();
                    record.setEventDate(eventDate);
                    record.setDeviceName(devices.get(i - 1));
                    record.setEventTotal(eventTotal);
                    iosDeviceStoreListingImpressionsByDeviceRepository.save(record);
                }
            } catch (Exception e) {
                System.err.println("Error processing line: " + line);
                e.printStackTrace();
            }
        }
    }


}
*/
