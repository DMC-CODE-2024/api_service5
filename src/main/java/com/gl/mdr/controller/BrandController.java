package com.gl.mdr.controller;


//

import com.gl.mdr.service.impl.BrandServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BrandController { //sachin

    
    @Autowired
    BrandServiceImpl brandServiceImpl ;
    
    private static final Logger logger = LogManager.getLogger(BrandController.class);

    @Tag(name = "Brand Name", description = "Device Management")
    @Operation(
            summary = "Fetch all brand",
            description = "Fetches all brand entities from data source")
    @RequestMapping(path = "gsma/brandName", method = RequestMethod.GET)
    public MappingJacksonValue getAllBrands() {
        var getBrands =brandServiceImpl.getAllBrands();
        MappingJacksonValue mapping = new MappingJacksonValue(getBrands);
        return mapping;
    }
}
