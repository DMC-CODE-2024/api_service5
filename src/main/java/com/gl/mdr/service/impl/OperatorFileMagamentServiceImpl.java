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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.gl.mdr.configuration.PropertiesReader;
import com.gl.mdr.configuration.SortDirection;
import com.gl.mdr.exceptions.ResourceServicesException;
import com.gl.mdr.model.app.ListFileManagementModel;
import com.gl.mdr.model.app.StatesInterpretationDb;
import com.gl.mdr.model.app.SystemConfigurationDb;
import com.gl.mdr.model.app.User;
import com.gl.mdr.model.app.UserProfile;
import com.gl.mdr.model.audit.AuditTrail;
import com.gl.mdr.model.constants.Datatype;
import com.gl.mdr.model.constants.SearchOperation;
import com.gl.mdr.model.file.FileDetails;
import com.gl.mdr.model.file.OperatorListFileModel;
import com.gl.mdr.model.filter.ListFileFilterRequest;
import com.gl.mdr.model.generic.SearchCriteria;
import com.gl.mdr.repo.app.AttachedFileInfoRepository;
import com.gl.mdr.repo.app.OperatorListFileManagementRepository;
import com.gl.mdr.repo.app.StatesInterpretaionRepository;
import com.gl.mdr.repo.app.SystemConfigListRepository;
import com.gl.mdr.repo.app.SystemConfigurationDbRepository;
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
public class OperatorFileMagamentServiceImpl {
	private static final Logger logger = LogManager.getLogger(OperatorFileMagamentServiceImpl.class);

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
	OperatorListFileManagementRepository listFileMgmtRepository;
	

	public Page<ListFileManagementModel> getOperatorFileDetails(
			ListFileFilterRequest fileRequest, Integer pageNo, Integer pageSize,String operation) {
		
		try {
			logger.info("Operator List File Managemnet Request" +fileRequest);
			String orderColumn;
			if(operation.equals("Export")) {
				orderColumn = "modified_on";
			}else {
				logger.info("column Name :: " + fileRequest.getOrderColumnName()); 
				orderColumn = "Date & Time".equalsIgnoreCase(fileRequest.getOrderColumnName()) ? "createdOn" :
						"File Name".equalsIgnoreCase(fileRequest.getOrderColumnName()) ? "fileName" :
							"File Type".equalsIgnoreCase(fileRequest.getOrderColumnName()) ? "fileType" 
								:"modifiedOn"; 
			}
			logger.info("orderColumn data:  "+orderColumn);
			logger.info("---system.getSort() : "+fileRequest.getSort());
			Sort.Direction direction;
			if("modified_on".equalsIgnoreCase(orderColumn)) {
				direction=Sort.Direction.DESC;
			}
			else {
				direction= SortDirection.getSortDirection(fileRequest.getSort());
			}
			String operatorName="";
			try {
				logger.info("Get User Profile Details equest User Id : "+fileRequest.auditTrailModel.getUserId());
				
				User user = userRepository.getByid( fileRequest.auditTrailModel.getUserId());
				
				//UserProfile profile= userProfileRepository.getByUserId(fileRequest.auditTrailModel.getUserId());
				
				//logger.info("Get User Profile Details  : "+profile.toString()+" Request User Id : "+fileRequest.getUserId());
				
				if(user!=null) {
					operatorName=user.getUserProfile().getOperatorTypeName();
				}
				logger.info("Get User Profile Details  : "+operatorName+" Request User Id : "+fileRequest.auditTrailModel.getUserId());
			} catch (Exception e) {
				// TODO: handle exception
				logger.info("Exception Get User Profile Details User Id : "+fileRequest.auditTrailModel.getUserId()+" Exception : "+e.getMessage());
			}
			AuditTrail auditTrail = new AuditTrail();
			auditTrail.setFeatureName("Operator File List");
			auditTrail.setSubFeature("View All");
			auditTrail.setPublicIp(fileRequest.getPublicIp());
			auditTrail.setBrowser(fileRequest.getBrowser());
			if( Objects.nonNull(fileRequest.getUserId()) ) {
				User user = userRepository.getByid( fileRequest.getUserId());
				auditTrail.setUserId( fileRequest.getUserId() );
				auditTrail.setUserName(user.getUsername());
			}else {
				auditTrail.setUserName( "NA");
			}
			if( Objects.nonNull(fileRequest.getUserType()) ) {
				auditTrail.setUserType( fileRequest.getUserType());
				auditTrail.setRoleType( fileRequest.getUserType() );
			}else {
				auditTrail.setUserType( "NA" );
				auditTrail.setRoleType( "NA" );
			}
			auditTrail.setTxnId("NA");
			auditTrailRepository.save(auditTrail);
			
			logger.info("Operator List File Managemnet search Start feature Id --> " +fileRequest.getFeatureId());

			if("modified_on".equalsIgnoreCase(orderColumn) && SortDirection.getSortDirection(fileRequest.getSort()).equals(Sort.Direction.ASC)) {
				direction=Sort.Direction.ASC;
			}
			Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, orderColumn));
			
			logger.info("Operator List File Managemnet search Pageable  Start feature Id --> " +fileRequest.getFeatureId()+" pageNo ["+pageNo+"], pageSize ["+pageSize+"], orderColumn["+orderColumn+"]");
			
			GenericSpecificationBuilder<ListFileManagementModel> uPSB = new GenericSpecificationBuilder<ListFileManagementModel>(propertiesReader.dialect);
			
			logger.info("Operator List File Managemnet search GenericSpecificationBuilder  Start feature Id --> " +fileRequest.getFeatureId()+" pageNo ["+pageNo+"], pageSize ["+pageSize+"], orderColumn["+orderColumn+"]");

			if(Objects.nonNull(fileRequest.getStartDate()) && !fileRequest.getStartDate().equals(""))
				uPSB.with(new SearchCriteria("createdOn", fileRequest.getStartDate() , SearchOperation.GREATER_THAN, Datatype.DATE));
			
			if(Objects.nonNull(fileRequest.getEndDate()) && !fileRequest.getEndDate().equals(""))
				uPSB.with(new SearchCriteria("createdOn",fileRequest.getEndDate() , SearchOperation.LESS_THAN, Datatype.DATE));
			
			if(Objects.nonNull(fileRequest.getModifiedOn()) && !fileRequest.getModifiedOn().equals(""))
				uPSB.with(new SearchCriteria("modifiedOn", fileRequest.getModifiedOn() , SearchOperation.EQUALITY, Datatype.DATE));
			
			if(Objects.nonNull(fileRequest.getFileName()) && !fileRequest.getFileName().equals(""))
				uPSB.with(new SearchCriteria("fileName",fileRequest.getFileName() ,  SearchOperation.LIKE, Datatype.STRING));
			
			if(Objects.nonNull(fileRequest.getFileType()) && !fileRequest.getFileType().equals(""))
				uPSB.with(new SearchCriteria("fileType",fileRequest.getFileType() ,  SearchOperation.EQUALITY, Datatype.STRING));
			
			
			uPSB.with(new SearchCriteria("listType",fileRequest.getListType() ,  SearchOperation.EQUALITY, Datatype.STRING));
			
			logger.info("Operator ["+operatorName+"] Wise search Interpretation feature Id --> " +fileRequest.getFeatureId());
			
			if(Objects.nonNull(operatorName) && !operatorName.equals(""))
			uPSB.with(new SearchCriteria("operatorName",operatorName ,  SearchOperation.EQUALITY, Datatype.STRING));
			else
				uPSB.with(new SearchCriteria("operatorName","NA" ,  SearchOperation.EQUALITY, Datatype.STRING));
			
			Page<ListFileManagementModel> pageResult = listFileMgmtRepository.findAll(uPSB.build(), pageable);
			
			logger.info("Operator ["+operatorName+"] List File Managemnet search Interpretation feature Id --> " +fileRequest.getFeatureId());
			
            for (ListFileManagementModel detail : pageResult.getContent()) {
            	
            	try {
					 String interpretation = getInterpretationForStatus(Integer.parseInt(detail.getFileType()), fileRequest.getFeatureId());
		             detail.setInterpretation(interpretation);
		                
				} catch (Exception e) {
					// TODO: handle exception
					logger.info("Exception when get status ="+detail.getFileType()+" getFeatureId="+fileRequest.getFeatureId()+" Exception="+e.getMessage());
				}
            	
            	logger.info("get List File Management status ="+detail.getFileType()+" getFeatureId="+fileRequest.getFeatureId());
               
            }
            
			return pageResult;
			
			
			//return  listFileMgmtRepository.findAll(uPSB.build(),pageable);

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
	public FileDetails exportData(ListFileFilterRequest fileRequest) {
		logger.info("inside export Operator List File Management Feature service");
		logger.info("Export Request:  "+fileRequest);
		String fileName = null;
		Writer writer   = null;
		OperatorListFileModel uPFm = null;
		SystemConfigurationDb dowlonadDir=systemConfigurationDbRepository.getByTag("file.download-dir");
		SystemConfigurationDb dowlonadLink=systemConfigurationDbRepository.getByTag("file.download-link");
		DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

		Integer pageNo = 0;
		Integer pageSize = Integer.valueOf(systemConfigurationDbRepository.getByTag("file.max-file-record").getValue());
		String filePath  = dowlonadDir.getValue();
		StatefulBeanToCsvBuilder<OperatorListFileModel> builder = null;
		StatefulBeanToCsv<OperatorListFileModel> csvWriter      = null;
		List<OperatorListFileModel> fileRecords       		  	= null;
		MappingStrategy<OperatorListFileModel> mapStrategy = new CustomMappingStrategy<>();

		AuditTrail auditTrail = new AuditTrail();
		auditTrail.setFeatureName("Operator List File Management");
		auditTrail.setSubFeature("Export");
		auditTrail.setPublicIp(fileRequest.getPublicIp());
		auditTrail.setBrowser(fileRequest.getBrowser());
		if( Objects.nonNull(fileRequest.getUserId()) ) {
			User user = userRepository.getByid( fileRequest.getUserId());
			auditTrail.setUserId( fileRequest.getUserId() );
			auditTrail.setUserName(user.getUsername());
		}else {
			auditTrail.setUserName( "NA");
		}
		if( Objects.nonNull(fileRequest.getUserType()) ) {
			auditTrail.setUserType( fileRequest.getUserType());
			auditTrail.setRoleType( fileRequest.getUserType() );
		}else {
			auditTrail.setUserType( "NA" );
			auditTrail.setRoleType( "NA" );
		}
		auditTrail.setTxnId("NA");
		auditTrailRepository.save(auditTrail);

		try {
			mapStrategy.setType(OperatorListFileModel.class);
			fileRequest.setSort("");
			List<ListFileManagementModel> list = getOperatorFileDetails(fileRequest, pageNo, pageSize,"Export").getContent();
			if( list.size()> 0 ) {
				fileName = LocalDateTime.now().format(dtf2).replace(" ", "_")+"_operator_file_list.csv";
			}else {
				fileName = LocalDateTime.now().format(dtf2).replace(" ", "_")+"_operator_file_list.csv";
			}
			logger.info(" file path plus file name: "+Paths.get(filePath+fileName));
			writer = Files.newBufferedWriter(Paths.get(filePath + fileName));
			builder = new StatefulBeanToCsvBuilder<>(writer);

			csvWriter = builder.withMappingStrategy(mapStrategy).withSeparator(',').withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();

			if( list.size() > 0 ) {
				
				fileRecords = new ArrayList<OperatorListFileModel>(); 
				
				for( ListFileManagementModel listFileManagementModel : list ) {
					
					uPFm = new OperatorListFileModel();
					uPFm.setCreated_on(listFileManagementModel.getCreatedOn());
					uPFm.setFile_name(listFileManagementModel.getFileName());
					uPFm.setFile_type(listFileManagementModel.getFileType());
					uPFm.setFile_path(listFileManagementModel.getFilePath());
					uPFm.setOperator_name(listFileManagementModel.getOperatorName());
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

	
		
}
