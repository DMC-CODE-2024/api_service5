package com.gl.mdr.bulk.imei.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.gl.mdr.model.app.NotificationModel;


@Service
@FeignClient(url = "${NotificationAPI}", value = "dsj" )
public interface NotificationFeign {

	@PostMapping(value="/addNotifications")
	public com.gl.mdr.model.generic.GenricResponse addNotifications(@RequestBody NotificationModel notificationModel) ;
	
}