package com.gl.mdr.repo.rep;


import com.gl.mdr.model.rep.IosTotalInstallationsByDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IosTotalInstallationsByDeviceRepository extends JpaRepository<IosTotalInstallationsByDevice, Integer> {
}
