package com.gl.mdr.repo.rep;


import com.gl.mdr.model.rep.IosDeviceStoreListingImpressionsByDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IosDeviceStoreListingImpressionsByDeviceRepository extends JpaRepository<IosDeviceStoreListingImpressionsByDevice, Integer> {
}
