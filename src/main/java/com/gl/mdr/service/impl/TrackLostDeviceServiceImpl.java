package com.gl.mdr.service.impl;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gl.mdr.bulk.imei.feign.AlertFeign;
import com.gl.mdr.configuration.PropertiesReader;
import com.gl.mdr.configuration.SortDirection;
import com.gl.mdr.exceptions.ResourceServicesException;
import com.gl.mdr.model.app.ActiveMsisdnList;
import com.gl.mdr.model.app.AlertDto;
import com.gl.mdr.model.app.LostDeviceDetail;
import com.gl.mdr.model.app.StatesInterpretationDb;
import com.gl.mdr.model.app.SystemConfigurationDb;
import com.gl.mdr.model.app.TrackLostDevices;
import com.gl.mdr.model.app.User;
import com.gl.mdr.model.audit.AuditTrail;
import com.gl.mdr.model.constants.Datatype;
import com.gl.mdr.model.constants.SearchOperation;
import com.gl.mdr.model.file.FileDetails;
import com.gl.mdr.model.file.TrackLostDeviceFileModel;
import com.gl.mdr.model.filter.LostDeviceRequest;
import com.gl.mdr.model.filter.TrackLostDeviceFilterRequest;
import com.gl.mdr.model.generic.GenricResponse;
import com.gl.mdr.model.generic.SearchCriteria;
import com.gl.mdr.repo.app.ActiveMsisdnListRepository;
import com.gl.mdr.repo.app.AlertRepository;
import com.gl.mdr.repo.app.AttachedFileInfoRepository;
import com.gl.mdr.repo.app.LostDeviceDetailRepository;
import com.gl.mdr.repo.app.StatesInterpretaionRepository;
import com.gl.mdr.repo.app.SystemConfigListRepository;
import com.gl.mdr.repo.app.SystemConfigurationDbRepository;
import com.gl.mdr.repo.app.TrackLostDevicesRepository;
import com.gl.mdr.repo.app.UserProfileRepository;
import com.gl.mdr.repo.app.UserRepository;
import com.gl.mdr.repo.audit.AuditTrailRepository;
import com.gl.mdr.specificationsbuilder.GenericSpecificationBuilder;
import com.gl.mdr.util.CustomMappingStrategy;
import com.gl.mdr.util.Utility;
import com.opencsv.CSVWriter;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;


@Service
public class TrackLostDeviceServiceImpl {
	private static final Logger logger = LogManager.getLogger(TrackLostDeviceServiceImpl.class);

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
	LostDeviceDetailRepository lostDeviceDetailRepository;
	
	@Autowired
	ActiveMsisdnListRepository activeMsisdnListRepository;
	
	@Autowired
	Utility utility;

	@Autowired
	TrackLostDevicesRepository trackLostDevicesRepository;
	
	@Autowired
	AlertFeign alertFeign;
	
	@Autowired
	AlertRepository alertRepository;
	
	
	@Value("${serverName}")
	public String serverName;

	public Page<TrackLostDevices> getTrackLostDevicesDetails(
			TrackLostDeviceFilterRequest trackLostRequest, Integer pageNo, Integer pageSize,String operation) {
		
		try {
			logger.info("Track Lost Device Request" +trackLostRequest);
			
			String orderColumn;
			if(operation.equals("Export")) {
				orderColumn = "modifiedOn";
			}else {
				logger.info("column Name :: " + trackLostRequest.getOrderColumnName()); 
				orderColumn = "Date & Time".equalsIgnoreCase(trackLostRequest.getOrderColumnName()) ? "createdOn" :
						"Request Number".equalsIgnoreCase(trackLostRequest.getOrderColumnName()) ? "request_id" :
							"IMSI".equalsIgnoreCase(trackLostRequest.getOrderColumnName()) ? "imsi" :
								"IMEI".equalsIgnoreCase(trackLostRequest.getOrderColumnName()) ? "imei" :
									"MSISDN".equalsIgnoreCase(trackLostRequest.getOrderColumnName()) ? "msisdn" :
										"Operator".equalsIgnoreCase(trackLostRequest.getOrderColumnName()) ? "operator" :
											"Request Type".equalsIgnoreCase(trackLostRequest.getOrderColumnName()) ? "requestType" :
												"Status".equalsIgnoreCase(trackLostRequest.getOrderColumnName()) ? "status"
														:"modifiedOn"; 
			}
			
			logger.info("orderColumn data:  "+orderColumn);
			logger.info("---system.getSort() : "+trackLostRequest.getSort());
			Sort.Direction direction;
			if("modifiedOn".equalsIgnoreCase(orderColumn)) {
				direction=Sort.Direction.DESC;
			}
			else {
				direction= SortDirection.getSortDirection(trackLostRequest.getSort());
			}

			AuditTrail auditTrail = new AuditTrail();
			auditTrail.setFeatureName("Track Lost/Stolen Device");
			auditTrail.setSubFeature("View All");
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

			if("modifiedOn".equalsIgnoreCase(orderColumn) && SortDirection.getSortDirection(trackLostRequest.getSort()).equals(Sort.Direction.ASC)) {
				direction=Sort.Direction.ASC;
			}
			Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, orderColumn));

			GenericSpecificationBuilder<TrackLostDevices> uPSB = new GenericSpecificationBuilder<TrackLostDevices>(propertiesReader.dialect);

			if(Objects.nonNull(trackLostRequest.getStartDate()) && !trackLostRequest.getStartDate().equals(""))
				uPSB.with(new SearchCriteria("createdOn", trackLostRequest.getStartDate() , SearchOperation.GREATER_THAN, Datatype.DATE));
			
			if(Objects.nonNull(trackLostRequest.getEndDate()) && !trackLostRequest.getEndDate().equals(""))
				uPSB.with(new SearchCriteria("createdOn",trackLostRequest.getEndDate() , SearchOperation.LESS_THAN, Datatype.DATE));
			
			if(Objects.nonNull(trackLostRequest.getModifiedOn()) && !trackLostRequest.getModifiedOn().equals(""))
				uPSB.with(new SearchCriteria("modifiedOn", trackLostRequest.getModifiedOn() , SearchOperation.EQUALITY, Datatype.DATE));
			
			if(Objects.nonNull(trackLostRequest.getImei()) && !trackLostRequest.getImei().equals(""))
				uPSB.with(new SearchCriteria("imei",trackLostRequest.getImei() ,  SearchOperation.LIKE, Datatype.STRING));
			
			if(Objects.nonNull(trackLostRequest.getImsi()) && !trackLostRequest.getImsi().equals(""))
				uPSB.with(new SearchCriteria("imsi",trackLostRequest.getImsi() ,  SearchOperation.LIKE, Datatype.STRING));
			
			if(Objects.nonNull(trackLostRequest.getRequestType()) && !trackLostRequest.getRequestType().equals(""))
				uPSB.with(new SearchCriteria("requestType",trackLostRequest.getRequestType() ,  SearchOperation.LIKE, Datatype.STRING));

			if(trackLostRequest.getMsisdn() !=null) {
				logger.info("getTrackLostDevicesDetails MSISDN found ="+trackLostRequest.getMsisdn());
				if(Objects.nonNull(trackLostRequest.getMsisdn()) && !trackLostRequest.getMsisdn().equals(""))
					uPSB.with(new SearchCriteria("msisdn",trackLostRequest.getMsisdn() ,  SearchOperation.LIKE, Datatype.STRING));
			}
			if(Objects.nonNull(trackLostRequest.getOperator()) && !trackLostRequest.getOperator().equals(""))
				uPSB.with(new SearchCriteria("operator",trackLostRequest.getOperator() ,  SearchOperation.LIKE, Datatype.STRING));

			if(Objects.nonNull(trackLostRequest.getRequestNo()) && !trackLostRequest.getRequestNo().equals(""))
				uPSB.with(new SearchCriteria("request_id", trackLostRequest.getRequestNo() , SearchOperation.LIKE, Datatype.STRING));
			
			if(Objects.nonNull(trackLostRequest.getStatus()) && !trackLostRequest.getStatus().equals(""))
				uPSB.with(new SearchCriteria("status", trackLostRequest.getStatus(), SearchOperation.EQUALITY, Datatype.STRING));
			
			List<String> statusesToExclude = Arrays.asList("null", "NULL");
			uPSB.with(new SearchCriteria("request_id", statusesToExclude, SearchOperation.NOT_IN, Datatype.STRING));
			
			//return  trackLostDevicesRepository.findAll(uPSB.build(),pageable);
			Page<TrackLostDevices> pageResult = trackLostDevicesRepository.findAll(uPSB.build(), pageable);

			// Filter out null or blank request_ids
			List<TrackLostDevices> filteredList = pageResult.getContent().stream()
			    .filter(detail -> detail.getRequest_id() != null && !detail.getRequest_id().isEmpty())
			    .collect(Collectors.toList());

			// Now, process the filtered list and set interpretations
			for (TrackLostDevices detail : filteredList) {
			    try {
			        String interpretation = getInterpretationForStatus(Integer.parseInt(detail.getStatus()), 93L);
			        detail.setInterpretation(interpretation);
			    } catch (Exception e) {
					e.printStackTrace();
			        logger.info("Exception when get status =" + detail.getStatus() + " getFeatureId=" + 93L + " Exception=" + e.getMessage());
			    }
			    
			    logger.info("get TrackLostDevices status =" + detail.getStatus() + " getFeatureId=" + 93L);
			}

			// Create a new Page with the filtered content
			Page<TrackLostDevices> filteredPageResult = new PageImpl<>(filteredList, pageable, pageResult.getTotalElements());

			// Return the filtered Page
			return filteredPageResult;

//			Page<TrackLostDevices> pageResult = trackLostDevicesRepository.findAll(uPSB.build(), pageable);
//			
//            for (TrackLostDevices detail : pageResult.getContent()) {
//            	
//            	try {
//            		if(detail.getRequest_id()==null || detail.getRequest_id().isEmpty()) {
//            			
//            		}else {
//            			String interpretation = getInterpretationForStatus(Integer.parseInt(detail.getStatus()),  93L);
//   		             	detail.setInterpretation(interpretation);
//            		}
//					 
//		                
//				} catch (Exception e) {
//					// TODO: handle exception
//					logger.info("Exception when get status ="+detail.getStatus()+" getFeatureId="+93L+" Exception="+e.getMessage());
//				}
//            	
//            	logger.info("get TrackLostDevices status ="+detail.getStatus()+" getFeatureId="+93L);
//               
//            }
//			return pageResult;
			

		}catch(Exception e) {
			logger.info("Exception found ="+e.getMessage());
			logger.info(e.getClass().getMethods().toString());
			logger.info(e.toString());
			return null;
		}
	}
	public String getInterpretationForStatus(int status, Long featureId) {
        StatesInterpretationDb interpretation = statesInterpretaionRepository.findByStateAndFeatureId(status, featureId);
        return interpretation != null ? interpretation.getInterpretation() : null;
    }
	
	public FileDetails exportData(TrackLostDeviceFilterRequest trackLostRequest) {
		logger.info("inside export Track Lost/Stolen Device Feature service");
		logger.info("Export Request:  "+trackLostRequest);
		String fileName = null;
		Writer writer   = null;
		TrackLostDeviceFileModel uPFm = null;
		SystemConfigurationDb dowlonadDir=systemConfigurationDbRepository.getByTag("file.download-dir");
		SystemConfigurationDb dowlonadLink=systemConfigurationDbRepository.getByTag("file.download-link");
		DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

		Integer pageNo = 0;
		Integer pageSize = Integer.valueOf(systemConfigurationDbRepository.getByTag("file.max-file-record").getValue());
		String filePath  = dowlonadDir.getValue();
		StatefulBeanToCsvBuilder<TrackLostDeviceFileModel> builder = null;
		StatefulBeanToCsv<TrackLostDeviceFileModel> csvWriter      = null;
		List<TrackLostDeviceFileModel> fileRecords       = null;
		MappingStrategy<TrackLostDeviceFileModel> mapStrategy = new CustomMappingStrategy<>();

		AuditTrail auditTrail = new AuditTrail();
		auditTrail.setFeatureName("Track Lost/Stolen Device");
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
			mapStrategy.setType(TrackLostDeviceFileModel.class);
			trackLostRequest.setSort("");
			List<TrackLostDevices> list = getTrackLostDevicesDetails(trackLostRequest, pageNo, pageSize,"Export").getContent();
			if( list.size()> 0 ) {
				fileName = LocalDateTime.now().format(dtf2).replace(" ", "_")+"_track_lost_device.csv";
			}else {
				fileName = LocalDateTime.now().format(dtf2).replace(" ", "_")+"_track_lost_device.csv";
			}
			logger.info(" file path plus file name: "+Paths.get(filePath+fileName));
			writer = Files.newBufferedWriter(Paths.get(filePath + fileName));
			builder = new StatefulBeanToCsvBuilder<>(writer);

			csvWriter = builder.withMappingStrategy(mapStrategy).withSeparator(',').withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();

			if( list.size() > 0 ) {
				fileRecords = new ArrayList<TrackLostDeviceFileModel>(); 
				for( TrackLostDevices trackLostDevices : list ) {
					uPFm = new TrackLostDeviceFileModel();
					uPFm.setCreatedOn(trackLostDevices.getCreatedOn());
					uPFm.setRequestNo(trackLostDevices.getRequest_id());
					uPFm.setImei(trackLostDevices.getImei());
					uPFm.setImsi(trackLostDevices.getImsi());
					uPFm.setMsisdn(trackLostDevices.getMsisdn());
					uPFm.setOperator(trackLostDevices.getOperator());
					uPFm.setRequestType(trackLostDevices.getRequestType());
//					if(trackLostDevices.getStatus().equals("0")) {
//						uPFm.setStatus(trackLostDevices.getStatus());
//					}else if(trackLostDevices.getStatus().equals("1")) {
//						uPFm.setStatus(trackLostDevices.getStatus();
//					}else if(trackLostDevices.getStatus().equals("0")) {
//						uPFm.setStatus(trackLostDevices.getStatus());
//					}else {
//						uPFm.setStatus(trackLostDevices.getStatus());
//					}
					String interpretation = getInterpretationForStatus(Integer.parseInt(trackLostDevices.getStatus()), 93L);
					uPFm.setStatus(interpretation);
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
				e.printStackTrace();
			}
		}

	}

	public ResponseEntity<?> getTrackLostData(TrackLostDeviceFilterRequest trackLostRequest){
		logger.info("View Track Lost Devices by id : "+trackLostRequest.getId());
		Optional<TrackLostDevices> result=trackLostDevicesRepository.findById(trackLostRequest.getId());
		logger.info("View result : "+result);
		if(result.isPresent()) {
			GenricResponse response=new GenricResponse(200,"","",result.get());
			return  new ResponseEntity<>(response,HttpStatus.OK);
		}else {
			GenricResponse response=new GenricResponse(500,"Something wrong happend","",result);
			return  new ResponseEntity<>(response,HttpStatus.OK);
		}
	}
	
	public List<String> getDistinctOperator() {
		List<String> operator=trackLostDevicesRepository.findDistinctOperator();
		return operator;
	}
	
	public List<String> getDistinctStatus() {
		List<String> status=trackLostDevicesRepository.findDistinctStatus();
		return status;
	}
	public GenricResponse setTrackLostDevices(LostDeviceRequest lostDeviceRequest , String operator, HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		GenricResponse genricResponse = new GenricResponse();
		
		String remoteIp=request.getRemoteAddr();

		String whilteListIP=propertiesReader.WHITE_LIST_IP;
		
		boolean checkIpStatus=propertiesReader.WHITE_LIST_IP_STATUS;
		
		com.gl.mdr.model.app.AlertMessages alertMessages = null;
		
		AlertDto alertDto = new AlertDto();
        
		logger.info("setTrackLostDevices() Start Track Lost Devices Request=" + lostDeviceRequest.toString()+", Remote IP ="+remoteIp+" WHITE_LIST_IP="+whilteListIP+", WHITE_LIST_IP_STATUS ="+checkIpStatus+"");
		
		if(checkIpStatus) {
			if(!whilteListIP.contains(remoteIp)) {
				genricResponse.setErrorCode(202);
				genricResponse.setMessage("IP not White listed");
				genricResponse.setTxnId("FAILED");
				return genricResponse;
			}
		}
		try {
			logger.info("Start Data insert in table Request=" + lostDeviceRequest.toString());
			
			alertMessages = alertRepository.findByAlertId("alert8004");
			
			if(alertMessages!=null) {
				
				alertDto.setAlertId(alertMessages.getAlertId());
		        alertDto.setUserId("0");
		        alertDto.setAlertMessage(alertMessages.getDescription());
		        alertDto.setAlertProcess("Tracking EDR Data");
		        alertDto.setServerName(serverName);
		        alertDto.setFeatureName("Tracking EDR");
		        alertDto.setTxnId("");
			}
			
			TrackLostDevices trackLostDevices=new TrackLostDevices();
			
			trackLostDevices.setCreatedOn(LocalDateTime.now());
			trackLostDevices.setModifiedOn(LocalDateTime.now());
			//trackLostDevices.setResult(lostDeviceRequest.getResult());
			trackLostDevices.setDeviceType(lostDeviceRequest.getDeviceType());
			trackLostDevices.setImei(lostDeviceRequest.getImei());
			trackLostDevices.setImsi(lostDeviceRequest.getImsi());
			trackLostDevices.setList_type(lostDeviceRequest.getAppliedListName());
			trackLostDevices.setMsisdn(lostDeviceRequest.getMsisdn());
			trackLostDevices.setOperator(operator);
			trackLostDevices.setHostname(lostDeviceRequest.getHostname());
			trackLostDevices.setOrigin_host(lostDeviceRequest.getOriginHost());
			trackLostDevices.setServer(lostDeviceRequest.getServer());
			trackLostDevices.setSession_id(lostDeviceRequest.getSessionId());
			try {
				trackLostDevices.setStatus( Integer.parseInt(lostDeviceRequest.getStatus())+"");
			} catch (Exception e) {
				// TODO: handle exception
				trackLostDevices.setStatus("7");
				e.printStackTrace();
			}
			
			trackLostDevices.setTime_stamp(lostDeviceRequest.getTimeStamp());
			trackLostDevices.setTime_taken(lostDeviceRequest.getTimeTaken());
			trackLostDevices.setProtocol(lostDeviceRequest.getProtocol());
			trackLostDevices.setReason_code(lostDeviceRequest.getReasonCode()+"");
			trackLostDevices.setTac(lostDeviceRequest.getTac());
			//trackLostDevices.setValue(lostDeviceRequest.getValue());
			
			ActiveMsisdnList activeMsisdnList= activeMsisdnListRepository.findByImsi(lostDeviceRequest.getImsi());
			
			if(activeMsisdnList!=null) {
				trackLostDevices.setMsisdn(activeMsisdnList.getMsisdn());
			}else {
				trackLostDevices.setMsisdn("");
			}
			
			String imei=lostDeviceRequest.getImei();
			if(imei.length()>=14 && imei.length()<=16) {
				//String  imei_14_digit= imei.substring(0, Math.min(imei.length(), 14));
				//logger.info("First 14 Digit IMEI = " +imei_14_digit +" and Request IMEI = "+lostDeviceRequest.getImei());
				
				LostDeviceDetail lostDeviceDetail= lostDeviceDetailRepository.findByImei(imei);
				
				if(lostDeviceDetail !=null) {
					
					trackLostDevices.setRequest_id(lostDeviceDetail.getRequestId());
					try {
						trackLostDevices.setRequestType(lostDeviceDetail.getRequestType());
					} catch (Exception e) {
						// TODO: handle exception
						trackLostDevices.setRequestType("");
						logger.info("Exception When Set Request type = " +lostDeviceDetail.getRequestType());
						e.printStackTrace();
					}
				}else {
					trackLostDevices.setRequest_id("");
					trackLostDevices.setRequestType("");
				}
				trackLostDevicesRepository.save(trackLostDevices);
				logger.info("data inserted successfully Request=" + lostDeviceRequest.toString());
				
				genricResponse.setErrorCode(200);
				genricResponse.setMessage("data inserted successfully ");
				genricResponse.setTxnId("DONE");
				return genricResponse;
			}else {
				genricResponse.setErrorCode(201);
				genricResponse.setMessage("Wrong IMEI Size ");
				genricResponse.setTxnId("FAILED");
				genricResponse.setTag(imei);
				return genricResponse;
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("When Data insert in table then Exception API Request=" + lostDeviceRequest.toString());
			genricResponse.setErrorCode(201);
			genricResponse.setMessage("Wrong Data");
			genricResponse.setTxnId("FAILED");
			logger.info("setTrackLostDevices():: Raise Alert request API=" + alertDto);
			genricResponse = alertFeign.raiseAnAlert(alertDto);
			logger.info("setTrackLostDevices():: Raise Alert API response=" + genricResponse);
			e.printStackTrace();
			return genricResponse;
		}
		
		
	}
	
	public List<String> getTrackLostRequestType() {
		List<String> status=trackLostDevicesRepository.findDistinctRequestType();
		return status;
	}
	
	
	
}
