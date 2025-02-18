package com.gl.mdr.model.constants;

public interface Tags {
	String CUSTOMS_TAX_STATUS = "CUSTOMS_TAX_STATUS";
	String SOURCE_TYPE = "SOURCE_TYPE";
	String REQ_TYPE = "REQ_TYPE";
	String OPERATOR = "OPERATOR";
	String DEVICE_ID_TYPE = "DEVICE_ID_TYPE";
	String DEVICE_TYPE = "DEVICE_TYPE";
	String CURRENCY = "CURRENCY";
	String DEVICE_STATUS = "DEVICE_STATUS";
	String CONFIG_TYPE = "CONFIG_TYPE";
	String CHANNEL = "CHANNEL";
	String IS_ACTIVE = "IS_ACTIVE";
	String APPROVED = "Approved";
	String REGECTED = "Rejected";
	String Pending_From_CEIR_Authority = "Pending Approval From CEIR Admin";
	String Withdrawn_By_User = "Withdrawn By User";
	String Withdrawn_By_CEIR_Admin = "Withdrawn By CEIR Admin";
	String Rejected_By_System = "Rejected By System";
	String Approved_By_CEIR_Admin = "Approved By CEIR Admin";
	String Rejected_By_CEIR_Admin = "Rejected By CEIR Admin";
	String NEW = "New";
	String COMPLETED = "Completed";
	String Pending_With_Admin = "Pending With Admin";
	String Pending_With_User = "Pending With User";
	String CLOSED = "Closed";
	String Processing = "Processing";
	String DELETED = "Deleted";
	String DELETE_FLAG = "DELETE_FLAG";
	String UPDATED = "Updated";
	String black_list="Black List";
	String grey_list="Grey List";
	String exception_list="Exception List";
	String gdce_data="Custom";
	String trc_local_manufactured_device_data="Local Manufacturer";
	String national_whitelist="National White List";
	String active_imei_with_different_msisdn="Duplicate";
	String active_imei_with_different_imsi="EDR_Duplicate";
	String imei_pair_detail="Pairing";
	String grey_list_his="Grey List History";
	String black_list_his="Black List History";
	String imei_pair_detail_his="Pairing History";
	String eirs_invalid_imei="Invalid IMEI";
	String duplicate_device_detail="Duplicate Device Detail";
	String lost_device_detail="Stolen";
	String lost_device_detail_his="Stolen History";
	String duplicate_device_detail_his="Duplicate Device Detail History";
	String exception_list_his="Exception List History";
	String lost_device_mgmt_status_init="INIT_START";
	String lost_device_mgmt_request_type_recover="Recover";
	String lost_device_mgmt_status_start="START";
}
