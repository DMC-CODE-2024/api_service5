package com.gl.mdr.controller;

import com.gl.mdr.model.app.DeviceModel;
import com.gl.mdr.service.impl.ModelServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


//import io.swagger.annotations.ApiOperation;

@RestController
public class modelController {  ////sachin

     private static final Logger logger = LogManager.getLogger(modelController.class);

     @Autowired
     ModelServiceImpl modelServiceImpl;

     //@ApiOperation(value = "View All list of Mdels of Brands", response = modelRepoPojo.class)
     @RequestMapping(path = "gsma/modelName", method = RequestMethod.GET)
     public MappingJacksonValue getAllModels(@RequestParam("brand_id") int brand_id) {
         logger.info("`Request TO view TO all record of user={}", brand_id);
          List<DeviceModel> getmodels = modelServiceImpl.getAll(brand_id);
          MappingJacksonValue mapping = new MappingJacksonValue(getmodels);
         logger.info("Response of View ={}for id {}", mapping.toString(), brand_id);
          return mapping;
     }
}
