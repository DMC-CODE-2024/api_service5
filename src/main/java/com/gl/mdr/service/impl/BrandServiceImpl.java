package com.gl.mdr.service.impl;

import com.gl.mdr.model.app.DeviceBrand;
import com.gl.mdr.repo.app.BrandRepository;
import com.gl.mdr.repo.app.DeviceBrandRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Service
public class BrandServiceImpl {

	@Value("${spring.jpa.properties.hibernate.dialect}")
	public String dialect;

	@Value("#{'${Top5Brands}'.split(',')}")
	public List<String> topBrands;


	@Autowired
	private BrandRepository brandRepo;

	private static final Logger logger = LogManager.getLogger(BrandServiceImpl.class);
	
	@Autowired
	DeviceBrandRepository brandRepository;

	
	public List<DeviceBrand> getBrandDetailsListByBrandNames(Set<String> brandNames){
		List<DeviceBrand> brandList = brandRepository.findByBrandNameIn(brandNames);
		return brandList;
	}
	
	public HashMap<String, Integer> getBrandNameIdMap(Set<String> brandNames){
		HashMap<String, Integer> brandNameIdMap = new HashMap<String, Integer>();
		List<DeviceBrand> brandList = null;
		try {
			brandList = this.getBrandDetailsListByBrandNames(brandNames);
			for(DeviceBrand brand: brandList) {
				brandNameIdMap.put(brand.getBrandName(), brand.getId());
			}
		}catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		return brandNameIdMap;
	}
	
	public void updateNewBrands(Set<String> brands) {
		List<DeviceBrand> existingBrands = null;
		List<DeviceBrand> brandList = new ArrayList<DeviceBrand>();
		try {
			existingBrands = this.getBrandDetailsListByBrandNames(brands);
			for(DeviceBrand brand: existingBrands)
				brands.remove(brand.getBrandName());
			for(String brand: brands) {
				brandList.add(new DeviceBrand(brand));
			}
			if(brandList.size() > 0)
				brandRepository.saveAll(brandList);
		}catch(Exception ex) {
			logger.error("Error during updating new brands in the brand table.");
			logger.error(ex.getMessage(), ex);
		}	
	}
	
	public DeviceBrand saveSingleBrand( String brandName ) {
		DeviceBrand brand = brandRepository.save(new DeviceBrand(brandName));
		return brand;
	}
	
	public DeviceBrand getBrandDetails( String brandName ) {
		return brandRepository.findByBrandName(brandName);
	}

	public List<DeviceBrand> getAllBrands() {
		try {
			List<DeviceBrand> list1 = null;

			logger.info("Going for top 5 brands");
			//   List<String> topBrands = propertiesReader.getTop5Brands();
			if (topBrands.size() < 5) {
				logger.error("Brands size is less than 5, Please provide  Top 5 Brand Name in properties via  Top5Brands  :: size " + topBrands.size() + "[]");
			}
			logger.info("Size " + topBrands.size() + " ,List" + topBrands.get(0) + topBrands.get(1) + topBrands.get(2) + topBrands.get(3) + topBrands.get(4));

			if (dialect.contains("Oracle")) {
				list1 = brandRepo.getBrandNameWithTop5NewOracle(topBrands.get(0), topBrands.get(1), topBrands.get(2), topBrands.get(3), topBrands.get(4));
			} else {
				list1 = brandRepo.getBrandNameWithTop5New(topBrands.get(0), topBrands.get(1), topBrands.get(2), topBrands.get(3), topBrands.get(4));
			}
			logger.info("Result  " + list1.size() + "[]" + list1.get(0) + "[]" + list1.get(1) + "[]" + list1.get(4));

			return list1;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
			//   throw new ResourceServicesException("en", e.getMessage());
		}

	}
	//private final Path fileStorageLocation;

}

