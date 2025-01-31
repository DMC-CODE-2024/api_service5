package com.gl.mdr.repo.rep;


import com.gl.mdr.model.rep.IosActiveDevicesByAppVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IosActiveDevicesByAppVersionRepository extends JpaRepository<IosActiveDevicesByAppVersion, Integer> {
}

