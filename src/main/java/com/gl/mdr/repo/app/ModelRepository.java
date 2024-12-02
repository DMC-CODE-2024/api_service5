package com.gl.mdr.repo.app;

import com.gl.mdr.model.app.DeviceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;







public  interface ModelRepository extends JpaRepository<DeviceModel, Long>, JpaSpecificationExecutor<DeviceModel>  {


	public List<DeviceModel> getByBrandNameIdOrderByModelNameAsc(int brandNameId);
     
}
