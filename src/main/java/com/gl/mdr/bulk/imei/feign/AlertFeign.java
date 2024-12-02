package com.gl.mdr.bulk.imei.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.gl.mdr.model.app.AlertDto;


@Service
@FeignClient(url = "${eirs.alert.url}", value = "dsj" )
public interface AlertFeign {

	@PostMapping(value="")
	public com.gl.mdr.model.generic.GenricResponse raiseAnAlert(@RequestBody AlertDto alertDto) ;
	
}