package com.gl.mdr.service.impl;

import com.gl.mdr.model.app.DeviceModel;
import com.gl.mdr.repo.app.ModelRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// import com.gl.ceir.config.exceptions.ResourceServicesException;



@Service
public class ModelServiceImpl {
	private static final Logger logger = LogManager.getLogger(ModelServiceImpl.class);

	//private final Path fileStorageLocation;

	@Autowired
	ModelRepository modelRepository;

	public List<DeviceModel> getAll(int brandNameId) {
		try {
			logger.info("Going to get All Model  List for id;; "+ brandNameId);
			return modelRepository.getByBrandNameIdOrderByModelNameAsc(brandNameId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
                        return null;
			//throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}

	}

}
