package com.gl.mdr.service.impl;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gl.mdr.bulk.imei.feign.NotificationFeign;
import com.gl.mdr.configuration.PropertiesReader;
import com.gl.mdr.configuration.SortDirection;
import com.gl.mdr.exceptions.ResourceServicesException;
import com.gl.mdr.model.app.EirsResponseParam;
import com.gl.mdr.model.app.NotificationModel;
import com.gl.mdr.model.app.StolenLostModel;
import com.gl.mdr.model.app.SystemConfigurationDb;
import com.gl.mdr.model.app.User;
import com.gl.mdr.model.app.WebActionDb;
import com.gl.mdr.model.audit.AuditTrail;
import com.gl.mdr.model.constants.Datatype;
import com.gl.mdr.model.constants.SearchOperation;
import com.gl.mdr.model.file.FileDetails;
import com.gl.mdr.model.file.StolenPoliceVerificationDeviceFileModel;
import com.gl.mdr.model.filter.TrackLostDeviceFilterRequest;
import com.gl.mdr.model.generic.GenricResponse;
import com.gl.mdr.model.generic.SearchCriteria;
import com.gl.mdr.repo.app.AttachedFileInfoRepository;
import com.gl.mdr.repo.app.EirsResponseParamRepository;
import com.gl.mdr.repo.app.StatesInterpretaionRepository;
import com.gl.mdr.repo.app.StolenPoliceVerificationDevicesRepository;
import com.gl.mdr.repo.app.SystemConfigListRepository;
import com.gl.mdr.repo.app.SystemConfigurationDbRepository;
import com.gl.mdr.repo.app.UserProfileRepository;
import com.gl.mdr.repo.app.UserRepository;
import com.gl.mdr.repo.app.WebActionDbRepository;
import com.gl.mdr.repo.audit.AuditTrailRepository;
import com.gl.mdr.specificationsbuilder.GenericSpecificationBuilder;
import com.gl.mdr.util.CustomMappingStrategy;
import com.gl.mdr.util.Utility;
import com.opencsv.CSVWriter;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;


@Service
public class StolenPoliceVerificationDeviceServiceImpl {
	private static final Logger logger = LogManager.getLogger(StolenPoliceVerificationDeviceServiceImpl.class);

	@Autowired
	PropertiesReader propertiesReader;

	@Autowired
	SystemConfigurationDbRepository systemConfigurationDbRepository;

	@Autowired
	AttachedFileInfoRepository attachedFileInfoRepository;

	@Autowired
	AuditTrailRepository auditTrailRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	StatesInterpretaionRepository statesInterpretaionRepository;

	@Autowired
	UserProfileRepository userProfileRepository;

	@Autowired
	SystemConfigListRepository systemConfigListRepository;

	@Autowired
	Utility utility;
	
	@Autowired
	WebActionDbRepository webActionDbRepository;

	@Autowired
	EirsResponseParamRepository eirsResponseParamRepository;
	
	@Autowired
	NotificationFeign notificationFeign;
	
	@Autowired
	StolenPoliceVerificationDevicesRepository stolenPoliceVerificationDevicesRepository;
	

	public Page<StolenLostModel> getPoliceVerificationDevicesDetails(
			TrackLostDeviceFilterRequest trackLostRequest, Integer pageNo, Integer pageSize,String operation) {
		
		try {
			logger.info("Police Verification Lost/Stolen Request" +trackLostRequest);
			String orderColumn;
			if(operation.equals("Export")) {
				orderColumn = "modifiedOn";
			}else {
				logger.info("column Name :: " + trackLostRequest.getOrderColumnName()); 
				orderColumn = "Date & Time".equalsIgnoreCase(trackLostRequest.getOrderColumnName()) ? "createdOn" :
						"Request Number".equalsIgnoreCase(trackLostRequest.getOrderColumnName()) ? "requestId" :
							"IMEI".equalsIgnoreCase(trackLostRequest.getOrderColumnName()) ? "imei1" :
								"MSISDN".equalsIgnoreCase(trackLostRequest.getOrderColumnName()) ? "contactNumber" :
									"Email ID".equalsIgnoreCase(trackLostRequest.getOrderColumnName()) ? "deviceOwnerEmail" :
										"Status".equalsIgnoreCase(trackLostRequest.getOrderColumnName()) ? "status"
												:"modifiedOn"; 
			}
			logger.info("orderColumn data:  "+orderColumn+" user Id : "+trackLostRequest.getUserId());
			logger.info("---system.getSort() : "+trackLostRequest.getSort());
			Sort.Direction direction;
			
			if("modifiedOn".equalsIgnoreCase(orderColumn)) {
				direction=Sort.Direction.DESC;
			}
			else {
				direction= SortDirection.getSortDirection(trackLostRequest.getSort());
			}
			
			AuditTrail auditTrail = new AuditTrail();
			auditTrail.setFeatureName("Police Track Stolen Device");
			auditTrail.setSubFeature("View All");
			auditTrail.setPublicIp(trackLostRequest.getPublicIp());
			auditTrail.setBrowser(trackLostRequest.getBrowser());
			String usersType="";
			if( Objects.nonNull(trackLostRequest.getUserId()) ) {
				User user = userRepository.getByid( trackLostRequest.getUserId());
				usersType=user.getUserProfile().getOperatorTypeName();
				auditTrail.setUserId( trackLostRequest.getUserId() );
				auditTrail.setUserName(user.getUsername());
			}else {
				auditTrail.setUserName( "NA");
			}
			if( Objects.nonNull(trackLostRequest.getUserType()) ) {
				auditTrail.setUserType( trackLostRequest.getUserType());
				auditTrail.setRoleType( trackLostRequest.getUserType() );
			}else {
				auditTrail.setUserType( "NA" );
				auditTrail.setRoleType( "NA" );
			}
			auditTrail.setTxnId("NA");
			auditTrailRepository.save(auditTrail);

			if("modifiedOn".equalsIgnoreCase(orderColumn) && SortDirection.getSortDirection(trackLostRequest.getSort()).equals(Sort.Direction.ASC)) {
				direction=Sort.Direction.ASC;
			}
			Pageable pageable = PageRequest.of(pageNo, pageSize, new Sort(direction, orderColumn));

			GenericSpecificationBuilder<StolenLostModel> uPSB = new GenericSpecificationBuilder<StolenLostModel>(propertiesReader.dialect);
			
			logger.info("GenericSpecificationBuilder usersType["+usersType+"]uPSB:  "+uPSB.toString()+" user Id : "+trackLostRequest.getUserId());
			
			

			if(Objects.nonNull(trackLostRequest.getStartDate()) && !trackLostRequest.getStartDate().equals(""))
				uPSB.with(new SearchCriteria("createdOn", trackLostRequest.getStartDate() , SearchOperation.GREATER_THAN, Datatype.DATE));
			
			if(Objects.nonNull(trackLostRequest.getEndDate()) && !trackLostRequest.getEndDate().equals(""))
				uPSB.with(new SearchCriteria("createdOn",trackLostRequest.getEndDate() , SearchOperation.LESS_THAN, Datatype.DATE));
			
			if(Objects.nonNull(trackLostRequest.getModifiedOn()) && !trackLostRequest.getModifiedOn().equals(""))
				uPSB.with(new SearchCriteria("modifiedOn", trackLostRequest.getModifiedOn() , SearchOperation.EQUALITY, Datatype.DATE));
			
			if(Objects.nonNull(trackLostRequest.getImei()) && !trackLostRequest.getImei().equals(""))
				uPSB.with(new SearchCriteria("imei1",trackLostRequest.getImei() ,  SearchOperation.LIKE, Datatype.STRING));
			
			if(Objects.nonNull(trackLostRequest.getMsisdn()) && !trackLostRequest.getMsisdn().equals(""))
				uPSB.with(new SearchCriteria("contactNumber",trackLostRequest.getMsisdn() ,  SearchOperation.LIKE, Datatype.STRING));
			
			if(Objects.nonNull(trackLostRequest.getEmail()) && !trackLostRequest.getEmail().equals(""))
				uPSB.with(new SearchCriteria("deviceOwnerEmail",trackLostRequest.getEmail() ,  SearchOperation.LIKE, Datatype.STRING));
			
			if(Objects.nonNull(trackLostRequest.getRequestNo()) && !trackLostRequest.getRequestNo().equals(""))
				uPSB.with(new SearchCriteria("requestId", trackLostRequest.getRequestNo() , SearchOperation.LIKE, Datatype.STRING));
			
			if(usersType.equalsIgnoreCase("police")) {
				//if(Objects.nonNull(trackLostRequest.getStatus()) && !trackLostRequest.getStatus().equals(""))
					uPSB.with(new SearchCriteria("status", "INIT", SearchOperation.EQUALITY, Datatype.STRING));
			}else if(usersType.equalsIgnoreCase("moi")){
				
				//if(Objects.nonNull(trackLostRequest.getStatus()) && !trackLostRequest.getStatus().equals(""))
					uPSB.with(new SearchCriteria("status", "VERIFY_MOI", SearchOperation.EQUALITY, Datatype.STRING));
			}else {
				if(Objects.nonNull(trackLostRequest.getStatus()) && !trackLostRequest.getStatus().equals(""))
					uPSB.with(new SearchCriteria("status", trackLostRequest.getStatus(), SearchOperation.EQUALITY, Datatype.STRING));
			}
			
			
			
			return  stolenPoliceVerificationDevicesRepository.findAll(uPSB.build(),pageable);

		}catch(Exception e) {
			logger.info("Exception found ="+e.getMessage());
			logger.info(e.getClass().getMethods().toString());
			logger.info(e.toString());
			return null;
		}
	}
	public FileDetails exportData(TrackLostDeviceFilterRequest trackLostRequest) {
		logger.info("inside export Track Lost/Stolen Device Feature service");
		logger.info("Export Request:  "+trackLostRequest);
		String fileName = null;
		Writer writer   = null;
		
		StolenPoliceVerificationDeviceFileModel uPFm = null;
		
		SystemConfigurationDb dowlonadDir=systemConfigurationDbRepository.getByTag("file.download-dir");
		SystemConfigurationDb dowlonadLink=systemConfigurationDbRepository.getByTag("file.download-link");
		DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

		Integer pageNo = 0;
		Integer pageSize = Integer.valueOf(systemConfigurationDbRepository.getByTag("file.max-file-record").getValue());
		String filePath  = dowlonadDir.getValue();
		StatefulBeanToCsvBuilder<StolenPoliceVerificationDeviceFileModel> builder = null;
		StatefulBeanToCsv<StolenPoliceVerificationDeviceFileModel> csvWriter      = null;
		List<StolenPoliceVerificationDeviceFileModel> fileRecords       		  = null;
		MappingStrategy<StolenPoliceVerificationDeviceFileModel> mapStrategy = new CustomMappingStrategy<>();

		AuditTrail auditTrail = new AuditTrail();
		auditTrail.setFeatureName("Police Track Stolen Device");
		auditTrail.setSubFeature("Export");
		auditTrail.setPublicIp(trackLostRequest.getPublicIp());
		auditTrail.setBrowser(trackLostRequest.getBrowser());
		if( Objects.nonNull(trackLostRequest.getUserId()) ) {
			User user = userRepository.getByid( trackLostRequest.getUserId());
			auditTrail.setUserId( trackLostRequest.getUserId() );
			auditTrail.setUserName(user.getUsername());
		}else {
			auditTrail.setUserName( "NA");
		}
		if( Objects.nonNull(trackLostRequest.getUserType()) ) {
			auditTrail.setUserType( trackLostRequest.getUserType());
			auditTrail.setRoleType( trackLostRequest.getUserType() );
		}else {
			auditTrail.setUserType( "NA" );
			auditTrail.setRoleType( "NA" );
		}
		auditTrail.setTxnId("NA");
		auditTrailRepository.save(auditTrail);

		try {
			mapStrategy.setType(StolenPoliceVerificationDeviceFileModel.class);
			trackLostRequest.setSort("");
			List<StolenLostModel> list = getPoliceVerificationDevicesDetails(trackLostRequest, pageNo, pageSize,"Export").getContent();
			if( list.size()> 0 ) {
				fileName = LocalDateTime.now().format(dtf2).replace(" ", "_")+"_police_verification_device.csv";
			}else {
				fileName = LocalDateTime.now().format(dtf2).replace(" ", "_")+"_police_verification_device.csv";
			}
			logger.info(" file path plus file name: "+Paths.get(filePath+fileName) +" and file size : "+list.size());
			writer = Files.newBufferedWriter(Paths.get(filePath + fileName));
			builder = new StatefulBeanToCsvBuilder<>(writer);

			csvWriter = builder.withMappingStrategy(mapStrategy).withSeparator(',').withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();

			if( list.size() > 0 ) {
				fileRecords = new ArrayList<StolenPoliceVerificationDeviceFileModel>(); 
				for( StolenLostModel stolenLostModel : list ) {
					uPFm = new StolenPoliceVerificationDeviceFileModel();
					uPFm.setCreatedOn(stolenLostModel.getCreatedOn());
					uPFm.setContactNumber(stolenLostModel.getContactNumber());
					uPFm.setDeviceBrand(stolenLostModel.getDeviceBrand());
					uPFm.setDeviceLostDdateTime(stolenLostModel.getDeviceLostDdateTime());
					uPFm.setDeviceModel(stolenLostModel.getDeviceModel());
					uPFm.setDeviceOwnerAddress(stolenLostModel.getDeviceOwnerAddress());
					uPFm.setDeviceOwnerEmail(stolenLostModel.getDeviceOwnerEmail());
					uPFm.setDeviceOwnerName(stolenLostModel.getDeviceOwnerName());
					uPFm.setDevicePurchaseInvoiceUrl(stolenLostModel.getDevicePurchaseInvoiceUrl());
					uPFm.setImei1(stolenLostModel.getImei1());
					uPFm.setModifiedOn(stolenLostModel.getModifiedOn());
					uPFm.setOwnerDOB(stolenLostModel.getOwnerDOB());
					uPFm.setRemarks(stolenLostModel.getRemarks());
					uPFm.setRequestId(stolenLostModel.getRequestId());
					uPFm.setRequestType(stolenLostModel.getRequestType());
					uPFm.setStatus(stolenLostModel.getStatus());
					fileRecords.add(uPFm);
				}
				csvWriter.write(fileRecords);
			}
			logger.info("fileName::"+fileName);
			logger.info("filePath::::"+filePath);
			logger.info("link:::"+dowlonadLink.getValue());
			return new FileDetails(fileName, filePath,dowlonadLink.getValue().replace("$LOCAL_IP",propertiesReader.localIp)+fileName ); 

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}finally {
			try {
				if( writer != null )
					writer.close();
			} catch (IOException e) {

			}
		}
	}

	public ResponseEntity<?> getPoliceVerificationData(TrackLostDeviceFilterRequest trackLostRequest){
		logger.info("View Track Lost Devices by id : "+trackLostRequest.getId());
		Optional<StolenLostModel> result=stolenPoliceVerificationDevicesRepository.findById(trackLostRequest.getId());
		logger.info("View result : "+result);
		if(result.isPresent()) {
			GenricResponse response=new GenricResponse(200,"","",result.get());
			return  new ResponseEntity<>(response,HttpStatus.OK);
		}else {
			GenricResponse response=new GenricResponse(500,"Something wrong happend","",result);
			return  new ResponseEntity<>(response,HttpStatus.OK);
		}
	}
	public List<String> getDistinctStolenStatus() {
		List<String> operator=stolenPoliceVerificationDevicesRepository.findDistinctStatus();
		return operator;
	}
	public  ResponseEntity<?> updateDevicesStatus(TrackLostDeviceFilterRequest filterRequest) {
		// TODO Auto-generated method stub
		 if (Objects.nonNull(filterRequest.getRequestNo()) && Objects.nonNull(filterRequest.getFileName())) {
	            logger.info("Verification lost/Stolen Device Request : " + filterRequest);
	            AuditTrail auditTrail = new AuditTrail();
	            auditTrail.setFeatureName("Police VERIFY MOI Device");
	            auditTrail.setSubFeature("VERIFY_MOI");
	            auditTrail.setPublicIp(filterRequest.getPublicIp());
	            auditTrail.setBrowser(filterRequest.getBrowser());
	            if (Objects.nonNull(filterRequest.getUserId())) {
	                User user = userRepository.getByid(filterRequest.getUserId());
	                auditTrail.setUserId(filterRequest.getUserId());
	                auditTrail.setUserName(user.getUsername());
	            } else {
	                auditTrail.setUserName("NA");
	            }
	            if (Objects.nonNull(filterRequest.getUserType())) {
	                auditTrail.setUserType(filterRequest.getUserType());
	                auditTrail.setRoleType(filterRequest.getUserType());
	            } else {
	                auditTrail.setUserType("NA");
	                auditTrail.setRoleType("NA");
	            }
	            auditTrail.setTxnId("NA");
	            auditTrailRepository.save(auditTrail);
	            //Changed status from duplicate to Original
	            StolenLostModel stolen=stolenPoliceVerificationDevicesRepository.findByRequestId(filterRequest.getRequestNo());
	    		if(stolen!=null) {
	    			stolen.setStatus(filterRequest.getStatus());
	    			stolen.setFirCopyUrl(filterRequest.getFileName());
	    			stolen.setFileName(filterRequest.getFileName());
	    			stolen.setRemarks(filterRequest.getRemarks());
	    			stolen.setModifiedOn(LocalDateTime.now());
	    			stolen= stolenPoliceVerificationDevicesRepository.save(stolen);
	    		}
	    		 GenricResponse response = new GenricResponse(HttpStatus.OK.value(), "Police Verification Successfully", "", stolen);
	              return new ResponseEntity<>(response, HttpStatus.OK);
	        }
		 GenricResponse response = new GenricResponse(HttpStatus.EXPECTATION_FAILED.value(), "Something wrong happend", "", "");
	     return new ResponseEntity<>(response, HttpStatus.OK);
	      
	}
	public ResponseEntity<?> updateLostDeviceStatus(TrackLostDeviceFilterRequest filterRequest) {
		// TODO Auto-generated method stub
		 if (Objects.nonNull(filterRequest.getRequestNo()) && Objects.nonNull(filterRequest.getStatus())) {
	            logger.info("Admin Verification lost/Stolen Device Request : " + filterRequest);
	            AuditTrail auditTrail = new AuditTrail();
	            auditTrail.setFeatureName("MOI Admin VERIFY Device");
	            auditTrail.setSubFeature(filterRequest.getStatus());
	            auditTrail.setPublicIp(filterRequest.getPublicIp());
	            auditTrail.setBrowser(filterRequest.getBrowser());
	            if (Objects.nonNull(filterRequest.getUserId())) {
	                User user = userRepository.getByid(filterRequest.getUserId());
	                auditTrail.setUserId(filterRequest.getUserId());
	                auditTrail.setUserName(user.getUsername());
	            } else {
	                auditTrail.setUserName("NA");
	            }
	            if (Objects.nonNull(filterRequest.getUserType())) {
	                auditTrail.setUserType(filterRequest.getUserType());
	                auditTrail.setRoleType(filterRequest.getUserType());
	            } else {
	                auditTrail.setUserType("NA");
	                auditTrail.setRoleType("NA");
	            }
	            auditTrail.setTxnId("NA");
	            auditTrailRepository.save(auditTrail);
	            //Changed status from duplicate to Original
	            StolenLostModel stolen=stolenPoliceVerificationDevicesRepository.findByRequestId(filterRequest.getRequestNo());
	    		if(stolen!=null) {
	    			stolen.setStatus(filterRequest.getStatus());
	    			stolen.setRemarks(filterRequest.getRemarks());
	    			stolen.setModifiedOn(LocalDateTime.now());
	    			stolen= stolenPoliceVerificationDevicesRepository.save(stolen);
	    			
	    			try {
	    				if(filterRequest.getStatus().equalsIgnoreCase("APPROVE_MOI")) {
	    					WebActionDb webDB=new WebActionDb();
							webDB.setCreatedOn(LocalDateTime.now());
							webDB.setRetry_count(0);
							webDB.setModifiedOn(LocalDateTime.now());
							webDB.setTxnId(stolen.getRequestId());
							webDB.setFeature("MOI");
							webDB.setSub_feature(stolen.getRequestType().toUpperCase());
							webDB.setState(1);
							webActionDbRepository.save(webDB);
	    				}else {
	    					NotificationAPI(stolen.getContactNumber(),stolen.getDeviceOwnerEmail(), stolen.getLanguage(), stolen.getRequestId());
	    				}
	    				
						
						
					} catch (Exception e) {
						// TODO: handle exception
					}
	    		}
	    		 GenricResponse response = new GenricResponse(HttpStatus.OK.value(), "MOI Admin Verification Successfully", "", stolen);
	              return new ResponseEntity<>(response, HttpStatus.OK);
	        }
		 GenricResponse response = new GenricResponse(HttpStatus.EXPECTATION_FAILED.value(), "Something wrong happend", "", "");
	     return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	public void NotificationAPI(String msisdn,String email, String lang, String tid) {
		
		NotificationModel notificationModel = new NotificationModel();
		GenricResponse genricResponse = new GenricResponse();
		
		if(lang==null || lang.isEmpty() || lang.equalsIgnoreCase("")) {
			lang="en";
		}
		String msg="";
		try {
				EirsResponseParam param=eirsResponseParamRepository.findByTagAndLanguage("REJECT_MOI_MSG",lang.toLowerCase());
				if(param!=null) {
					msg=param.getValue().replaceAll("<Request_Id>", tid);
				}
				logger.info("NotificationAPI() MOI Reject :: get message Details ["+msg+"] msisdn=" + msisdn);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.info("NotificationAPI() MOI Reject :: Exception "+e.getMessage()+" when get message Details msisdn=" + msisdn);
			
		}
		
		notificationModel.setMessage(msg);
		notificationModel.setFeatureName("REJECT_MOI");
		notificationModel.setSubFeature("Admin Reject Request");
		notificationModel.setFeatureTxnId(tid);
		if(msisdn==null || msisdn.equals("") || msisdn.equalsIgnoreCase("null")) {
			notificationModel.setSubject("Request ID "+tid+" :Update Notification for Device stolen request reject");
			notificationModel.setChannelType("EMAIL");
			notificationModel.setEmail(email);
			
		}else {
			notificationModel.setChannelType("SMS");
			notificationModel.setMsisdn(msisdn);
		}
		
		notificationModel.setMsgLang(lang);
		logger.info("MOI Reject request send to notification API=" + notificationModel);
		genricResponse = notificationFeign.addNotifications(notificationModel);
		logger.info("MOI Reject Notification API response=" + genricResponse);
	}
	
}
