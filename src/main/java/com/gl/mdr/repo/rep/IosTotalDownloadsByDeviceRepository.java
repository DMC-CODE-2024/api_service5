package com.gl.mdr.repo.rep;


import com.gl.mdr.model.rep.IosTotalDownloadsByDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IosTotalDownloadsByDeviceRepository extends JpaRepository<IosTotalDownloadsByDevice, Integer> {
}
