package com.gl.mdr.service.impl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.mdr.bulk.imei.entity.BulkIMEIRequest;
import com.gl.mdr.bulk.imei.feign.NotificationFeign;
import com.gl.mdr.configuration.PropertiesReader;
import com.gl.mdr.model.app.ActiveMsisdnList;
import com.gl.mdr.model.app.BulkCheckIMEIMgmt;
import com.gl.mdr.model.app.EirsResponseParam;
import com.gl.mdr.model.app.LostDeviceDetail;
import com.gl.mdr.model.app.NotificationModel;
import com.gl.mdr.model.app.TrackLostDevices;
import com.gl.mdr.model.app.WebActionDb;
import com.gl.mdr.model.audit.AuditTrail;
import com.gl.mdr.model.filter.LostDeviceRequest;
import com.gl.mdr.model.generic.GenricResponse;
import com.gl.mdr.model.oam.RequestHeaders;
import com.gl.mdr.repo.app.ActiveMsisdnListRepository;
import com.gl.mdr.repo.app.BulkIMEIFileUploadRepository;
import com.gl.mdr.repo.app.DeviceBrandRepository;
import com.gl.mdr.repo.app.EirsResponseParamRepository;
import com.gl.mdr.repo.app.LostDeviceDetailRepository;
import com.gl.mdr.repo.app.TrackLostDevicesRepository;
import com.gl.mdr.repo.app.WebActionDbRepository;
import com.gl.mdr.repo.audit.AuditTrailRepository;
import com.gl.mdr.repo.oam.RequestHeadersRepository;
import com.gl.mdr.util.Utility;



@Service
public class BulkIMEIFileUploadServiceImpl {
	
	private static final Logger logger = LogManager.getLogger(BulkIMEIFileUploadServiceImpl.class);
	
	@Autowired
	DeviceBrandRepository brandRepository;
	
	@Autowired
	BulkIMEIFileUploadRepository bulkIMEIFileUploadRepository;
	
	@Autowired
	WebActionDbRepository webActionDbRepository;
	
	@Autowired
	TrackLostDevicesRepository trackLostDevicesRepository;
	
	@Autowired
	LostDeviceDetailRepository lostDeviceDetailRepository;
	
	@Autowired
	ActiveMsisdnListRepository activeMsisdnListRepository;
	
	@Autowired
	NotificationFeign notificationFeign;
	
	@Autowired
	PropertiesReader propertiesReader;
	
	@Autowired
	AuditTrailRepository auditTrailRepository;
	
	@Autowired
	RequestHeadersRepository requestHeadersRepository;
	
	@Autowired
	EirsResponseParamRepository eirsResponseParamRepository;
	
	
	@Transactional
	public GenricResponse uploadDetails(BulkIMEIRequest bulkIMEIRequest) {
		logger.info("First Request come : Request --->>>  "+bulkIMEIRequest.toString());
		try {
			
			long count=bulkIMEIFileUploadRepository.findCount(bulkIMEIRequest.getContactNumber());
			
			logger.info("Total  Request come : Request --->>>  "+count);
			if(count>5) {
				
				return new GenricResponse(201, "You have reached the limit.",bulkIMEIRequest.getTransactionId() );
			}
			
			
			BulkCheckIMEIMgmt webActionDb = new BulkCheckIMEIMgmt();
			webActionDb.setContactNumber(bulkIMEIRequest.getContactNumber());
			webActionDb.setEmail(bulkIMEIRequest.getEmail());
			webActionDb.setFileName(bulkIMEIRequest.getFileName());
			webActionDb.setOtp(Utility.getOtp(6));
			webActionDb.setTxnId(bulkIMEIRequest.getTransactionId());
			webActionDb.setCreatedOn(LocalDateTime.now());
			webActionDb.setModifiedOn(LocalDateTime.now());
			webActionDb.setStatus("");
//			if(bulkIMEIRequest.getLang().equalsIgnoreCase("en")) {
//				webActionDb.setLanguage("english");
//			}else {
//				webActionDb.setLanguage("khmer");
//			}
			
			webActionDb.setLanguage(bulkIMEIRequest.getLang());
			
			logger.info("Set Language Request come : Request --->>>  "+bulkIMEIRequest.getLang());
			BulkCheckIMEIMgmt resp=bulkIMEIFileUploadRepository.save(webActionDb);
			
			logger.info("Save Request come : Request --->>>  "+webActionDb.toString());
			
			NotificationAPI(resp.getContactNumber(),"cambodian",resp.getOtp()+"",bulkIMEIRequest.getLang(),bulkIMEIRequest.getTransactionId());
			
			audiTrail(bulkIMEIRequest.auditTrailModel.getPublicIp(), bulkIMEIRequest.auditTrailModel.getBrowser(), bulkIMEIRequest.getTransactionId(), "New File",bulkIMEIRequest.auditTrailModel.getBrowser());
			
			return new GenricResponse(200, "Upload Successfully.",bulkIMEIRequest.getTransactionId() );
			
		}catch (Exception e) {
			logger.error("Request validation failed for txnId[" + bulkIMEIRequest.getTransactionId() + "]" + e.getMessage());
			e.printStackTrace();
			return new GenricResponse(201, "Upload Failed.",bulkIMEIRequest.getTransactionId() );
		}
	}
	public long getDayWiseCount(BulkIMEIRequest bulkIMEIRequest) {
		try {
			audiTrail(bulkIMEIRequest.auditTrailModel.getPublicIp(), bulkIMEIRequest.auditTrailModel.getBrowser(), bulkIMEIRequest.getTransactionId(), "day wise file upload",bulkIMEIRequest.auditTrailModel.getBrowser());
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return bulkIMEIFileUploadRepository.findCount(bulkIMEIRequest.getContactNumber()) ;
	}
	
	@Transactional
	public GenricResponse verifyOTP(String requestID, String otp, String lang, String ip, String browser,String userAgent) {
		
		GenricResponse genricResponse= new GenricResponse();
		
		logger.info("inside Otp verification block , request Id ="+requestID+" , OTP is ="+otp);
		
		BulkCheckIMEIMgmt res=bulkIMEIFileUploadRepository.findByTxnId(requestID);
		
		
		
		logger.info(" response from DB="+res);
		if(res!=null) {
			
			Instant instant = res.getModifiedOn().atZone(ZoneId.systemDefault()).toInstant();
			
			Date date = Date.from(instant);
			final int diffMinutes = Utility.getNumOfMin(date, new Date());
			
			logger.info("diffMinutes  Request come : Request --->>>  "+diffMinutes+ " < OTP_EXPIRE_TIME="+propertiesReader.OTP_EXPIRE_TIME);
			
			if (diffMinutes < propertiesReader.OTP_EXPIRE_TIME ) {
				if(res.getOtp().equals(otp)) {
					logger.info("verification successfull");
					res.setStatus("INIT");
					bulkIMEIFileUploadRepository.save(res);
					genricResponse.setErrorCode(200);
					genricResponse.setMessage("verification successfull");
					genricResponse.setTxnId(requestID);
					try {
						WebActionDb webDB=new WebActionDb();
						webDB.setCreatedOn(LocalDateTime.now());
						webDB.setRetry_count(0);
						webDB.setModifiedOn(LocalDateTime.now());
						webDB.setTxnId(requestID);
						webDB.setFeature("BulkIMEICheck");
						webDB.setSub_feature("CHECK_IMEI");
						webDB.setState(1);
						webActionDbRepository.save(webDB);
						
					} catch (Exception e) {
						// TODO: handle exception
					}
					audiTrail(ip, browser, requestID, "Verify OTP",userAgent);
					
				}
				else {
					logger.info("verification failed");
					genricResponse.setErrorCode(201);
					genricResponse.setMessage("verification failed");
					genricResponse.setTxnId(requestID);
				}
			}else {
				logger.info("verification failed because expired");
				genricResponse.setErrorCode(410);
				genricResponse.setMessage("verification expired");
				genricResponse.setTxnId(requestID);
			}
			
			
		}else {
			logger.info("verification failed");
			genricResponse.setErrorCode(201);
			genricResponse.setMessage("verification failed");
			genricResponse.setTxnId(requestID);
		}
		
		
		return genricResponse;
		
	}
	public GenricResponse resendOTP(String requestID, String lang,  String ip, String browser,String userAgent) {
		logger.info("inside resend OTP=" + requestID);

		BulkCheckIMEIMgmt res=bulkIMEIFileUploadRepository.findByTxnId(requestID);
		GenricResponse genricResponse = new GenricResponse();
		String OTPsms = Utility.getOtp(6);
		res.setOtp(OTPsms);
		logger.info("request to update  resend OTP=" + res);
		bulkIMEIFileUploadRepository.save(res);// updating OTP in bulk_check_IMEI_mgmt table
		
		NotificationAPI(res.getContactNumber(),"cambodian",res.getOtp()+"",lang,res.getTxnId());// calling notification API
		genricResponse.setErrorCode(200);
		genricResponse.setMessage("Resend OTP is succesfull");
		audiTrail(ip, browser, requestID, "Resend OTP",browser);
		return genricResponse;
	}
	public GenricResponse getFileStatus(String requestID, String lang, String ip, String browser,String userAgent) {
		
		logger.info("getFileStatus() :: Request Id=" + requestID);

		BulkCheckIMEIMgmt res=bulkIMEIFileUploadRepository.findByTxnId(requestID);
		GenricResponse genricResponse = new GenricResponse();
		try {
			if(res!=null) {
				genricResponse.setErrorCode(200);
				genricResponse.setMessage("File Status");
				genricResponse.setData(res.getStatus());
				genricResponse.setTxnId(res.getTxnId());
				genricResponse.setData(res);
				audiTrail(ip, browser, requestID, "File Status",userAgent);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("Exception getFileStatus() :: Request Id=" + requestID);
			genricResponse.setData(res);
			e.printStackTrace();
		}
		logger.info("getFileStatus() :: Response =" + genricResponse.toString());
		return genricResponse;
	}
	public void NotificationAPI(String msisdn, String nationality, String otp, String lang, String tid) {
		
		NotificationModel notificationModel = new NotificationModel();
		GenricResponse genricResponse = new GenricResponse();
		
		if(lang==null || lang.isEmpty() || lang.equalsIgnoreCase("")) {
			lang="en";
		}
		String msg="";
		try {
				EirsResponseParam param=eirsResponseParamRepository.findByTagAndLanguage("BULK_IMEI_OTP_SMG",lang.toLowerCase());
				if(param!=null) {
					msg=param.getValue().replaceAll("<OTP>", otp);
				}
				logger.info("NotificationAPI() :: get message Details ["+msg+"] msisdn=" + msisdn);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			msg="<OTP> is your OTP to upload the bulk check IMEI file.\n Never share OTP with anyone.".replaceAll("<OTP>", otp);
			logger.info("NotificationAPI() :: Exception "+e.getMessage()+" when get message Details msisdn=" + msisdn);
			
			
		}
		
		notificationModel.setMessage(msg);
		notificationModel.setFeatureName("BULK IMEI");
		notificationModel.setSubFeature("Bulk IMEI OTP Verify");
		notificationModel.setFeatureTxnId(tid);
		
		if (nationality.equalsIgnoreCase("cambodian")) {
			notificationModel.setChannelType("SMS_OTP");
			notificationModel.setMsisdn(msisdn);
		}
		else {
			notificationModel.setChannelType("EMAIL");
			notificationModel.setEmail(otp);
		}
		notificationModel.setMsgLang(lang);
		logger.info("request send to notification API=" + notificationModel);
		genricResponse = notificationFeign.addNotifications(notificationModel);
		logger.info("Notification API response=" + genricResponse);
	}
	public GenricResponse setTrackLostDevices(LostDeviceRequest lostDeviceRequest , String operator, HttpServletRequest request) {
		// TODO Auto-generated method stub
		GenricResponse genricResponse = new GenricResponse();
		String remoteIp=request.getRemoteAddr();
		String whilteListIP=propertiesReader.WHITE_LIST_IP;
		boolean checkIpStatus=propertiesReader.WHITE_LIST_IP_STATUS;
		
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
			if(imei.length()>14) {
				String  imei_14_digit= imei.substring(0, Math.min(imei.length(), 14));
				
				logger.info("First 14 Digit IMEI = " +imei_14_digit +" and Request IMEI = "+lostDeviceRequest.getImei());
				
				LostDeviceDetail lostDeviceDetail= lostDeviceDetailRepository.findByImei(imei);
				
				if(lostDeviceDetail !=null) {
					
					trackLostDevices.setRequest_id(lostDeviceDetail.getRequestId());
					try {
						trackLostDevices.setRequestType(lostDeviceDetail.getRequestType());
					} catch (Exception e) {
						// TODO: handle exception
						trackLostDevices.setRequestType("");
						logger.info("Exception When Set Request type = " +lostDeviceDetail.getRequestType());
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
			return genricResponse;
		}
		
		
	}
	public void audiTrail(String ip, String browser, String txnId, String subFeature,String userAgent)
	{
		//New File
		try {
			AuditTrail auditTrail = new AuditTrail();
			auditTrail.setFeatureName("Bulk Upload Portal");
			auditTrail.setSubFeature(subFeature);
			auditTrail.setFeatureId(1000001);
			auditTrail.setPublicIp(ip);
			auditTrail.setBrowser(getBrowser(browser));
			auditTrail.setUserId(1000001);
			auditTrail.setUserName("Public Portal");
			auditTrail.setUserType( "NA" );
			auditTrail.setRoleType( "NA" );
			auditTrail.setTxnId(txnId);
			auditTrailRepository.save(auditTrail);
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			RequestHeaders header=new RequestHeaders();
			header.setBrowser(getBrowser(browser));
			header.setPublicIp(ip);
			header.setUserAgent(userAgent);
			header.setUsername("Public Portal");
			header.setCreatedOn(LocalDateTime.now());
			header.setModifiedOn(LocalDateTime.now());
			requestHeadersRepository.save(header);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	public static String getBrowser(String userAgent) {
        String browser = "";
        String version = "";
        Integer startLen = 0;
        Integer endLen = 0;
        if (userAgent.toLowerCase().indexOf("msie") != -1) {
            browser = "IE";
            startLen = userAgent.toLowerCase().indexOf("msie");
            endLen = userAgent.indexOf(";", startLen);
            version = userAgent.substring(startLen + 5, endLen);
        } else if (userAgent.toLowerCase().indexOf("trident/7") != -1) {
            browser = "IE";
            startLen = userAgent.toLowerCase().indexOf("rv:") + 3;
            endLen = userAgent.indexOf(")", startLen);
            version = userAgent.substring(startLen, endLen);
        } else if (userAgent.toLowerCase().indexOf("chrome") != -1) {
            browser = "CHROME";
            startLen = userAgent.toLowerCase().indexOf("chrome") + 7;
            endLen = userAgent.indexOf(" ", startLen);
            version = userAgent.substring(startLen, endLen);
        } else if (userAgent.toLowerCase().indexOf("firefox") != -1) {
            browser = "FIREFOX";
            startLen = userAgent.toLowerCase().indexOf("firefox") + 8;
            endLen = userAgent.length();
            version = userAgent.substring(startLen, endLen);

        } else if (userAgent.toLowerCase().indexOf("safari") != -1) {
            browser = "SAFARI";
            startLen = userAgent.toLowerCase().indexOf("version") + 8;
            endLen = userAgent.indexOf(" ", startLen);
            version = userAgent.substring(startLen, endLen);
        } else if (userAgent.toLowerCase().indexOf("opera") != -1) {
            browser = "OPERA";
            startLen = userAgent.toLowerCase().indexOf("opera") + 6;
            endLen = userAgent.length();
            version = userAgent.substring(startLen, endLen);
        } else {
            browser = "OTHER";
        }

        return browser + "_" + version;

    }

}
