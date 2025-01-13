package com.gl.mdr.service.impl;

import com.gl.mdr.bulk.imei.feign.NotificationFeign;
import com.gl.mdr.configuration.PropertiesReader;
import com.gl.mdr.configuration.SortDirection;
import com.gl.mdr.dto.StolenLostModelDto;
import com.gl.mdr.dto.StolenLostModelDtoResponse;
import com.gl.mdr.exceptions.ResourceServicesException;
import com.gl.mdr.mapper.StolenLostModelMapper;
import com.gl.mdr.model.app.*;
import com.gl.mdr.model.audit.AuditTrail;
import com.gl.mdr.model.constants.Datatype;
import com.gl.mdr.model.constants.SearchOperation;
import com.gl.mdr.model.file.FileDetails;
import com.gl.mdr.model.file.StolenPoliceVerificationDeviceFileModel;
import com.gl.mdr.model.filter.MOIStatus;
import com.gl.mdr.model.filter.MOIVerificationDeviceFilterRequest;
import com.gl.mdr.model.generic.GenricResponse;
import com.gl.mdr.model.generic.SearchCriteria;
import com.gl.mdr.repo.app.*;
import com.gl.mdr.repo.audit.AuditTrailRepository;
import com.gl.mdr.specificationsbuilder.GenericSpecificationBuilder;
import com.gl.mdr.util.CustomMappingStrategy;
import com.gl.mdr.util.Utility;
import com.opencsv.CSVWriter;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
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


@Service
public class StolenVerificationDeviceServiceImpl {
	private static final Logger logger = LogManager.getLogger(StolenVerificationDeviceServiceImpl.class);

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


	@Autowired
	ProvinceRepository provinceRepository;

	@Autowired DistrictRepository districtRepository;

	@Autowired CommunePoliceRepository communePoliceRepository;

	@Autowired CommuneRepository communeRepository;


	public StolenLostModelDtoResponse getVerificationDevicesDetails(
			MOIVerificationDeviceFilterRequest trackLostRequest, Integer pageNo, Integer pageSize,String operation) {
		StolenLostModelDtoResponse stolenLostModelDtoResponse=new StolenLostModelDtoResponse();
		try {
			logger.info("Police Verification Lost/Stolen Request" +trackLostRequest);
			String orderColumn;
			if(operation.equals("Export")) {
				orderColumn = "modifiedOn";
			}else {
				logger.info("column Name :: " + trackLostRequest.getOrderColumnName()); 
				orderColumn = "Date & Time".equalsIgnoreCase(trackLostRequest.getOrderColumnName()) ? "createdOn" :
					"Request Number".equalsIgnoreCase(trackLostRequest.getOrderColumnName()) ? "requestId" :
						"IMEI".equalsIgnoreCase(trackLostRequest.getOrderColumnName()) ? "imei" :
							"Uploaded By".equalsIgnoreCase(trackLostRequest.getOrderColumnName()) ? "uploadedBy" :
								"Request Mode".equalsIgnoreCase(trackLostRequest.getOrderColumnName()) ? "requestMode" :
									"Request Type".equalsIgnoreCase(trackLostRequest.getOrderColumnName()) ? "requestType" :
										"Province".equalsIgnoreCase(trackLostRequest.getOrderColumnName()) ? "province" :
											"District".equalsIgnoreCase(trackLostRequest.getOrderColumnName()) ? "district" :
												"Commune".equalsIgnoreCase(trackLostRequest.getOrderColumnName()) ? "commune" :
													"Device Type".equalsIgnoreCase(trackLostRequest.getOrderColumnName()) ? "deviceType" :
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
			Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, orderColumn));

			GenericSpecificationBuilder<StolenLostModel> uPSB = new GenericSpecificationBuilder<StolenLostModel>(propertiesReader.dialect);

			logger.info("GenericSpecificationBuilder usersType["+usersType+"]uPSB:  "+uPSB.toString()+" user Id : "+trackLostRequest.getUserId()+", "
					+ "Staus="+trackLostRequest.getStatus()+", Province="+trackLostRequest.getProvince()+", Commune="+trackLostRequest.getCommune()+", District ="+trackLostRequest.getDistrict()+"");

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

			if (Objects.nonNull(trackLostRequest.getUploadedBy())  && !trackLostRequest.getUploadedBy().equals("") )
				uPSB.with(new SearchCriteria("createdBy", trackLostRequest.getUploadedBy() , SearchOperation.EQUALITY, Datatype.STRING));

			if(Objects.nonNull(trackLostRequest.getRequestMode()) && !trackLostRequest.getRequestMode().equals(""))
				uPSB.with(new SearchCriteria("requestMode", trackLostRequest.getRequestMode() , SearchOperation.EQUALITY, Datatype.STRING));

			if(Objects.nonNull(trackLostRequest.getRequestType()) && !trackLostRequest.getRequestType().equals(""))
				uPSB.with(new SearchCriteria("requestType", trackLostRequest.getRequestType() , SearchOperation.EQUALITY, Datatype.STRING));


			if (Objects.nonNull(trackLostRequest.getProvince()) && !trackLostRequest.getProvince().equals("")) {
				uPSB.with(new SearchCriteria("province", trackLostRequest.getProvince(), SearchOperation.EQUALITY, Datatype.STRING));
			}

			if (Objects.nonNull(trackLostRequest.getDistrict()) && !trackLostRequest.getDistrict().equals("")) {
				uPSB.with(new SearchCriteria("district", trackLostRequest.getDistrict(), SearchOperation.EQUALITY, Datatype.STRING));
			}

			if (Objects.nonNull(trackLostRequest.getCommune()) && !trackLostRequest.getCommune().equals("")) {
				uPSB.with(new SearchCriteria("commune", trackLostRequest.getCommune(), SearchOperation.EQUALITY, Datatype.STRING));
			}

			if(Objects.nonNull(trackLostRequest.getDeviceType()) && !trackLostRequest.getDeviceType().equals(""))
				uPSB.with(new SearchCriteria("deviceType",trackLostRequest.getDeviceType() ,  SearchOperation.EQUALITY, Datatype.STRING));
			
//			if(Objects.nonNull(trackLostRequest.getStatus()) && !trackLostRequest.getStatus().equals(""))
//				uPSB.with(new SearchCriteria("status",trackLostRequest.getStatus() ,  SearchOperation.EQUALITY, Datatype.STRING));
			

			if (Objects.nonNull(trackLostRequest.getStatus()) && !trackLostRequest.getStatus().isEmpty()) {
			    // Single status provided by trackLostRequest
			    uPSB.with(new SearchCriteria("status",  trackLostRequest.getStatus() , SearchOperation.EQUALITY, Datatype.STRING));
			}
			else {
			    // Status is null or empty, add multiple predefined status conditions with OR logic
//			    List<String> statuses = Arrays.asList("DONE","Done","PENDING_MOI","REJECT", "VERIFY_MOI", "INIT","Fail");
//			    for (String status : statuses) {
//			        uPSB.or(new SearchCriteria("status", status,  SearchOperation.EQUALITY, Datatype.STRING));
//			    }
				
				uPSB.addSpecification(uPSB.getStatus(new SearchCriteria("status", "Null", SearchOperation.EQUALITY_CASE_INSENSITIVE, Datatype.STRING)));

			}
			
			
			

			Page<StolenLostModel> pageResult=stolenPoliceVerificationDevicesRepository.findAll(uPSB.build(),pageable);

			SystemConfigurationDb ss=systemConfigurationDbRepository.getByTag("upload_file_link");

			List<StolenLostModelDto> stolenLostModelDtolist= new ArrayList<>();
			stolenLostModelDtoResponse.setContent(stolenLostModelDtolist);
			stolenLostModelDtoResponse.setTotalElements(pageResult.getTotalElements());
			stolenLostModelDtoResponse.setTotalPages(pageResult.getTotalPages());
			for(StolenLostModel stolen : pageResult.getContent()) {
				StolenLostModelDto stolenLostModelDto= StolenLostModelMapper.INSTANCE.stolenLostModelToDto(stolen);
				stolenLostModelDtolist.add(stolenLostModelDto);
				stolenLostModelDto.setFileUrl(ss.getValue().replace("$LOCAL_IP",propertiesReader.localIp));
				stolenLostModelDto.setStatus(stolen.getStatus().trim());
				try {
					if(stolen.getProvince()!=null) {
						ProvinceDb pro= provinceRepository.findById(Long.parseLong(stolen.getProvince()));
						if(pro!=null) {
							stolenLostModelDto.setProvince(pro.getProvince());
						}
					}

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				try {
					if(stolen.getDistrict()!=null) {
						DistrictDb dis=districtRepository.findById(Long.parseLong(stolen.getDistrict()));
						if(dis!=null) {
							stolenLostModelDto.setDistrict(dis.getDistrict());
						}
					}


				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				try {
					if(stolen.getCommune()!=null) {
						CommuneDb commune=communeRepository.findById(Long.parseLong(stolen.getCommune()));
						if(commune!=null) {
							stolenLostModelDto.setCommune(commune.getCommune());
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

			}
			logger.info(" stolenLostModelDtoResponse ="+stolenLostModelDtoResponse);
			return stolenLostModelDtoResponse;

		}catch(Exception e) {
			logger.info("Exception found ="+e.getMessage());
			logger.info(e.getClass().getMethods().toString());
			logger.info(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	public FileDetails exportData(MOIVerificationDeviceFilterRequest trackLostRequest) {
		logger.info("inside export MOI Verification Device Feature service");
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
		auditTrail.setFeatureName("Export MOI Verification");
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
			List<StolenLostModelDto> list = getVerificationDevicesDetails(trackLostRequest, pageNo, pageSize,"Export").getContent();
			if( list.size()> 0 ) {
				fileName = LocalDateTime.now().format(dtf2).replace(" ", "_")+"_moi_verification_device.csv";
			}else {
				fileName = LocalDateTime.now().format(dtf2).replace(" ", "_")+"_moi_verification_device.csv";
			}
			logger.info(" file path plus file name: "+Paths.get(filePath+fileName) +" and file size : "+list.size());
			writer = Files.newBufferedWriter(Paths.get(filePath + fileName));
			builder = new StatefulBeanToCsvBuilder<>(writer);

			csvWriter = builder.withMappingStrategy(mapStrategy).withSeparator(',').withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();

			if( list.size() > 0 ) {
				fileRecords = new ArrayList<StolenPoliceVerificationDeviceFileModel>(); 
				for( StolenLostModelDto stolenLostModel : list ) {
					uPFm = new StolenPoliceVerificationDeviceFileModel();
					uPFm.setCreatedOn(stolenLostModel.getCreatedOn());
					uPFm.setRequestId(stolenLostModel.getRequestId());
					uPFm.setRequestMode(stolenLostModel.getRequestMode());
					uPFm.setProvince(stolenLostModel.getProvince());
					uPFm.setDistrict(stolenLostModel.getDistrict());
					uPFm.setCommune(stolenLostModel.getCommune());
					uPFm.setImei1(stolenLostModel.getImei1());
					uPFm.setRequestId(stolenLostModel.getRequestId());
					uPFm.setRequestType(stolenLostModel.getRequestType());
					//uPFm.setUserStatus(stolenLostModel.getStatus());
					if (stolenLostModel.getStatus().equalsIgnoreCase("INIT")) {
						uPFm.setUserStatus("Pending");
					} else if (stolenLostModel.getStatus().equalsIgnoreCase("VERIFY_MOI")) {
						uPFm.setUserStatus("Pending MOI");
					} else if (stolenLostModel.getStatus().equalsIgnoreCase("REJECT")) {
						uPFm.setUserStatus("Reject");
					} else if (stolenLostModel.getStatus().equalsIgnoreCase("START")) {
						uPFm.setUserStatus("Pending EIRS");
					} else if (stolenLostModel.getStatus().equalsIgnoreCase("DONE")) {
						uPFm.setUserStatus("Done");
					}
					else if (stolenLostModel.getStatus().equalsIgnoreCase("FAIL")) {
						uPFm.setUserStatus("Fail");
					}

					uPFm.setUploadedBy(stolenLostModel.getCreatedBy());
					uPFm.setDeviceType(stolenLostModel.getDeviceType());
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

	public ResponseEntity<?> getPoliceVerificationData(MOIVerificationDeviceFilterRequest trackLostRequest){
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

	public List<String> getDistinctRequestMode() {
		List<String> operator=stolenPoliceVerificationDevicesRepository.findDistinctrequestMode();
		return operator;
	}

	public List<String> getDistinctRequestType() {
		List<String> operator=stolenPoliceVerificationDevicesRepository.getDistinctRequestType();
		return operator;
	}

	public List<String> getDistinctCreatedBy() {
		List<String> operator=stolenPoliceVerificationDevicesRepository.getDistinctCreatedBy();
		return operator;
	}

	public List<ProvinceDb> getProvince() {
		List<ProvinceDb> provinceDb=provinceRepository.findAll();
		return provinceDb;
	}

	public List<CommuneDb> getCommune() {
		List<CommuneDb> communeDb=communeRepository.findAll();
		return communeDb;
	}

	public List<DistrictDb> getDistrict() {
		List<DistrictDb> districtDb=districtRepository.findAll();
		return districtDb;
	}
	public List<PoliceStationDb> getPoliceStation() {
		List<PoliceStationDb> policeStationDb=communePoliceRepository.findAll();
		return policeStationDb;
	}

	public List<MOIStatus> getPolistStatus(){

		List<MOIStatus> list=new ArrayList<MOIStatus>();
		list.add(createMOIStatus("Pending", "INIT"));
		list.add(createMOIStatus("Pending MOI", "VERIFY_MOI"));
		list.add(createMOIStatus("Reject", "REJECT"));
		list.add(createMOIStatus("Pending EIRS", "START"));
		list.add(createMOIStatus("Done", "Done"));
		list.add(createMOIStatus("Fail", "Fail"));
		return list;
	}
	public List<MOIStatus> getMOIAdminStatus(){
		List<MOIStatus> list=new ArrayList<MOIStatus>();
		list.add(createMOIStatus("Pending Police", "INIT"));
		list.add(createMOIStatus("Pending", "VERIFY_MOI"));
		list.add(createMOIStatus("Reject", "REJECT"));
		list.add(createMOIStatus("Pending EIRS", "START"));
		list.add(createMOIStatus("Done", "Done"));
		list.add(createMOIStatus("Fail", "Fail"));
		return list;
	}
	private MOIStatus createMOIStatus(String name, String value) {
		MOIStatus status = new MOIStatus();
		status.setName(name);
		status.setValue(value);
		return status;
	}


	public  ResponseEntity<?> updateDevicesStatus(MOIVerificationDeviceFilterRequest filterRequest) {
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
	public ResponseEntity<?> updateLostDeviceStatus(MOIVerificationDeviceFilterRequest filterRequest) {
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
				if(filterRequest.getStatus().equalsIgnoreCase("START")) {
					stolen.setUserStatus("Pending");
				}else {
					stolen.setUserStatus("Reject");
				}


				stolen= stolenPoliceVerificationDevicesRepository.save(stolen);

				try {
					if(filterRequest.getStatus().equalsIgnoreCase("START")) {
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
						if(filterRequest.getStatus().equalsIgnoreCase("REJECT")) {
							NotificationAPI(stolen.getContactNumberForOtp(),stolen.getOtpEmail(), stolen.getLanguage(), stolen.getRequestId());
						}

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
		//notificationModel.setFeatureName("MOI Admin VERIFY Device");
		notificationModel.setFeatureName(propertiesReader.stolenFeatureName);
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
	public List<String> getDistinctMOIDeviceType() {
		// TODO Auto-generated method stub
		List<String> deviceType=stolenPoliceVerificationDevicesRepository.findDistinctDeviceType();
		return deviceType;
	}
	
//	 List<String> statuses = Arrays.asList("DONE","Done","PENDING_MOI","REJECT", "VERIFY_MOI", "INIT","Fail");
//	    for (String status : statuses) {
//	        uPSB.or(new SearchCriteria("status", status,  SearchOperation.EQUALITY, Datatype.STRING));
//	    }
	

}
