package com.gl.mdr.service.impl;

import java.time.LocalDateTime;
import java.util.Objects;

import com.gl.mdr.configuration.PropertiesReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.crypto.RuntimeCryptoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.gl.mdr.bulk.imei.feign.NotificationFeign;
import com.gl.mdr.model.app.ActiveMsisdnList;
import com.gl.mdr.model.app.ContactNumberChange;
import com.gl.mdr.model.app.EirsResponseParam;
import com.gl.mdr.model.app.NotificationModel;
import com.gl.mdr.model.app.StolenLostModel;
import com.gl.mdr.model.app.User;
import com.gl.mdr.model.audit.AuditTrail;
import com.gl.mdr.model.filter.ChangeNumberFilterRequest;
import com.gl.mdr.model.generic.GenricResponse;
import com.gl.mdr.repo.app.ActiveMsisdnListRepository;
import com.gl.mdr.repo.app.ChangeContactNumberDevicesRepository;
import com.gl.mdr.repo.app.ContactNumberChangeRepository;
import com.gl.mdr.repo.app.EirsResponseParamRepository;
import com.gl.mdr.repo.app.UserProfileRepository;
import com.gl.mdr.repo.app.UserRepository;
import com.gl.mdr.repo.audit.AuditTrailRepository;


@Service
public class ChangeContactNumberServiceImpl {
	private static final Logger logger = LogManager.getLogger(ChangeContactNumberServiceImpl.class);

	@Autowired
	PropertiesReader propertiesReader;

	@Autowired
	AuditTrailRepository auditTrailRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserProfileRepository userProfileRepository;

	@Autowired
	ContactNumberChangeRepository contactNumberChangeRepository;

	@Autowired
	EirsResponseParamRepository eirsResponseParamRepository;

	@Autowired
	NotificationFeign notificationFeign;

	@Autowired
	ActiveMsisdnListRepository activeMsisdnListRepository;

	@Autowired
	ChangeContactNumberDevicesRepository changeContactNumberDevicesRepository;

	public ResponseEntity<?> verifyRequestNumber(ChangeNumberFilterRequest request){
		GenricResponse response;
		logger.info("getVerifyRequestNumber() :: Request Number : "+request.getRequestNo()+" and Mobile Number : "+request.getMsisdn());

		StolenLostModel result=changeContactNumberDevicesRepository.findByRequestId(request.getRequestNo());
		logger.info("getVerifyRequestNumber() :: View result : "+result);
		if(result!=null) {
			if(result.getContactNumberForOtp().equalsIgnoreCase(request.getMsisdn())) {

				response=new GenricResponse(200,"Successfully Verified","",result.getStatus());

				return  new ResponseEntity<>(response,HttpStatus.OK);

			}else {

				response=new GenricResponse(500,"Please provide correct Request Id Or ContactNumber","","");
				return  new ResponseEntity<>(response,HttpStatus.OK);
			}

		}else {
			response=new GenricResponse(500,"Something wrong happend","","");
			return  new ResponseEntity<>(response,HttpStatus.OK);
		}
	}

	public  ResponseEntity<?> updateContactNumberForDeviceInfo(ChangeNumberFilterRequest filterRequest) {
		// TODO Auto-generated method stub

		logger.info("Update Contact Number For Device Info lost/Stolen  Request : " + filterRequest);

		AuditTrail auditTrail = new AuditTrail();

		auditTrail.setFeatureName("Change Contact Number");
		auditTrail.setSubFeature("Customer Care Request");

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
		try {
			auditTrailRepository.save(auditTrail);
			logger.info("Update Contact Number auditTrail Entry Complete ");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.info("Update Contact Number auditTrail Entry Complete when come Exception "+e.getLocalizedMessage());
		}
		try {

			logger.info("Check Contact Number Request Id "+filterRequest.getRequestNo()+" in StolenLostModel");
			StolenLostModel stolen=null;
			try {
				stolen=changeContactNumberDevicesRepository.findByRequestId(filterRequest.getRequestNo());
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				logger.info("Exception When Check Contact Number Request Id "+filterRequest.getRequestNo()+" in StolenLostModel");
			}

			ActiveMsisdnList activeMsisdn=activeMsisdnListRepository.findByMsisdn(filterRequest.getMsisdn());

			logger.info("Update Contact Number Data available in ActiveMsisdnList table ["+filterRequest.getMsisdn()+"] : "+activeMsisdn);

			if(activeMsisdn!=null && stolen!=null){
					ContactNumberChange change=new ContactNumberChange();
					change.setMsisdn(stolen.getContactNumberForOtp());
					stolen.setContactNumberForOtp(filterRequest.getMsisdn());
					stolen.setRemarks(filterRequest.getRemarks());
					stolen.setModifiedOn(LocalDateTime.now());

					try {
						logger.info("Update Contact Number Create New Object for ContactNumberChange");

						change.setCreatedOn(LocalDateTime.now());
						change.setModifiedOn(LocalDateTime.now());

						change.setNewMsisdn(filterRequest.getMsisdn());
						change.setRequestId(filterRequest.getRequestNo());
						change.setOperator("");
						change.setRemarks(filterRequest.getRemarks());
						change.setStatus("SUCCESS");
						contactNumberChangeRepository.save(change);
						logger.info("Update Contact Number History  Successfully : "+stolen);
						changeContactNumberDevicesRepository.save(stolen);

						logger.info("Update Contact Number Successfully : "+stolen);

						GenricResponse response = new GenricResponse(200, "Request Processed Successfully", "", "");
						return new ResponseEntity<>(response, HttpStatus.OK);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						GenricResponse response = new GenricResponse(500, "Something wrong happend", "", "");
						return new ResponseEntity<>(response, HttpStatus.OK);
					}

			}else {
				logger.info("Update Contact Number Data not available in ActiveMsisdnList table ["+filterRequest.getMsisdn()+"] ");
				GenricResponse response=new GenricResponse(500,"New contact number is not valid","",activeMsisdn);
				return  new ResponseEntity<>(response,HttpStatus.OK);
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.info("Exception Contact Number When Check Rquest Number : "+e.getMessage());
			GenricResponse response = new GenricResponse(500, "Something wrong happend for No Data", "", "");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

	}

	public void NotificationAPI(String msisdn,String email, String lang, String tid) {

		NotificationModel notificationModel = new NotificationModel();
		GenricResponse genricResponse = new GenricResponse();

		if(lang==null || lang.isEmpty() || lang.equalsIgnoreCase("")) {
			lang="en";
		}
		String msg="";
		try {
			EirsResponseParam param=eirsResponseParamRepository.findByTagAndLanguage("CHANGE_CONATACT_NUMBER_MSG",lang.toLowerCase());
			if(param!=null) {
				msg=param.getValue().replaceAll("<Request_Id>", tid);
			}
			logger.info("Change Contact Number NotificationAPI() :: get message Details ["+msg+"] msisdn=" + msisdn);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.info("Change Contact Number NotificationAPI() :: Exception "+e.getMessage()+" when get message Details msisdn=" + msisdn);

		}
		notificationModel.setMessage(msg);
		//notificationModel.setFeatureName("Change Contact Number");
		notificationModel.setFeatureName(propertiesReader.stolenFeatureName);
		notificationModel.setSubFeature("Customer Care Request");
		notificationModel.setFeatureTxnId(tid);
		if(msisdn==null || msisdn.equals("") || msisdn.equalsIgnoreCase("null")) {
			notificationModel.setSubject("Request ID "+tid+" :Update Notification for Change Contact Number Request");
			notificationModel.setChannelType("EMAIL");
			notificationModel.setEmail(email);

		}else {
			notificationModel.setChannelType("SMS");
			notificationModel.setMsisdn(msisdn);
		}

		notificationModel.setMsgLang(lang);
		logger.info("Change Contact Number request send to notification API=" + notificationModel);
		genricResponse = notificationFeign.addNotifications(notificationModel);
		logger.info("Change Contact Number Notification API response=" + genricResponse);
	}

}
